CREATE TABLE users(
                      id serial PRIMARY KEY NOT NULL ,
                      username VARCHAR(50) NOT NULL ,
                      email VARCHAR(50) NOT NULL ,
                      password VARCHAR(255) NOT NULL
);

CREATE TABLE roles(
                      id serial PRIMARY KEY NOT NULL,
                      role_name VARCHAR(50) NOT NULL
);

CREATE TABLE user_role
(
    user_id INT NOT NULL REFERENCES users (id) ON DELETE CASCADE ,
    role_id INT NOT NULL REFERENCES roles (id) ON DELETE CASCADE ,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE books
(
    id SERIAL PRIMARY KEY NOT NULL ,
    title VARCHAR(50),
    year INT
)

