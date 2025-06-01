# 🔗 URL Shortener Service (Spring Boot + MySQL)

A minimal, production-ready URL shortener service built with **Java**, **Spring Boot**, and **MySQL**.  
It supports URL shortening, redirection, and usage tracking with a clean structure and future extensibility in mind.

---

## 🚀 Features

- ✅ Shorten long URLs into compact short codes
- ✅ Resolve short codes to original URLs with redirection
- ✅ Track how often a short URL has been:
  - **Created**
  - **Accessed**
- ✅ Idempotent URL creation (same URL won't generate duplicates)
- ✅ Simple, layered architecture for easy extension

---

## 🧱 Tech Stack

| Layer        | Technology               |
|--------------|---------------------------|
| Backend      | Java 21, Spring Boot      |
| Build Tool   | Gradle                    |
| Database     | MySQL                     |
| ORM          | Spring Data JPA + HikariCP|
| Validation   | Jakarta Bean Validation   |

---

## 📂 Project Structure
```
url-shortener/
├── src/
│ ├── main/java/
│ │ ├── controller/ # REST API endpoints
│ │ ├── dto/ # DTOs for input/output
│ │ ├── model/ # JPA entity
│ │ ├── repository/ # Spring Data repository
│ │ └── service/ # Business logic
│ └── resources/
│ ├── application.yaml # DB config & app settings
├── build.gradle
├── settings.gradle
├── README.md
└── .gitattributes
```




---

## ⚙️ Setup Instructions

### ✅ Prerequisites

- Java 21+
- Gradle (or use `./gradlew`)
- MySQL database running (or use Docker)

### 🔧 Database Setup

1. Create a MySQL database:

```sql
CREATE DATABASE url-shortener;
```

2. Add a user with access or use root.

3. Update your application.properties:
```properties
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/url-shortener
spring.datasource.username=<<<YOUR USERNAME>>>
spring.datasource.password=<<<YOUR PASSWORD>>>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
```


## 📡 API Endpoints
### POST /shorten
Shorten a long URL or return the existing mapping.

#### Request Body (application/json):
```json
{
  "originalUrl": "https://www.example.com"
}
```
#### Response:
```json
{
  "originalUrl": "https://www.example.com",
  "shortUrl": "http://localhost:8080/12ab34cd",
  "visitCount": 0,
  "createRequestCount": 1
}
```

### GET /{shortCode}
Redirects to the original URL and increments visitCount.
Example:
```
GET http://localhost:8080/abcd1234
→ Redirects to https://www.example.com
```


## 💡 Notes
- Focused on simplicity and readability.
- Ideal for real-world deployment with minimal setup.
- Easily extensible

