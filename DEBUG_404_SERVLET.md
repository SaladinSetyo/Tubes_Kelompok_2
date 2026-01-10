# Debug 404 - Servlet Tidak Ter-Compile

## Status Saat Ini
✅ Deployment successful: "OK - Deployed application at context path [/PaulQuiz]"  
✅ Application started  
❌ 404 error di `/index`

## Root Cause
**Servlet files TIDAK TER-COMPILE** karena Libraries belum benar!

---

## VERIFY LIBRARIES TERLEBIH DAHULU

### Di NetBeans:

1. Expand project **"PaulQuiz"** (NOT Fintechs!)

2. Expand **"Libraries"**

3. **Verify ada 6 JAR files:**
   ```
   ✓ javax.servlet-api-4.0.1.jar       ← CRITICAL!
   ✓ javax.servlet.jsp-api-2.3.3.jar   ← CRITICAL!
   ✓ jbcrypt-0.4.jar
   ✓ jstl-1.2.jar
   ✓ mysql-connector-j-8.2.0.jar
   ✓ standard-1.1.2.jar
   ```

4. **Check path dari JAR:**
   - Right-click salah satu JAR → Properties
   - Path HARUS: `C:\CODING\PaulQuiz\lib\...`
   - **BUKAN**: `C:\CODING\Fintechs\lib\...`

### Jika JAR Files TIDAK ADA atau PATH SALAH:

1. Right-click project "PaulQuiz" → **Properties**
2. **Libraries** → **Compile** tab
3. **REMOVE** semua JAR references yang salah
4. Click **"Add JAR/Folder..."**
5. Navigate: `C:\CODING\PaulQuiz\lib\`
6. **Hold Ctrl**, select **SEMUA 6 files**:
   ```
   - javax.servlet-api-4.0.1.jar
   - javax.servlet.jsp-api-2.3.3.jar
   - jbcrypt-0.4.jar
   - jstl-1.2.jar
   - mysql-connector-j-8.2.0.jar
   - standard-1.1.2.jar
   ```
7. **Open** → **OK**

---

## REBUILD PROJECT

### 1. Clean Build Folder

Right-click project "PaulQuiz" → **Clean**

### 2. Build Project

Right-click project "PaulQuiz" → **Build** (F11)

**Check Output window** - should see:
```
Compiling X source files to C:\CODING\PaulQuiz\build\web\WEB-INF\classes
BUILD SUCCESSFUL (total time: X seconds)
```

**NOT:**
```
error: package javax.servlet does not exist
BUILD FAILED
```

### 3. Verify .class Files Created

**Check folder:**
```
C:\CODING\PaulQuiz\build\web\WEB-INF\classes\com\paulquiz\servlet\
```

**Should contain:**
```
- HomepageServlet.class   ← MUST EXIST!
- TestServlet.class        ← MUST EXIST!
- auth\LoginServlet.class  ← MUST EXIST!
```

**If .class files NOT exist** → Compilation failed → Fix Libraries first!

---

## RUN & TEST

### 1. Run Project
Press **F6** (or Right-click → Run)

### 2. Test URLs (IN ORDER):

**Test 1:** `http://localhost:8080/PaulQuiz/test`
- Should show: "✅ Servlet Works!"
- If 404 → Servlet masih belum compile

**Test 2:** `http://localhost:8080/PaulQuiz/index`
- Should show homepage
- If 404 tapi /test works → Homepage servlet issue

**Test 3:** `http://localhost:8080/PaulQuiz/login`
- Should show login page

---

## Quick Checklist

- [ ] Project "PaulQuiz" has Libraries with 6 JARs
- [ ] All JARs from `C:\CODING\PaulQuiz\lib\` (NOT Fintechs!)
- [ ] javax.servlet-api-4.0.1.jar present
- [ ] Clean project
- [ ] Build project - BUILD SUCCESSFUL
- [ ] Check build/web/WEB-INF/classes/ has .class files
- [ ] Run project
- [ ] Test /test URL first

---

## If Still 404

Share screenshot of:
1. Libraries section (expanded) showing all JARs
2. Build Output window
3. Contents of `build\web\WEB-INF\classes\com\paulquiz\servlet\` folder
