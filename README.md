## Приложение поддерживает запуск с базами H2 и PostgreSQL.
## FatJAR по дефолту запускается с H2; Docker Compose по дефолту использует PostgreSQL
### Инструкция по запуску Docker-версии
#### Windows:
Запуск: ```docker compose build; docker compose up -d```
<br>
Логи: ```docker compose logs -f```

#### Linux:
Запуск: ```docker compose build && docker compose up -d```
<br>
Логи: ```docker compose logs -f```

## 📑 Технологический стек

- Java 17
- Spring Boot
- H2
- Liquibase
- PostgreSQL
- Docker Compose

## 📊 Пример данных

Таблица сотрудников:

| **id** | **name**            | **username** | **password**   | 
|--------|---------------------|--------------|----------------|
| 1      | Ivanov Ivan (ADMIN) | ivanov         | HelloWorld123@ | 
| 2      | Petrov Petr         | petrov         | HelloWorld123@ | 
| 3      | Kozlov Oleg         | kozlov         | HelloWorld123@ | 
| 4      | Smirnova Anna       | smirnova         | HelloWorld123@ | 


Таблица мест для бронирования:

| **id** | **place_name**  |
|--------|-----------------|
| 1      | K-19    | 
| 2      | M-16    |
| 3      | T-1   |

Таблица переговорки:

| **id** | **name**            | **username** | **password**   | 
|--------|---------------------|--------------|----------------|
| 1      | K-19  | peregovor1   | HelloWorld123@ | 
| 2      | M-16        | peregovor2   | HelloWorld123@ | 
| 3      | T-1         | peregovor3   | HelloWorld123@ |



Таблица бронирований:

| **id** | **date**  | **place_id** | **employee_id**  |
|--------|-----------|--------------|------------------|
| 1      | 2025-11-08 | 1 | 1 |
| 2      | 2025-11-10 | 2 | 2 |

