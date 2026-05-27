# Smart Parking Management System 🚗

A full-stack Smart Parking Management System built using **Spring Boot** and **Angular 17**.  
The application provides real-time parking slot management, booking, QR-based vehicle entry/exit, payment tracking, towing management, and admin analytics.

---

## 🏗️ Tech Stack

| Layer | Technology |
|--------|------------|
| Frontend | Angular 17, Bootstrap 5, Bootstrap Icons |
| Backend | Java 17, Spring Boot 3.2, Spring Data JPA, Hibernate |
| Database | H2 In-Memory Database |
| API Documentation | Swagger / OpenAPI |
| QR Code | ZXing Library |
| Build Tool | Maven |

---

## 📁 Project Structure

```text
smartParking/
├── src/main/java/com/parking/smartparking/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   ├── dto/
│   ├── enums/
│   ├── exception/
│   ├── util/
│   └── config/
│
├── src/main/resources/
│   ├── application.properties
│   └── data.sql
│
├── angular-frontend/
│   └── src/app/
│       ├── components/
│       ├── services/
│       ├── models/
│       └── guards/
│
├── database/
│   └── smartparking.sql
│
├── pom.xml
└── README.md