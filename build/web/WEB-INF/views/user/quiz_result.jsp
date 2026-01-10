<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="id" class="dark">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Hasil Kuis: ${quiz.title} - Paul Quiz</title>
            <script src="https://cdn.tailwindcss.com"></script>
            <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700;800&display=swap"
                rel="stylesheet">
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

        <body class="bg-[#0B1120] text-gray-100 min-h-screen font-sans flex items-center justify-center p-4">

            <div
                class="max-w-md w-full bg-gray-800 rounded-3xl border border-gray-700 p-8 text-center shadow-2xl relative overflow-hidden">

                <!-- Background Glow -->
                <div
                    class="absolute top-0 left-1/2 transform -translate-x-1/2 w-full h-32 bg-gradient-to-b from-blue-500/20 to-transparent pointer-events-none">
                </div>

                <c:choose>
                    <c:when test="${score >= 70}">
                        <div
                            class="w-24 h-24 bg-green-500/10 rounded-full flex items-center justify-center mx-auto mb-6 ring-4 ring-green-500/20">
                            <svg class="w-12 h-12 text-green-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                            </svg>
                        </div>
                        <h1 class="text-3xl font-bold text-white mb-2">Selamat! 🎉</h1>
                        <p class="text-gray-400 mb-6">Anda telah menyelesaikan kuis ini.</p>
                    </c:when>
                    <c:otherwise>
                        <div
                            class="w-24 h-24 bg-red-500/10 rounded-full flex items-center justify-center mx-auto mb-6 ring-4 ring-red-500/20">
                            <svg class="w-12 h-12 text-red-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z">
                                </path>
                            </svg>
                        </div>
                        <h1 class="text-3xl font-bold text-white mb-2">Belum Lulus 😔</h1>
                        <p class="text-gray-400 mb-6">Jangan menyerah, coba lagi!</p>
                    </c:otherwise>
                </c:choose>



                <c:if test="${pointsAwarded}">
                    <div class="mb-6 p-4 bg-green-500/10 border border-green-500/20 rounded-xl animate-pulse">
                        <p class="text-green-400 font-bold text-lg flex items-center justify-center gap-2">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                            </svg>
                            +${pointsEarned} XP Ditambahkan ke Profil!
                        </p>
                    </div>
                </c:if>

                <c:if test="${alreadyPassed && !pointsAwarded}">
                    <div class="mb-6 p-4 bg-blue-500/10 border border-blue-500/20 rounded-xl">
                        <p class="text-blue-400 font-medium text-sm flex items-center justify-center gap-2">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                            </svg>
                            Anda sudah lulus kuis ini sebelumnya. Poin XP hanya diberikan sekali.
                        </p>
                    </div>
                </c:if>

                <div class="bg-gray-900/50 rounded-2xl p-6 mb-8 border border-gray-700">
                    <div class="text-5xl font-extrabold text-transparent bg-clip-text bg-gradient-to-r 
                ${score >= 70 ? 'from-green-400 to-emerald-600' : 'from-red-400 to-orange-600'}">
                        ${score}
                    </div>
                    <div class="text-sm font-medium text-gray-500 uppercase tracking-widest mt-2">Nilai Akhir</div>

                    <div class="flex justify-between mt-6 text-sm">
                        <div class="text-center">
                            <span class="block text-green-400 font-bold text-lg">${correctCount}</span>
                            <span class="text-gray-500">Benar</span>
                        </div>
                        <div class="text-center">
                            <span class="block text-gray-300 font-bold text-lg">${totalQuestions}</span>
                            <span class="text-gray-500">Total Soal</span>
                        </div>
                        <div class="text-center">
                            <span class="block text-blue-400 font-bold text-lg">${score}%</span>
                            <span class="text-gray-500">Akurasi</span>
                        </div>
                    </div>
                </div>

                <div class="space-y-3">
                    <a href="${pageContext.request.contextPath}/modules"
                        class="block w-full py-3 bg-gray-700 hover:bg-gray-600 text-white font-medium rounded-xl transition">
                        Kembali ke Modul
                    </a>
                    <a href="${pageContext.request.contextPath}/quizzes/${quiz.id}"
                        class="block w-full py-3 bg-gray-800/50 hover:bg-gray-800 text-gray-300 font-medium rounded-xl border border-gray-700 transition">
                        Ulangi Kuis
                    </a>
                </div>
            </div>

        </body>

        </html>