#-  HCL
![alt travis build](https://travis-ci.org/zirconias/hcl_1.svg?branch=master)


- [x] Create a backend java app using spring boot
- [x] rest web services
- [x] json request/response payloads
- [x] robot store data persisted in data store, accessed via jpa 

### Data Persistence
- [x] in memory (H2 for dev profile)

### Security
- [x] in memory (H2 for dev/test profile)

### api metrics
- [x] downstream api metrics [search metrics](https://hclzir.herokuapp.com/actuator/metrics/service.api.search)
- [x] downstream api metrics [album api's metrics](https://hclzir.herokuapp.com/actuator/metrics/service.api.alubm.search)
- [x] downstream api metrics [book api's  metrics](https://hclzir.herokuapp.com/actuator/metrics/service.api.book.search)



### api documentation
- [x] api documentation for upstream and downstream services [swaager-ui](https://hclzir.herokuapp.com/swagger-ui.html)


### Stretch Goals:
- [x] deployment in cloud platform [demo](https://hclzir.herokuapp.com/api/search?term=welcome)
- [ ] resilience
- [ ] stability


### test the api
json to be imported in postman.

