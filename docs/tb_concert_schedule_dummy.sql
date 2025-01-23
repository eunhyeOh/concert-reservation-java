DELIMITER $$

CREATE PROCEDURE InsertConcertSchedules()
BEGIN
    DECLARE i INT DEFAULT 1; -- 반복 변수
    DECLARE max_id INT DEFAULT 500; -- 최대 concert_id

    WHILE i <= max_id DO
        -- 2025-02-02 데이터 삽입
        INSERT INTO tb_concert_schedule (concert_id, date, max_capacity)
        VALUES (i, '2025-02-02 19:00:00', 50);
        
        -- 2025-02-03 데이터 삽입
        INSERT INTO tb_concert_schedule (concert_id, date, max_capacity)
        VALUES (i, '2025-02-03 19:00:00', 50);

        SET i = i + 1; -- 다음 concert_id로 증가
    END WHILE;
END$$

DELIMITER ;

-- 프로시저 실행
CALL InsertConcertSchedules();
