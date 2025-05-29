# 🚗 Uber Backend Clone

A backend system inspired by Uber, designed using **Java**, **Spring Boot**, **JWT Authentication**, and **Elasticsearch** to handle ride requests, real-time driver availability, and admin management features.

## 📌 Features Implemented

### ✅ User Authentication
- JWT-based secure login and registration
- Roles: Admin and Rider/Driver
- Stateless session management

### ✅ Admin Dashboard
- View all users, drivers, and rides
- Monitor system usage
- Dashboard secured using role-based access

### ✅ Ride Request & Matching
- Riders can request a ride by providing pickup and drop locations
- The system finds the **nearest available driver** (not in `IN_PROGRESS`) using Haversine distance formula
- Real-time driver filtering from Elasticsearch

### ✅ Driver Availability
- Drivers' current locations and availability are indexed in **Elasticsearch**
- Only available and free drivers are considered during matching

### ✅ Secure APIs
- All sensitive APIs are protected using JWT
- Swagger UI available at `/swagger-ui/` (accessible without auth for testing)

---

## ⚙️ Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Security (JWT)**
- **Elasticsearch**
- **JPA/Hibernate (PostgreSQL or MySQL)**
- **Swagger (Springfox 3.0)**
- **Docker** (optional for containerized deployment)

---

## 🔐 Authentication

JWT Token is required for protected endpoints.  
You can obtain a token by calling:


---

## 📍 Real-time Driver Matching Logic

- Driver locations are stored in `DriverES` index in Elasticsearch
- Distance is calculated using Haversine formula (not just straight-line)
- Only drivers who are:
  - Marked as `available = true`
  - Not having a ride with status `IN_PROGRESS`
  
are considered for matching.

---

## 🧪 Running the Project

### Prerequisites

- Java 17+
- Maven
- Elasticsearch (7.x or compatible)
- PostgreSQL / MySQL

### Steps

```bash
# Clone repo
git clone https://github.com/your-username/uber-backend-clone.git
cd uber-backend-clone

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run


src/main/java/com/uber/
│
├── controller         # REST Controllers
├── model              # JPA and ES Entities
├── repository         # JPA and Elasticsearch Repositories
├── security           # JWT Config, Filters
├── service            # Business Logic
└── utils              # Distance calculation, token utilities



---

Let me know if you'd like to tailor this for GitHub Pages, include Postman collection links, or deployment instructions (Docker, Heroku, etc.).
