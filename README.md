# LiveScore

Welcome to LiveScore - a web application for tracking live scores of sports games.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Configuration](#configuration)
  - [application.yaml](#application-yaml)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

To run LiveScore, you will need the following software:

- [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/)
- [PostgreSQL](https://www.postgresql.org/download/)
- [pgAdmin](https://www.pgadmin.org/download/)

## Getting Started

To get started with LiveScore, follow these steps:

1. Clone the LiveScore repository using Git: git clone https://github.com/Arsensio/LiveScore.git
2. Open the project in IntelliJ IDEA.

3. Create a new database in PostgreSQL using pgAdmin. We recommend naming it `live_score`.

4. Copy the content of the `application.yaml` file in the `src/main/resources` folder, and create a new file named `application.yaml` in the same folder. Paste the content into the new file, and replace the values for `username` and `password` with your PostgreSQL credentials.

5. Run the application. You can access it at `http://localhost:8080`. The Swagger documentation is available at `http://localhost:8080/swagger-ui/`.

## Usage

...

## Configuration

### application.yaml

The `application.yaml` file contains the configuration for the LiveScore application. It should be located in the `src/main/resources` folder. Here is an example configuration:

```yaml
spring:
datasource:
 url: jdbc:postgresql://localhost:5432/live_score
 driver-class-name: org.postgresql.Driver
 username: // your username for the database
 password: // your password for the database
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
 username: // your username for the database
 password: // your password for the database
 baseline-version: 1.0

server:
port: 8081

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



## Как запустить проект?

1. Скачать и установить IntelliJ Idea Community edition. (Можно и другие ИДЕшки, этот просто удобнее будет)
2. Скачать и установить базу данных PostgreSQL, и клиент к нему PgAdmin. (когда будете креды заполнять запишите их куда
   то, понадобятся)
3. Откройте PgAdmin, зайдите под своими кредами и создайте базу данных. Мы обычно называем ее live_score
4. Скачайте наш проект, перейдя по ссылке https://github.com/Arsensio/LiveScore.git (или можете сделать гит клон)
5. Откройте наш проект в IntelliJ, добавтье в папку \LiveScore\src\main\resources файл<a id="application-yaml"></a>Configuration File: application.yaml (пример файла скину, там надо будет свои креды из постгреса написать, и имя базы данных)
6. Запускаете проект, готово! Приложение работает по адресу localhost:8080. Swagger документацию найдете по
   адресу http://localhost:8080/swagger-ui/

## Configuration File: application.yaml
application.yaml
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/live_score
    driver-class-name: org.postgresql.Driver
    username: //ваше имя пользевателя БД
    password: //ваш пароль от БД
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
    username: //ваше имя пользевателя БД
    password: //ваш пароль от БД
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
