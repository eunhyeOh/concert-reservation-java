CREATE TABLE `tb_concert` (
  `concert_id` bigint NOT NULL AUTO_INCREMENT COMMENT '콘서트의 일련번호',
  `title` varchar(250) NOT NULL COMMENT '콘서트 제목',
  `description` varchar(255) DEFAULT NULL COMMENT '콘서트 설명',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성날짜',
  PRIMARY KEY (`concert_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_concert_schedule` (
  `schedule_id` bigint NOT NULL AUTO_INCREMENT COMMENT '스케쥴 일련번호',
  `concert_id` bigint NOT NULL COMMENT '콘서트 일련번호',
  `start_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '콘서트 시작 시간',
  `end_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '콘서트 종료 시간',
  `max_capacity` int NOT NULL DEFAULT '0' COMMENT '최대 수용 인원',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
  PRIMARY KEY (`schedule_id`),
  KEY `tb_concert_schedule_tb_concert_concert_id_fk` (`concert_id`),
  CONSTRAINT `tb_concert_schedule_tb_concert_concert_id_fk` FOREIGN KEY (`concert_id`) REFERENCES `tb_concert` (`concert_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_concert_seat` (
  `seat_id` bigint NOT NULL AUTO_INCREMENT COMMENT '좌석 일련번호',
  `schedule_id` bigint NOT NULL COMMENT '스케쥴 일련번호',
  `seat_number` int NOT NULL COMMENT '좌석번호',
  `price` int NOT NULL COMMENT '좌석 가격',
  `status` enum('AVAILABLE','TEMP_RESERVED','RESERVED') NOT NULL DEFAULT 'AVAILABLE' COMMENT '좌석 상태 (''AVAILABLE'', ''TEMP_RESERVED'', ''RESERVED'')',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
  PRIMARY KEY (`seat_id`),
  KEY `tb_concert_seat_tb_concert_schedule_schedule_id_fk` (`schedule_id`),
  CONSTRAINT `tb_concert_seat_tb_concert_schedule_schedule_id_fk` FOREIGN KEY (`schedule_id`) REFERENCES `tb_concert_schedule` (`schedule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '유저 일련번호',
  `user_mail` varchar(250) NOT NULL COMMENT '유저의 메일',
  `user_amount` int NOT NULL DEFAULT '0' COMMENT '유저 잔액',
  `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '유저 생성 시간',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_reservation` (
  `reservation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '예약 일련번호',
  `user_id` bigint NOT NULL COMMENT '유저 일련번호',
  `seat_id` bigint NOT NULL COMMENT '좌석 일련번호',
  `reserved_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '예약 시간',
  `reserved_untill_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '예약 만료 시간',
  `created_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
  PRIMARY KEY (`reservation_id`),
  KEY `tb_reservation_tb_concert_seat_seat_id_fk` (`seat_id`),
  KEY `tb_reservation_tb_user_user_id_fk` (`user_id`),
  CONSTRAINT `tb_reservation_tb_concert_seat_seat_id_fk` FOREIGN KEY (`seat_id`) REFERENCES `tb_concert_seat` (`seat_id`),
  CONSTRAINT `tb_reservation_tb_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_payment` (
  `payment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '결제 일련번호',
  `user_id` bigint NOT NULL COMMENT '유저 일련번호',
  `reservation_id` bigint NOT NULL COMMENT '예약 일련번호',
  `price` int NOT NULL COMMENT '결제 금액',
  `status` enum('PROGRESS','DONE','CANCELED','FAIL') NOT NULL DEFAULT 'PROGRESS' COMMENT '결제 상태 (''PROGRESS'',''DONE'',''CANCELED'',''FAIL'')',
  `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성날짜',
  PRIMARY KEY (`payment_id`),
  KEY `tb_payment_tb_reservation_reservation_id_fk` (`reservation_id`),
  KEY `tb_payment_tb_user_user_id_fk` (`user_id`),
  CONSTRAINT `tb_payment_tb_reservation_reservation_id_fk` FOREIGN KEY (`reservation_id`) REFERENCES `tb_reservation` (`reservation_id`),
  CONSTRAINT `tb_payment_tb_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_payment_history` (
  `payment_history_id` bigint NOT NULL AUTO_INCREMENT COMMENT '결제 히스토리 일련번호',
  `user_id` bigint NOT NULL COMMENT '유저 일련번호',
  `payment_id` bigint DEFAULT '0' COMMENT '결제 일련번호',
  `amount_change` int NOT NULL COMMENT '변동된 금액',
  `type` enum('CHARGE','USE','REFUND') NOT NULL COMMENT '금액 사용 타입',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 날짜',
  PRIMARY KEY (`payment_history_id`),
  KEY `tb_payment_history_tb_payment_payment_id_fk` (`payment_id`),
  KEY `tb_payment_history_tb_user_user_id_fk` (`user_id`),
  CONSTRAINT `tb_payment_history_tb_payment_payment_id_fk` FOREIGN KEY (`payment_id`) REFERENCES `tb_payment` (`payment_id`),
  CONSTRAINT `tb_payment_history_tb_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_queue` (
  `queue_id` bigint NOT NULL AUTO_INCREMENT COMMENT '대기 일련번호',
  `user_id` bigint NOT NULL COMMENT '유저 일련번호',
  `token` varchar(255) NOT NULL COMMENT '대기열 토큰',
  `status` enum('WAITING','PROGRESS','DONE','EXPIRED') NOT NULL DEFAULT 'WAITING' COMMENT '대기열 상태 (''WATING'', ''PROGRESS'', ''DONE'', ''EXPIRED'')',
  `entered_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '대기열 진입 시간',
  `expired_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '대기열 만료 시간',
  PRIMARY KEY (`queue_id`),
  KEY `tb_queue_tb_user_user_id_fk` (`user_id`),
  CONSTRAINT `tb_queue_tb_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;