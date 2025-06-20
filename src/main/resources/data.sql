CREATE TABLE country (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE city (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      country_id BIGINT REFERENCES country(id)
);

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       login VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE trip (
                      id BIGSERIAL PRIMARY KEY,
                      type VARCHAR(50),
                      start_date DATE,
                      end_date DATE,
                      budget NUMERIC(10, 2),
                      include_transport BOOLEAN,
                      has_children BOOLEAN,
                      completed BOOLEAN DEFAULT FALSE,
                      additional_info TEXT,
                      advice TEXT,
                      image_path TEXT,
                      user_id BIGINT REFERENCES users(id));
                          -- Страны
INSERT INTO country (name) VALUES
                               ('France'),
                               ('Italy'),
                               ('Germany');

-- Города
INSERT INTO city (name, country_id) VALUES
                                        ('Paris', 1),
                                        ('Lyon', 1),
                                        ('Rome', 2),
                                        ('Milan', 2),
                                        ('Berlin', 3),
                                        ('Munich', 3);
-- Пользователь demo / password
INSERT INTO users (login, password) VALUES
    ('asd', '$2a$12$NAkTTIRkchpe/r2N3fLDbOVgZVi8RnOle3gKqo5taaq4lPCND8nMO');