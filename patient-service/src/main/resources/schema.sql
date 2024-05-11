CREATE TABLE IF NOT EXISTS genre (
    id INT NOT NULL AUTO_INCREMENT,
    name CHAR,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE DATABASE IF NOT EXISTS patient_db;
USE patient_db;
CREATE TABLE IF NOT EXISTS patient(
    id INT NOT NULL AUTO_INCREMENT,
    last_name VARCHAR(25),
    first_name VARCHAR(25),
    date_of_birth DATE,
    fk_genre_id INT ,
    patient_address VARCHAR(25),
    phone_number VARCHAR(25),
    primary key (id),
    CONSTRAINT fk_genre
            FOREIGN KEY (fk_genre_id)
            REFERENCES genre(id),
    CONSTRAINT unique_name UNIQUE (last_name, first_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

