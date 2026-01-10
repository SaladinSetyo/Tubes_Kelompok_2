# Quick Fix - Build Errors

## Problem
100+ errors, semua "cannot find symbol" untuk HttpSession, ServletException, dll.

## Solution

### 1. Add Apache Tomcat Library
```
Right-click "PaulQuiz" → Properties → Libraries → Compile
→ Add Library... → Select "Apache Tomcat" → Add Library → OK
```

### 2. Add JAR Files
```
Still in Libraries → Compile
→ Add JAR/Folder... → Navigate to C:\CODING\PaulQuiz\lib\
→ Hold Ctrl, select ALL .jar files → Open → OK
```

### 3. Clean and Build
```
Right-click "PaulQuiz" → Clean and Build (Shift+F11)
→ Wait for "BUILD SUCCESSFUL"
```

## Done!
Run project (F6) → Test: http://localhost:8080/PaulQuiz/test
