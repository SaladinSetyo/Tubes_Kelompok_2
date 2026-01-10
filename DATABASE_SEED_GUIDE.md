# 🚀 Database Population Guide

## Import Seed Data

**File:** `C:\CODING\PaulQuiz\database\seed_data.sql`

### Via phpMyAdmin:
1. Open phpMyAdmin
2. Select database `paulquiz`
3. Click **SQL** tab
4. Click **Import** or copy-paste file content
5. Click **Go**

### Via Command Line:
```bash
mysql -u root -p paulquiz < C:\CODING\PaulQuiz\database\seed_data.sql
```

## What's Included

### 5 Modules:
1. ✅ Apa itu Fintech?
2. ✅ Digital Payment
3. ✅ E-Wallet & Mobile Banking
4. ✅ Cryptocurrency Basics
5. ✅ Investment & Trading

### Contents:
- 📝 **Articles** (educational text)
- 🎥 **Videos** (YouTube embeds)
- 📊 **Infographics** (images)
- ❓ **Quizzes** (interactive tests)

### 3 Quizzes:
- Quiz Pengenalan Fintech (3 questions)
- Quiz Digital Payment (2 questions)
- Quiz E-Wallet Safety

### 3 Test Users:
- Email: `user1@test.com` - Password: `admin123` (150 points)
- Email: `user2@test.com` - Password: `admin123` (200 points)
- Email: `user3@test.com` - Password: `admin123` (100 points)

## After Import

Test the features:

1. **Module List:** `http://localhost:8080/PaulQuiz/modules`
2. **Module Detail:** `http://localhost:8080/PaulQuiz/modules/1`
3. **Homepage:** Should show featured content
4. **Admin Dashboard:** Should show correct counts

---

**Status:** ✅ Ready to import and test!
