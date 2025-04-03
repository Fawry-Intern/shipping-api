package com.fawry.shipping_api.kafka.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fawry.shipping_api.enums.ShippingStatus;

public class ShippingStatusEvent extends ShippingBaseEvent {

    private final String trackingLink;
    private final String confirmationCode;

    public ShippingStatusEvent(@JsonProperty("email") String email,
                               @JsonProperty("trackingLink") String trackingLink,
                               @JsonProperty("confirmationCode")String confirmationCode,
                               @JsonProperty("orderId") Long orderId,
                               @JsonProperty("shippingStatus") ShippingStatus shippingStatus) {
        super(email,orderId,shippingStatus);
        this.trackingLink = trackingLink;
        this.confirmationCode = confirmationCode;

    }

    public String getTrackingLink() {
        return trackingLink;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

}
