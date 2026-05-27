# Smart Parking Management System

A full-stack **CRUD-based parking management system** built with **Spring Boot** (backend) and **Angular 17** (frontend), using **H2** in-memory database.

## 🏗 Tech Stack

| Layer | Technology |
|-------|-----------|
| **Frontend** | Angular 17, Bootstrap 5, Bootstrap Icons |
| **Backend** | Java 17, Spring Boot 3.2, Spring Data JPA, Hibernate |
| **Database** | H2 (In-Memory) |
| **API Docs** | Swagger / OpenAPI (SpringDoc) |
| **QR Code** | ZXing Library |
| **Build Tool** | Maven |

## 📁 Project Structure

```
smartParking/
├── src/main/java/com/parking/smartparking/
│   ├── controller/     (9 REST controllers)
│   ├── service/        (11 service classes)
│   ├── repository/     (6 JPA repositories)
│   ├── entity/         (6 JPA entities)
│   ├── dto/            (9 DTO classes)
│   ├── enums/          (4 enum types)
│   ├── exception/      (4 exception classes)
│   ├── util/           (3 utility classes)
│   └── config/         (2 config classes)
├── src/main/resources/
│   ├── application.properties
│   └── data.sql        (sample seed data)
├── angular-frontend/
│   └── src/app/
│       ├── components/ (8 components)
│       ├── services/   (5 services)
│       ├── models/     (5 model files)
│       └── guards/     (2 route guards)
├── database/
│   └── smartparking.sql
├── pom.xml
└── README.md
```

## 🚀 Setup & Run Instructions

### Prerequisites
- **Java 17+** (JDK)
- **Maven 3.8+**
- **Node.js 18+** and **npm**
- **Angular CLI** (`npm install -g @angular/cli@17`)

### Backend Setup

```bash
# 1. Navigate to project root
cd smartParking

# 2. Build the project
mvnw clean install -DskipTests     # Windows
./mvnw clean install -DskipTests   # Mac/Linux

# 3. Run the application
mvnw spring-boot:run               # Windows
./mvnw spring-boot:run             # Mac/Linux
```

Backend starts at: **http://localhost:8080**

### Frontend Setup

```bash
# 1. Navigate to frontend directory
cd angular-frontend

# 2. Install dependencies
npm install

# 3. Start development server
ng serve
```

Frontend starts at: **http://localhost:4200**

### Access Points

| URL | Description |
|-----|-------------|
| http://localhost:4200 | Angular Frontend |
| http://localhost:8080/swagger-ui.html | Swagger API Documentation |
| http://localhost:8080/h2-console | H2 Database Console |

### H2 Console Login
- **JDBC URL:** `jdbc:h2:mem:smartparkingdb`
- **Username:** `sa`
- **Password:** *(leave empty)*

## 👤 Demo Credentials

| Role | Email | Password |
|------|-------|----------|
| **Admin** | admin@smartparking.com | admin123 |
| **User** | rahul@example.com | password123 |
| **User** | priya@example.com | password123 |

## 📋 Modules

1. **Authentication** - Register / Login
2. **User Management** - CRUD operations
3. **Parking Locations** - Admin CRUD
4. **Parking Slots** - Admin CRUD with status tracking
5. **Booking Management** - Book slots, view bookings, cancel
6. **QR Code Generation** - ZXing-based QR for each booking
7. **Entry & Exit** - Security staff QR scanning
8. **Billing & Payments** - Duration-based fee calculation
9. **Towing Management** - Overstay detection (24hrs)
10. **Reports & Dashboard** - Admin analytics

## 🔗 API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login |

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Parking Locations
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/locations` | Get all locations |
| GET | `/api/locations/active` | Get active locations |
| POST | `/api/locations` | Create location |
| PUT | `/api/locations/{id}` | Update location |
| DELETE | `/api/locations/{id}` | Delete location |

### Parking Slots
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/slots` | Get all slots |
| GET | `/api/slots/available` | Get available slots |
| GET | `/api/slots/available/{locationId}` | Available by location |
| GET | `/api/slots/available/{locationId}/{vehicleType}` | Filter by type |
| POST | `/api/slots?locationId={id}` | Create slot |
| PUT | `/api/slots/{id}` | Update slot |
| DELETE | `/api/slots/{id}` | Delete slot |

### Bookings
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/bookings` | Create booking |
| GET | `/api/bookings` | Get all bookings |
| GET | `/api/bookings/{id}` | Get booking by ID |
| GET | `/api/bookings/user/{userId}` | Get user's bookings |
| DELETE | `/api/bookings/{id}` | Cancel booking |

### Entry & Exit
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/entry` | Record vehicle entry |
| POST | `/api/exit` | Record vehicle exit |

### Payments
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/payments` | Process payment |
| GET | `/api/payments` | Get all payments |
| GET | `/api/payments/booking/{id}` | Get payment by booking |
| GET | `/api/payments/revenue` | Get total revenue |

### Towing
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/towing/detect` | Detect overstayed vehicles |
| POST | `/api/towing/{bookingId}` | Create towing request |
| GET | `/api/towing` | Get all towing requests |

### Reports
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/reports/dashboard` | Dashboard statistics |
| GET | `/api/reports/bookings` | Bookings report |
| GET | `/api/reports/payments` | Payments report |
| GET | `/api/reports/revenue` | Revenue report |

## 📝 Sample API Requests

### Register User
```json
POST /api/auth/register
{
  "name": "Test User",
  "email": "test@example.com",
  "mobile": "9876543200",
  "password": "password123",
  "vehicleNumber": "MH-14-XY-9999"
}
```

### Login
```json
POST /api/auth/login
{
  "email": "admin@smartparking.com",
  "password": "admin123"
}
```

### Create Booking
```json
POST /api/bookings
{
  "userId": 2,
  "slotId": 1,
  "vehicleNumber": "MH-12-AB-1234"
}
```

### Vehicle Entry
```json
POST /api/entry
{ "bookingId": 1 }
```

### Vehicle Exit
```json
POST /api/exit
{ "bookingId": 1 }
```

### Process Payment
```json
POST /api/payments
{ "bookingId": 1 }
```

## 💰 Fee Structure

| Vehicle Type | Rate per Hour (₹) |
|-------------|-------------------|
| CAR | 50 |
| BIKE | 20 |
| TRUCK | 100 |

## 🔄 Complete Parking Workflow

```
Register → Login → Select Location → Select Slot → Book Slot
→ QR Code Generated → Security Scans QR (Entry)
→ Vehicle Parked → Security Scans QR (Exit)
→ Fee Calculated → Payment Made → Slot Released
```

If vehicle overstays 24+ hours → Marked as TOWED → Admin manages towing.
