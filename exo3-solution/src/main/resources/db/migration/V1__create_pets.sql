CREATE TABLE IF NOT EXISTS pets (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    kind VARCHAR(32) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    currency VARCHAR(3) NOT NULL
);

INSERT INTO pets (name, kind, price, currency) VALUES
('Buddy', 'DOG', 300.00, 'USD'),
('Mittens', 'CAT', 150.00, 'USD'),
('Twilight Sparkle', 'UNICORN', 12345.67, 'PNY');