<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="id" class="dark">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>
                <c:if test="${module != null}">Edit Modul</c:if>
                <c:if test="${module == null}">Tambah Modul</c:if> - Admin Paul Quiz
            </title>
            <script src="https://cdn.tailwindcss.com"></script>
            <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700;800&display=swap"
                rel="stylesheet">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
            <script>
                tailwind.config = {
                    darkMode: 'class',
                    theme: {
                        extend: {
                            fontFamily: { sans: ['Outfit', 'sans-serif'] },
                            colors: { dark: { 900: '#0F172A', 800: '#1E293B', 700: '#334155' } }
                        }
                    }
                }
            </script>
        </head>

        <body class="bg-[#0B1120] text-gray-100 min-h-screen font-sans">

            <%@ include file="../components/navbar.jsp" %>

                <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                    <div class="flex items-center gap-4 mb-8">
                        <a href="${pageContext.request.contextPath}/admin/modules"
                            class="p-2 bg-gray-800 rounded-lg hover:bg-gray-700 transition text-gray-400 hover:text-white">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M10 19l-7-7m0 0l7-7m-7 7h18" />
                            </svg>
                        </a>
                        <h1 class="text-2xl font-bold">
                            <c:if test="${module != null}">Edit Modul: ${module.title}</c:if>
                            <c:if test="${module == null}">Tambah Modul Baru</c:if>
                        </h1>
                    </div>

                    <div class="bg-gray-800 rounded-2xl border border-gray-700 p-8">
                        <form action="${pageContext.request.contextPath}/admin/modules/<c:if test=" ${module !=null}">
                            edit</c:if>
                            <c:if test="${module == null}">create</c:if>" method="POST" class="space-y-6">

                            <c:if test="${module != null}">
                                <input type="hidden" name="id" value="${module.id}">
                            </c:if>

                            <!-- Judul -->
                            <div>
                                <label for="title" class="block text-sm font-medium text-gray-300 mb-2">Judul
                                    Modul</label>
                                <input type="text" name="title" id="title" required
                                    value="<c:out value='${module.title}' />"
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                                    placeholder="Contoh: Pengantar Fintech">
                            </div>

                            <!-- Deskripsi -->
                            <div>
                                <label for="description" class="block text-sm font-medium text-gray-300 mb-2">Deskripsi
                                    Singkat</label>
                                <textarea name="description" id="description" rows="3" required
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                                    placeholder="Jelaskan isi modul secara singkat..."><c:out value='${module.description}' /></textarea>
                            </div>

                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <!-- Level -->
                                <div>
                                    <label for="level" class="block text-sm font-medium text-gray-300 mb-2">Level
                                        Difficulty</label>
                                    <select name="level" id="level" required
                                        class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition appearance-none">
                                        <option value="Beginner" <c:if test="${module.level == 'Beginner'}">selected
                                            </c:if>>Beginner</option>
                                        <option value="Intermediate" <c:if test="${module.level == 'Intermediate'}">
                                            selected</c:if>>Intermediate</option>
                                        <option value="Advanced" <c:if test="${module.level == 'Advanced'}">selected
                                            </c:if>>Advanced</option>
                                    </select>
                                </div>

                                <!-- Poin -->
                                <div>
                                    <label for="points" class="block text-sm font-medium text-gray-300 mb-2">Poin
                                        (XP)</label>
                                    <input type="number" name="points" id="points" required min="0"
                                        value="<c:out value='${module.points}' default='100' />"
                                        class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition">
                                </div>
                            </div>

                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <!-- Durasi -->
                                <div>
                                    <label for="durationMinutes"
                                        class="block text-sm font-medium text-gray-300 mb-2">Durasi (Menit)</label>
                                    <input type="number" name="durationMinutes" id="durationMinutes" required min="1"
                                        value="<c:out value='${module.durationMinutes}' default='15' />"
                                        class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition">
                                </div>

                                <!-- Order Index -->
                                <div>
                                    <label for="orderIndex" class="block text-sm font-medium text-gray-300 mb-2">Urutan
                                        Tampil</label>
                                    <input type="number" name="orderIndex" id="orderIndex" required min="1"
                                        value="<c:out value='${module.orderIndex}' default='1' />"
                                        class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition">
                                </div>
                            </div>

                            <!-- Icon (SVG) -->
                            <div>
                                <label for="icon" class="block text-sm font-medium text-gray-300 mb-2">Icon (SVG
                                    Code)</label>
                                <div class="text-xs text-gray-500 mb-2">Paste kode SVG di sini, atau gunakan teks pendek
                                    jika belum siap.</div>
                                <textarea name="icon" id="icon" rows="3" required
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white font-mono text-sm focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                                    placeholder="<svg...>...</svg>"><c:out value='${module.icon}' /></textarea>
                            </div>

                            <!-- Submit Button -->
                            <div class="pt-4 flex justify-end">
                                <button type="submit"
                                    class="px-8 py-3 bg-blue-600 hover:bg-blue-700 text-white font-bold rounded-xl shadow-lg hover:shadow-blue-500/30 transition transform hover:-translate-y-0.5">
                                    <c:if test="${module != null}">Simpan Perubahan</c:if>
                                    <c:if test="${module == null}">Buat Modul</c:if>
                                </button>
                            </div>

                        </form>
                    </div>
                </div>
        </body>

        </html>