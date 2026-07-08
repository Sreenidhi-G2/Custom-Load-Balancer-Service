package  com.sreenidhi.load.balancer.service.healthChecker;
import com.sreenidhi.load.balancer.service.registry.ServiceRegistry;
import com.sreenidhi.load.balancer.service.model.BackendServer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;

@Component
public class HealthCheckService {

    private final ServiceRegistry serviceRegistry;
    private final WebClient webClient;
    public HealthCheckService(ServiceRegistry serviceRegistry , WebClient webClient) {
        this.serviceRegistry = serviceRegistry;
        this.webClient = webClient;
    }

    private void checkServer(BackendServer server) {

        webClient.get()
                .uri(server.getBaseUrl() + "/api/test")
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(2))
                .doOnSuccess(response -> {

                    if (!server.isHealthy()) {
                        server.setHealthy(true);
                        serviceRegistry.publishRegistryChangedEvent();

                    }

                })
                .doOnError(error -> {
                System.out.println("Entered DO on error");
                    if (server.isHealthy()) {
                        server.setHealthy(false);
                        serviceRegistry.publishRegistryChangedEvent();
                    }

                }).subscribe(
                        v -> {},
                        err -> System.out.println("Unhandled error in health check pipeline: " + err)
                );

    }


    @Scheduled(fixedRate = 5000)
    public void checkServers() {



        serviceRegistry.getAllServers()
                .values()
                .forEach(this::checkServer);


    }






}


