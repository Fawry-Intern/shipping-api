CREATE TABLE order_areas (
                            order_area_id BIGSERIAL PRIMARY KEY,
                            delivery_person_id BIGINT,
                            governorate VARCHAR(255) NOT NULL,
                            city VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX idx_city ON order_areas(city);
