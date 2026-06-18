# 📚 Library Management System

A production-ready REST API built with **Java Spring Boot**, **MongoDB**, **Maven**, and **Swagger/OpenAPI**.

---

## 🚀 Quick Start

### Prerequisites
| Tool | Version | Download |
|------|---------|----------|
| Java | 17+ | https://adoptium.net |
| Maven | 3.8+ | https://maven.apache.org |
| MongoDB | 6.0+ | https://www.mongodb.com/try/download/community |

### Steps

```bash
# 1. Start MongoDB (in a separate terminal)
mongod

# 2. Go into the project folder
cd library-management-system

# 3. Run the app
mvn spring-boot:run
```

### Open in Browser
- **Swagger UI** → http://localhost:8080/swagger-ui.html
- **API Docs** → http://localhost:8080/api-docs

---

## 🗂️ Project Structure

```
src/main/java/com/library/
├── controller/        ← REST endpoints
├── service/           ← Service interfaces
├── service/impl/      ← Business logic
├── repository/        ← MongoDB queries
├── model/             ← MongoDB documents
├── dto/request/       ← Incoming request bodies
├── dto/response/      ← Outgoing response bodies
├── exception/         ← Custom exceptions + global handler
└── config/            ← MongoDB + Swagger config
```

---

## 📦 Collections (MongoDB)

| Collection | Description |
|-----------|-------------|
| `authors` | Book authors |
| `categories` | Book genres/categories |
| `books` | Library books with availability tracking |
| `members` | Library members |
| `library_cards` | One card per member |
| `issue_records` | Book borrow/return history |

---

## 🔗 Relationships

| Type | Entities |
|------|----------|
| One-to-One | Member ↔ LibraryCard |
| One-to-Many | Category → Books |
| Many-to-One | Books → Author |
| Many-to-Many | Members ↔ Books (via IssueRecord) |

---

## 🛣️ API Endpoints

### Authors `/api/v1/authors`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/` | Create author |
| GET | `/` | Get all (paginated) |
| GET | `/{id}` | Get by ID |
| PUT | `/{id}` | Update |
| DELETE | `/{id}` | Delete |

### Categories `/api/v1/categories`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/` | Create category |
| GET | `/` | Get all (paginated) |
| GET | `/{id}` | Get by ID |
| PUT | `/{id}` | Update |
| DELETE | `/{id}` | Delete |

### Books `/api/v1/books`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/` | Create book |
| GET | `/` | Get all (paginated) |
| GET | `/{id}` | Get by ID |
| PUT | `/{id}` | Update |
| DELETE | `/{id}` | Delete |
| GET | `/search/title?title=` | Search by title |
| GET | `/search/author/{authorId}` | Books by author |
| GET | `/search/category/{categoryId}` | Books by category |
| GET | `/available` | Only available books |

### Members `/api/v1/members`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/` | Register member |
| GET | `/` | Get all (paginated) |
| GET | `/{id}` | Get by ID |
| PUT | `/{id}` | Update |
| DELETE | `/{id}` | Delete |

### Library Cards `/api/v1/library-cards`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/` | Issue a card |
| GET | `/{cardId}` | Get by card ID |
| GET | `/member/{memberId}` | Get by member |
| PUT | `/{cardId}` | Update / extend |
| DELETE | `/{cardId}` | Revoke card |

### Issue Records `/api/v1/issues`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/` | Issue a book |
| PUT | `/{issueId}/return` | Return a book |
| GET | `/{issueId}` | Get by ID |
| GET | `/` | Full history |
| GET | `/member/{memberId}` | Issues by member |
| GET | `/book/{bookId}` | Issues by book |

---

## 🧠 Business Rules

- A book can only be issued if `availableQuantity > 0`
- Issuing a book decrements `availableQuantity` by 1
- Returning a book increments `availableQuantity` by 1
- A member cannot issue the same book twice without returning it
- Each member can have only **one** library card

---

## 📝 Sample Request Bodies

### Create Author
```json
{
  "authorName": "George R.R. Martin",
  "email": "george.martin@authors.com",
  "phone": "+919876543210"
}
```

### Create Category
```json
{
  "categoryName": "Science Fiction",
  "description": "Books exploring futuristic science and technology"
}
```

### Create Book
```json
{
  "title": "A Game of Thrones",
  "isbn": "978-0553103540",
  "publicationYear": 1996,
  "quantity": 5,
  "authorId": "<author_id_here>",
  "categoryId": "<category_id_here>"
}
```

### Create Member
```json
{
  "memberName": "Rahul Sharma",
  "email": "rahul.sharma@gmail.com",
  "phone": "+919876543210",
  "address": "123, MG Road, Bangalore - 560001"
}
```

### Issue Library Card
```json
{
  "issueDate": "2024-01-15",
  "expiryDate": "2025-01-15",
  "memberId": "<member_id_here>"
}
```

### Issue a Book
```json
{
  "memberId": "<member_id_here>",
  "bookId": "<book_id_here>",
  "issueDate": "2024-01-15"
}
```

---

## ⚠️ Error Responses

All errors follow this structure:
```json
{
  "success": false,
  "message": "Book not found with id: 'abc123'",
  "data": null,
  "timestamp": "2024-01-15 10:30:00"
}
```

| HTTP Code | Meaning |
|-----------|---------|
| 400 | Validation error / Invalid operation |
| 404 | Resource not found |
| 409 | Duplicate resource (email, ISBN, etc.) |
| 500 | Unexpected server error |

---

## 🔧 Configuration

Edit `src/main/resources/application.properties`:

```properties
# Change MongoDB database name
spring.data.mongodb.database=library_db

# Change port
server.port=8080

# MongoDB with auth
spring.data.mongodb.uri=mongodb://user:pass@localhost:27017/library_db
```
