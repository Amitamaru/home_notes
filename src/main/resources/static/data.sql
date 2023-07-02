CREATE TABLE home_notes.user
(
    id           BIGINT AUTO_INCREMENT,
    nickname     VARCHAR(15)  NOT NULL,
    password     VARCHAR(100) NOT NULL,
    access_token VARCHAR(100) NOT NULL,
    time_insert  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `nickname` (`nickname`),
    UNIQUE KEY `access_token` (`access_token`)
) COLLATE utf8_bin;
