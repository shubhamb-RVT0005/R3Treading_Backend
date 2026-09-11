//
//package com.r3vtech.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestClient;
//
//@Configuration
//public class RestClientConfig {
//
//    @Bean
//    public RestClient restClient() {
//        return RestClient.builder().build();
//    }
//
//    @Bean
//    @LoadBalanced
//    @Qualifier("notificationRestClientBuilder")
//    public RestClient.Builder notificationRestClientBuilder() {
//        return RestClient.builder();
//    }
//}


//package com.r3vtech.config;
//

//package com.r3vtech.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestClient;
//
//@Configuration
//public class RestClientConfig {
//
//    @Bean
//    public RestClient restClient() {
//        return RestClient.builder().build();
//    }
//}
//```java
package com.r3vtech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }
}

