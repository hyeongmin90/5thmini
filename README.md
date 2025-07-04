# 

## Model
www.msaez.io/#/152073691/storming/2ee24e4d50bc99be30a82ac1b1e37c84

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
