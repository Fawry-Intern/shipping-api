package com.fawry.shipping_api.dto.shipment;

public record ConfirmShipment(
        Long shipmentId,
        String confirmationCode
) {
}
