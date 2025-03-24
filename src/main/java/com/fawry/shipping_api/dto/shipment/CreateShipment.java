package com.fawry.shipping_api.dto.shipment;

import com.fawry.shipping_api.dto.customer.CustomerDetails;

public record CreateShipment(
        Long orderId,
        CustomerDetails customerDetails
) {}
