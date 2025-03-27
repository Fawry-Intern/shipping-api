CREATE TABLE delivery_persons (
                                  delivery_person_id BIGSERIAL PRIMARY KEY,
                                  delivery_name VARCHAR(255) NOT NULL,
                                  delivery_email VARCHAR(255) NOT NULL UNIQUE,
                                  delivery_phone VARCHAR(20) NOT NULL ,
                                  order_area_id BIGINT,
                                  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL

);


CREATE INDEX idx_delivery_email ON delivery_persons (delivery_email);

