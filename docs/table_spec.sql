CREATE TABLE tb_concert (
    concert_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '콘서트 고유 식별자',
    title VARCHAR(200) NOT NULL COMMENT '콘서트 제목',
    description TEXT COMMENT '콘서트 설명',
    price DECIMAL(10, 2) NOT NULL COMMENT '콘서트 예약 금액',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '콘서트 정보 생성일'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='콘서트 테이블';

CREATE TABLE tb_concert_schedule (
	schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '콘서트 스케줄 고유 식별자',
    concert_id BIGINT NOT NULL COMMENT '콘서트 고유 식별자',
    date DATETIME NOT NULL COMMENT '콘서트 날짜 및 시간',
    max_capacity INT NOT NULL COMMENT '콘서트 정원',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '콘서트 스케줄 정보 생성일'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='콘서트 테이블';
CREATE INDEX idx_concert_schedule_concert ON tb_concert_schedule (concert_id);

CREATE TABLE tb_user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자의 고유 식별자',
    username VARCHAR(100) NOT NULL COMMENT '사용자의 이름',
    email VARCHAR(255) NOT NULL UNIQUE COMMENT '사용자의 이메일',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '사용자 계정 생성일'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 테이블';

CREATE TABLE tb_user_wallet (
    wallet_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '유저 지갑 고유 식별자',
    user_id BIGINT NOT NULL COMMENT '사용자 ID',
    current_balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '현재 잔액',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '지갑 생성일',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '지갑 수정일',
	INDEX idx_user_id (user_id),
    UNIQUE KEY uniq_user_wallet (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 지갑 테이블';


CREATE TABLE tb_user_wallet_log (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '지갑 변경 로그 ID',
    wallet_id BIGINT NOT NULL COMMENT '지갑 ID (변경 대상)',
    user_id BIGINT NOT NULL COMMENT '사용자 ID',
    change_amount DECIMAL(10, 2) NOT NULL COMMENT '변경된 금액 (양수: 충전, 음수: 차감)',
    log_type VARCHAR(10) NOT NULL COMMENT '변경 유형 (충전, 예약, 환불)',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '변경 로그 생성 시간',
    INDEX idx_wallet_id (wallet_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 지갑 변경 내역 테이블';


CREATE TABLE tb_concert_reservation (
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '예약 고유 식별자',
    concert_id BIGINT NOT NULL COMMENT '예약한 콘서트 ID',
    schedule_id BIGINT NOT NULL COMMENT '콘서트 스케줄 고유 식별자',
    user_id BIGINT NOT NULL COMMENT '예약한 사용자 ID',
    payment_amount DECIMAL(10,2) NOT NULL COMMENT '결제 금액',
    reserved_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '예약 시간',
   -- UNIQUE KEY uniq_concert_user (concert_id, schedule_id, user_id) COMMENT '중복 예약 방지를 위한 유니크 제약',
    INDEX idx_concert_id (concert_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='예약 테이블';



CREATE TABLE tb_seat_reservation (
    seat_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '좌석 고유 식별자',
    concert_id BIGINT NOT NULL COMMENT '콘서트 ID',
    schedule_id BIGINT NOT NULL COMMENT '콘서트 스케줄 고유 식별자',
    seat_number VARCHAR(10)  NOT NULL COMMENT '좌석번호',
    user_id BIGINT NOT NULL COMMENT '사용자 ID',
	reserved_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '임시 배정 시작 시간',
    reserved_until DATETIME COMMENT '임시 배정 만료 시간',
    status VARCHAR(10) NOT NULL DEFAULT 'AVAILABLE' COMMENT '좌석 상태',
    UNIQUE KEY uniq_concert_seat (concert_id, schedule_id, seat_number) COMMENT '콘서트별 좌석 중복 방지를 위한 유니크 제약',
    INDEX idx_concert_id (concert_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='좌석 예약 테이블';
CREATE INDEX idx_seat_reservation_concert ON tb_seat_reservation (concert_id, schedule_id);
CREATE INDEX idx_seat_reservation_status ON tb_seat_reservation (status);
CREATE INDEX idx_seat_reservation_concert_status ON tb_seat_reservation (concert_id, schedule_id, status);

CREATE TABLE tb_waiting_queue (
    queue_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '대기열 항목 고유 식별자',
    concert_id BIGINT NOT NULL COMMENT '콘서트 ID',
    schedule_id BIGINT NOT NULL COMMENT '콘서트 스케줄 고유 식별자',
    user_id BIGINT NOT NULL COMMENT '사용자 ID',
    token_uuid CHAR(36) NOT NULL UNIQUE COMMENT '유저 고유 토큰 (UUID)',
    queued_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '대기열 등록 시간',
    expires_at DATETIME COMMENT ' 만료 시간',
    status VARCHAR(10) NOT NULL DEFAULT 'WAITING' COMMENT '대기 상태',
    UNIQUE KEY uniq_concert_user (concert_id, schedule_id, user_id) COMMENT '대기열 중복 방지를 위한 유니크 제약',
    INDEX idx_queued_at (queued_at),
    INDEX idx_concert_id_schedule_id_user_id (concert_id, schedule_id, user_id),
    INDEX idx_concert_id_schedule_id_status (concert_id, schedule_id, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='대기열 테이블';
