<div align="center">

# 🧩 Microservices Architecture
### Spring Boot · Eureka · JWT Security · MongoDB · Spring Cloud

[![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.6-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-Eureka-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-cloud)
[![MongoDB](https://img.shields.io/badge/MongoDB-Database-47A248?style=for-the-badge&logo=mongodb&logoColor=white)](https://www.mongodb.com/)
[![JWT](https://img.shields.io/badge/JWT-Security-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![Lombok](https://img.shields.io/badge/Lombok-Enabled-pink?style=for-the-badge)](https://projectlombok.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

A production-ready **Microservices backend** built with Spring Boot and Spring Cloud. Features service discovery via **Netflix Eureka**, **JWT-based authentication**, **role-based access control**, **MongoDB** for product persistence, and paginated + filtered REST APIs.

</div>

---

## 📌 Table of Contents

- [Architecture Overview](#-architecture-overview)
- [Services](#-services)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [API Reference](#-api-reference)
- [Security](#-security)
- [Data Models](#-data-models)
- [Getting Started](#-getting-started)
- [Service Ports](#-service-ports)
- [Roadmap](#-roadmap)
- [Author](#-author)

---

## 🏗 Architecture Overview

```
                        ┌─────────────────────┐
                        │    Client / Frontend  │
                        │  (React / Vite App)   │
                        └──────────┬────────────┘
                                   │ HTTP Requests
                                   ▼
                    ┌──────────────────────────┐
                    │      API Gateway          │
                    │   (Routes all requests)   │
                    └──────────┬───────────────┘
                               │
              ┌────────────────┼────────────────┐
              │                │                 │
              ▼                ▼                 ▼
   ┌──────────────────┐  ┌──────────────┐  ┌─────────────────┐
   │  Customer Service │  │Product Service│  │  Future Services│
   │   Port: 8081      │  │  Port: 8090  │  │  (Order, Payment)│
   └──────────┬────────┘  └──────┬───────┘  └─────────────────┘
              │                  │
              └─────────┬────────┘
                        │ Register & Discover
                        ▼
           ┌────────────────────────┐
           │    Eureka Server        │
           │  Service Discovery      │
           │     Port: 8761          │
           └────────────────────────┘
```

---

## 🔧 Services

### 1️⃣ Eureka Service — Service Discovery
- Acts as the **central registry** for all microservices
- Every service registers itself here on startup
- Services discover each other through Eureka — no hardcoded URLs
- Running on **port 8761**

### 2️⃣ Product Service — Core Product Management
- Full **CRUD** for products
- **MongoDB** as the database (`@Document`)
- **JWT authentication** with role-based access
- Pagination, sorting, filtering, search
- Analytics endpoints (count by category, company, status)
- Automatic **selling price** calculation from MRP + discount
- Running on **port 8090**

### 3️⃣ Customer Service — Customer Management
- Customer data management
- Registered with Eureka for service discovery
- Running on **port 8081**

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 25|
| Framework | Spring Boot 4.0.6 |
| Service Discovery | Spring Cloud Netflix Eureka |
| Database | MongoDB (NoSQL) |
| Security | JWT (JSON Web Tokens) + Spring Security |
| ORM/Mapping | ModelMapper |
| Build Tool | Maven |
| Boilerplate | Lombok |
| CORS | Configured for React (3000) & Vite (5173) |

---

## 📁 Project Structure

```
micro-service/
│
├── eureka-service/                         # 🔍 Service Registry
│   ├── src/main/java/com/springsecurity/eurekaservice/
│   │   └── EurekaServiceApplication.java   # @EnableEurekaServer
│   └── src/main/resources/
│       └── application.properties          # Port: 8761
│
├── customer-service/                       # 👤 Customer Management
│   ├── src/main/java/com/customerservice/
│   │   └── controller/
│   │       └── CustomerController.java     # Customer REST endpoints
│   └── src/main/resources/
│       └── application.properties          # Port: 8081
│
└── product-service/                        # 📦 Product Management
    ├── src/main/java/com/productservice/
    │   │
    │   ├── ProductServiceApplication.java  # Main entry point
    │   │
    │   ├── config/
    │   │   └── AppConfig.java              # Security + CORS config
    │   │
    │   ├── controller/
    │   │   └── ProductController.java      # 20+ REST endpoints
    │   │
    │   ├── service/
    │   │   ├── ProductService.java         # Service interface
    │   │   └── impl/
    │   │       └── ProductServiceImpl.java # Business logic
    │   │
    │   ├── repository/
    │   │   └── ProductRepository.java      # MongoDB queries
    │   │
    │   ├── entity/
    │   │   └── Product.java               # MongoDB document
    │   │
    │   ├── dto/
    │   │   ├── request/
    │   │   │   ├── ProductRequestDto.java
    │   │   │   └── ProductUpdateRequestDto.java
    │   │   └── response/
    │   │       ├── ProductResponseDto.java  # Includes sellingPrice
    │   │       ├── CategoryCountDto.java
    │   │       ├── CompanyCountDto.java
    │   │       └── StatusCountDto.java
    │   │
    │   ├── enums/
    │   │   ├── Category.java              # ELECTRONICS, FASHION, GROCERY, HOME_APPLIANCES
    │   │   └── Status.java                # AVAILABLE, OUT_OF_STOCK, DISCONTINUED
    │   │
    │   ├── security/
    │   │   ├── JwtUtil.java               # JWT decode & validation
    │   │   └── JwtAuthenticationFilter.java # Request filter
    │   │
    │   ├── mapper/
    │   │   └── ProductMapper.java         # Entity <-> DTO mapping
    │   │
    │   ├── projection/
    │   │   ├── CategoryCountResponse.java
    │   │   ├── CompanyCountResponse.java
    │   │   └── StatusCountResponse.java
    │   │
    │   └── exception/
    │       ├── GlobalExceptionHandler.java
    │       ├── ProductNotFoundException.java
    │       └── ProductExistsException.java
    │
    └── src/main/resources/
        └── application.properties          # Port: 8090
```

---

## 📡 API Reference

### 📦 Product Service — `http://localhost:8090`

#### Basic CRUD

| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| `POST` | `/api/products` | ADMIN | Create a new product |
| `GET` | `/api/products` | ADMIN, CUSTOMER | Get all products |
| `GET` | `/api/products/{productId}` | ADMIN, CUSTOMER | Get product by ID |
| `PUT` | `/api/products/{productId}` | ADMIN | Update product |
| `DELETE` | `/api/products/{productId}` | ADMIN | Delete product |

#### 🔍 Filtering & Search

| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| `GET` | `/api/products/category/{category}` | ADMIN, CUSTOMER | Filter by category |
| `GET` | `/api/products/status/{status}` | ADMIN, CUSTOMER | Filter by status |
| `GET` | `/api/products/search?keyword=` | ADMIN, CUSTOMER | Search by name |
| `GET` | `/api/products/top-rated` | ADMIN, CUSTOMER | Top 10 rated products |

#### 📄 Pagination

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/products/pagination?page=0&size=10&sortBy=name` | Paginated results |

#### 📊 Analytics

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/products/count-by-category` | Product count per category |
| `GET` | `/api/products/count-by-company` | Product count per company |
| `GET` | `/api/products/count-by-status` | Product count per status |

---

**Create Product — Request Body:**
```json
{
  "name": "iPhone 15 Pro",
  "maxRetailPrice": 129900,
  "discountPercentage": 10,
  "brand": "Apple",
  "category": "ELECTRONICS",
  "company": "Apple Inc"
}
```

**Product Response — includes auto-calculated selling price:**
```json
{
  "productId": "64f1a2b3c4d5e6f7a8b9c0d1",
  "name": "iPhone 15 Pro",
  "maxRetailPrice": 129900,
  "discountPercentage": 10,
  "sellingPrice": 116910,
  "rating": 0.0,
  "reviewsCount": 0,
  "category": "ELECTRONICS",
  "company": "Apple Inc",
  "status": "AVAILABLE",
  "createdDate": "2025-06-16T10:30:00",
  "updatedDate": null
}
```

---

## 🔐 Security

This project uses **JWT (JSON Web Tokens)** with **Spring Security** for authentication and authorization.

### How it works:
```
Client                          Product Service
  │                                   │
  │── POST /auth/login ──────────────>│
  │<── JWT Token ─────────────────────│
  │                                   │
  │── GET /api/products               │
  │   Authorization: Bearer <token> ─>│
  │                         JwtAuthenticationFilter
  │                         validates token
  │<── 200 OK + Product Data ─────────│
```

### Role-Based Access Control:

| Role | Permissions |
|------|------------|
| `ADMIN` | GET, POST, PUT, PATCH, DELETE on all endpoints |
| `CUSTOMER` | GET only on `/api/products/**` |

### CORS Configuration:
Configured to allow requests from:
- `http://localhost:3000` (React)
- `http://localhost:5173` (Vite)

---

## 📐 Data Models

### 📦 Product (MongoDB Document)

| Field | Type | Description |
|-------|------|-------------|
| id | String | MongoDB ObjectId (auto-generated) |
| name | String | Product name |
| maxRetailPrice | int | MRP in paise/rupees |
| discountPercentage | byte | Discount % (0-100) |
| rating | float | Average rating (0.0 - 5.0) |
| reviewsCount | int | Total review count |
| category | Category | ELECTRONICS / FASHION / GROCERY / HOME_APPLIANCES |
| company | String | Brand/company name |
| status | Status | AVAILABLE / OUT_OF_STOCK / DISCONTINUED |
| createdDate | LocalDateTime | Auto-set on creation |
| updatedDate | LocalDateTime | Auto-set on update |

### 💰 Selling Price Formula
```
sellingPrice = maxRetailPrice - (maxRetailPrice × discountPercentage / 100)
```
Example: MRP ₹1000, Discount 20% → Selling Price ₹800

---

## 🚀 Getting Started

### Prerequisites
- ✅ Java 17+
- ✅ Maven 3.8+
- ✅ MongoDB (local or Atlas)
- ✅ IntelliJ IDEA (recommended)

### 1. Clone the Repository
```bash
git clone https://github.com/kota64453/micro-service.git
cd micro-service
```

### 2. Configure MongoDB

In `product-service/src/main/resources/application.properties`:
```properties
spring.application.name=product-service
server.port=8090

# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/productdb

# Eureka
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
eureka.client.registerWithEureka=true
eureka.client.fetchRegistry=true

# JWT
jwt.secret=your_secret_key_here
```

### 3. Start Services IN ORDER ⚠️

**Step 1 — Start Eureka Server FIRST:**
```bash
cd eureka-service
./mvnw spring-boot:run
```
> Wait until you see `Started EurekaServiceApplication` then open http://localhost:8761

**Step 2 — Start Customer Service:**
```bash
cd customer-service
./mvnw spring-boot:run
```

**Step 3 — Start Product Service:**
```bash
cd product-service
./mvnw spring-boot:run
```

### 4. Verify Everything is Running

| Service | URL | Expected |
|---------|-----|---------|
| Eureka Dashboard | http://localhost:8761 | Shows registered services |
| Product Service | http://localhost:8090/api/products | Returns product list |
| Customer Service | http://localhost:8081 | Returns customer data |

---

## 🌐 Service Ports

| Service | Port | Description |
|---------|------|-------------|
| Eureka Server | 8761 | Service registry dashboard |
| Customer Service | 8081 | Customer management API |
| Product Service | 8090 | Product management API |

---

## 🗺 Roadmap

- [x] Eureka Service Discovery
- [x] Product Service with MongoDB
- [x] JWT Authentication & Authorization
- [x] Role-based access control (ADMIN / CUSTOMER)
- [x] Product CRUD operations
- [x] Pagination & Sorting
- [x] Search & Filtering by category/status
- [x] Top-rated products endpoint
- [x] Analytics (count by category/company/status)
- [x] Auto selling price calculation
- [x] CORS configuration for React/Vite
- [x] Customer Service skeleton
- [ ] Complete Customer Service (full CRUD)
- [ ] API Gateway (Spring Cloud Gateway)
- [ ] Order Service
- [ ] Payment Service
- [ ] Inter-service communication (Feign Client)
- [ ] Centralized configuration (Spring Cloud Config)
- [ ] Docker + Docker Compose setup
- [ ] Circuit Breaker (Resilience4j)

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch
```bash
git checkout -b feature/order-service
```
3. Commit your changes
```bash
git commit -m "Add: Order service with Eureka integration"
```
4. Push and open a Pull Request
```bash
git push origin feature/order-service
```

---

## 👨‍💻 Author

**kota64453**
GitHub: [@kota64453](https://github.com/kota64453)

---

## 📄 License

This project is licensed under the **MIT License** — free to use, modify and share.

---

<div align="center">

⭐ **If this project helped you, give it a star!** ⭐

<br/>

Built with ☕ Spring Boot · ☁️ Spring Cloud · 🍃 MongoDB

</div>
