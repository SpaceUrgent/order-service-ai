CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_time TIMESTAMP WITH TIME ZONE NOT NULL,
    customer VARCHAR(255) NOT NULL,
    product VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    price_per_unit NUMERIC(19, 4) NOT NULL,
    total_price NUMERIC(19, 4) NOT NULL
);

CREATE INDEX idx_orders_customer ON orders(customer);
CREATE INDEX idx_orders_product ON orders(product);