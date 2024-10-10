# 콘서트 예약 서비스# 콘서트 예약 서비스

## Docs
### [1. 마일스톤](https://github.com/Goryeojin/hhplus-concert/blob/step5/docs/01_Milstone.md)
### [2. 이벤트 시퀀스 다이어그램](https://github.com/Goryeojin/hhplus-concert/blob/step5/docs/02_Sequence.md)
### [3. ERD](https://github.com/Goryeojin/hhplus-concert/blob/step6/docs/03_ERD.md)
### [4. API 명세 문서](https://github.com/Goryeojin/hhplus-concert/blob/step6/docs/04_API_specification.md)

## Mock API
### [1. Mock API Controller](https://github.com/Goryeojin/hhplus-concert/blob/step6/src/main/java/hhplus/concert/application/controller/MockApiController.java)

## 패키지 구조
![img.png](img.png)   
> DDD 기반 4 Layered Architecture 채택하였습니다.

- `infrastructure`: presentation 계층으로, `controller` 및 `dto` 를 관리합니다. (추후 필터나 인터셉터 추가 예정)
- `application`: application 계층으로, `UseCase` 구현을 위해 Domain 과 분리하여 `Facade` 를 관리합니다.
- `domain`: domain 계층으로, 비즈니스 로직 구현을 담당합니다. `Service`, `Domain Object`, `Repository Interface`를 관리합니다.
- `interfaces`: interfaces 계층으로, Database 와 연동하여 쿼리를 수행하는 등 `Repository`, `Entity` 등을 관리합니다.

## 기술 스택
- Java 17
- Spring Boot 3.4
- Gradle build
- h2

- 프로젝트 의존성
  - Spring Data JPA
  - Spring Web
  - Spring Validation
  - Spring Boot Configuration
  - JUnit5
  - Lombok
