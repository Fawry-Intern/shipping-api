package com.fawry.shipping_api.kafka.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fawry.shipping_api.dto.customer.CustomerDetails;
import com.fawry.shipping_api.dto.delivery.DeliveryPersonDetails;
import com.fawry.shipping_api.entity.DeliveryPerson;
import com.fawry.shipping_api.enums.ShippingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
public class ShippingDetailsEvent extends ShippingBaseEvent {

    private final CustomerDetails customerDetails;
    private final Long shipmentId;
    private final DeliveryPersonDetails deliveryPerson;
    private final LocalDateTime deliveredAt;
    private  final LocalDateTime expectedDeliveryDate;

    public ShippingDetailsEvent(@JsonProperty("orderId") Long orderId,
                                @JsonProperty("status") ShippingStatus shippingStatus,
                                @JsonProperty("customerDetails") CustomerDetails customerDetails,
                                @JsonProperty("shipmentId") Long shipmentId,
                                @JsonProperty("deliveryPersonDetails") DeliveryPersonDetails deliveryPersonDetails,
                                @JsonProperty("deliveredAt") LocalDateTime deliveredAt,
                                @JsonProperty("expectedDeliveryDate") LocalDateTime expectedDeliveryDate) {
        super(orderId, shippingStatus);
        this.customerDetails = customerDetails;
        this.shipmentId = shipmentId;
        this.deliveryPerson = deliveryPersonDetails;
        this.deliveredAt = deliveredAt;
        this.expectedDeliveryDate = expectedDeliveryDate;
    }
}


