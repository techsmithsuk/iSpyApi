CREATE TABLE users (id int serial primary key, username varchar(100) not null unique);
INSERT INTO users (id,username) VALUES (1,"Tom09");