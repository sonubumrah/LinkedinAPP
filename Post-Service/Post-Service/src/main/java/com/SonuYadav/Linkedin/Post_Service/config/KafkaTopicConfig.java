package com.SonuYadav.Linkedin.Post_Service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic createPostTopic() {
        return new NewTopic("create-post-topic", 3, (short) 1);
    }
    @Bean
    public NewTopic likePostTopic() {
        return new NewTopic("like-post-topic", 3, (short) 1);
    }
}
