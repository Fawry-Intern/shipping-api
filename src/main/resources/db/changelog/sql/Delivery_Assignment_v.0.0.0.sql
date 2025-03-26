CREATE TABLE delivery_assignments (
                                      id BIGSERIAL PRIMARY KEY,
                                      order_area_id BIGINT NOT NULL,
                                      delivery_person_id BIGINT NOT NULL,
                                      CONSTRAINT fk_order_area FOREIGN KEY (order_area_id)
                                          REFERENCES order_areas(order_area_id) ON DELETE CASCADE,
                                      CONSTRAINT fk_delivery_person FOREIGN KEY (delivery_person_id)
                                          REFERENCES delivery_persons(delivery_person_id) ON DELETE CASCADE
);

CREATE INDEX idx_order_area ON delivery_assignments (order_area_id);
CREATE INDEX idx_delivery_person ON delivery_assignments (delivery_person_id);
