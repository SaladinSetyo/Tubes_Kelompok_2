<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="id" class="dark">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>
                <c:if test="${content != null}">Edit Konten</c:if>
                <c:if test="${content == null}">Tambah Konten</c:if> - Admin Paul Quiz
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
                        <a href="${pageContext.request.contextPath}/admin/contents"
                            class="p-2 bg-gray-800 rounded-lg hover:bg-gray-700 transition text-gray-400 hover:text-white">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M10 19l-7-7m0 0l7-7m-7 7h18" />
                            </svg>
                        </a>
                        <h1 class="text-2xl font-bold">
                            <c:if test="${content != null}">Edit Konten</c:if>
                            <c:if test="${content == null}">Tambah Konten Baru</c:if>
                        </h1>
                    </div>

                    <div class="bg-gray-800 rounded-2xl border border-gray-700 p-8">
                        <form
                            action="${pageContext.request.contextPath}/admin/contents/${content != null ? 'edit' : 'create'}"
                            method="POST" class="space-y-6">

                            <c:if test="${content != null}">
                                <input type="hidden" name="id" value="${content.id}">
                            </c:if>

                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <!-- Modul Selection -->
                                <div>
                                    <label for="moduleId" class="block text-sm font-medium text-gray-300 mb-2">Pilih
                                        Modul</label>
                                    <select name="moduleId" id="moduleId" required
                                        class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition appearance-none">
                                        <option value="" disabled <c:if test="${content == null}">selected</c:if>>--
                                            Pilih Modul --</option>
                                        <c:forEach var="module" items="${listModules}">
                                            <option value="${module.id}" <c:if
                                                test="${(content != null && content.moduleId == module.id) || (content == null && selectedModuleId == module.id)}">
                                                selected
                                                </c:if>>
                                                ${module.title}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Tipe Konten -->
                                <div>
                                    <label for="type" class="block text-sm font-medium text-gray-300 mb-2">Tipe
                                        Konten</label>
                                    <select name="type" id="type" required
                                        class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition appearance-none">
                                        <option value="article" <c:if test="${content.type == 'article'}">selected
                                            </c:if>>Artikel</option>
                                        <option value="video" <c:if test="${content.type == 'video'}">selected</c:if>
                                            >Video</option>
                                        <option value="infographic" <c:if test="${content.type == 'infographic'}">
                                            selected</c:if>>Infografis</option>
                                        <option value="quiz" <c:if test="${content.type == 'quiz'}">selected</c:if>>Kuis
                                            (Link ke Kuis)</option>
                                    </select>
                                </div>
                            </div>

                            <!-- Judul -->
                            <div>
                                <label for="title" class="block text-sm font-medium text-gray-300 mb-2">Judul
                                    Konten</label>
                                <input type="text" name="title" id="title" required
                                    value="<c:out value='${content.title}' />"
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition"
                                    placeholder="Contoh: Pengenalan Dasar">
                            </div>

                            <!-- Deskripsi -->
                            <div>
                                <label for="description" class="block text-sm font-medium text-gray-300 mb-2">Deskripsi
                                    Singkat</label>
                                <textarea name="description" id="description" rows="2"
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition"
                                    placeholder="Deskripsi singkat konten..."><c:out value='${content.description}' /></textarea>
                            </div>

                            <!-- Konten Body / HTML -->
                            <div>
                                <label for="body" class="block text-sm font-medium text-gray-300 mb-2">Isi Konten (HTML
                                    / Teks)</label>
                                <textarea name="body" id="body" rows="6"
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white font-mono text-sm focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition"
                                    placeholder="<p>Isi artikel di sini...</p>"><c:out value='${content.body}' /></textarea>
                                <p class="text-xs text-gray-500 mt-1">*Untuk tipe Artikel</p>
                            </div>

                            <!-- Media URL -->
                            <div>
                                <label for="mediaUrl" class="block text-sm font-medium text-gray-300 mb-2">URL Media
                                    (Video/Image)</label>
                                <input type="text" name="mediaUrl" id="mediaUrl"
                                    value="<c:out value='${content.mediaUrl}' />"
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition"
                                    placeholder="https://youtube.com/embed/...">
                                <p class="text-xs text-gray-500 mt-1">*Untuk tipe Video (Link Embed) atau Infografis
                                    (Path Image)</p>
                            </div>

                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <!-- Quiz ID (Optional) -->
                                <div>
                                    <label for="quizId" class="block text-sm font-medium text-gray-300 mb-2">Link ke
                                        Kuis (Opsional)</label>
                                    <select name="quizId" id="quizId"
                                        class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition appearance-none">
                                        <option value="">-- Tidak Terhubung ke Kuis --</option>
                                        <c:forEach var="quiz" items="${listQuizzes}">
                                            <option value="${quiz.id}" <c:if test="${content.quizId == quiz.id}">
                                                selected</c:if>>
                                                ${quiz.title} (Modul ${quiz.moduleId})
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <p class="text-xs text-gray-500 mt-1">*Jika tipe konten adalah 'quiz'</p>
                                </div>

                                <!-- Order -->
                                <div>
                                    <label for="order" class="block text-sm font-medium text-gray-300 mb-2">Urutan
                                        Tampil</label>
                                    <input type="number" name="order" id="order" required min="0"
                                        value="<c:out value='${content.order}' default='1' />"
                                        class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition">
                                </div>
                            </div>

                            <!-- Is Featured -->
                            <div class="flex items-center gap-3">
                                <input type="checkbox" name="isFeatured" id="isFeatured" value="true" <c:if
                                    test="${content.isFeatured}">checked</c:if>
                                class="w-5 h-5 rounded border-gray-700 bg-gray-900 text-purple-600 focus:ring-purple-500
                                focus:ring-offset-gray-900">
                                <label for="isFeatured" class="text-sm font-medium text-gray-300">Tampilkan sebagai
                                    Featured Content?</label>
                            </div>

                            <!-- Submit Button -->
                            <div class="pt-4 flex justify-end">
                                <button type="submit"
                                    class="px-8 py-3 bg-purple-600 hover:bg-purple-700 text-white font-bold rounded-xl shadow-lg hover:shadow-purple-500/30 transition transform hover:-translate-y-0.5">
                                    <c:if test="${content != null}">Simpan Perubahan</c:if>
                                    <c:if test="${content == null}">Buat Konten</c:if>
                                </button>
                            </div>

                        </form>
                    </div>
                </div>
        </body>

        </html>