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
    dates      VARCHAR(255),
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
VALUES ('11111111-1111-1111-1111-111111111111', 'travel_anna', 'Anna Traveler', 'https://example.com/avatar1.jpg',
        'Exploring the world one trip at a time.', CURRENT_TIMESTAMP),
       ('22222222-2222-2222-2222-222222222222', 'globetrotter_bob', 'Bob Globetrotter',
        'https://example.com/avatar2.jpg', 'Adventure is out there!', CURRENT_TIMESTAMP);

-- POSTS
INSERT INTO posts (id, user_id, dates, type, budget, is_planned, created_at)
VALUES ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111', 'June 1 - June 10, 2025',
        'ROMANTIC', '$2500', FALSE, CURRENT_TIMESTAMP);

-- POST_PHOTOS
INSERT INTO posts_photos (post_id, photo_url)
VALUES ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'https://example.com/photo1.jpg');

-- POST_COUNTRIES
INSERT INTO posts_countries (post_id, country)
VALUES ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Italy'),
       ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'France');

-- POST_CITIES
INSERT INTO posts_cities (post_id, city)
VALUES ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Rome'),
       ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Paris');
