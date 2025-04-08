package com.fawry.shipping_api.kafka.producer;

import com.fawry.shipping_api.kafka.events.ShippingBaseEvent;
import com.fawry.shipping_api.kafka.events.ShippingDetailsEvent;
import com.fawry.shipping_api.kafka.events.ShippingStatusEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
@Service
public class ShippingProducer<T extends ShippingBaseEvent> {
    private final Logger log = LoggerFactory.getLogger(ShippingProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String SHIPPING_STATUS_TOPIC = "shipping-status-events";
    private static final String SHIPPING_DETAILS_TOPIC = "shipping-details-events";

    public ShippingProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendShippingStatusEvent(ShippingStatusEvent event, int partition) {
        sendEvent(event, SHIPPING_STATUS_TOPIC, partition);
    }

    public void sendShippingDetailsEvent(ShippingDetailsEvent event, int partition) {
        sendEvent(event, SHIPPING_DETAILS_TOPIC, partition);
    }

    private <T extends ShippingBaseEvent> void sendEvent(T event, String topic, int partition) {
        Message<T> message =
                MessageBuilder.withPayload(event)
                        .setHeader(KafkaHeaders.TOPIC, topic)
                        .setHeader(KafkaHeaders.PARTITION, partition)
                        .build();
        log.info("Sending event to topic {} to partition {}", event, partition);
        kafkaTemplate.send(message);
    }
}
