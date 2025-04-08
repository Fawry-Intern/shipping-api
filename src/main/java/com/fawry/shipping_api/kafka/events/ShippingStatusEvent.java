package com.fawry.shipping_api.kafka.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fawry.shipping_api.enums.ShippingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ShippingStatusEvent extends ShippingBaseEvent {

    private final String trackingLink;
    private final String confirmationCode;
    private final String email;

    public ShippingStatusEvent(@JsonProperty("email") String email,
                               @JsonProperty("trackingLink") String trackingLink,
                               @JsonProperty("confirmationCode")String confirmationCode,
                               @JsonProperty("orderId") Long orderId,
                               @JsonProperty("shippingStatus") ShippingStatus shippingStatus) {
        super(orderId,shippingStatus);
        this.trackingLink = trackingLink;
        this.confirmationCode = confirmationCode;
        this.email = email;
    }


}
