CREATE TABLE direction_translations
(
    id             BIGSERIAL PRIMARY KEY,
    direction_id   BIGINT     NOT NULL REFERENCES direction (id) ON DELETE CASCADE,
    language_code  VARCHAR(2) NOT NULL, -- 'kk', 'ru'
    title          VARCHAR(255),        -- Название направления
    description    TEXT,                -- Чем занимается направление
    offer_details       TEXT,                -- Что мы предлагаем?
    UNIQUE (direction_id, language_code)
);

CREATE INDEX idx_direction_translations_direction_id ON direction_translations (direction_id);
