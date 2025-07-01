## 카프카 모니터링
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic ktminithteam --from-beginning 

## Author

- 작가 등록 (작가)
  - http POST localhost:8082/authors name="tester" email="test@aivle.com" password="testtest" introduction="I'm tester"
- 작가 목록 (관리자)
  - http GET localhost:8082/authors
- 작가 승인 (관리자)
  - http PATCH localhost:8082/authors/{id}/accept
- 작가 비승인 (관리자)
  - http PATCH localhost:8082/authors/{id}/reject
- 작가 로그인 (작가)
  - http POST localhost:8082/authors/login email="test@aivle.com" password="testtest"

---

## Manuscript

- 원고 등록 (작가)
  - http POST localhost:8082/manuscripts title="test manuscript" content="test content" authorId=1
- 원고 목록 (작가)
  - http GET localhost:8082/manuscripts?authorId=1
- 원고 조회 (작가)
  - http GET localhost:8082/manuscripts/{id}
- 원고 임시 수정 (작가)
  - http PUT localhost:8082/manuscripts/{id}/save-temp title="save-temp manuscript" content="save-temp content"
- 원고 최종 저장 (작가)
  - http PUT localhost:8082/manuscripts/{id}/save-final title="save-final manuscript" content="save-final content"
- 원고 출판 신청 (작가)
  - http POST localhost:8082/manuscripts/{id}/request-publish
  - event
  ```json
  {
    "eventType": "PublishRequestedEvent",
    "timestamp": 1751309009238,
    "manuscriptId": 1,
    "authorId": 1,
    "title": "test manuscript",
    "content": "save-final content",
    "requestedAt": "2025-06-30T18:43:29.240+00:00"
  }
  ```
## Subscription
- 구독자 가입(구독자)
  - http POST localhost:8082/subscribers name="subscriber" email="subscriber@aivle.com" password="subsub" phoneNumber="01012345678" telecom="SKT"
  - event
  ```json
  {
    "eventType": "SignedUp",
    "timestamp": 1751310119264,
    "subscriberId": 1,
    "telecom": "SKT"
  }
  ```

- 구독자 로그인 (구독자)
  - http POST localhost:8082/subscribers/login email="subscriber@aivle.com" password="subsub"

- 구독자 정보 조회(구독자)
  - http GET localhost:8082/subscribers/{id}

- 추천 종료(구독자)
  - http PATCH localhost:8082/subscribers/{id}/recommend-off
 
- kt인증(구독자)
  - http PATCH localhost:8082/subscribers/kt-auth phoneNumber="01012345678" telecom="KT"
  - event
  ```json
  {
    "eventType":"Verified",
    "timestamp":1751312303084,
    "subscriberId":1,
    "telecom":"KT"
  }
  ```
- 구독 신청(구독자)
  - http POST localhost:8082/subscriptions subscriberId=1 manuscriptId=1
  - event
  ```json
  {
    "eventType":"RequestSubscribed",
    "timestamp":1751313346636,
    "subscribeId":1,
    "subscriberId":1,
    "publishId":1,
    "cost":null
  }
  ```

## Publisher

- **출간 요청 이벤트 수신 (내부)**
    - Kafka 이벤트: `PublishRequestedEvent`
    - event
        
        ```json
        {
          "eventType": "PublishRequestedEvent",
          "timestamp": 1751309009238,
          "bookId": 1,
          "authorId": 1,
          "title": "test manuscript",
          "content": "save-final content",
          "requestedAt": "2025-06-30T18:43:29.240+00:00"
        }
        ```
        
    - 이벤트 수신 시 `Publish` 도메인에 저장 (초기 상태: `isAccept=false`)
- **AI 결과 반영 (AI 자동화 서비스)**
    - HTTP API:
        
        `POST localhost:8086/publish/{id}/ai-result`
        
    - 요청 Body 예시:
        
        ```json
        {
          "summaryUrl": "https://s3.bucket/summary.pdf",
          "coverUrl": "https://s3.bucket/cover.jpg",
          "category": "소설"
        }
        ```
        
    - 해당 출간 데이터에 AI가 생성한 요약, 표지, 카테고리 정보 반영
- **출간 확정 (관리자)**
    - HTTP API:
        
        `PUT localhost:8086/publish/{id}/confirm`  
        
    - 출간 확정 처리 후 Kafka 이벤트 `PublishedEvent` 발행
    - `PublishedEvent`
        
        ```json
        {
          "eventType": "PublishedEvent",
          "timestamp": 1751310000000,
          "publishId": 1,
          "bookId": 1,
          "isAccept": true,
          "summaryUrl": "https://s3.bucket/summary.pdf",
          "coverUrl": "https://s3.bucket/cover.jpg",
          "content": "save-final content",
          "category": "소설",
          "cost": 10000,
          "title": "test manuscript",
          "createdAt": "2025-06-30T19:00:00.000+00:00"
        }
        ```
        
- **출간 전체 목록 조회 (관리자)**
    - HTTP API:
        
        `GET localhost:8086/publish`
        
- **출간 상세 조회 (관리자)**
    - HTTP API:
        
        `GET localhost:8086/publish/{id}`
        
- **출간 요청 수동 테스트 (개발자)**
    - HTTP API:
        
        `POST localhost:8086/publish/request-test`
        
    - 요청 Body 예시:
        
        ```json
        {
          "title": "dummy title",
          "content": "dummy content",
          "bookId": 1
        }
        ```
        
    - 수동으로 `PublishRequestedEvent` 이벤트 발행
