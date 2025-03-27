CREATE TABLE work_days (
                                id BIGSERIAL PRIMARY KEY,
                                delivery_person_id BIGINT NOT NULL,
                                work_day VARCHAR(50) NOT NULL,
                                CONSTRAINT fk_work_area_days FOREIGN KEY (delivery_person_id)
                                    REFERENCES delivery_persons(delivery_person_id) ON DELETE CASCADE
);
