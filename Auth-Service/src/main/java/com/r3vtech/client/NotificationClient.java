
package com.r3vtech.client;

import java.net.URI;
import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.r3vtech.entityDTO.EmailRequest;

@Component
public class NotificationClient {

    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

    public NotificationClient(
            RestClient restClient,
            DiscoveryClient discoveryClient) {

        this.restClient = restClient;
        this.discoveryClient = discoveryClient;
    }

    public void sendEmail(
            String to,
            String subject,
            String body) {

        // =====================================================
        // 1. Find Notification-Service from Eureka
        // =====================================================

        List<ServiceInstance> instances =
                discoveryClient.getInstances("notification-service");

        if (instances == null || instances.isEmpty()) {

            throw new RuntimeException(
                    "Notification-Service is not available in Eureka"
            );
        }

        // =====================================================
        // 2. Get available Notification-Service instance
        // =====================================================

        ServiceInstance instance = instances.get(0);

        URI serviceUri = instance.getUri();

        System.out.println("=================================");
        System.out.println("NOTIFICATION SERVICE CALL");
        System.out.println("Service : " + serviceUri);
        System.out.println("To      : " + to);
        System.out.println("Subject : " + subject);
        System.out.println("Message : " + body);
        System.out.println("=================================");

        // =====================================================
        // 3. Create Email Request
        // =====================================================

        EmailRequest request = new EmailRequest();

        request.setTo(to);
        request.setSubject(subject);

        // IMPORTANT:
        // Notification-Service expects "message"
        request.setMessage(body);

        // =====================================================
        // 4. Call Notification-Service
        // =====================================================

        restClient.post()
                .uri(serviceUri + "/notification/email")
                .body(request)
                .retrieve()
                .toBodilessEntity();

        System.out.println(
                "Notification-Service email request completed"
        );
    }
}

