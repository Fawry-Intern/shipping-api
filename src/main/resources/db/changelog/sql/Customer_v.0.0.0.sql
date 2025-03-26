CREATE TABLE customers (
                           customer_id BIGSERIAL PRIMARY KEY,
                           governorate VARCHAR(255) NOT NULL,
                           city VARCHAR(255) NOT NULL,
                           customer_name VARCHAR(255) NOT NULL,
                           customer_address TEXT NOT NULL,
                           customer_email VARCHAR(255) NOT NULL UNIQUE,
                           customer_phone VARCHAR(20) NOT NULL
);

CREATE INDEX idx_customer_email ON customers (customer_email);

