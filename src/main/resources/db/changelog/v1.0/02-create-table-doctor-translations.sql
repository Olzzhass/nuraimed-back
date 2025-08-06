CREATE TABLE doctor_translations
(
    id             BIGSERIAL PRIMARY KEY,
    doctor_id      BIGINT     NOT NULL REFERENCES doctors (id) ON DELETE CASCADE,
    language_code  VARCHAR(2) NOT NULL, -- 'kk', 'ru'
    description    TEXT,                -- "О враче"
    education      TEXT,                -- "Образование"
    experience     TEXT,                -- "Стаж"
    specialization TEXT,                -- "Специализация: Уролог, Неврапотолог и так далее"
    UNIQUE (doctor_id, language_code)
);

CREATE INDEX idx_doctor_translations_doctor_id ON doctor_translations (doctor_id);
