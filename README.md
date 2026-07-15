# 🏛️ Barangay Management System

> A secure, full-stack web application designed to digitize local government operations, resident profiling, and incident management for Barangay Luntian.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)

## 📖 Overview

The **Barangay Management System** transitions traditional, paper-based local government administration into a highly available digital portal. Built with a monolithic architecture, the system enforces strict data privacy through Role-Based Access Control (RBAC), compartmentalizing administrative capabilities from standard citizen access.

## ✨ Key Features

*   🔐 **Role-Based Access Control (RBAC):** Secure login routing providing distinct portals for Barangay Officials (Admin) and standard citizens (Resident).
*   📊 **Analytics Dashboard:** A real-time overview of total population, pending document requests, and active blotter cases.
*   👥 **Resident Masterlist:** Full CRUD (Create, Read, Update, Delete) operations for managing community profiles.
*   📄 **Automated Clearance Generation:** One-click generation of printable, format-ready official Barangay Clearances.
*   ⚖️ **Blotter & Incident Management:** A secure pipeline for residents to file complaints and for officials to track case resolutions.

## 🛠️ Technology Stack

*   **Backend:** Java 17, Spring Boot 3.x
*   **Security:** Spring Security (BCrypt Password Hashing)
*   **Database & ORM:** MySQL, Spring Data JPA / Hibernate
*   **Frontend:** HTML5, Vanilla JavaScript (ES6+), Bootstrap 5
*   **Architecture:** MVC (Model-View-Controller), RESTful API

## 🚀 Getting Started

### Prerequisites
*   [Java Development Kit (JDK) 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
*   [Maven](https://maven.apache.org/)
*   [MySQL Server](https://dev.mysql.com/downloads/)

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone [https://github.com/yourusername/barangay-system.git](https://github.com/yourusername/barangay-system.git)
   cd barangay-system

2. Configure the Database
Create a new MySQL database named barangay_db. Then, update your src/main/resources/application.properties file with your MySQL credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/barangay_db
spring.datasource.username=root
spring.datasource.password=your_password

3. Run the Application
Using Maven, build and run the Spring Boot application:

mvn spring-boot:run


Test Accounts
The application includes a DataSeeder that automatically generates test accounts upon startup.

Role - Admin
Username: admin
Password: admin123

Role - Resident    
Username: juan123      
Password: password123
