## KT 걷다가 서재
- 작가가 글을 쓰고 AI가 자동으로 표지 이미지를 만들어 전자책으로 등록해 주는 출간 자동화 플랫폼
- 구독자가 월정액으로 다양한 책을 무제한 열람하며, KT 고객에게는 특별 포인트 혜택이 제공
- 3회 이상 열람된 도서는 ‘베스트 셀러’로 선정되어 더 많은 독자들에게 노출

## 주요 기능
- 작가 등록 및 로그인
- 원고 등록 / 임시 저장 / 최종 저장
- 출간 요청 및 출간 확정
- AI 자동화 (요약 생성, 카테고리 분류, 커버 이미지 생성)
- 관리자 페이지에서 전체 목록 확인 및 승인 처리 등

## 기술 스택
| 분류 | 역할 | 적용 스택 |
| --- | --- | --- |
| 백엔드 | 마이크로서비스 설계 및 개발 | `SpringBoot, Kafka` |
| AI | 책 요약 생성, 카테고리 분류, 커버 생 | `OpenAI GPT, DALL·E` |
| 클라우드 | Azure 인프라 구성 | `AKS, ACR` |
| 운영/배포 | 무정지 배포, 셀프 힐링 | `Docker, Kubernetes` |
| 운영/배포 | CI/CD 파이프라인 구축 | `Azure DevOps` |
| 모니터링 | 사이드카 패턴으로 트래픽 수집 | `Istio` |
| 모니터링 | 리소스 모니터링 | `Grafana` |
| 모니터링 | 로그 모니터링 | `Grafana` |

## Model
www.msaez.io/#/152073691/storming/2ee24e4d50bc99be30a82ac1b1e37c84

## Cloud Architecture
![제목 없는 다이어그램 drawio](https://github.com/user-attachments/assets/309c8327-e00f-4c67-aa00-105207e0b1c1)


## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- author
- subscribermanagement
- manuscript
- point
- publisher
- bookplatform


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- author
```
 http :8088/authors id="id"name="name"introduction="introduction"createdAt="createdAt"password="password"isAccept="isAccept"
```
- subscribermanagement
```
 http :8088/subscribers id="id"name="name"email="email"subscription="subscription"password="password"expirationDate="expirationDate"telecom="telecom"phoneNumber="phoneNumber"isRecommended="isRecommended"
 http :8088/subscribes id="id"subscriberId="subscriberId"publishId="publishId"status="status"expirationDate="expirationDate"
```
- manuscript
```
 http :8088/manuscripts manuscriptId="manuscriptId"authorId="authorId"title="title"content="content"createdAt="createdAt"updatedAt="updatedAt"
```
- point
```
 http :8088/points id="id"subscriberId="subscriberId"point="point"
```
- publisher
```
 http :8088/publishes publishId="publishId"bookId="bookId"isAccept="isAccept"summaryUrl="summaryUrl"coverUrl="coverUrl"content="content"createdAt="createdAt"category="category"cost="cost"title="title"
```
- bookplatform
```
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```
