<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="id" class="dark">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>
                <c:if test="${quiz != null}">Edit Kuis</c:if>
                <c:if test="${quiz == null}">Tambah Kuis</c:if> - Admin Paul Quiz
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
                        <a href="${pageContext.request.contextPath}/admin/quizzes"
                            class="p-2 bg-gray-800 rounded-lg hover:bg-gray-700 transition text-gray-400 hover:text-white">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M10 19l-7-7m0 0l7-7m-7 7h18" />
                            </svg>
                        </a>
                        <h1 class="text-2xl font-bold">
                            <c:if test="${quiz != null}">Edit Kuis: ${quiz.title}</c:if>
                            <c:if test="${quiz == null}">Buat Kuis Baru</c:if>
                        </h1>
                    </div>

                    <div class="bg-gray-800 rounded-2xl border border-gray-700 p-8">
                        <form action="${pageContext.request.contextPath}/admin/quizzes/<c:if test=" ${quiz !=null}">edit
                            </c:if>
                            <c:if test="${quiz == null}">create</c:if>" method="POST" class="space-y-6">

                            <c:if test="${quiz != null}">
                                <input type="hidden" name="id" value="${quiz.id}">
                            </c:if>

                            <!-- Modul Selection -->
                            <div>
                                <label for="moduleId" class="block text-sm font-medium text-gray-300 mb-2">Pilih
                                    Modul</label>
                                <select name="moduleId" id="moduleId" required
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-green-500 focus:border-transparent outline-none transition appearance-none">
                                    <option value="" disabled <c:if test="${quiz == null}">selected</c:if>>-- Pilih
                                        Modul --</option>
                                    <c:forEach var="module" items="${listModules}">
                                        <option value="${module.id}" <c:if
                                            test="${quiz != null && quiz.moduleId == module.id}">selected</c:if>>
                                            ${module.title}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Judul -->
                            <div>
                                <label for="title" class="block text-sm font-medium text-gray-300 mb-2">Judul
                                    Kuis</label>
                                <input type="text" name="title" id="title" required
                                    value="<c:out value='${quiz.title}' />"
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-green-500 focus:border-transparent outline-none transition"
                                    placeholder="Contoh: Kuis Dasar Fintech">
                            </div>

                            <!-- Deskripsi -->
                            <div>
                                <label for="description" class="block text-sm font-medium text-gray-300 mb-2">Deskripsi
                                    Kuis</label>
                                <textarea name="description" id="description" rows="3" required
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-green-500 focus:border-transparent outline-none transition"
                                    placeholder="Jelaskan tentang kuis ini..."><c:out value='${quiz.description}' /></textarea>
                            </div>

                            <!-- Submit Button -->
                            <div class="pt-4 flex justify-end">
                                <button type="submit"
                                    class="px-8 py-3 bg-green-600 hover:bg-green-700 text-white font-bold rounded-xl shadow-lg hover:shadow-green-500/30 transition transform hover:-translate-y-0.5">
                                    <c:if test="${quiz != null}">Simpan Perubahan</c:if>
                                    <c:if test="${quiz == null}">Buat Kuis</c:if>
                                </button>
                            </div>

                        </form>
                    </div>
                </div>
        </body>

        </html>