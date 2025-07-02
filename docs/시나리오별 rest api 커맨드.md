# 카프카 모니터링
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic ktminithteam --from-beginning 

## Author

- 작가 등록 (작가)
  - http POST localhost:8084/authors name="tester" email="test@aivle.com" password="testtest" introduction="I'm tester"
- 작가 목록 (관리자)
  - http GET localhost:8084/authors
- 작가 승인 (관리자)
  - http PATCH localhost:8084/authors/{id}/accept
- 작가 비승인 (관리자)
  - http PATCH localhost:8082/authors/{id}/reject
- 작가 로그인 (작가)
  - http POST localhost:8084/authors/login email="test@aivle.com" password="testtest"

## Manuscript

- 원고 등록 (작가)
  - http POST localhost:8084/manuscripts title="test manuscript" content="test content" authorId=1
- 원고 목록 (작가)
  - http GET localhost:8084/manuscripts?authorId=1
- 원고 조회 (작가)
  - http GET localhost:8084/manuscripts/{id}
- 원고 임시 수정 (작가)
  - http PUT localhost:8084/manuscripts/{id}/save-temp title="save-temp manuscript" content="save-temp content"
- 원고 최종 저장 (작가)
  - http PUT localhost:8084/manuscripts/{id}/save-final title="save-final manuscript" content="save-final content"
- 원고 출판 신청 (작가)
  - http POST localhost:8084/manuscripts/{id}/request-publish
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
  - http POST localhost:8083/subscribers name="subscriber" email="subscriber@aivle.com" password="subsub" phoneNumber="01012345678" telecom="SKT"
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
  - http POST localhost:8083/subscribers/login email="subscriber@aivle.com" password="subsub"

- 구독자 정보 조회(구독자)
  - http GET localhost:8083/subscribers/{id}

- 추천 종료(구독자)
  - http PATCH localhost:8083/subscribers/{id}/recommend-off
 
- kt인증(구독자)
  - http PATCH localhost:8083/subscribers/{id}/kt-auth phoneNumber="01012345678" telecom="KT"
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
  - http POST localhost:8083/subscribes subscriberId=1 publishId=1
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
## Point

- 구독권 구매(구독자)
  - http localhost:8085/points/purchase subscriberId=1

- **구독 서비스 이벤트 수신 (내부)**
- 구독 요청
  - Kafka 이벤트: 'RequestSubscribed'
  - event
  ```json
  {
    "eventType":"RequestSubscribed",
    "timestamp":1751347414574,
    "subscribeId":3,
    "subscriberId":1,
    "publishId":2,
    "cost":1100
  }
  ```

- 회원가입 포인트 지급 요청
  - Kafka 이벤트: 'SignedUp'
  - event
    ```json
    {
      "eventType": "SignedUp",
      "timestamp": 1751310119264,
      "subscriberId": 1,
      "telecom": "SKT"
    }
    ```
- KT 인증 포인트 지급 요청
  - Kafka 이벤트: 'Verified'
  - event
    ``` json
    {
      "eventType":"Verified",
      "timestamp":1751347037552,
      "subscriberId":1,
      "telecom":"KT"
    }
    ```

- **포인트 서비스 이벤트 송신 (내부)**
- 구독 성공시
  - Kafka 이벤트: 'Substart'
  - event
  - 포인트 소모
    ```json
    {
      "eventType":"Substart",
      "timestamp":1751347108904,
      "id":5,"subscribeId":5,
      "subscriberId":1,
      "expirationDate":"null"
    }
    ```
  - 구독권 사용
    ```json
    {
      "eventType":"Substart",
      "timestamp":1751347108904,
      "id":5,"subscribeId":5,
      "subscriberId":1,
      "expirationDate":"2025-07-31"
    }
    ```
- 구독 실패시   
  - Kafka 이벤트: 'RejectSubscribe'
  - event
    ```json
    {
      "eventType":"RejectSubscribe",
      "timestamp":1751347414582,
      "id":6,
      "subscribeId":3,
      "subscriberId":1
    }
    ```

## Publisher

- 출간 요청 이벤트 수신 (내부)
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
        
- 출간 전체 목록 조회 (관리자)
    - http GET localhost:8086/publishes
        
- 출간 상세 조회 (관리자)
    - http GET localhost:8086/publishes/{id}

- 작가 출간 목록(작가)
  - http GET localhost:8086/publishes?authorId=1

- 출간 재요청(작가)
  - http POST localhost:8086/publishes/{id}/retry

- 출간 확정(작가)
  - http PUT localhost:8086/publishes/{id}/confirm
  - event
  ```json
  {
    "eventType":"BookPublished",
    "timestamp":1751348164056,
    "publishId":1,
    "manuscriptId":2,
    "title":"test manuscript",
    "content":"save-final content",
    "summaryUrl":"- 최종 콘텐츠 저장\n- 완성된 콘텐츠 보관\n- 최종 버전 유지",
    "coverUrl":"https://oaidalleapiprodscus.blob.core.windows.net/private/org-Dm26yx9HwJf0UwvmLDm571SQ/user-mP6IHNB5vK15VgtEbAfVhHVW/img-nYndwPSLS63tevTYaUBlgXuI.png?st=2025-07-01T04%3A33%3A27Z&se=2025-07-01T06%3A33%3A27Z&sp=r&sv=2024-08-04&sr=b&rscd=inline&rsct=image/png&skoid=cc612491-d948-4d2e-9821-2683df3719f5&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2025-06-30T19%3A41%3A10Z&ske=2025-07-01T19%3A41%3A10Z&sks=b&skv=2024-08-04&sig=2pfORxY3GTk8VSqaIRzbGBJslisvaE4YP33hQtIYB5I%3D",
    "category":"정리",
    "cost":800,
    "isAccept":true,
    "createdAt":"2025-07-01T05:36:04.055+00:00"
  }
  ```

# 서재 플랫폼
- 출간된 책 목록 조회 (구독자)
  - http GET localhost:8087/books
- 출간된 책 상세 조회 (구독자)
  - http GET localhost:8087/books/{id}
- 내 서재 조회 (구독자)
  - http GET localhost:8087/mybooks?subscriberId=1
- 구독수 내림차순 책목록
  - http GET localhost:8087/recommendbooks
