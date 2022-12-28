use festivus_of_programming;

CREATE TABLE auth_type(
    id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    primary key (id)
);

CREATE TABLE user(
    id VARCHAR(36) NOT NULL,
    identifier VARCHAR(255) NOT NULL,
    auth_type_id VARCHAR(36) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    primary key (id),
    FOREIGN KEY fk_users__auth_type_id (auth_type_id) REFERENCES auth_type(id),
    INDEX idx_users__identifier__auth_type (identifier, auth_type_id)
);

CREATE TABLE user_session(
    id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    primary key (id),
    FOREIGN KEY fk_user_session__user_id (user_id) REFERENCES user(id),
    INDEX idx_user_session__id__user_id (id, user_id)
);

START TRANSACTION;

INSERT INTO auth_type (id, name) VALUES ("aa191cd3-50b3-4dbd-b838-7d6e0c40e487", "Github");

COMMIT;
