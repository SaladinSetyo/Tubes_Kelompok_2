# FINAL SOLUTION - Servlet API Configuration

## Problem
```
error: package javax.servlet does not exist
```

**ROOT CAUSE:** Servlet API JARs removed dari WEB-INF/lib (correct!) tapi juga TIDAK ada di NetBeans Libraries compile classpath!

---

## Solution - Add to Libraries with "Compile" Scope Only

### Step 1: Add Servlet API to NetBeans Libraries

1. **Right-click** project "PaulQuiz" → **Properties**

2. **Libraries** → tab **Compile**

3. Click **"Add JAR/Folder..."**

4. Navigate to: `C:\CODING\PaulQuiz\lib\`

5. **SELECT THESE TWO:**
   - `javax.servlet-api-4.0.1.jar`
   - `javax.servlet.jsp-api-2.3.3.jar`

6. Click **Open**

7. **CRITICAL:** Di Libraries list, **UNCHECK** "Package" column untuk:
   - javax.servlet-api-4.0.1.jar ← UNCHECK!
   - javax.servlet.jsp-api-2.3.3.jar ← UNCHECK!
   
   **This prevents them from being copied to WEB-INF/lib!**

8. **ADD OTHER JARS** (with Package CHECKED):
   - Select ALL:
     - jbcrypt-0.4.jar
     - jstl-1.2.jar
     - mysql-connector-j-8.2.0.jar
     - standard-1.1.2.jar
   - These SHOULD be packaged (Package checkbox CHECKED)

9. Click **OK**

---

## Expected Library Configuration

```
Compile Libraries:
┌─────────────────────────────────────────┬─────────┐
│ JAR File                                │ Package │
├─────────────────────────────────────────┼─────────┤
│ javax.servlet-api-4.0.1.jar            │   ☐     │ ← UNCHECKED!
│ javax.servlet.jsp-api-2.3.3.jar        │   ☐     │ ← UNCHECKED!
│ jbcrypt-0.4.jar                        │   ☑     │ ← CHECKED
│ jstl-1.2.jar                           │   ☑     │ ← CHECKED
│ mysql-connector-j-8.2.0.jar            │   ☑     │ ← CHECKED
│ standard-1.1.2.jar                     │   ☑     │ ← CHECKED
└─────────────────────────────────────────┴─────────┘
```

**Package Column:**
- ☑ CHECKED = Copy to WEB-INF/lib (for runtime)
- ☐ UNCHECKED = Compile-only, NOT copied (Tomcat provides)

---

## After Configuration

1. **Click OK** to close Properties

2. **Clean** project

3. **Build** project (F11)
   - Should be BUILD SUCCESSFUL
   - No "package javax.servlet does not exist" errors

4. **Verify WEB-INF/lib:**
   - Contains: jbcrypt, jstl, mysql-connector, standard
   - Does NOT contain: servlet-api, jsp-api

5. **Run** project (F6)

6. **Test:** `http://localhost:8080/PaulQuiz/test`

---

## Why This Works

- **Compile-time:** Servlet API available via Libraries
- **Runtime:** Tomcat provides servlet-api (no conflict!)
- **Other JARs:** Packaged with app (needed at runtime)
