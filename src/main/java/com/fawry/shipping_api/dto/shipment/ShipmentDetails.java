package com.fawry.shipping_api.dto.shipment;

import com.fawry.shipping_api.dto.customer.CustomerDetails;
import com.fawry.shipping_api.dto.delivery.DeliveryPersonDetails;
import com.fawry.shipping_api.enums.ShippingStatus;
import lombok.Builder;

@Builder
public record ShipmentDetails(
        Long shipmentId,
        Long orderId,
        CustomerDetails customerDetails,
        DeliveryPersonDetails deliveryPerson,
        ShippingStatus status
){
}
