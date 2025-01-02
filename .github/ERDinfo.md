# [STEP-6] 콘서트 예약 시스템 - ERD 설계

## 1.ERD
![20250103_052838](https://github.com/user-attachments/assets/6df0b636-56ba-442b-a47e-2e9d251c932d)

## 2.Table간 연관관계
### 1:N관계
- `tb_user` ↔ `tb_user_token`, `tb_reservation`, `tb_waiting_queue`, `tb_seat_reservation`, `tb_user_balance_log`
- `tb_concert` ↔ `tb_reservation`, `tb_waiting_queue`, `tb_seat_reservation`
### 1:1관계
- `tb_waiting_queue` ↔ `tb_user_token`

## 3.Table 정의
### 콘서트 정보 - tb_concert
- PK : `concert_id`
```
CREATE TABLE tb_concert (
    concert_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '콘서트 고유 식별자',
    title VARCHAR(200) NOT NULL COMMENT '콘서트 제목',
    description TEXT COMMENT '콘서트 설명',
    date DATETIME NOT NULL COMMENT '콘서트 날짜 및 시간',
    capacity INT NOT NULL COMMENT '콘서트 정원',
    price DECIMAL(10, 2) NOT NULL COMMENT '콘서트 예약 금액',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '콘서트 정보 생성일'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='콘서트 테이블';
```

### 유저 정보 - tb_user
- PK : `user_id`
```
CREATE TABLE tb_user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자의 고유 식별자',
    username VARCHAR(100) NOT NULL COMMENT '사용자의 이름',
    email VARCHAR(255) NOT NULL UNIQUE COMMENT '사용자의 이메일',
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '사용자의 잔액',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '사용자 계정 생성일'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 테이블';
```

### 유저 토큰 관리 - tb_user_token
- 토큰 만료시간 설정하여 유효성 관리. 대기열과 연동하여 관리.
- PK : `token_id`
```
CREATE TABLE tb_user_token (
    token_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '토큰 고유 식별자',
    user_id BIGINT NOT NULL COMMENT '토큰이 발급된 사용자 ID',
    token_uuid CHAR(36) NOT NULL UNIQUE COMMENT '유저 고유 토큰 (UUID)',
    queue_id BIGINT COMMENT '연결된 대기열 항목 ID (대기열 토큰)',
    issued_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '토큰 발급 시간',
    expires_at DATETIME COMMENT '토큰 만료 시간',
    INDEX idx_user_id (user_id),
    INDEX idx_queue_id (queue_id),
    INDEX idx_expires_at (expires_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='유저 토큰 관리 테이블';

```
### 대기열 관리 - tb_waiting_queue
- 대기순서, 잔여시간 등 대기열 상태를 관리
- PK : `queue_id`
- 제약 조건
  - 동일한 유저가 같은 콘서트에 중복으로 대기열에 등록되지 않도록 `concert_id`와 `user_id` 조합으로 Unique Index 설정
  - 주기적으로 `queued_at`을 확인하여 만료(EXPIRED) 상태로 변경
  - `queued_at`을 기준으로 정렬하여 대기 순서를 보장
- status
  ```
  WAITING : 대기중
  EXPIRED : 만료된 대기열
  CONFIRMED : 좌석 배정이 완료된 상태
  ```
```
CREATE TABLE tb_waiting_queue (
    queue_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '대기열 항목 고유 식별자',
    concert_id BIGINT NOT NULL COMMENT '대기열이 적용된 콘서트 ID',
    user_id BIGINT NOT NULL COMMENT '대기열에 등록된 사용자 ID',
    queued_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '대기열 등록 시간',
    status ENUM('WAITING', 'EXPIRED', 'CONFIRMED') NOT NULL DEFAULT 'WAITING' COMMENT '대기 상태',
    UNIQUE KEY uniq_concert_user (concert_id, user_id) COMMENT '대기열 중복 방지를 위한 유니크 제약',
    INDEX idx_queued_at (queued_at),
    INDEX idx_concert_id_status (concert_id, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='대기열 테이블';
```

### 콘서트 예약 - tb_reservation
- 유저의 콘서트 예약 정보
- PK : `reservation_id`
- 제약 조건
  - 중복 예약 방지를 위해 `concert_id`와 `user_id` 조합으로 Unique Index를 설정
  - 예약 시 해당 유저의 `balance`가 콘서트 `price` 이상이어야 예약 가능
  - 콘서트 정원을 초과하지 않아야함.
```
CREATE TABLE tb_reservation (
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '예약 고유 식별자',
    concert_id BIGINT NOT NULL COMMENT '예약한 콘서트 ID',
    user_id BIGINT NOT NULL COMMENT '예약한 사용자 ID',
    reserved_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '예약 시간',
    UNIQUE KEY uniq_concert_user (concert_id, user_id) COMMENT '중복 예약 방지를 위한 유니크 제약',
    INDEX idx_concert_id (concert_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='예약 테이블';
```

### 콘서트 좌석 예약 - tb_seat_reservation
- 좌석 배성, 임시 예약 상태 관리(임시 배정 시간, 좌석 번호)
- PK : `seat_id`
- status
  ```
  AVAILABLE : 예약 가능
  TEMPORARY : 특정 사용자에게 임시 배정된 상태
  CONFIRMED : 예약 확정 상태
  ```
- reserved_until : 임시 배정 만료 시간을 저장(5분 후 자동 만료 처리)
```
CREATE TABLE tb_seat_reservation (
    seat_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '좌석 고유 식별자',
    concert_id BIGINT NOT NULL COMMENT '콘서트 ID',
    seat_number VARCHAR(10) NOT NULL COMMENT '좌석 번호',
    user_id BIGINT COMMENT '임시 배정된 사용자 ID',
    reserved_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '임시 배정 시작 시간',
    reserved_until DATETIME COMMENT '임시 배정 만료 시간',
    status ENUM('AVAILABLE', 'TEMPORARY', 'CONFIRMED') NOT NULL DEFAULT 'AVAILABLE' COMMENT '좌석 상태',
    UNIQUE KEY uniq_concert_seat (concert_id, seat_number) COMMENT '콘서트별 좌석 중복 방지를 위한 유니크 제약',
    INDEX idx_concert_id (concert_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='좌석 예약 테이블';

CREATE INDEX idx_seat_reservation_concert ON tb_seat_reservation (concert_id);
CREATE INDEX idx_seat_reservation_status ON tb_seat_reservation (status);
CREATE INDEX idx_seat_reservation_concert_status ON tb_seat_reservation (concert_id, status);
```

### 유저 잔액 이력(선택사항) - tb_user_balance_log
```
CREATE TABLE tb_user_balance_log (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '잔액 변경 로그 ID',
    user_id BIGINT NOT NULL COMMENT '잔액 변경 대상 사용자 ID',
    amount DECIMAL(10, 2) NOT NULL COMMENT '변경된 금액 (양수: 충전, 음수: 차감)',
    log_type ENUM('충전', '예약') NOT NULL COMMENT '변경 유형 (충전 또는 예약)',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '변경 로그 생성 시간',
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 잔액 변경 내역 테이블';
```
