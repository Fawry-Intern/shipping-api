package com.fawry.shipping_api.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic shippingStatusTopic() {
        return TopicBuilder
                .name("shipping-status-events")
                .partitions(2)
                .replicas((short) 1)
                .build();
    }

    @Bean
    public NewTopic shippingDetailsTopic() {
        return TopicBuilder
                .name("shipping-details-events")
                .partitions(2)
                .replicas((short) 1)
                .build();
    }
}
