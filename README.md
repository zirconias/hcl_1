#-  HCL
![alt travis build](https://travis-ci.org/zirconias/hcl_1.svg?branch=master)

- [x] rest apis
- [x] json request/response payloads

### Data Persistence
- [x] in memory (H2 for dev/test profile), bootstrap users

### Security
- [x] basic authentication, and in memory database(H2 for dev/test profile)

### api metrics
- [x] downstream api metrics [search metrics](https://hclzir.herokuapp.com/actuator/metrics/service.api.search)
- [x] downstream api metrics [album api's metrics](https://hclzir.herokuapp.com/actuator/metrics/service.api.alubm.search)
- [x] downstream api metrics [book api's  metrics](https://hclzir.herokuapp.com/actuator/metrics/service.api.book.search)



### api documentation
- [x] api documentation for upstream and downstream services [swaager-ui](https://hclzir.herokuapp.com/swagger-ui.html)


### Stretch Goals:
- [x] deployment in cloud platform [demo](https://hclzir.herokuapp.com/api/search?term=welcome)
- [x] CompletableFuture
- [ ] resilience , Exceptionally fail (to be done with resilience,  retry, cercuit breaker...)
- [ ] stability


import json file into postman to see some request examples.

