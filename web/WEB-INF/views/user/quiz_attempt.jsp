<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="id" class="dark">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>${quiz.title} - Paul Quiz</title>
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

                <div class="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
                    <!-- Header Kuis -->
                    <div class="mb-8">
                        <a href="${pageContext.request.contextPath}/modules"
                            class="inline-flex items-center text-gray-400 hover:text-white mb-4 transition">
                            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M10 19l-7-7m0 0l7-7m-7 7h18" />
                            </svg>
                            Kembali ke Daftar Modul
                        </a>
                        <h1
                            class="text-3xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-green-400 to-blue-500 mb-2">
                            ${quiz.title}
                        </h1>
                        <p class="text-gray-400 text-lg">${quiz.description}</p>
                    </div>

                    <form action="${pageContext.request.contextPath}/quizzes/${quiz.id}/submit" method="POST"
                        class="space-y-8">
                        <c:forEach var="q" items="${questions}" varStatus="loop">
                            <div
                                class="bg-gray-800/50 border border-gray-700 rounded-2xl p-6 hover:border-gray-600 transition duration-300">
                                <div class="flex items-start gap-4 mb-6">
                                    <span
                                        class="flex-shrink-0 w-8 h-8 flex items-center justify-center rounded-lg bg-gray-700 text-green-400 font-bold">
                                        ${loop.index + 1}
                                    </span>
                                    <div>
                                        <h3 class="text-xl font-semibold text-white leading-relaxed">
                                            ${q.questionText}
                                        </h3>
                                        <c:if test="${not empty q.description}">
                                            <p class="text-sm text-gray-500 mt-2 italic">${q.description}</p>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="space-y-3 pl-12">
                                    <c:forEach var="answer" items="${questionsAnswers[q.id]}">
                                        <label
                                            class="flex items-center p-4 rounded-xl border border-gray-700 cursor-pointer hover:bg-gray-700/50 hover:border-gray-500 transition group">
                                            <input type="radio" name="question_${q.id}" value="${answer.id}" required
                                                class="w-5 h-5 text-green-500 bg-gray-900 border-gray-600 focus:ring-green-500 focus:ring-2 focus:ring-offset-gray-900">
                                            <span
                                                class="ml-3 text-gray-300 group-hover:text-white transition">${answer.answerText}</span>
                                        </label>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:forEach>

                        <div class="flex justify-end pt-6">
                            <button type="submit"
                                class="px-8 py-4 bg-gradient-to-r from-green-600 to-green-500 hover:from-green-500 hover:to-green-400 text-white font-bold rounded-xl shadow-lg hover:shadow-green-500/20 transform hover:-translate-y-1 transition duration-200">
                                Kirim Jawaban
                            </button>
                        </div>
                    </form>
                </div>

        </body>

        </html>