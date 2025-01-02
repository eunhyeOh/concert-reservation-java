<!--
  제목은 [(과제 STEP)] (작업한 내용) 로 작성해 주세요
  예시: [STEP-5] 이커머스 시스템 설계 
-->
# [STEP-6] 콘서트 예약 시스템 - API 명세

## 1.유저 대기열 토큰 발급 API
  * 유저의 UUID와 대기열 정보를 포함하는 토큰 발급
  * Endpoint
    * URL : `/api/v1/token/issue`
    * Method : POST
  * Request
    * Header
      * Content-Type: application/json
    * Body
      ```
        {
          "userId": 12345,
          "concertId": 67890
        }
      ```
    * Response
      * HTTP Status Codes: 
        * 200 OK: 토큰 발급 성공. 
        ```
          {
            "token": "550e8400-e29b-41d4-a716-446655440000",
            "queuePosition": 5,
            "estimatedWaitTime": "15 minutes",
            "expiresAt": "2025-01-10T10:15:00Z"
          }
        ```

        * 404 Not Found: 사용자 또는 콘서트를 찾을 수 없음.
        ```
            {
            "error": "사용자 정보를 찾을 수 없습니다."
            }
            {
            "error": "콘서트 정보를 찾을 수 없습니다."
            }
          ```
    * Authorization
      * 공개적으로 접근 가능
        
## 2.잔액 충전/조회 API
  * Endpoint
    1. 사용자 잔액 조회
       * URL: `/api/v1/users/{userId}/balance`
       * Method: GET
    2. 사용자 잔액 충전
       * URL: `/api/v1/users/{userId}/balance`
       * Method: POST
  * Request
    1. 잔액 조회
        * Headers : Authorization: Bearer <token>
        * Params : userId
    2. 잔액 충전
      * Headers
        * Authorization: Bearer <token> 
        * Content-Type: application/json
      * Body
        ```
          {
           "amount": 50000.00
          }
        ```
  * Response
    * 잔액 조회 (성공)
    ```
        {
          "userId": 12345,
          "balance": 10000.50
        }
    ```
    * 잔액 충전 (성공)
    ```
      {
        "userId": 12345,
        "newBalance": 150000.50
      }
    ```
    * 401 Unauthorized
    ```
      {
        "error": "잘못된 토큰 정보입니다."
      }
    ```
    * Authorization
      * 필수 : 토큰 인증 필요
        
## 3.예약 가능 날짜/좌석 API
  * Endpoint
      1. 예약 가능 날짜 조회
         * 콘서트 ID로 예약 가능한 날짜 목록 조회
         * URL: `/api/v1/concerts/{concertId}/available-dates`
         * Method: GET
      2. 예약 가능 좌석 조회
         * 날짜에 따라 예약 가능한 좌석 목록 조회
         * URL: `/api/v1/concerts/{concertId}/available-seats`
         * Method: GET
  * Request
    * Headers : Authorization: Bearer <token>
    * Params : concertId(콘서트 ID)
    * Query(좌석 조회) : date
  * Response
    * HTTP Status Codes
      1. 200 OK : 예약 가능 날짜 조회
      ```
        {
           "concertId": 67890,
           "availableDates": [
           "2025-01-10",
           "2025-01-11",
           "2025-01-12"
           ]
         }
      ```
      2. 200 OK : 예약 가능 좌석 조회
      ```
        {
           "concertId": 67890,
           "selectedDate": "2025-01-10",
           "availableSeats": [1, 2, 3, 4, 5]
         }
      ```
      * 404 Not Found
      ```
        {
          "error": "콘서트 정보를 찾을 수 없습니다."
        }
      ```
      * 401 Unauthorized
      ```
        {
          "error": "잘못된 토큰 정보입니다."
        }
      ```
  * Authorization
    * 필수 : 토큰 인증 필요

## 4.좌석 예약 요청 API
  * Endpoint
    * 날짜와 좌석 번호를 입력받아 좌석을 임시 배정
    * URL: `/api/v1/seats/reserve`
    * Method: POST
  * Request
    * Headers
      * Authorization: Bearer <token>
      * Content-Type : application/json
    * Body
    ```
      {
        "concertId": 67890,
        "selectedDate": "2025-01-10",
        "seatNumber": 25,
        "userId": 12345
      }
    ```
  * Response
    * HTTP Status Codes
      * 200 OK : 배석 임시 배정 성공
      ```
        {
          "status": "SUCCESS",
          "message": "Seat reserved temporarily.",
          "reservedUntil": "2025-01-10T10:15:00Z"
        }
      ```
      * 409 Conflict: 좌석이 이미 배정됨
      ```
        {
          "error": "이미 선택된 좌석입니다."
        }
      ```
      * 400 Bad Request
      ```
        {
          "error": "잘못된 좌석 번호입니다."
        }
      ```
  * Authorization
    * 필수 : 토큰 인증 필요

## 5.결제 API
  * 결제를 완료하고 좌석 확정 및 대기열 토큰 만료
  * Endpoint
    * URL: `/api/v1/payments/complete`
    * Method: POST
  * Request
    * Headers
      * Authorization: Bearer <token>
      * Content-Type: application/json
    * Body
    ```
      {
        "concertId": 67890,
        "seatNumber": 25,
        "userId": 12345,
        "token": "550e8400-e29b-41d4-a716-446655440000"
      }
    ```
  * Response
    * HTTP Status Codes
      * 200 OK: 결제 성공
      ```
      {
          "status": "SUCCESS",
          "message": "결제가 완료되었습니다."
        }
      ```
      * 400 Bad Request: 잘못된 요청
      ```
        {
          "error": "잘못된 결제 정보입니다."
        }
      ```
  * Authorization 
    * 필수 : 토큰 인증 필요
