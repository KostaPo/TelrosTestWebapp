CREATE TABLE users
(
    id  BIGSERIAL NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    surname VARCHAR(255),
    dob date,
    email VARCHAR(255),
    phone VARCHAR(255),
    PRIMARY KEY (id)
);