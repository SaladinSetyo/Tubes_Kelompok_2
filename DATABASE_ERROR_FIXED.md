# Database Error Fixed!

## Problem
Error: `#1054 - Unknown column 'is_admin' in 'field list'`

## Root Cause
The `create_admin.sql` script was using `is_admin` column (boolean), but the actual database schema uses `role` column (ENUM: 'admin' or 'user').

## Solution Applied
Updated `create_admin.sql` to use:
- Column: `role` instead of `is_admin`
- Value: `'admin'` instead of `1`
- Removed: `is_online` column (not needed for insert)

## How to Run (Fixed)

Now you can run the script successfully:

```bash
mysql -u root -p paulquiz < C:\CODING\PaulQuiz\database\create_admin.sql
```

or in phpMyAdmin:
1. Select  `paulquiz` database
2. Go to SQL tab
3. Paste the content of `create_admin.sql`
4. Click "Go"

## Expected Output
```
✅ Admin account created/verified successfully!

+----+---------------+---------------------+------------+--------+---------------------+
| id | name          | email               | admin_role | points | created_at          |
+----+---------------+---------------------+------------+--------+---------------------+
|  1 | Administrator | admin@paulquiz.com  | admin      |      0 | 2026-01-03 04:25:00 |
+----+---------------+---------------------+------------+--------+---------------------+
```

## Login Credentials
- **Email:** `admin@paulquiz.com`
- **Password:** `admin123`

The password is already BCrypt hashed in the SQL script, so it's ready to use!
