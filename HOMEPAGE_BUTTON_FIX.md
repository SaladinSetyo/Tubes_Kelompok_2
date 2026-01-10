# Quick Fix untuk Homepage Button Loop

## Problem
Button "Mulai Sekarang" di homepage looping kembali ke homepage.

## Root Cause
JSP conditional logic menggunakan `${firstModule.id}` yang mungkin null atau empty dari database.

## Quick Fix

**Option 1: Langsung link ke /modules** (RECOMMENDED - Simple)

Replace button di index.jsp line 104-119 dengan:

```jsp
<div class="mt-10 flex flex-col sm:flex-row justify-center gap-4">
    <a href="${pageContext.request.contextPath}/modules"
       class="inline-flex items-center justify-center px-8 py-4 text-base font-bold text-white bg-blue-600 rounded-full hover:bg-blue-700 shadow-lg transition-all hover:shadow-xl hover:-translate-y-1">
        Mulai Belajar Sekarang
    </a>
</div>
```

**Option 2: Import seed data**

Atau buat module di database dulu via admin panel:
1. Login sebagai admin
2. Go to `/admin`
3. Create module pertama
4. Homepage akan otomatis detect dan link ke module tersebut

## Testing

After fix:
1. Clean & Build (Shift+F11)
2. Run (F6)
3. Click "Mulai Sekarang"
4. Should navigate to `/modules` page

---

**Saya recommend Option 1 (direct link) karena lebih simple dan always works.**
