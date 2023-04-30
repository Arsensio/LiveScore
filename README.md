# LiveScore

## Как запустить проект?

1. Скачать и установить IntelliJ Idea Community edition. (Можно и другие ИДЕшки, этот просто удобнее будет)
2. Скачать и установить базу данных PostgreSQL, и клиент к нему PgAdmin. (когда будете креды заполнять запишите их куда
   то, понадобятся)
3. Откройте PgAdmin, зайдите под своими кредами и создайте базу данных. Мы обычно называем ее live_score
4. Скачайте наш проект, перейдя по ссылке https://github.com/Arsensio/LiveScore.git (или можете сделать гит клон)
5. Откройте наш проект в IntelliJ, добавтье в папку \LiveScore\src\main\resources файл application.yaml (пример файла
   скину, там надо будет свои креды из постгреса написать, и имя базы данных)
6. Запускаете проект, готово! Приложение работает по адресу localhost:8080. Swagger документацию найдете по
   адресу http://localhost:8080/swagger-ui/


```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/live_score
    driver-class-name: org.postgresql.Driver
    username: postgres
    password: 123
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        jdbc:
          lob:
            non_contextual_creation: true
  flyway:
    locations: classpath:db/migration
    baseline-on-migrate: true
    url: jdbc:postgresql://localhost:5432/live_score
    user: postgres
    password: 123
    baseline-version: 1.0

server:
  port : 8081



logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE

jwt:
  token:
    secret: 26452948404D6351655468576D5A7134743777217A25432A462D4A614E645267
    expired: 10800000

gcp:
  firebase:
    service-account: classpath:livescoresdu-firebase-adminsdk-6p6r9-e69205f00f.json
