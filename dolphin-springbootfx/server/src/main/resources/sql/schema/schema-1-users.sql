DROP TABLE IF EXISTS users_authorities;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL,
    account_non_expired BOOLEAN NOT NULL,
    account_non_locked BOOLEAN NOT NULL,
    credentials_non_expired BOOLEAN NOT NULL,
    image_link VARCHAR(255) DEFAULT "" COMMENT 'Ссылка изображения',
    enabled BOOLEAN NOT NULL,
    UNIQUE KEY uk_username(username)
);

CREATE TABLE IF NOT EXISTS authorities (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    authority VARCHAR(64) NOT NULL,
    UNIQUE KEY uk_authority(authority)
);

CREATE TABLE IF NOT EXISTS users_authorities (
    users_id BIGINT UNSIGNED NOT NULL,
    authorities_id BIGINT UNSIGNED NOT NULL,
    UNIQUE KEY uk_users_id_authorities_id (users_id, authorities_id),
    CONSTRAINT fk_users_id FOREIGN KEY (users_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_authorities_id FOREIGN KEY (authorities_id) REFERENCES authorities(id) ON DELETE CASCADE
);