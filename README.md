# Paul Quiz - Java Fintechs

Aplikasi edukasi literasi keuangan berbasis Java Servlet + JSP, dikonversi dari proyek Laravel fintech.

## Fitur Utama

- ✅ Authentication System (Login, Register, Logout)
- ✅ Module & Content Management  
- ✅ Quiz System with Questions & Answers
- ✅ User Progress Tracking
- ✅ Points & Leaderboard System
- ✅ Notifications
- ✅ Admin Panel (CRUD untuk Modules, Contents, Quizzes, Questions, Answers)
- ✅ Dark/Light Mode
- ✅ Responsive Design

## Tech Stack

- **Backend**: Java Servlet + JSP
- **Database**: MySQL 8.0+
- **Server**: Apache Tomcat 9.0+
- **IDE**: NetBeans 12.0+
- **Dependencies**:
  - MySQL Connector/J (JDBC Driver)
  - jBCrypt (Password Hashing)
  - JSTL (JSP Standard Tag Library)

## Prerequisites

1. **JDK 8 atau lebih tinggi**
   ```bash
   java -version
   ```

2. **MySQL 8.0 atau lebih tinggi**
   ```bash
   mysql --version
   ```

3. **Apache Tomcat 9.0**
   - Download dari [https://tomcat.apache.org/download-90.cgi](https://tomcat.apache.org/download-90.cgi)
   - Ekstrak ke folder tertentu
   
4. **NetBeans IDE 12.0 atau lebih tinggi**
   - Download dari [https://netbeans.apache.org/download](https://netbeans.apache.org/download)
   - Pastikan sudah termasuk Java EE bundle

## Setup Instructions

### 1. Database Setup

#### A. Buat Database Baru

```sql
CREATE DATABASE paulquiz CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### B. Import Schema

```bash
mysql -u root -p paulquiz < C:/CODING/Fintechs/database/schema.sql
```

Atau gunakan MySQL Workbench / phpMyAdmin:
1. Buka file `database/schema.sql`
2. Copy semua konten
3. Paste dan execute di MySQL client

#### C. Import Seed Data (Optional - untuk testing)

```bash
mysql -u root -p paulquiz < C:/CODING/Fintechs/database/seed_data.sql
```

**Default Admin User (setelah seed data):**
- Email: `admin@paulquiz.my.id`
- Password: `password123`

**Default Test Users:**
- Email: `john@example.com` | Password: `password123`
- Email: `jane@example.com` | Password: `password123`

### 2. Database Configuration

Edit file `src/java/com/paulquiz/config/DbConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/paulquiz";
private static final String DB_USER = "root";           // Sesuaikan username MySQL Anda
private static final String DB_PASSWORD = "";           // Sesuaikan password MySQL Anda
```

**Untuk XAMPP/WAMP:**
- DB_USER: `root`
- DB_PASSWORD: `` (kosong)

**Untuk MySQL Standalone:**
- DB_USER: username MySQL Anda
- DB_PASSWORD: password MySQL Anda

### 3. Library Dependencies

Download dan tambahkan JAR files berikut ke folder `lib/`:

#### A. MySQL Connector/J
- Download: [https://dev.mysql.com/downloads/connector/j/](https://dev.mysql.com/downloads/connector/j/)
- Pilih versi 8.0.x
- Ekstrak dan copy file `mysql-connector-java-8.0.xx.jar` ke `lib/`

#### B. jBCrypt  
- Download: [https://www.mindrot.org/projects/jBCrypt/](https://www.mindrot.org/projects/jBCrypt/)
- Atau dari Maven Central: [https://mvnrepository.com/artifact/org.mindrot/jbcrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt)
- Copy `jbcrypt-0.4.jar` (atau versi terbaru) ke `lib/`

#### C. JSTL
- Download: [https://mvnrepository.com/artifact/javax.servlet/jstl/1.2](https://mvnrepository.com/artifact/javax.servlet/jstl/1.2)
- Copy `jstl-1.2.jar` ke `lib/`

**Alternatif (jika kesulitan download):**

Buat folder `lib/` jika belum ada:
```bash
mkdir lib
```

Kemudian tambahkan JAR files ke NetBeans:
1. Right-click project "Fintechs" → Properties
2. Libraries → Add JAR/Folder
3. Pilih JAR files yang sudah di-download

### 4. Tomcat Configuration di NetBeans

1. **Tools** → **Servers** → **Add Server**
2. Pilih **Apache Tomcat**
3. Browse ke folder Tomcat Anda (contoh: `C:\Program Files\Apache\Tomcat 9.0`)
4. Set username dan password untuk Tomcat Manager (optional)
5. Click **Finish**

### 5. Build & Deploy

1. **Buka Project di NetBeans**
   - File → Open Project
   - Pilih folder `C:\CODING\Fintechs`

2. **Clean and Build**
   - Right-click project → Clean and Build
   - Tunggu sampai build berhasil (no errors)

3. **Run Project**
   - Right-click project → Run
   - Atau tekan **F6**
   - Browser akan otomatis membuka: `http://localhost:8080/Fintechs/`

## Project Structure

```
Fintechs/
├── database/
│   ├── schema.sql          # Database schema
│   └── seed_data.sql       # Sample data
├── lib/                    # JAR dependencies
├── src/
│   └── java/
│       └── com/
│           └── paulquiz/
│               ├── config/      # Database configuration
│               ├── dao/         # Data Access Objects
│               ├── filter/      # Servlet filters
│               ├── model/       # Entity classes
│               ├── servlet/     # Servlets
│               │   ├── auth/   # Authentication servlets
│               │   ├── user/   # User servlets
│               │   └── admin/  # Admin servlets
│               └── util/        # Utility classes
└── web/
    ├── WEB-INF/
    │   ├── views/          # JSP pages
    │   │   ├── auth/      # Login, Register pages
    │   │   ├── user/      # User pages
    │   │   └── admin/     # Admin pages
    │   └── web.xml        # Web app configuration
    ├── assets/
    │   ├── css/           # Stylesheets
    │   ├── js/            # JavaScript files
    │   └── images/        # Images (logo, etc)
    └── META-INF/
        └── context.xml    # Context configuration
```

## URL Mapping

### Public Pages
- `/` - Homepage
- `/login` - Login page
- `/register` - Registration page
- `/modules` - Module list
- `/modules/{id}` - Module detail
- `/quizzes/{id}` - Quiz page
- `/leaderboard` - Leaderboard

### User Pages (Requires Login)
- `/profile` - User profile
- `/user/stats` - User statistics

### Admin Pages (Requires Admin Role)
- `/admin` - Admin dashboard
- `/admin/modules` - Module management
- `/admin/contents` - Content management
- `/admin/quizzes` - Quiz management
- `/admin/questions` - Question management
- `/admin/answers` - Answer management
- `/admin/notifications` - Notification management

## Troubleshooting

### 1. Database Connection Error

**Error:** `Communications link failure` atau `Access denied for user`

**Solution:**
- Pastikan MySQL service sudah running
- Check kredensial di `DbConnection.java`
- Pastikan database `paulquiz` sudah dibuat
- Test koneksi dengan MySQL client terlebih dahulu

### 2. ClassNotFoundException: com.mysql.cj.jdbc.Driver

**Solution:**
- Pastikan `mysql-connector-java.jar` sudah di folder `lib/`
- Atau tambahkan ke Libraries di NetBeans Project Properties

### 3. NoClassDefFoundError: org/mindrot/jbcrypt/BCrypt

**Solution:**
- Pastikan `jbcrypt.jar` sudah di folder `lib/`
- Rebuild project (Clean and Build)

### 4. Port 8080 Already in Use

**Solution:**
- Stop aplikasi lain yang menggunakan port 8080
- Atau ubah port Tomcat di NetBeans Server configuration

### 5. 404 Error - Servlet Not Found

**Solution:**
- Pastikan URL mapping sudah benar di `@WebServlet` annotation
- Clean and Build project
- Restart Tomcat server

## Development

### Menambahkan Module Baru

1. Buat content di Admin Panel (`/admin/modules`)
2. Buat quiz untuk module (`/admin/quizzes`)
3. Tambahkan questions dan answers

### Menambahkan User Admin Baru

```sql
INSERT INTO users (name, email, password, role, points) 
VALUES ('Admin Name', 'admin@example.com', '$2a$10$...', 'admin', 0);
```

**Note:** Password harus di-hash dengan BCrypt terlebih dahulu.

## Credits

Dikonversi dari proyek Laravel fintech oleh Paul Quiz Team.

## License

Educational purpose only.
