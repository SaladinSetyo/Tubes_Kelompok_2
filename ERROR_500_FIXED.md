# Error 500 - javax/servlet/http/HttpServlet

## Problem
Error 500: `javax/servlet/http/HttpServlet` not found at runtime.

## Root Cause
Tomcat tidak providing servlet-api di classpath untuk aplikasi ini.

## Solution Applied
Copied servlet-api JARs ke WEB-INF/lib:
- javax.servlet-api-4.0.1.jar
- javax.servlet.jsp-api-2.3.3.jar

**Note:** Ini workaround. Idealnya Tomcat should provide ini, tapi untuk testing purposes ini acceptable.

## Next Steps
1. Clean project
2. Build (F11)
3. Run (F6)
4. Test: http://localhost:8080/PaulQuiz/test

Should work now!
