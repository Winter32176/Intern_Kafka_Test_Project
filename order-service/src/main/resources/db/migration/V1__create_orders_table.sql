CREATE TABLE orders (
                       id UUID PRIMARY KEY,
                        customer_id BIGINT NOT NULL,
                        product VARCHAR(255) NOT NULL,
                        quantity INTEGER NOT NULL,
                        status VARCHAR(32) NOT NULL,
                        created_at TIMESTAMP WITH TIME ZONE NOT NULL
);