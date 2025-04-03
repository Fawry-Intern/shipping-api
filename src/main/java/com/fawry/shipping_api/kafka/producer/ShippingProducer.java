package com.fawry.shipping_api.kafka.producer;

import com.fawry.shipping_api.kafka.events.ShippingBaseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ShippingProducer <T extends ShippingBaseEvent> {

    private final Logger log = LoggerFactory.getLogger(ShippingProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC_NAME = "shipping-events";

    public ShippingProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(T event, int partition) {
        Message<T> message =
                MessageBuilder.withPayload(event)
                        .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME)
                        .setHeader(KafkaHeaders.PARTITION, partition)
                        .build();
        log.info("send event to topic {} to partition {}", event, partition);
        kafkaTemplate.send(message);
    }
}
