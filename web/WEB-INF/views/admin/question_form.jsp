<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="id" class="dark">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>
                <c:if test="${question != null}">Edit Soal</c:if>
                <c:if test="${question == null}">Tambah Soal</c:if> - Admin Paul Quiz
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
                        <a href="${pageContext.request.contextPath}/admin/questions?quizId=${quiz.id}"
                            class="p-2 bg-gray-800 rounded-lg hover:bg-gray-700 transition text-gray-400 hover:text-white">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M10 19l-7-7m0 0l7-7m-7 7h18" />
                            </svg>
                        </a>
                        <div>
                            <h1 class="text-2xl font-bold">
                                <c:if test="${question != null}">Edit Soal</c:if>
                                <c:if test="${question == null}">Tambah Soal Baru</c:if>
                            </h1>
                            <p class="text-gray-400 text-sm mt-1">Quiz: ${quiz.title}</p>
                        </div>
                    </div>

                    <div class="bg-gray-800 rounded-2xl border border-gray-700 p-8">
                        <form
                            action="${pageContext.request.contextPath}/admin/questions/${question != null ? 'edit' : 'create'}"
                            method="POST" class="space-y-6">

                            <input type="hidden" name="quizId" value="${quiz.id}">
                            <c:if test="${question != null}">
                                <input type="hidden" name="id" value="${question.id}">
                            </c:if>

                            <!-- Pertanyaan -->
                            <div>
                                <label for="questionText" class="block text-sm font-medium text-gray-300 mb-2">Teks
                                    Pertanyaan</label>
                                <textarea name="questionText" id="questionText" rows="3" required
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-green-500 focus:border-transparent outline-none transition"
                                    placeholder="Tulis pertanyaan di sini..."><c:out value='${question.questionText}' /></textarea>
                            </div>

                            <!-- Deskripsi (Opsional) -->
                            <div>
                                <label for="description" class="block text-sm font-medium text-gray-300 mb-2">Deskripsi
                                    Tambahan / Hint (Opsional)</label>
                                <input type="text" name="description" id="description"
                                    value="<c:out value='${question.description}' />"
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-green-500 focus:border-transparent outline-none transition"
                                    placeholder="Contoh: Pilih jawaban yang paling tepat">
                            </div>

                            <div class="border-t border-gray-700 pt-6 mt-6">
                                <h3 class="text-xl font-bold mb-4">Pilihan Jawaban</h3>
                                <p class="text-gray-400 text-sm mb-6 flex items-center gap-2">
                                    <svg class="w-4 h-4 text-green-500" fill="currentColor" viewBox="0 0 20 20">
                                        <path fill-rule="evenodd"
                                            d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                                            clip-rule="evenodd"></path>
                                    </svg>
                                    Pilih radio button di sebelah kiri untuk menandai jawaban yang benar.
                                </p>

                                <div class="space-y-4">
                                    <% java.util.List<com.paulquiz.model.Answer> answersList = (java.util.List
                                        <com.paulquiz.model.Answer>) request.getAttribute("answers");
                                            String[] opts = {"A", "B", "C", "D"};
                                            for(int i=0; i<4; i++) { String opt=opts[i]; String val="" ; boolean
                                                checked=false; if (answersList !=null && i < answersList.size()) {
                                                val=answersList.get(i).getAnswerText();
                                                checked=answersList.get(i).getIsCorrect(); } %>
                                                <div
                                                    class="flex items-start gap-4 p-4 bg-gray-900/50 border border-gray-700 rounded-xl hover:border-gray-600 transition group">
                                                    <div class="pt-3">
                                                        <input type="radio" name="correctAnswer" value="<%= opt %>"
                                                            required <%=checked ? "checked" : "" %>
                                                        class="w-5 h-5 text-green-600 bg-gray-800 border-gray-600
                                                        focus:ring-green-600 focus:ring-2">
                                                    </div>
                                                    <div class="flex-1">
                                                        <label
                                                            class="block text-xs font-bold text-gray-400 mb-1 uppercase">Opsi
                                                            <%= opt %>
                                                        </label>
                                                        <input type="text" name="option<%= opt %>" value="<%= val %>"
                                                            required
                                                            class="w-full bg-transparent border-0 border-b border-gray-700 focus:border-green-500 focus:ring-0 text-white placeholder-gray-600 px-0 py-2 transition"
                                                            placeholder="Jawaban <%= opt %>...">
                                                    </div>
                                                </div>
                                                <% } %>
                                </div>
                            </div>

                            <!-- Submit Button -->
                            <div class="pt-4 flex justify-end">
                                <button type="submit"
                                    class="px-8 py-3 bg-green-600 hover:bg-green-700 text-white font-bold rounded-xl shadow-lg hover:shadow-green-500/30 transition transform hover:-translate-y-0.5">
                                    <c:if test="${question != null}">Simpan Perubahan</c:if>
                                    <c:if test="${question == null}">Simpan Soal</c:if>
                                </button>
                            </div>

                        </form>
                    </div>
                </div>
        </body>

        </html>