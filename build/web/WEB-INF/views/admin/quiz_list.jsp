<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="id" class="dark">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Kelola Kuis - Admin Paul Quiz</title>
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

                <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                    <div class="flex justify-between items-center mb-8">
                        <div>
                            <h1 class="text-2xl font-bold">Manajemen Kuis</h1>
                            <p class="text-gray-400">Buat dan atur kuis untuk setiap modul.</p>
                        </div>
                        <div class="flex gap-4">
                            <a href="${pageContext.request.contextPath}/admin/dashboard"
                                class="px-4 py-2 bg-gray-700 hover:bg-gray-600 rounded-lg text-white font-medium transition">
                                Kembali
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/quizzes/create"
                                class="px-4 py-2 bg-green-600 hover:bg-green-700 rounded-lg text-white font-medium transition flex items-center gap-2">
                                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M12 4v16m8-8H4" />
                                </svg>
                                Tambah Kuis
                            </a>
                        </div>
                    </div>

                    <div class="bg-gray-800 rounded-2xl border border-gray-700 overflow-hidden">
                        <div class="overflow-x-auto">
                            <table class="w-full text-left text-sm text-gray-400">
                                <thead class="bg-gray-900/50 text-gray-100 uppercase font-bold text-xs">
                                    <tr>
                                        <th scope="col" class="px-6 py-4">ID</th>
                                        <th scope="col" class="px-6 py-4">Modul</th>
                                        <th scope="col" class="px-6 py-4">Judul Kuis</th>
                                        <th scope="col" class="px-6 py-4">Deskripsi</th>
                                        <th scope="col" class="px-6 py-4 text-center">Aksi</th>
                                    </tr>
                                </thead>
                                <tbody class="divide-y divide-gray-700">
                                    <c:forEach var="quiz" items="${listQuizzes}">
                                        <tr class="hover:bg-gray-700/50 transition">
                                            <td class="px-6 py-4 font-medium text-gray-300">#${quiz.id}</td>
                                            <td class="px-6 py-4 text-blue-400 font-medium">${quiz.moduleName}</td>
                                            <td class="px-6 py-4 font-semibold text-white">${quiz.title}</td>
                                            <td class="px-6 py-4 truncate max-w-xs">${quiz.description}</td>
                                            <td class="px-6 py-4 text-center">
                                                <div class="flex justify-center gap-2">
                                                    <a href="${pageContext.request.contextPath}/admin/questions?quizId=${quiz.id}"
                                                        class="p-2 text-gray-400 hover:text-blue-600 transition-colors bg-white/5 rounded-lg border border-white/10"
                                                        title="Kelola Pertanyaan">
                                                        <svg class="w-5 h-5" fill="none" stroke="currentColor"
                                                            viewBox="0 0 24 24">
                                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                                stroke-width="2"
                                                                d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z">
                                                            </path>
                                                        </svg>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/quizzes/edit?id=${quiz.id}"
                                                        class="p-2 text-gray-400 hover:text-green-500 transition-colors bg-white/5 rounded-lg border border-white/10"
                                                        title="Edit Kuis">
                                                        <svg class="w-5 h-5" fill="none" stroke="currentColor"
                                                            viewBox="0 0 24 24">
                                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                                stroke-width="2"
                                                                d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z">
                                                            </path>
                                                        </svg>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/quizzes/delete?id=${quiz.id}"
                                                        onclick="return confirm('Apakah Anda yakin ingin menghapus kuis ini?');"
                                                        class="p-2 text-gray-400 hover:text-red-500 transition-colors bg-white/5 rounded-lg border border-white/10"
                                                        title="Hapus Kuis">
                                                        <svg class="w-5 h-5" fill="none" stroke="currentColor"
                                                            viewBox="0 0 24 24">
                                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                                stroke-width="2"
                                                                d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16">
                                                            </path>
                                                        </svg>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty listQuizzes}">
                                        <tr>
                                            <td colspan="5" class="px-6 py-12 text-center text-gray-500 italic">
                                                Belum ada kuis yang dibuat.
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
        </body>

        </html>