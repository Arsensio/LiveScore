# LiveScore

Добро пожаловать в LiveScore - веб-приложение для отслеживания результатов спортивных игр в режиме реального времени.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
  - [application.yaml](#application-yaml)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

Для запуска LiveScore вам понадобится следующее программное обеспечение:

- [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/)
- [PostgreSQL](https://www.postgresql.org/download/)
- [pgAdmin](https://www.pgadmin.org/download/)

## Getting Started

Чтобы начать работу с LiveScore, выполните следующие действия:

1. Клонируйте репозиторий LiveScore с помощью Git: git clone https://github.com/Arsensio/LiveScore.git
2. Откройте проект в IntelliJ IDEA.

3. Создайте новую базу данных в PostgreSQL с помощью pgAdmin. Мы рекомендуем называть его `live_score`.

4. Скопируйте содержимое файла `application.yaml`, после создайте файл, назовите его `application.yaml` и вставьте содержимое. После разместите этот файл в папку `src/main/resources`. После в файле замените значине `username` и `password` с вашими учетными данными PostgreSQL.

5. Запустите приложение. Вы можете получить к нему доступ по адресу `http://localhost:8080`. Документация Swagger доступна по адресу `http://localhost:8080/swagger-ui/`.


## Configuration

### application.yaml

`application.yaml` файл содержить конфигурацию для приложения LiveScore. После разместите этот файл в папку `src/main/resources`. Ниже приведена конфигурация:

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
