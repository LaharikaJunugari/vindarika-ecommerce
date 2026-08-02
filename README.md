# Vindarika — Fashion E-Commerce Platform

A full-stack e-commerce web application for a fashion/clothing brand, built with Spring Boot, MySQL, and Thymeleaf. Includes complete customer shopping flows and an admin dashboard for managing products, categories, and orders.

**Live Demo:** [vindarika-ecommerce.onrender.com](https://vindarika-ecommerce.onrender.com)
*(Hosted on Render's free tier — the app may take 20–30 seconds to wake up on the first request after inactivity.)*

---

## Features

### Customer
- Browse products by category
- Filter by size and color
- Product detail pages with size, color, price, and description
- Add to cart, update quantities, remove items
- Checkout with delivery address
- Order confirmation and order history
- Customer registration and login

### Admin
- Admin registration and login
- Add, update, and delete categories
- Add, update, and delete products (with image upload, size, and color)
- View and manage all customer orders
- Update order status (Placed → Packed → Out for Delivery → Delivered)

---

## Tech Stack

| Layer          | Technology                        |
|----------------|------------------------------------|
| Language       | Java 17                            |
| Framework      | Spring Boot 3.3.2, Spring Data JPA |
| Templating     | Thymeleaf                          |
| Database       | MySQL                              |
| Build Tool     | Maven                              |
| Containerization | Docker                           |
| Hosting        | Render (app) + Aiven (managed MySQL) |

---

## Project Structure

```
src/main/java/com/project/
├── config/         # Web/resource configuration
├── controller/      # Page, admin, cart, and customer controllers
├── dto/             # Data transfer objects (e.g. ProductDt)
├── entity/          # JPA entities (Product, Order, Customer, etc.)
├── repository/      # Spring Data JPA repositories
└── service/         # Business logic layer

src/main/resources/
├── static/          # CSS, images, uploaded product images
└── templates/        # Thymeleaf HTML templates
```

---

## Running Locally

### Prerequisites
- Java 17
- Maven (or use the included wrapper `./mvnw`)
- MySQL running locally

### Setup

1. Clone the repository
   ```bash
   git clone https://github.com/LaharikaJunugari/vindarika-ecommerce.git
   cd vindarika-ecommerce
   ```

2. Create a local MySQL database named `eproject`

3. Run the application (default local credentials are `root`/`root` — update `application.properties` if yours differ)
   ```bash
   ./mvnw spring-boot:run
   ```

4. Open [http://localhost:8084](http://localhost:8084)

---

## Deployment

This project is deployed using:
- **Docker** — multi-stage build (Maven build stage + lightweight JRE runtime stage)
- **Render** — hosts the containerized Spring Boot app
- **Aiven** — provides a managed, free-tier MySQL database

Datasource configuration is environment-variable driven (`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`), with local fallback defaults for development.

---

## Notes

- Free-tier hosting means the app "spins down" after inactivity; the first request after a period of idle time will be slower while it restarts.
- Uploaded product images are stored on the container's local filesystem, which is ephemeral on Render's free tier — images may need to be re-uploaded after a redeploy.

---

## Author

Built by Laharika Junugari as a full-stack portfolio project.
