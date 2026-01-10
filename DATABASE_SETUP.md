# Database Setup Instructions

## Quick Start

### 1. Create Database
```bash
mysql -u root -p
```

```sql
CREATE DATABASE paulquiz CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE paulquiz;
```

### 2. Import Schema
```bash
mysql -u root -p paulquiz < C:\CODING\PaulQuiz\database\schema.sql
```

### 3. Import Seed Data (Optional - includes sample data)
```bash
mysql -u root -p paulquiz < C:\CODING\PaulQuiz\database\seed_data.sql
```

### 4. Create Admin Account
```bash
mysql -u root -p paulquiz < C:\CODING\PaulQuiz\database\create_admin.sql
```

## Admin Login Credentials

After running the scripts above, you can login as admin:

- **Email:** `admin@paulquiz.com`
- **Password:** `admin123`

> [!WARNING]
> **IMPORTANT:** Change the admin password after first login for security!

## Configure Database Connection

Edit `src/java/com/paulquiz/config/DbConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/paulquiz";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "YOUR_MYSQL_PASSWORD"; // ← CHANGE THIS!
```

## Verify Setup

After setup, verify everything works:

1. **Test Database Connection:**
   - Run the application
   - Check console for connection success/errors

2. **Test Admin Login:**
   - Navigate to `http://localhost:8080/PaulQuiz/login`
   - Login with admin credentials
   - Should redirect to homepage (admin dashboard will be available soon)

## Troubleshooting

### Connection Refused
- Check if MySQL is running: `mysql --version`
- Verify MySQL port is 3306
- Check firewall settings

### Access Denied
- Verify MySQL username/password in `DbConnection.java`
- Check MySQL user permissions: `GRANT ALL ON paulquiz.* TO 'root'@'localhost';`

### Table Already Exists
- Safe to ignore if re-running schema
- Or drop database first: `DROP DATABASE paulquiz;` then recreate

### Admin Account Already Exists
- Safe to ignore - script checks if admin exists
- Admin won't be duplicated
