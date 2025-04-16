package com.fawry.shipping_api.kafka.producer;

import com.fawry.shipping_api.kafka.events.OrderCanceledEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingCancellationPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrderCanceledEvent(OrderCanceledEventDTO canceledEvent) {
        Message<OrderCanceledEventDTO> message =
                MessageBuilder
                        .withPayload(canceledEvent)
                        .setHeader(TOPIC, "payment-canceled-events")
                        .build();
        kafkaTemplate.send(message);
        log.info("Payment cancellation order successfully {}", canceledEvent);
    }
}
