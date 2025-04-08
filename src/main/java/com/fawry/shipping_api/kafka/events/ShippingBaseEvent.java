package com.fawry.shipping_api.kafka.events;

import com.fawry.shipping_api.enums.ShippingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class ShippingBaseEvent {

    private final Long orderId;
    private final ShippingStatus shippingStatus;
    public ShippingBaseEvent( Long orderId, ShippingStatus shippingStatus) {

        this.orderId = orderId;
        this.shippingStatus = shippingStatus;
    }



}
