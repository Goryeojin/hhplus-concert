```mermaid
---
title: Concert Reservation Service ERD
---
erDiagram
    user ||--o{ reservation : has
    user ||--o{ payment : makes
    user ||--|| balance : has
    user ||--o{ queue : makes
    concert ||--|{ concert_schedule : has
    concert ||--o{ reservation : for
    concert_schedule ||--|{ seat : has
    reservation ||--|| seat : has
    reservation ||--o| payment : has

    concert {
        int id PK
        string title
        string description
    }
    concert_schedule {
        int id PK
        int concert_id FK
        datetime reservation_available_at
        datetime concert_at
    }
    seat {
        int id PK
        int concert_schedule_id FK
        int seat_number
        string status
        int seat_price
    }
		
    reservation {
        int id PK
        int concert_id FK
        int seat_id FK
        int user_id FK
        string status
        datetime reservation_at
    }

    payment {
        int id PK
        int reservation_id FK
        int user_id FK
        int amount
        datetime payment_at
    }

    balance {
        int id PK
        int user_id FK
        int amount
        datetime last_updated_at
    }

    queue {
        int id PK
        int user_id FK
        int waiting_id
        string status
        datetime created_at
        datetime entered_at
        datetime last_updated_at
        string token
    }
    user {
        int id PK
        string name
    }
```