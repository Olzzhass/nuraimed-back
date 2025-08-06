CREATE TABLE doctors
(
    id            BIGSERIAL PRIMARY KEY,
    last_name     VARCHAR(100) NOT NULL,
    first_name    VARCHAR(100) NOT NULL,
    middle_name   VARCHAR(100),
    profile_image BYTEA,
    created_at    TIMESTAMP DEFAULT now(),
    updated_at    TIMESTAMP DEFAULT now()
);