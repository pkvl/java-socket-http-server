CREATE TABLE user (
	ID INT PRIMARY KEY,
	NAME VARCHAR(50),
	SURNAME(50)
);

INSERT INTO user VALUES(1, 'John', 'Smith');
INSERT INTO user VALUES(2, 'Ben', 'Shark');
INSERT INTO user VALUES(3, 'Clark', 'Kent');

SELECT * FROM user;