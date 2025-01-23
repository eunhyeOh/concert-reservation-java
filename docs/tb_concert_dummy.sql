DELIMITER $$
DROP PROCEDURE IF EXISTS loopInsert$$
 
CREATE PROCEDURE loopInsert()
BEGIN
    DECLARE i INT DEFAULT 1;
        
    WHILE i <= 500 DO
        INSERT INTO tb_concert(title , description, created_at)
          VALUES(concat('콘서트',i), concat('내용',i), now());
        SET i = i + 1;
    END WHILE;
END$$
DELIMITER $$
 
 
CALL loopInsert;

