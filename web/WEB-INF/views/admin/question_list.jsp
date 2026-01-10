<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="id" class="dark">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Kelola Soal - Admin Paul Quiz</title>
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
                    <div class="flex flex-col md:flex-row justify-between items-start md:items-center mb-8 gap-4">
                        <div>
                            <h1 class="text-2xl font-bold">Manajemen Soal</h1>
                            <p class="text-gray-400">Quiz: <span class="text-white font-semibold">${quiz.title}</span>
                            </p>
                        </div>
                        <div class="flex gap-4">
                            <a href="${pageContext.request.contextPath}/admin/quizzes"
                                class="px-4 py-2 bg-gray-700 hover:bg-gray-600 rounded-lg text-white font-medium transition">
                                Kembali ke Kuis
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/questions/create?quizId=${quiz.id}"
                                class="px-4 py-2 bg-green-600 hover:bg-green-700 rounded-lg text-white font-medium transition flex items-center gap-2">
                                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M12 4v16m8-8H4" />
                                </svg>
                                Tambah Soal
                            </a>
                        </div>
                    </div>

                    <div class="bg-gray-800 rounded-2xl border border-gray-700 overflow-hidden">
                        <div class="overflow-x-auto">
                            <table class="w-full text-left text-sm text-gray-400">
                                <thead class="bg-gray-900/50 text-gray-100 uppercase font-bold text-xs">
                                    <tr>
                                        <th scope="col" class="px-6 py-4 w-16">ID</th>
                                        <th scope="col" class="px-6 py-4">Pertanyaan</th>
                                        <th scope="col" class="px-6 py-4">Deskripsi/Detail</th>
                                        <th scope="col" class="px-6 py-4 text-center w-48">Aksi</th>
                                    </tr>
                                </thead>
                                <tbody class="divide-y divide-gray-700">
                                    <c:forEach var="q" items="${listQuestions}">
                                        <tr class="hover:bg-gray-700/50 transition">
                                            <td class="px-6 py-4 font-medium text-gray-300">#${q.id}</td>
                                            <td class="px-6 py-4 font-semibold text-white">
                                                ${q.questionText}
                                            </td>
                                            <td class="px-6 py-4 truncate max-w-md">
                                                ${q.description}
                                            </td>
                                            <td class="px-6 py-4 text-center">
                                                <div class="inline-flex rounded-md shadow-sm" role="group">
                                                    <a href="${pageContext.request.contextPath}/admin/questions/edit?id=${q.id}"
                                                        class="px-3 py-2 text-sm font-medium text-white bg-blue-600 rounded-l-lg hover:bg-blue-700 border-r border-blue-500 transition">
                                                        Edit
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/questions/delete?id=${q.id}"
                                                        onclick="return confirm('Apakah Anda yakin ingin menghapus soal ini?');"
                                                        class="px-3 py-2 text-sm font-medium text-white bg-red-600 rounded-r-lg hover:bg-red-700 transition">
                                                        Hapus
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty listQuestions}">
                                        <tr>
                                            <td colspan="4" class="px-6 py-12 text-center text-gray-500 italic">
                                                Belum ada soal untuk kuis ini.
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