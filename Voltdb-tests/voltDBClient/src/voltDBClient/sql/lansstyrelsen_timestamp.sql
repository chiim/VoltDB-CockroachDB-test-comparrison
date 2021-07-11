CREATE TABLE usertable(datetime timestamp NOT NULL);

CREATE PROCEDURE Insert
   AS INSERT INTO usertable (datetime) VALUES (?);

CREATE PROCEDURE Select
   AS SELECT datetime FROM usertable WHERE datetime = ?;

CREATE PROCEDURE Delete
   AS DELETE FROM usertable WHERE datetime = ?;
   
CREATE PROCEDURE Update
   AS UPDATE usertable SET datetime = ? WHERE datetime = ?;