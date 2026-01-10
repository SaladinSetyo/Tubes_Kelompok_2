# FINAL FIX - Add Servlet API Manually

## Problem
Error: "package javax.servlet does not exist"

Meskipun "Apache Tomcat" ada di Libraries, servlet-api.jar tidak ter-load dengan benar.

---

## Solution - Add Servlet API as JAR File

Saya sudah download 2 JAR files ke folder lib:
- `javax.servlet-api-4.0.1.jar` (Servlet API)
- `javax.servlet.jsp-api-2.3.3.jar` (JSP API)

### Steps di NetBeans:

1. **Right-click** project "PaulQuiz" → **Properties**

2. **Libraries** → tab **Compile**

3. **OPTIONAL: Remove "Apache Tomcat or TomEE"** jika masih ada
   - Select it → Remove
   - Kita akan use JAR files langsung

4. **Add JAR/Folder...**
   - Navigate ke: `C:\CODING\PaulQuiz\lib\`
   - **Hold Ctrl**, select JAR files:
     - javax.servlet-api-4.0.1.jar ← NEW!
     - javax.servlet.jsp-api-2.3.3.jar ← NEW!
     - jbcrypt-0.4.jar
     - jstl-1.2.jar
     - mysql-connector-j-8.2.0.jar
     - standard-1.1.2.jar
   - Click **Open**

5. **Verify Libraries** now shows:
   ```
   ✓ javax.servlet-api-4.0.1.jar
   ✓ javax.servlet.jsp-api-2.3.3.jar
   ✓ jbcrypt-0.4.jar
   ✓ jstl-1.2.jar
   ✓ mysql-connector-j-8.2.0.jar
   ✓ standard-1.1.2.jar
   ✓ JDK 22
   ```

6. Click **OK**

7. **Clean and Build** (Shift+F11)

---

## Expected Result

✅ **BUILD SUCCESSFUL**  
✅ **0 errors**  
✅ Can Run project (F6)

---

## Why This Works

Servlet API JAR langsung lebih reliable daripada depend on server library configuration.

## Next Step

After BUILD SUCCESSFUL:
1. Press F6 (Run)
2. Test: http://localhost:8080/PaulQuiz/test
3. Should see "✅ Servlet Works!"
