CREATE TABLE direction
(
    id              BIGSERIAL PRIMARY KEY,
    direction_image BYTEA,
    created_at      TIMESTAMP DEFAULT now(),
    updated_at      TIMESTAMP DEFAULT now()
);
