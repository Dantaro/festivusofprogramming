use festivus_of_programming;

CREATE TABLE question(
    id INT NOT NULL AUTO_INCREMENT,
    question_year INT NOT NULL,
    question_number INT NOT NULL,
    answer VARCHAR(255) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    primary key (id),
    UNIQUE INDEX uidx_question__question_year__question_number (question_year, question_number)
);

CREATE TABLE user_question_success(
    id INT NOT NULL AUTO_INCREMENT,
    user_id VARCHAR(36) NOT NULL,
    question_id INT NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    primary key (id),
    FOREIGN KEY fk_user_question_success__question_id (question_id) REFERENCES question (id),
    FOREIGN KEY fk_user_question_success__user_id (user_id) REFERENCES user (id),
    UNIQUE INDEX uidx_user_question_success__user_id__question_id (user_id, question_id)
);
