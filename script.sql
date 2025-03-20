DROP TABLE IF EXISTS order_shipments CASCADE;
DROP TABLE IF EXISTS delivery_person_work_areas CASCADE;
DROP TABLE IF EXISTS delivery_persons CASCADE;
DROP TABLE IF EXISTS work_area_days CASCADE;

CREATE TABLE delivery_persons (
      person_id BIGSERIAL PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      email VARCHAR(255) NOT NULL,
      phone_number VARCHAR(20) UNIQUE NOT NULL,
      is_active BOOLEAN DEFAULT TRUE,
      created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
      updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE delivery_person_work_areas (
        work_area_id BIGSERIAL PRIMARY KEY,
        delivery_person_id BIGINT NOT NULL,
        governorate_id BIGINT NOT NULL,
        city_id BIGINT NOT NULL,
        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
        updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
        FOREIGN KEY (delivery_person_id) REFERENCES delivery_persons(person_id) ON DELETE CASCADE
);

CREATE TABLE work_area_days (
        work_area_id BIGINT      NOT NULL,
        work_day     VARCHAR(20) NOT NULL,
        FOREIGN KEY (work_area_id) REFERENCES delivery_person_work_areas(work_area_id) ON DELETE CASCADE
);

CREATE TABLE order_shipments (
     shipment_id BIGSERIAL PRIMARY KEY,
     order_id BIGINT NOT NULL UNIQUE,
     governorate_id BIGINT NOT NULL,
     city_id BIGINT NOT NULL,
     customer_address TEXT NOT NULL,
     customer_email VARCHAR(255) NOT NULL,
     customer_phone VARCHAR(20) NOT NULL,
     delivery_person_id BIGINT,
     tracking_token VARCHAR(255),
     confirmation_code VARCHAR(255),
     status VARCHAR(50),
     expected_delivery_date TIMESTAMP WITH TIME ZONE,
     delivered_at TIMESTAMP WITH TIME ZONE,
     created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
     updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
     FOREIGN KEY (delivery_person_id) REFERENCES delivery_persons(person_id) ON DELETE SET NULL
);
