package com.SonuYadav.Linkedin.connection_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic createConnectionRequestTopic() {
        return new NewTopic("create-connection-request-topic", 3, (short) 1);
    }
    @Bean
    public NewTopic acceptConnectionRequestTopic() {
        return new NewTopic("accept-connection-request-topic", 3, (short) 1);
    }
    @Bean
    public NewTopic rejectConnectionRequestTopic() {
        return new NewTopic("reject-connection-request-topic", 3, (short) 1);
    }
}
