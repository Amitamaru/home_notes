CREATE TABLE IF NOT EXISTS user
(
    id           BIGINT AUTO_INCREMENT,
    nickname     VARCHAR(15)  NOT NULL,
    password     VARCHAR(100) NOT NULL,
    access_token VARCHAR(100) NOT NULL,
    time_insert  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `nickname` (`nickname`),
    UNIQUE KEY `access_token` (`access_token`)
) COLLATE UTF8MB4_bin;

CREATE TABLE IF NOT EXISTS note
(
    id          BIGINT AUTO_INCREMENT,
    user_id     BIGINT NOT NULL,
    text        VARCHAR(140) NOT NULL,
    time_insert TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE
) COLLATE UTF8MB4_bin;

CREATE TABLE IF NOT EXISTS tag
(
    id          BIGINT AUTO_INCREMENT,
    text        VARCHAR(25) NOT NULL,
    time_insert TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE (text)
) COLLATE UTF8MB4_bin;

CREATE TABLE IF NOT EXISTS note_tag
(
    id          BIGINT AUTO_INCREMENT,
    note_id     BIGINT NOT NULL,
    tag_id      BIGINT NOT NULL,
    time_insert TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (note_id) REFERENCES note (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE `phrase_id_tag_id` (`note_id`, `tag_id`)
) COLLATE UTF8MB4_bin;

CREATE TABLE IF NOT EXISTS subscription
(
    id          BIGINT AUTO_INCREMENT,
    sub_user_id BIGINT    NOT NULL,
    pub_user_id BIGINT    NOT NULL,
    time_insert TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (sub_user_id) REFERENCES user (id),
    FOREIGN KEY (pub_user_id) REFERENCES user (id),
    UNIQUE KEY sub_user_id_pub_user_id (sub_user_id, pub_user_id)
) COLLATE UTF8MB4_bin;

CREATE TABLE IF NOT EXISTS like_note
(
    id          BIGINT AUTO_INCREMENT,
    note_id     BIGINT    NOT NULL,
    user_id     BIGINT    NOT NULL,
    time_insert TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (note_id) REFERENCES note (id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    UNIQUE `phrase_id_user_id` (`note_id`, `user_id`)
) COLLATE UTF8MB4_bin;

CREATE TABLE IF NOT EXISTS comment
(
    id          BIGINT AUTO_INCREMENT,
    user_id     BIGINT       NOT NULL,
    note_id   BIGINT       NOT NULL,
    text        VARCHAR(140) NOT NULL,
    time_insert TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (note_id) REFERENCES note (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
) COLLATE UTF8MB4_bin;

CREATE TABLE IF NOT EXISTS block
(
    id              BIGINT AUTO_INCREMENT,
    user_id         BIGINT    NOT NULL,
    block_user_id   BIGINT    NOT NULL,
    time_insert     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (block_user_id) REFERENCES user (id),
    UNIQUE user_id_block_user_id (user_id, block_user_id)
) COLLATE UTF8MB4_bin;