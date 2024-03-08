# **Court reservation system for Tennis club.**

### Table of Contents

1. Features
2. Getting Started
3. Prerequisites
   - Installation
4. API Endpoints
5. Authentication


### Features

- **_Court Management_** Manage courts, including creation, deletion, and updating court details.
- **_Reservation Management:_** Create, update, and delete reservations for courts, with support for checking availability and pricing.
- **_Surface Management:_** Manage court surfaces, including creation, deletion, and updating surface details.
- **_User Authentication_** Secure user authentication using JWT tokens.
- **_Authorization:_** Role-based access control for different user roles (e.g., admin, user).

---
  
### Getting Started
#### Installation
1. Clone the repository:
```
git clone https://github.com/mbjalon/inQool-Java-project.git
```
2. Navigate to the project directory:
```
cd inQool-Java-project
cd ReservationSystemApp
```
3. Build the project using Maven:
```
mvn clean install
```
4. Configure the database by updating the application.properties file with your database credentials.
5. Run the application:
```
mvn spring-boot:run
```
---

### API Endpoints

The application exposes RESTful APIs for managing courts, reservations, surfaces, and users.

- Courts: `/api/courts`
- Reservations: `/api/reservations`
- Surfaces: `/api/surfaces`
- Users: `/api/users`
- Authentication: `/api/auth`

### Authentication

The application uses JSON Web Tokens (JWT) for user authentication. Users can obtain JWT tokens by logging in or signing up.



> Credit: [YouTube](https://www.youtube.com/watch?v=4fqSaiGF2yg) 🥇
