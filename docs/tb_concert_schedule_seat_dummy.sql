DELIMITER $$

CREATE PROCEDURE InsertConcertScheduleSeat()
BEGIN
    -- 1. 변수 선언
    DECLARE scheduleId BIGINT;
    DECLARE concertId BIGINT;
    DECLARE done INT DEFAULT FALSE;

    -- 2. CURSOR 선언
    DECLARE schedule_cursor CURSOR FOR 
        SELECT schedule_id, concert_id FROM tb_concert_schedule;

    -- 3. HANDLER 선언
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- 4. 커서 열기
    OPEN schedule_cursor;
    
    seat_loop: LOOP
        FETCH schedule_cursor INTO scheduleId, concertId;
        IF done THEN 
            LEAVE seat_loop; 
        END IF;
        
        -- 각 스케줄에 대해 좌석 30개 삽입
        SET @seat_number = 1;
        WHILE @seat_number <= 30 DO
            INSERT INTO tb_concert_schedule_seat (
                concert_id, 
                schedule_id, 
                seat_number, 
                price
            )
            VALUES (
                concertId,
                scheduleId,
                CONCAT('A', @seat_number),
                150000.00
            );
            SET @seat_number = @seat_number + 1;
        END WHILE;
    END LOOP;

    -- 5. 커서 닫기
    CLOSE schedule_cursor;
END$$

DELIMITER ;

-- 프로시저 실행
CALL InsertConcertScheduleSeat();
