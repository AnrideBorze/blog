CREATE TABLE IF NOT EXISTS BlogDB
(
    id    BIGSERIAL PRIMARY KEY ,
    title  VARCHAR(200) NOT NULL ,
    content VARCHAR(254) NOT NULL

);