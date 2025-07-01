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
- 
