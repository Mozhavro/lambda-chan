CREATE TABLE board (
    id SERIAL PRIMARY KEY,
    slug VARCHAR(5) UNIQUE NOT NULL, -- TODO use as primary key?;
    name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(240)
);

CREATE TABLE thread (
    id SERIAL PRIMARY KEY,
    board_id INT NOT NULL,
    name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(240),
    FOREIGN KEY (board_id) REFERENCES board(id)
);

CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    thread_id INT NOT NULL,
    content VARCHAR(1000) NOT NULL,
    FOREIGN KEY (thread_id) REFERENCES thread(id)
);
