# Restaurant POS System

## Overview
The **Restaurant POS System** is a full-featured Point of Sale (POS) application designed for restaurants. It provides essential functionalities such as user authentication with JWT, account recovery, product and order management, and API integration for email validation using ZeroBounce to ensure the authenticity of registered emails.

## Features
- **User Authentication**: Secure login and registration using JWT tokens.
- **Account Recovery**: Users can recover accounts using email verification.
- **Email Validation**: Integrated with ZeroBounce API to verify email authenticity.
- **Product Management**: Create, update, and list products.
- **Order Management**: Manage orders and track order items.
- **Category Management**: Organize products into categories.
- **Token Management**: Secure handling of authentication tokens.
- **Sales Tracking**: Keep records of product sales.

## Tech Stack
- **Backend**: Spring Boot (Java framework)
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Token)
- **Email Validation API**: ZeroBounce

## Installation
### Prerequisites
Make sure you have the following installed on your system:
- Java 17 or later
- MySQL database
- Maven

### Steps
1. **Clone the repository:**
   ```sh
   git clone https://github.com/yourusername/restaurant-pos.git
   cd restaurant-pos
   ```
2. **Install dependencies:**
   ```sh
   mvn install
   ```
3. **Set up environment variables:**
   Create an `application.properties` file in the `src/main/resources` directory and add the following:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update
   jwt.secret=your_jwt_secret_key
   zerobounce.api.key=your_zerobounce_api_key
   ```
4. **Run the application:**
   ```sh
   mvn spring-boot:run
   ```
5. **Access the API:**
   Open `http://localhost:8080` in your browser or use Postman to test API endpoints.

## API Endpoints
### **User Routes**
- **Register User**: `POST /user/register`
- **Login User**: `POST /user/login`
- **Account Recovery**: `POST /user/recover`

### **Product Routes**
- **Get All Products**: `GET /product`
- **Create Product**: `POST /product/create`
- **Update Product**: `PUT /product/update/:id`
- **Delete Product**: `DELETE /product/delete/:id`

### **Order Routes**
- **Create Order**: `POST /order/create`
- **Get Order by ID**: `GET /order/:id`
- **Update Order**: `PUT /order/update/:id`
- **Delete Order**: `DELETE /order/delete/:id`

### **Category Routes**
- **Get Categories**: `GET /category`
- **Create Category**: `POST /category/create`

## Database Schema
The system consists of the following main entities:
1. **User** - Stores user information.
2. **Product** - Stores product details.
3. **Category** - Groups products into categories.
4. **Order** - Stores order information.
5. **OrderItem** - Links products to orders.
6. **Token** - Manages authentication tokens.
7. **ProductSales** - Tracks sales history.

## Future Improvements
- Implement an admin dashboard for better management.
- Add a reporting feature for sales analytics.
- Improve order tracking with status updates.

## Contact
For questions or contributions, feel free to reach out:
- **GitHub**: (https://github.com/Elietannous1)
- **Email**: tannous.elie.me@gmail.com

