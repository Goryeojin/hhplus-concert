# 콘서트 예약 시스템 API 문서

## 1. 대기열 토큰 발급

### Description
- 대기열 큐에 사용자를 추가하고 토큰을 발급한다.

### Request

- **URI**: `api/v1/queue/tokens`
- **Method**: POST
- **Body**:
```json
{
  "userId": 1
}
```
- `userId`: Long (사용자 ID)

### Response

```json
{
  "token": "a1b2c3d4",
  "createdAt": "2024-10-10 00:00:00",
  "expiredAt": "2024-10-10 00:10:00"
}
```
- **Response**:
  - `token`: String (토큰 UUID)
  - `createdAt`: DateTime (생성 시각)
  - `expiredAt`: DateTime (만료 시각)

### Error
```json
{
  "code": 404,
  "message": "User Not Found"
}
```

## 2. 대기열 토큰 조회

### Description
- 사용자의 발급된 대기열 토큰을 조회한다.

### Request

- **URI**: `api/v1/queue/tokens?userId={userId}`
- **Method**: GET
- **Query Params**:
    - `userId`: Long (사용자 ID)

### Response

```json
{
  "token": "a1b2c3d4",
  "createdAt": "2024-10-10 00:00:00",
  "expiredAt": "2024-10-10 00:10:00"
}
```
- **Response**:
  - `token`: String (토큰 UUID)
  - `createdAt`: DateTime (생성 시각)
  - `expiredAt`: DateTime (만료 시각)

### Error
```json
{
  "code": 404,
  "message": "User Not Found"
}
```

## 3. 대기열 상태 조회

### Description
- 폴링 방식으로 사용자의 대기열 상태를 조회한다.

### Request

- **URI**: `api/v1/queue/status?userId={userId}`
- **Method**: GET
- **Query Params**:
    - `userId`: Long (사용자 ID)

- **Headers**:
    - `TOKEN`: String (토큰 UUID)

### Response

```json
{
  "createdAt": "2024-10-10 00:00:00",
  "status": "WAITING",
  "remainingQueueCount": 10
}
```
- **Response**:
  - `createdAt`: DateTime (생성시각)
  - `status`: Enum (`WAITING`: 대기 / `ACTIVE`: 활성화 / `EXPIRED`: 만료)
  - `remainingQueueCount`: Long (남은 대기 수)

### Error
```json
{
  "code": 404,
  "message": "User Not Found"
}
```
```json
{
  "code": 401,
  "message": "Invalid Token"
}
```

## 4. 콘서트 예약 가능 일정 조회

### Description
- 특정 콘서트의 예약 가능한 일정을 조회한다.

### Request

- **URI**: `api/v1/concerts/{concertId}/schedule`
- **Method**: GET
- **Path Variable**:
    - `concertId`: Long (콘서트 ID)
- **Headers**:
    - `TOKEN`: String (토큰 UUID)

### Response

```json
{
  "concertId": 1,
  "schedule": [
    {
      "scheduleId": 1,
      "concertAt": "2024-10-10 00:00:00",
      "reservationAt": "2024-10-10 00:00:00"
    },
    {
      "scheduleId": 2,
      "concertAt": "2024-10-10 00:00:00",
      "reservationAt": "2024-10-10 00:00:00"
    }
  ]
}
```
- **Response**:
  - `concertId`: Long (콘서트 ID)
  - `schedule`: List (일정 목록)
    - `scheduleId`: Long (일정 ID)
    - `concertAt`: DateTime (콘서트 시각)
    - `reservationAt`: DateTime (예약 시각)

### Error
```json
{
  "code": 401,
  "message": "Invalid Token"
}
```
```json
{
  "code": 404,
  "message": "User Not Found"
}
```
```json
{
  "code": 404,
  "message": "Concert Not Found"
}
```

## 5. 콘서트 잔여 좌석 조회

### Description
- 특정 콘서트 일정의 잔여 좌석을 조회한다.

### Request

- **URI**: `api/v1/concerts/{concertId}/schedule/{scheduleId}/seats`
- **Method**: GET
- **Path Variable**:
    - `concertId`: Long (콘서트 ID)
    - `scheduleId`: Long (일정 ID)
- **Headers**:
    - `TOKEN`: String (토큰 UUID)

### Response

```json
{
  "concertId": 1,
  "concertAt": "2024-10-10 00:00:00",
  "maxSeats": 50,
  "seats": [
    {
      "seatId": 1,
      "seatNumber": 1,
      "seatStatus": "AVAILABLE",
      "seatPrice": 10000
    },
    {
      "seatId": 2,
      "seatNumber": 2,
      "seatStatus": "AVAILABLE",
      "seatPrice": 10000
    }
  ]
}
```
- **Response**:
  - `concertId`: Long (콘서트 ID)
  - `concertAt`: DateTime (콘서트 시각)
  - `maxSeats`: Long (총 좌석 수)
  - `seats`: List (예약 가능 좌석 목록)
    - `seatId`: Long (좌석 ID)
    - `seatNumber`: Long (좌석 번호)
    - `seatStatus`: Enum (`AVAILABLE`: 예약 가능)
    - `seatPrice`: Long (좌석 가격)

