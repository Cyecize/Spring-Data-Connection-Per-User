CREATE TABLE `tasks`
(
    `id`          BIGINT                                                  NOT NULL AUTO_INCREMENT,
    `description` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `in_progress` tinyint                                                 NOT NULL,
    `due_date`    TIMESTAMP,
    `create_date` TIMESTAMP                                               NOT NULL,
    `user_id`     BIGINT                                                  NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES users (`id`),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;
