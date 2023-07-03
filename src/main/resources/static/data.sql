CREATE TABLE home_notes.userDto
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

CREATE TABLE home_notes.note
(
    id          BIGINT AUTO_INCREMENT,
    user_id     BIGINT NOT NULL,
    text        VARCHAR(140) NOT NULL,
    time_insert TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES userDto (id)
) COLLATE UTF8MB4_bin;

CREATE TABLE home_notes.tag
(
    id          BIGINT AUTO_INCREMENT,
    text        VARCHAR(25) NOT NULL,
    time_insert TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE (text)
) COLLATE UTF8MB4_bin;

CREATE TABLE home_notes.note_tag
(
    id          BIGINT AUTO_INCREMENT,
    note_id     BIGINT NOT NULL,
    tag_id      BIGINT NOT NULL,
    time_insert TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (note_id) REFERENCES note (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id),
    UNIQUE `phrase_id_tag_id` (`note_id`, `tag_id`)
) COLLATE UTF8MB4_bin;