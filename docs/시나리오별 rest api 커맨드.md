## Author
- 작가 등록(작가)
  - http POST localhost:8082/authors name="tester" email="test@aivle.com" password="testtest" introduction="I'm tester"
- 작가 목록(관리자)
  - http GET localhost:8082/authors
- 작가 승인(관리자)
  - http PATCH localhost:8082/authors/{id}/accept
- 작가 비승인(관리자)
  - http PATCH localhost:8082/authors/{id}/reject
- 작가 로그인(작가)
  - http POST localhost:8082/authors/login email="test@aivle.com" password="testtest"

## 