# Technical references

## Distributed tracing 
sleuth -> micrometer migration<br>
https://www.appsdeveloperblog.com/micrometer-and-zipkin-in-spring-boot/

### Configurations 
- add log specific configurations to config server and add required pom dependencies
- then pull and run the docker image 
docker run -d -p 9411:9411 openzipkin/zipkin

### How to check logs
- http://localhost:9411/zipkin/
- here we can simply click on run query or search by traceid. We can get the traceId from console during API call as well.

## API Gateway
- After adding API gateway, all the apis should be accessible with the port defined for API gateway from the postman

### Circuit Breaker 
- this is used to check if the requested service is up/down. we need to add circuit breaker to the calling service
- For eg here we can API gateway from there we are making call to order service which internally calls product/payment service.
- So we need to add circuit breaker to api gateway to check if order service is up/down and also add circuit breaker to order service to check condition of product/payment service.
Status changes
- to configure circuit breaker in api gateway, we just need to configure in application yml file. For order service, since we are using feign clients to access payment/product service we need to configure fallback methods for both the service
- bring down any of the service and validate if we are getting proper exception message
- Reference: https://resilience4j.readme.io/docs/circuitbreaker

### Rate limitter
- Bring up redis image and add pom dependency
- docker run -p 6379:6379 -d redis
- also add the required configuration in gateway yml file
- now if we hit the api from postman too many times, we will see this in status "429 Too Many Requests" with blank response instead of 200 ok

### Build & run commands

#### Service registry
- docker build -t smanickavasa/serviceregistry:0.0.1 .
- docker run -d -p8761:8761 --name serviceregistry <imageId>

#### Config server [need to set ssh private key path and eureka server address as env variable]
- docker build -t smanickavasa/configserver:0.0.1 .
- docker run -d -p9296:9296 -v C:/Users/smanickavasa/.ssh:/root/.ssh -e SSH_PRIVATE_KEY_PATH=/root/.ssh/personal_key -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name configserver <imageId>

#### API Gateway 
- docker build -t smanickavasa/cloudgateway:0.0.1 .
- docker run -d -p9090:9090 -e CONFIG_SERVER_URL=host.docker.internal -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name cloudgateway <imageId>

# cloud-native-microservices-sample

## Design patterns
https://www.udemy.com/course/design-microservices-architecture-with-patterns-principles/

### Service Communication Patterns:
1. API Gateway Pattern
2. Gateway Routing/Aggregation/Offloading Pattern
3. Service Registry/Discovery Pattern
4. Fan-out Publish/Subscribe Messaging Pattern
5. Topic-Queue Chaining & Load Balancing Pattern

### Data Management Patterns:
6. Database-per-Service Pattern
7. Polyglot Persistence
8. Database Sharding Pattern
9. Materialized View Pattern
10. CQRS and Event Sourcing Patterns

### Deployment and Scaling Patterns:
11. Blue-green, Rolling, and Canary Deployments
12. Infrastructure as Code (IaC)
13. Serverless Microservices Architecture on AWS

### Resilience and Reliability Patterns:
14. Retry and Circuit Breaker Patterns
15. Bulkhead / Timeout / Cache / Fallback Patterns
16. Elastic Stack for Microservices Observability with Distributed Logging
17. Microservices Health Checks: Liveness, Readiness, and Performance Checks

### Microservices Architecture Patterns:
18. Backends for Frontends pattern (BFF)
19. Service Aggregator Pattern
20. Service Mesh Pattern
21. Sidecar Pattern

### Data Consistency and Transactions Patterns:
22. SAGA Pattern — Choreography-based/Orchestration-based SAGA
23. Compensating Transaction Pattern
24. Transactional Outbox Pattern
25. Dual Write Problem
26. Change Data Capture (CDC) with Outbox Pattern

### Anti-Patterns and Challenges:
27. The Shared Database Anti-Pattern
28. CAP Theorem
