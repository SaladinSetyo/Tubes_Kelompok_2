<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="id">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Paul Quiz - Tingkatkan Literasi Keuangan Anda</title>
            <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logo.svg" type="image/svg+xml">
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap"
                rel="stylesheet">
            <script src="https://cdn.tailwindcss.com"></script>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
        </head>

        <body class="font-['Outfit'] antialiased bg-slate-50 dark:bg-gray-900 text-gray-900 dark:text-white">

            <!-- Header (Unified) -->
            <jsp:include page="/WEB-INF/views/components/navbar.jsp" />

            <!-- Main Content -->
            <main class="pt-20">
                <!-- Hero Section -->
                <section
                    class="relative overflow-hidden bg-gradient-to-br from-blue-50 via-white to-purple-50 dark:from-gray-900 dark:via-gray-900 dark:to-gray-800 py-20">
                    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center relative z-10">
                        <span
                            class="inline-block py-1 px-3 rounded-full bg-blue-100 dark:bg-blue-900/30 text-blue-600 dark:text-blue-300 text-sm font-semibold mb-6">
                            Masa Depan Finansial
                        </span>
                        <h1 class="text-4xl md:text-5xl lg:text-6xl font-bold tracking-tight mb-6 leading-tight">
                            Platform Inovatif untuk<br>
                            <span
                                class="text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-purple-600">Literasi
                                Keuangan</span>
                        </h1>
                        <p class="mt-6 max-w-3xl mx-auto text-xl text-gray-600 dark:text-gray-300 leading-relaxed">
                            Paul Quiz hadir untuk memberdayakan Anda dengan pengetahuan finansial yang relevan, praktis,
                            dan mudah diakses.
                        </p>
                        <div class="mt-10 flex flex-col sm:flex-row justify-center gap-4">
                            <a href="${pageContext.request.contextPath}/modules"
                                class="inline-flex items-center justify-center px-8 py-4 text-base font-bold text-white bg-blue-600 rounded-full hover:bg-blue-700 shadow-lg transition-all hover:shadow-xl hover:-translate-y-1">
                                Mulai Belajar Sekarang
                            </a>
                        </div>
                    </div>
                </section>

                <!-- Featured Content Section -->
                <c:if test="${not empty infographics or not empty videos}">
                    <section class="py-20 bg-white dark:bg-gray-800">
                        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                            <div class="text-center mb-12">
                                <h2 class="text-3xl font-bold">Konten Edukasi Pilihan</h2>
                                <p class="mt-4 text-lg text-gray-600 dark:text-gray-400">Pelajari fintech dengan cara
                                    yang menyenangkan</p>
                            </div>

                            <!-- Videos -->
                            <c:if test="${not empty videos}">
                                <h3 class="text-2xl font-bold mb-6">Video Edukasi</h3>
                                <div class="grid gap-8 md:grid-cols-2 lg:grid-cols-3 mb-12">
                                    <c:forEach items="${videos}" var="video">
                                        <div
                                            class="group relative aspect-video rounded-2xl overflow-hidden shadow-lg border border-gray-200 dark:border-gray-700 hover:shadow-2xl hover:scale-105 transition-all duration-300">
                                            <iframe class="w-full h-full" src="${video.embedUrl}" title="${video.title}"
                                                frameborder="0"
                                                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                                                allowfullscreen></iframe>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:if>

                            <!-- Infographics -->
                            <c:if test="${not empty infographics}">
                                <h3 class="text-2xl font-bold mb-6">Infografis Keuangan</h3>
                                <div class="grid gap-8 md:grid-cols-3">
                                    <c:forEach items="${infographics}" var="infographic">
                                        <a href="${infographic.mediaUrl}" target="_blank"
                                            class="group relative rounded-2xl overflow-hidden shadow-lg hover:shadow-2xl transition-all hover:-translate-y-1 block aspect-[3/4]">
                                            <img src="${infographic.mediaUrl}" alt="${infographic.title}"
                                                class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-700">
                                            <div
                                                class="absolute inset-0 bg-gradient-to-t from-black/90 via-black/40 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex flex-col justify-end p-6">
                                                <h4 class="text-white font-bold text-lg mb-2">${infographic.title}</h4>
                                                <p class="text-gray-300 text-sm">${infographic.description}</p>
                                            </div>
                                        </a>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </div>
                    </section>
                </c:if>

                <!-- Features Section -->
                <section class="py-20 bg-slate-50 dark:bg-gray-900">
                    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                        <div class="text-center mb-16">
                            <h2 class="text-3xl font-bold">Mengapa Paul Quiz?</h2>
                            <p class="mt-4 text-lg text-gray-600 dark:text-gray-400">Belajar fintech dengan cara yang
                                modern dan interaktif</p>
                        </div>

                        <div class="grid md:grid-cols-2 lg:grid-cols-4 gap-8">
                            <div
                                class="group p-8 rounded-2xl bg-gradient-to-br from-blue-600 to-indigo-700 text-white shadow-lg shadow-blue-500/20 hover:shadow-blue-500/50 hover:scale-105 transition-all duration-300 relative overflow-hidden ring-1 ring-white/10">
                                <div
                                    class="absolute top-0 right-0 -mt-4 -mr-4 w-24 h-24 bg-white/10 rounded-full blur-xl group-hover:scale-150 transition-transform duration-500">
                                </div>
                                <div
                                    class="w-14 h-14 rounded-xl bg-white/20 backdrop-blur-sm flex items-center justify-center mb-6 text-white group-hover:bg-white group-hover:text-blue-600 transition-colors shadow-[0_0_15px_rgba(255,255,255,0.3)]">
                                    <svg class="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
                                    </svg>
                                </div>
                                <h3 class="text-xl font-bold mb-2 text-white drop-shadow-md">Konten Edukatif</h3>
                                <p class="text-blue-100">Akses modul lengkap tentang Fintech</p>
                            </div>

                            <div
                                class="group p-8 rounded-2xl bg-gradient-to-br from-indigo-600 to-purple-700 text-white shadow-lg shadow-indigo-500/20 hover:shadow-indigo-500/50 hover:scale-105 transition-all duration-300 relative overflow-hidden ring-1 ring-white/10">
                                <div
                                    class="absolute top-0 right-0 -mt-4 -mr-4 w-24 h-24 bg-white/10 rounded-full blur-xl group-hover:scale-150 transition-transform duration-500">
                                </div>
                                <div
                                    class="w-14 h-14 rounded-xl bg-white/20 backdrop-blur-sm flex items-center justify-center mb-6 text-white group-hover:bg-white group-hover:text-indigo-600 transition-colors shadow-[0_0_15px_rgba(255,255,255,0.3)]">
                                    <svg class="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                                    </svg>
                                </div>
                                <h3 class="text-xl font-bold mb-2 text-white drop-shadow-md">Kuis Interaktif</h3>
                                <p class="text-indigo-100">Uji pemahaman dengan kuis menarik</p>
                            </div>

                            <div
                                class="group p-8 rounded-2xl bg-gradient-to-br from-purple-600 to-pink-700 text-white shadow-lg shadow-purple-500/20 hover:shadow-purple-500/50 hover:scale-105 transition-all duration-300 relative overflow-hidden ring-1 ring-white/10">
                                <div
                                    class="absolute top-0 right-0 -mt-4 -mr-4 w-24 h-24 bg-white/10 rounded-full blur-xl group-hover:scale-150 transition-transform duration-500">
                                </div>
                                <div
                                    class="w-14 h-14 rounded-xl bg-white/20 backdrop-blur-sm flex items-center justify-center mb-6 text-white group-hover:bg-white group-hover:text-purple-600 transition-colors shadow-[0_0_15px_rgba(255,255,255,0.3)]">
                                    <svg class="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
                                    </svg>
                                </div>
                                <h3 class="text-xl font-bold mb-2 text-white drop-shadow-md">Pelacakan Progres</h3>
                                <p class="text-purple-100">Pantau kemajuan belajar Anda</p>
                            </div>

                            <div
                                class="group p-8 rounded-2xl bg-gradient-to-br from-pink-600 to-rose-700 text-white shadow-lg shadow-pink-500/20 hover:shadow-pink-500/50 hover:scale-105 transition-all duration-300 relative overflow-hidden ring-1 ring-white/10">
                                <div
                                    class="absolute top-0 right-0 -mt-4 -mr-4 w-24 h-24 bg-white/10 rounded-full blur-xl group-hover:scale-150 transition-transform duration-500">
                                </div>
                                <div
                                    class="w-14 h-14 rounded-xl bg-white/20 backdrop-blur-sm flex items-center justify-center mb-6 text-white group-hover:bg-white group-hover:text-pink-600 transition-colors shadow-[0_0_15px_rgba(255,255,255,0.3)]">
                                    <svg class="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M11.049 2.927c.3-.921 1.603-.921 1.902 0l1.519 4.674a1 1 0 00.95.69h4.915c.969 0 1.371 1.24.588 1.81l-3.976 2.888a1 1 0 00-.363 1.118l1.518 4.674c.3.922-.755 1.688-1.538 1.118l-3.976-2.888a1 1 0 00-1.176 0l-3.976 2.888c-.783.57-1.838-.197-1.538-1.118l1.518-4.674a1 1 0 00-.363-1.118l-3.976-2.888c-.784-.57-.38-1.81.588-1.81h4.914a1 1 0 00.951-.69l1.519-4.674z" />
                                    </svg>
                                </div>
                                <h3 class="text-xl font-bold mb-2 text-white drop-shadow-md">Gamifikasi</h3>
                                <p class="text-pink-100">Bersaing di papan peringkat</p>
                            </div>
                        </div>
                    </div>
                </section>
            </main>

            <!-- Footer -->
            <footer class="bg-gray-900 text-white py-12">
                <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div class="text-center">
                        <div class="flex items-center justify-center mb-6">
                            <img src="${pageContext.request.contextPath}/assets/images/logo.svg" alt="Paul Quiz"
                                class="h-16 mr-4">
                            <h4 class="text-3xl font-bold">PAUL <span class="text-blue-400">QUIZ</span></h4>
                        </div>
                        <p class="text-gray-400 mb-6">Platform edukasi literasi keuangan di era digital</p>
                        <p class="text-gray-500 text-sm">&copy; 2026 Paul Quiz. Educational purpose only.</p>
                    </div>
                </div>
            </footer>

            <script src="${pageContext.request.contextPath}/assets/js/theme.js"></script>
        </body>

        </html>