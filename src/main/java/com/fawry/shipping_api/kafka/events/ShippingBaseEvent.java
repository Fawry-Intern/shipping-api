package com.fawry.shipping_api.kafka.events;

import com.fawry.shipping_api.enums.ShippingStatus;

public abstract class ShippingBaseEvent {
    private final String email;
    private final Long orderId;
    private final ShippingStatus shippingStatus;
    public ShippingBaseEvent(String email, Long orderId, ShippingStatus shippingStatus) {
        this.email = email;
        this.orderId = orderId;
        this.shippingStatus = shippingStatus;
    }

    public String getEmail() {
        return email;
    }


    public Long getOrderId() {
        return orderId;
    }

    public ShippingStatus getShippingStatus() {
        return shippingStatus;
    }
}
