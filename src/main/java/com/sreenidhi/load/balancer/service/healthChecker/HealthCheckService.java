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
    int count = 0;
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
                    count = count + 1;
                    server.setHealthy(true);
                })
                .doOnError(error -> {
                    server.setHealthy(false);
                })
                .subscribe();


    }


    @Scheduled(fixedRate = 5000)
    public void checkServers() {

        serviceRegistry.getAllServers()
                .values()
                .forEach(this::checkServer);


    }






}


