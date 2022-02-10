CREATE TABLE `users`
(
    `id`              BIGINT                                                  NOT NULL AUTO_INCREMENT,
    `username`        VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `password`        VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `date_registered` TIMESTAMP                                               NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `roles`
(
    `id`        BIGINT                                                  NOT NULL AUTO_INCREMENT,
    `authority` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;


CREATE TABLE `users_roles`
(
    `user_id` BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES users (`id`),
    FOREIGN KEY (`role_id`) REFERENCES roles (`id`)
) ENGINE=InnoDB;

insert into roles (authority)
values ('STANDARD'),
       ('ADMIN');