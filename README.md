# Edu Block API

Edu Block API — это RESTful веб-приложение на Java с использованием Spring Boot. Проект построен с учётом современных практик и архитектуры MVC.

## 🔧 Технологии

- Java 21
- Spring Boot 3.4.4
- Spring Data JPA
- Hibernate
- PostgreSQL
- Flyway (миграции)
- MapStruct (маппинг DTO ↔ Entity)
- Lombok
- Testcontainers + JUnit 5
- SpringDoc OpenAPI (Swagger UI)
- Gradle

## ⚙️ Конфигурация профилей

Приложение поддерживает запуск с использованием разных embedded-серверов на основе активного Spring-профиля:

| Профиль  | Сервер | Порт |
|----------|--------|------|
| `tomcat` | Tomcat | 8081 |
| `jetty`  | Jetty  | 8082 |

По умолчанию используется профиль `tomcat`.

### Пример запуска с указанием профиля:

```bash
./gradlew bootRun -Dspring.profiles.active=jetty
```

### Переменные окружения по умолчанию:

| Переменная                | Значение по умолчанию                       |
|--------------------------|--------------------------------------------|
| `SERVER_PORT`            | `8080` (переопределяется в профилях)       |
| `DATA_SOURCE_URL`        | `jdbc:postgresql://localhost:5432/edu`     |
| `DATA_SOURCE_USERNAME`   | `postgres`                                 |
| `DATA_SOURCE_PASSWORD`   | `4`                                        |

## 🗃️ Миграции базы данных

Миграции выполняются автоматически при запуске приложения с использованием [Flyway](https://flywaydb.org/). SQL-скрипты размещаются в `src/main/resources/db/migration`.

## 📄 Документация API

Документация доступна по адресу:

```
http://localhost:8081/swagger-ui/index.html#/
```

(порт зависит от активного профиля)

## 📦 Сборка и запуск

### Сборка проекта

```bash
./gradlew clean build
```

### Запуск

```bash
./gradlew bootRun
```

Или с указанием профиля:

```bash
./gradlew bootRun -Dspring.profiles.active=tomcat
```

## ✅ Тестирование

Проект использует `Testcontainers` и `JUnit 5` для интеграционного тестирования. Для запуска:

```bash
./gradlew test
```

## 🛠️ Структура проекта

```
src/
├── main/
│   ├── java/               # Исходный код
│   └── resources/
│       ├── application.yml # Основные настройки
│       └── db/migration/   # SQL миграции
└── test/                   # Тесты
```
