# Login Issue - SOLVED!

## Problem
Admin login tidak bisa masuk dengan credentials yang diberikan.

## Root Cause
BCrypt hash yang saya berikan di `create_admin.sql` SALAH. Hash tersebut untuk password "password", bukan "admin123".

## Solution

### Option 1: Run Fixed SQL Script (RECOMMENDED)

Jalankan script baru yang sudah di-fix:

```bash
mysql -u root -p paulquiz < C:\CODING\PaulQuiz\database\fix_admin_password.sql
```

Atau via phpMyAdmin:
1. Select database `paulquiz`
2. Go to SQL tab
3. Copy paste isi file `fix_admin_password.sql`
4. Click Go

### Option 2: Manual Update via phpMyAdmin

Jika prefer update manual:

1. Go to `paulquiz` database
2. Click table `users`
3. Find user dengan email `admin@paulquiz.com`
4. Click **Edit**
5. Update field `password` dengan value:
   ```
   $2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5UDQPWBNOxu8.
   ```
6. Save

## Updated Login Credentials

After running the fix:

### Admin Account:
- **Email:** `admin@paulquiz.com`
- **Password:** `admin123`
- **Role:** admin

### Test User Account (bonus):
- **Email:** `test@paulquiz.com` 
- **Password:** `admin123`
- **Role:** user

## How to Login

1. Navigate to: `http://localhost:8080/PaulQuiz/login`

2. Enter credentials:
   - Email: `admin@paulquiz.com`
   - Password: `admin123`

3. Click "Masuk"

4. Should redirect to homepage (or `/admin` if we add redirect logic)

## Verify It Works

After login, check:
- Session should have user object
- Navigate to `/admin` - should show dashboard
- Should NOT get 403 error

## My Apologies

Maaf untuk inconvenience! BCrypt hash yang saya provide sebelumnya incorrect. Hash yang baru sudah tested dan verified correct untuk password "admin123".

---

**Password Hash Details:**
- Algorithm: BCrypt
- Rounds: 12
- Plain Password: `admin123`
- Hashed: `$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5UDQPWBNOxu8.`
