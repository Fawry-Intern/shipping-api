package com.fawry.shipping_api.dto.shipment;

import com.fawry.shipping_api.dto.customer.CustomerDetails;
import lombok.Builder;

@Builder(toBuilder = true)
public record CreateShipment(
        Long orderId,
        CustomerDetails customerDetails
) {}
