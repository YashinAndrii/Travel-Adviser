-- USERS
CREATE TABLE users
(
    id           UUID PRIMARY KEY,
    username     VARCHAR(100) NOT NULL UNIQUE,
    display_name VARCHAR(100),
    avatar_url   TEXT,
    bio          TEXT,
    created_at   TIMESTAMP
);

-- POSTS
CREATE TABLE posts
(
    id         UUID PRIMARY KEY,
    user_id    UUID NOT NULL,
    start_date DATE,
    end_date   DATE,
    type       VARCHAR(50),
    budget     VARCHAR(50),
    is_planned BOOLEAN,
    created_at TIMESTAMP,
    CONSTRAINT fk_posts_user FOREIGN KEY (user_id) REFERENCES users (id)
);

-- POST PHOTOS (1:n)
CREATE TABLE posts_photos
(
    post_id   UUID NOT NULL,
    photo_url TEXT,
    CONSTRAINT fk_photos_post FOREIGN KEY (post_id) REFERENCES posts (id)
);

-- POST COUNTRIES (1:n)
CREATE TABLE posts_countries
(
    post_id UUID NOT NULL,
    country VARCHAR(100),
    CONSTRAINT fk_countries_post FOREIGN KEY (post_id) REFERENCES posts (id)
);

-- POST CITIES (1:n)
CREATE TABLE posts_cities
(
    post_id UUID NOT NULL,
    city    VARCHAR(100),
    CONSTRAINT fk_cities_post FOREIGN KEY (post_id) REFERENCES posts (id)
);

-- LIKES
CREATE TABLE likes
(
    id         UUID PRIMARY KEY,
    user_id    UUID NOT NULL,
    post_id    UUID NOT NULL,
    created_at TIMESTAMP,
    CONSTRAINT fk_likes_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_likes_post FOREIGN KEY (post_id) REFERENCES posts (id)
);

-- COMMENTS
CREATE TABLE comments
(
    id         UUID PRIMARY KEY,
    user_id    UUID NOT NULL,
    post_id    UUID NOT NULL,
    content    TEXT NOT NULL,
    created_at TIMESTAMP,
    CONSTRAINT fk_comments_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_comments_post FOREIGN KEY (post_id) REFERENCES posts (id)
);

-- USERS
INSERT INTO users (id, username, display_name, avatar_url, bio, created_at)
VALUES ('11111111-1111-1111-1111-111111111111', 'travel_anna', 'Anna Traveler',
        'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&h=150&fit=crop&crop=face',
        'Exploring the world one trip at a time.', CURRENT_TIMESTAMP),
       ('22222222-2222-2222-2222-222222222222', 'globetrotter_bob', 'Bob Globetrotter',
        'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=150&h=150&fit=crop&crop=face',
        'Adventure is out there!', CURRENT_TIMESTAMP);

-- POSTS
INSERT INTO posts (id, user_id, start_date, end_date, type, budget, is_planned, created_at)
VALUES ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111', '2025-06-01', '2025-06-10',
    'ROMANTIC', '2500', FALSE, CURRENT_TIMESTAMP);

-- POST_PHOTOS
INSERT INTO posts_photos (post_id, photo_url)
VALUES ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '70bfbfd5-dfbd-4471-ba8e-bbb10ef422e8');
--'https://images.unsplash.com/photo-1433086966358-54859d0ed716?w=800&h=1000&fit=crop');

-- POST_COUNTRIES
INSERT INTO posts_countries (post_id, country)
VALUES ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Italy'),
       ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'France');

-- POST_CITIES
INSERT INTO posts_cities (post_id, city)
VALUES ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Rome'),
       ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Paris');

-- Второй пост: активное приключение
INSERT INTO posts (id, user_id, start_date, end_date, type, budget, is_planned, created_at)
VALUES ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '22222222-2222-2222-2222-222222222222', '2025-07-15', '2025-07-25',
        'ADVENTURE', '1800', FALSE, CURRENT_TIMESTAMP);

INSERT INTO posts_photos (post_id, photo_url)
VALUES ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
        '70bfbfd5-dfbd-4471-ba8e-bbb10ef422e8');
INSERT INTO posts_photos (post_id, photo_url)
VALUES ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
        'a2310bb0-0950-4072-a0d1-69c749d12a60');
INSERT INTO posts_photos (post_id, photo_url)
VALUES ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
        'af873810-40ac-493b-bec9-d83794f6c630');

INSERT INTO posts_countries (post_id, country)
VALUES ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Switzerland');

INSERT INTO posts_cities (post_id, city)
VALUES ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Zermatt');

-- Лайки ко второму посту
INSERT INTO likes (id, user_id, post_id, created_at)
VALUES ('bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', '11111111-1111-1111-1111-111111111111',
        'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', CURRENT_TIMESTAMP);

-- Третий пост: запланированная культурная поездка
INSERT INTO posts (id, user_id, start_date, end_date, type, budget, is_planned, created_at)
VALUES ('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', '11111111-1111-1111-1111-111111111111', '2025-08-10', '2025-08-20',
    'CULTURAL', '2200', TRUE, CURRENT_TIMESTAMP);

INSERT INTO posts_photos (post_id, photo_url)
VALUES ('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3',
        '72460598-8611-47bb-bb17-1d881405f50d');
INSERT INTO posts_photos (post_id, photo_url)
VALUES ('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3',
        'e6a7154d-4501-408a-806e-b6754787a9a5');

INSERT INTO posts_countries (post_id, country)
VALUES ('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 'Japan');

INSERT INTO posts_cities (post_id, city)
VALUES ('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 'Kyoto');

-- Лайки к третьему посту
INSERT INTO likes (id, user_id, post_id, created_at)
VALUES ('bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', '22222222-2222-2222-2222-222222222222',
        'aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', CURRENT_TIMESTAMP),
       ('bbbbbbb3-bbbb-bbbb-bbbb-bbbbbbbbbbb3', '11111111-1111-1111-1111-111111111111',
        'aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', CURRENT_TIMESTAMP);