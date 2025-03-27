CREATE TABLE shipments (
                           shipment_id BIGSERIAL PRIMARY KEY,
                           order_id BIGINT NOT NULL UNIQUE,
                           customer_id BIGINT NOT NULL,
                           delivery_person_id BIGINT,
                           tracking_token VARCHAR(255)  ,
                           confirmation_code VARCHAR(255) UNIQUE ,
                           status VARCHAR(20) NOT NULL,
                           expected_delivery_date TIMESTAMP WITH TIME ZONE,
                           delivered_at TIMESTAMP WITH TIME ZONE,
                           created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
                           CONSTRAINT fk_shipments_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
                           CONSTRAINT fk_shipments_delivery_person FOREIGN KEY (delivery_person_id) REFERENCES delivery_persons(delivery_person_id) ON DELETE SET NULL
);

CREATE INDEX idx_confirmation_code ON shipments (confirmation_code);