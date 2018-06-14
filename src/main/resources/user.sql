CREATE TABLE user (
	id INT PRIMARY KEY,
	first_name VARCHAR(50),
	last_name VARCHAR(50)
);

INSERT INTO user VALUES(1, 'John', 'Smith');
INSERT INTO user VALUES(2, 'Ben', 'Shark');
INSERT INTO user VALUES(3, 'Clark', 'Kent');

SELECT * FROM user;