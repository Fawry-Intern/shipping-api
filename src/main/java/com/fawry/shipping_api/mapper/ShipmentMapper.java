package com.fawry.shipping_api.mapper;

import com.fawry.shipping_api.dto.customer.CustomerDetails;
import com.fawry.shipping_api.dto.shipment.CreateShipment;
import com.fawry.shipping_api.dto.shipment.ShipmentDetails;
import com.fawry.shipping_api.dto.shipment.ShipmentTracking;
import com.fawry.shipping_api.entity.Shipment;
import com.fawry.shipping_api.kafka.events.PaymentCreatedEventDTO;
import com.fawry.shipping_api.kafka.events.ShippingDetailsEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShipmentMapper {
    CustomerMapper customerMapper;
    DeliveryMapper deliveryMapper;
    public ShipmentDetails toShipmentDetails(Shipment shipment) {
        return ShipmentDetails.builder()
                .shipmentId(shipment.getShipmentId())
                .orderId(shipment.getOrderId())
                .customerDetails(customerMapper.toResponse(shipment.getCustomer()))
                .deliveryPerson(shipment.getDeliveryPerson() != null ? deliveryMapper.toResponse(shipment.getDeliveryPerson()) : null)
                .status(shipment.getStatus())
                .deliveredAt(shipment.getDeliveredAt())
                .expectedDeliveryDate(shipment.getExpectedDeliveryDate())
                .build();
    }
    public ShippingDetailsEvent toShipmentDetailsEvent(Shipment shipment) {
        return ShippingDetailsEvent.builder()
                .shipmentId(shipment.getShipmentId())
                .orderId(shipment.getOrderId())
                .customerDetails(customerMapper.toResponse(shipment.getCustomer()))
                .deliveryPerson(shipment.getDeliveryPerson() != null ? deliveryMapper.toResponse(shipment.getDeliveryPerson()) : null)
                .shippingStatus(shipment.getStatus())
                .deliveredAt(shipment.getDeliveredAt())
                .expectedDeliveryDate(shipment.getExpectedDeliveryDate())
                .build();
    }

    public ShipmentTracking toShipmentTracking(Shipment shipment) {
        return ShipmentTracking.builder()
                .shipmentId(shipment.getShipmentId())
                .orderId(shipment.getOrderId())
                .deliveryPersonName(shipment.getDeliveryPerson()!=null ? shipment.getDeliveryPerson().getName():null)
                .status(shipment.getStatus())
                .expectedDeliveryDate(shipment.getExpectedDeliveryDate())
                .build();
    }

    public CreateShipment toShippingOrderDetails(PaymentCreatedEventDTO paymentCreatedEventDTO) {
        return CreateShipment
                .builder()
                .orderId(paymentCreatedEventDTO.getOrderId())
                .customerDetails(
                        CustomerDetails
                                .builder()
                                .governorate(paymentCreatedEventDTO.getAddressDetails().getGovernorate())
                                .city(paymentCreatedEventDTO.getAddressDetails().getCity())
                                .address(paymentCreatedEventDTO.getAddressDetails().getAddress())
                                .name(paymentCreatedEventDTO.getCustomerName())
                                .email(paymentCreatedEventDTO.getCustomerEmail())
                                .phone(paymentCreatedEventDTO.getCustomerContact())
                                .build()
                )
                .build();
    }


}
