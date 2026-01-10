# PaulQuiz - Setup Completion Guide

## ✅ Files Sudah Di-Copy

Semua files dari Fintechs sudah di-integrasikan ke PaulQuiz:
- ✅ Source code (Java classes)
- ✅ JSP views
- ✅ CSS & JavaScript
- ✅ Database schema
- ✅ JAR dependencies

---

## 🔧 Next Steps - Complete Setup

### 1. Add JAR Libraries ke NetBeans

**Di NetBeans:**
1. Right-click project **"PaulQuiz"**
2. **Properties** → **Libraries** → tab **Compile**
3. Click **"Add JAR/Folder"**
4. Navigate ke `C:\CODING\PaulQuiz\lib\`
5. **Hold Ctrl**, select ALL .jar files:
   - `mysql-connector-j-8.2.0.jar`
   - `jbcrypt-0.4.jar`
   - `jstl-1.2.jar`
   - `standard-1.1.2.jar` (if exists)
6. Click **Open** → **OK**

### 2. Verify Libraries Added

Expand **"Libraries"** di project tree, should see:
```
Libraries
├── JDK 1.8
├── Apache Tomcat
├── jbcrypt-0.4.jar              ✓
├── jstl-1.2.jar                 ✓
├── mysql-connector-j-8.2.0.jar  ✓
└── standard-1.1.2.jar           ✓
```

### 3. Clean and Build

1. Right-click project → **Clean and Build** (Shift+F11)
2. Check **Output** window:
   ```
   BUILD SUCCESSFUL (total time: X seconds)
   ```
3. **NO RED ICONS** di source files

### 4. Setup Database

**Create Database:**
```bash
mysql -u root -p
```

```sql
CREATE DATABASE paulquiz CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
exit;
```

**Import Schema:**
```bash
mysql -u root -p paulquiz < C:/CODING/PaulQuiz/database/schema.sql
```

**Import Seed Data (Optional):**
```bash
mysql -u root -p paulquiz < C:/CODING/PaulQuiz/database/seed_data.sql
```

### 5. Configure Database Connection

Edit `src/java/com/paulquiz/config/DbConnection.java`:

Find these lines:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/paulquiz";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "";  // ← SET YOUR MYSQL PASSWORD HERE!
```

**Update `DB_PASSWORD`** dengan password MySQL Anda.

### 6. Run Project

1. Press **F6** (or Right-click → Run)
2. Browser will open automatically
3. URL: `http://localhost:8080/PaulQuiz/`

### 7. Test URLs

Try these URLs:

✅ **Test Servlet:** `http://localhost:8080/PaulQuiz/test`
- Should show: "✅ Servlet Works!"

✅ **Homepage:** `http://localhost:8080/PaulQuiz/index`
- Should show homepage with hero section

✅ **Login:** `http://localhost:8080/PaulQuiz/login`
- Should show login form

✅ **Register:** `http://localhost:8080/PaulQuiz/register`
- Should show registration form

---

## 📋 Checklist

- [ ] Add JAR files ke Libraries di NetBeans
- [ ] Clean and Build - BUILD SUCCESSFUL
- [ ] Create database `paulquiz`
- [ ] Import schema.sql
- [ ] Import seed_data.sql (optional)
- [ ] Set password di DbConnection.java
- [ ] Run project (F6)
- [ ] Test `/test` URL ← START HERE!
- [ ] Test `/login` URL
- [ ] Test `/register` URL
- [ ] Test `/index` URL

---

## 🎯 Default Login (After Seed Data)

**Admin:**
- Email: `admin@paulquiz.my.id`
- Password: `password123`

**Test User:**
- Email: `john@example.com`
- Password: `password123`

---

## 🆘 Troubleshooting

### Build Failed
- Check if JAR files are added to Libraries
- Check Output window for specific error

### Database Connection Error
- Verify MySQL is running
- Check credentials in `DbConnection.java`
- Verify database `paulquiz` exists

### 404 Error
- Make sure BUILD SUCCESSFUL first
- Try `/test` URL before others
- Check Tomcat deployment in Output window

---

**After completing checklist, project should work perfectly! 🚀**