### Error
```json
{
  "code": 401,
  "message": "Invalid Token"
}
```
```json
{
  "code": 404,
  "message": "User Not Found"
}
```
```json
{
  "code": 404,
  "message": "Concert Not Found"
}
```
```json
{
  "code": 404,
  "message": "Schedule Not Found"
}
```

## 6. 좌석 예약

### Description
- 콘서트 좌석을 예약한다.
- 좌석을 여러개 예약할 수 있다.

### Request

- **URI**: `api/v1/reservations`
- **Method**: POST
- **Headers**:
    - `TOKEN`: String (토큰 UUID)
    - `Content-Type`: application/json


- **Body**:
```json
{
  "userId": 1,
  "concertId": 1,
  "scheduleId": 1,
  "seatIds": [1, 2]
}
```
  - `userId`: Long (사용자 ID)
  - `concertId`: Long (콘서트 ID)
  - `scheduleId`: Long (일정 ID)
  - `seatIds`: List (선택한 좌석 목록)

### Response

```json
{
  "reservationId": 1,
  "concertId": 1,
  "concertName": "콘서트",
  "concertAt": "2024-10-10 00:00:00",
  "seats": [
    {
      "seatNumber": 1,
      "seatPrice": 10000
    },
    {
      "seatNumber": 2,
      "seatPrice": 10000
    }
  ],
  "totalPrice": 20000,
  "reservationStatus": "PAYMENT_WAITING"
}
```
- **Response**:
  - `reservationId`: Long (예약 ID)
  - `concertId`: Long (콘서트 ID)
  - `concertName`: String (콘서트 이름)
  - `concertAt`: DateTime (콘서트 시각)
  - `seats`: List (선택한 좌석 목록)
    - `seatNumber`: Long (좌석 번호)
    - `price`: Long (좌석 금액)
  - `totalPrice`: Long (결제 금액)
  - `reservationStatus`: Enum (`PAYMENT_WAITING`: 결제 대기 상태)


### Error
```json
{
  "code": 401,
  "message": "Invalid Token"
}
```
```json
{
  "code": 404,
  "message": "User Not Found"
}
```
```json
{
  "code": 404,
  "message": "Concert Not Found"
}
```
```json
{
  "code": 404,
  "message": "Schedule Not Found"
}
```
```json
{
  "code": 404,
  "message": "Seat Not Found"
}
```
```json
{
  "code": 500,
  "message": "Reservation Failed"
}
```

## 7. 결제

### Description
- 예약에 대한 결제를 진행한다.
- 예약을 5분내에 결제해야 한다.

### Request

- **URI**: `api/v1/payments`
- **Method**: POST
- **Headers**:
    - `TOKEN`: String (토큰 UUID)
    - `Content-Type`: application/json


- **Body**:
```json
{
  "userId": 1,
  "reservationId": 1
}
```
- `userId`: Long (사용자 ID)
- `reservationId`: Long (예약 ID)

### Response
```json
{
  "paymentId": 1,
  "amount": 30000,
  "paymentStatus": "COMPLETED"
}
```
- **Response**
  - `paymentId`: Long (결제 ID)
  - `amount`: Long (결제 금액)
  - `paymentStatus`: Enum (`COMPLETED`: 완료)


### Error
```json
{
  "code": 401,
  "message": "Invalid Token"
}
```
```json
{
  "code": 404,
  "message": "User Not Found"
}
```
```json
{
  "code": 404,
  "message": "Reservation Not Found"
}
```
```json
{
  "code": 400,
  "message": "Not Enough Balance"
}
```
```json
{
  "code": 500,
  "message": "Payment Failed"
}
```

## 8. 잔액 충전

### Description
- 사용자의 잔액을 충전합니다.

### Request

- **URI**: `api/v1/users/{userId}/balance`
- **Method**: PATCH
- **Path Variable**:
    - `userId`: Long (사용자 ID)
- **Headers**:
    - `Content-Type`: application/json

- **Body**:
```json
{
  "amount": 50000
}
```
- `amount`: Long (충전 금액)

### Response
```json
{
  "userId": 1,
  "currentAmount": 50000
}
```
- **Response**
  - `userId`: Long (사용자 ID)
  - `currentAmount`: Long (충전 후 잔액)

### Error

```json
{
  "code": 404,
  "message": "User Not Found"
}
```
```json
{
  "code": 400,
  "message": "Invalid Charge Amount"
}
```
```json
{
  "code": 500,
  "message": "Charge Failed"
}
```

## 9. 잔액 조회

### Description
- 사용자의 현재 잔액을 조회한다.

### Request

- **URI**: `api/v1/users/{userId}/balance`
- **Method**: GET
- **Path Variable**:
    - `userId`: Long (사용자 ID)

### Response
```json
{
  "userId": 1,
  "currentAmount": 50000
}
```
- **Response**
  - `userId`: Long (사용자 ID)
  - `currentAmount`: Long (잔액)

### Error

```json
{
  "code": 404,
  "message": "User Not Found"
}
```