<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="id">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Login - Paul Quiz</title>
            <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logo.svg" type="image/svg+xml">
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap"
                rel="stylesheet">
            <script src="https://cdn.tailwindcss.com"></script>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
        </head>

        <body
            class="font-['Outfit'] antialiased bg-slate-50 dark:bg-gray-900 min-h-screen flex items-center justify-center">

            <div class="w-full max-w-md px-6">
                <!-- Logo -->
                <div class="text-center mb-8">
                    <a href="${pageContext.request.contextPath}/" class="inline-block">
                        <img src="${pageContext.request.contextPath}/assets/images/logo.svg" alt="Paul Quiz"
                            class="h-20 mx-auto mb-4">
                    </a>
                    <h1 class="text-3xl font-bold text-gray-900 dark:text-white">
                        Paul <span class="text-blue-600 dark:text-blue-400">Quiz</span>
                    </h1>
                    <p class="text-gray-600 dark:text-gray-400 mt-2">Masuk ke akun Anda</p>
                </div>

                <!-- Login Card -->
                <div
                    class="bg-white dark:bg-gray-800 rounded-2xl shadow-xl p-8 border border-gray-200 dark:border-gray-700">

                    <!-- Flash Messages -->
                    <c:if test="${not empty sessionScope.flashMessage}">
                        <div
                            class="mb-6 p-4 rounded-lg ${sessionScope.flashType == 'success' ? 'bg-green-50 dark:bg-green-900/20 text-green-800 dark:text-green-400 border border-green-200 dark:border-green-800' : 'bg-red-50 dark:bg-red-900/20 text-red-800 dark:text-red-400 border border-red-200 dark:border-red-800'}">
                            ${sessionScope.flashMessage}
                        </div>
                        <c:remove var="flashMessage" scope="session" />
                        <c:remove var="flashType" scope="session" />
                    </c:if>

                    <!-- Login Form -->
                    <form method="POST" action="${pageContext.request.contextPath}/login">
                        <!-- Email -->
                        <div class="mb-6">
                            <label for="email" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                                Email
                            </label>
                            <input type="email" id="email" name="email" required
                                class="w-full px-4 py-3 rounded-lg border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
                                placeholder="Masukkan alamat email">
                        </div>

                        <!-- Password -->
                        <div class="mb-6">
                            <label for="password"
                                class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                                Password
                            </label>
                            <input type="password" id="password" name="password" required
                                class="w-full px-4 py-3 rounded-lg border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
                                placeholder="Masukkan kata sandi">
                        </div>

                        <!-- Submit Button -->
                        <button type="submit"
                            class="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded-lg transition-all duration-200 shadow-lg hover:shadow-xl transform hover:-translate-y-0.5">
                            Masuk
                        </button>
                    </form>

                    <!-- Divider -->
                    <div class="relative my-6">
                        <div class="absolute inset-0 flex items-center">
                            <div class="w-full border-t border-gray-300 dark:border-gray-600"></div>
                        </div>
                        <div class="relative flex justify-center text-sm">
                            <span class="px-2 bg-white dark:bg-gray-800 text-gray-500 dark:text-gray-400">
                                Belum punya akun?
                            </span>
                        </div>
                    </div>

                    <!-- Register Link -->
                    <a href="${pageContext.request.contextPath}/register"
                        class="block text-center text-blue-600 dark:text-blue-400 hover:text-blue-700 dark:hover:text-blue-300 font-medium transition">
                        Daftar Sekarang →
                    </a>
                </div>

                <!-- Back to Home -->
                <div class="text-center mt-6">
                    <a href="${pageContext.request.contextPath}/"
                        class="text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-white transition">
                        ← Kembali ke Beranda
                    </a>
                </div>
            </div>

            <script src="${pageContext.request.contextPath}/assets/js/theme.js"></script>
        </body>

        </html>