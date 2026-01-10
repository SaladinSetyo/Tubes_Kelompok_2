# CRITICAL FIX - JARs Not in Deployment!

## Problem
Servlets .class files ada tapi 404 → **JAR files tidak ter-copy ke deployment folder!**

---

## SOLUTION - Copy JARs ke WEB-INF/lib

### Manual Copy (Quick Fix)

```powershell
Copy-Item C:\CODING\PaulQuiz\lib\*.jar -Destination C:\CODING\PaulQuiz\web\WEB-INF\lib\ -Force
```

Atau via Windows Explorer:
1. Open `C:\CODING\PaulQuiz\lib\`
2. Select ALL .jar files (Ctrl+A)
3. Copy (Ctrl+C)
4. Navigate to `C:\CODING\PaulQuiz\web\WEB-INF\lib\`
5. Paste (Ctrl+V)

### Verify JARs in WEB-INF/lib

Folder `web\WEB-INF\lib\` MUST contain:
```
- javax.servlet-api-4.0.1.jar
- javax.servlet.jsp-api-2.3.3.jar
- jbcrypt-0.4.jar
- jstl-1.2.jar
- mysql-connector-j-8.2.0.jar
- standard-1.1.2.jar
```

---

## After Copying JARs

1. Clean project
2. Build project
3. Run project
4. Test: http://localhost:8080/PaulQuiz/test

Should work now!
