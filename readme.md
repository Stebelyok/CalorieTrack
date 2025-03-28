# Трекер калорий

**Описание**  
Трекер калорий — это приложение, предназначенное для контроля питания и отслеживания потребления калорий. Оно помогает пользователям отслеживать приемы пищи, контролировать их калорийность и следить за достижением целей, таких как похудение, поддержание веса или набор массы.

### Основные функции:
- **Добавление приемов пищи:** Пользователи могут добавлять свои приемы пищи (завтрак, обед, ужин, перекус) и связывать их с блюдами.
- **Добавление блюд:** Каждый прием пищи может содержать одно или несколько блюд с указанием их калорийности, белков, жиров и углеводов.
- **Автоматическое вычисление калорийности:** На основе данных о весе, возрасте, росте, поле и целях пользователя рассчитывается суточная норма калорий, которую следует потреблять.
- **Генерация отчетов:** Пользователи могут получать отчеты о потребленных калориях за день или за выбранный период.
- **Оповещения о превышении нормы калорий:** Приложение информирует пользователей, если они превышают свою суточную норму калорий.

### Стек технологий:
- **Backend:**
    - Java 17
    - Spring Boot
    - Spring Data JPA
    - PostgreSQL
    - JUnit 5 для тестирования
    - Lombok для упрощения кода
    - MapStruct для преобразования DTO

- **API:**
    - OpenAPI 3.0 для документирования API

### Архитектура:
Приложение построено по принципу микросервисов с использованием RESTful API. Основная логика включает сервисы для работы с пользователями, приемами пищи и отчетами, а также для вычисления калорийности на основе методов Харриса-Бенедикта.

### Описание API:

#### Пользователи:
- **POST /api/users** — Создание нового пользователя (необходимы данные: имя, возраст, пол, рост, вес, цель).

#### Приемы пищи:
- **POST /api/{userId}/meals** — Добавление нового приема пищи для пользователя (необходимы данные: тип приема пищи, блюда, дата).

#### Блюда:
- **POST /api/{userId}/dishes** — Добавление нового блюда для пользователя (необходимы данные: название блюда, калорийность, белки, жиры, углеводы).

#### Отчеты:
- **GET /api/{userId}/reports/daily-calories** — Получение отчета по калориям за день.
- **GET /api/{userId}/reports/meal-history** — Получение истории приемов пищи за выбранный период.
- **GET /api/{userId}/reports/calorie-limit** — Проверка, уложился ли пользователь в свою дневную норму калорий.

### Работа с датами и временем:
В приложении используется класс **`LocalDateTime`** для работы с датами и временем. Это означает, что время, связанное с приемами пищи и отчетами, представлено без привязки к временной зоне. **`LocalDateTime`** хранит только дату и время в формате `yyyy-MM-ddTHH:mm:ss`, без учета часового пояса.

### 1. Запуск без Docker

Для того чтобы запустить приложение без Docker, выполните следующие шаги:

1. Склонируйте репозиторий:
    ```bash
    git clone https://github.com/your-repo/CalorieTrack.git
    cd CalorieTrack
    ```

2. Убедитесь, что у вас установлен [JDK 17+](https://adoptopenjdk.net/) и [Maven](https://maven.apache.org/).

3. Настройте базу данных PostgreSQL:
  - Убедитесь, что база данных PostgreSQL доступна и настроена с использованием следующих данных:
    - Хост: `localhost`
    - Порт: `5433`
    - База данных: `calorietrackdb`
    - Пользователь: `user`
    - Пароль: `pass`

4. Соберите и запустите приложение:
    ```bash
    mvn clean spring-boot:run
    ```

Приложение будет доступно по адресу: `http://localhost:8081`.

## 2. Запуск с использованием Docker

Для запуска приложения с использованием Docker Compose выполните следующие шаги:

### 2.1. Требования

- Установите [Docker](https://www.docker.com/) и [Docker Compose](https://docs.docker.com/compose/install/).

### 2.2. Запуск приложения с Docker Compose

1. В том же каталоге, где находится `docker-compose.yml`, выполните команду:

    ```bash
    docker-compose up --build
    ```

   Эта команда:
  - Соберет Docker-образ для вашего приложения.
  - Запустит контейнер, который будет доступен на порту `8081`.

2. Приложение будет доступно по адресу:

    ```
    http://localhost:8081
    ```