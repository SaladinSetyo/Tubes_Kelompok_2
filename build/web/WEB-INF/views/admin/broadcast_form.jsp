<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="id" class="dark">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Broadcast Notifikasi - Admin Paul Quiz</title>
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

                <div class="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                    <div class="flex items-center gap-4 mb-8">
                        <a href="${pageContext.request.contextPath}/admin/dashboard"
                            class="p-2 bg-gray-800 rounded-lg hover:bg-gray-700 transition text-gray-400 hover:text-white">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M10 19l-7-7m0 0l7-7m-7 7h18" />
                            </svg>
                        </a>
                        <h1 class="text-2xl font-bold">Broadcast Notifikasi</h1>
                    </div>

                    <c:if test="${not empty successMessage}">
                        <div
                            class="mb-6 p-4 bg-green-500/10 border border-green-500/20 text-green-400 rounded-xl flex items-center gap-3">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M5 13l4 4L19 7" />
                            </svg>
                            ${successMessage}
                        </div>
                    </c:if>

                    <div class="bg-gray-800 rounded-2xl border border-gray-700 p-8 shadow-xl relative overflow-hidden">
                        <!-- Decorative background -->
                        <div class="absolute top-0 right-0 -mt-16 -mr-16 w-64 h-64 bg-red-600/5 rounded-full blur-3xl">
                        </div>

                        <form action="${pageContext.request.contextPath}/admin/notifications" method="POST"
                            class="space-y-6 relative z-10">

                            <!-- Tipe Notifikasi -->
                            <div>
                                <label class="block text-sm font-medium text-gray-300 mb-3">Tipe Notifikasi</label>
                                <div class="grid grid-cols-3 gap-4">
                                    <label class="cursor-pointer">
                                        <input type="radio" name="type" value="info" checked class="peer sr-only">
                                        <div
                                            class="p-3 rounded-xl bg-gray-900 border border-gray-700 text-center peer-checked:border-blue-500 peer-checked:bg-blue-500/10 peer-checked:text-blue-400 transition hover:bg-gray-700">
                                            Info
                                        </div>
                                    </label>
                                    <label class="cursor-pointer">
                                        <input type="radio" name="type" value="success" class="peer sr-only">
                                        <div
                                            class="p-3 rounded-xl bg-gray-900 border border-gray-700 text-center peer-checked:border-green-500 peer-checked:bg-green-500/10 peer-checked:text-green-400 transition hover:bg-gray-700">
                                            Success
                                        </div>
                                    </label>
                                    <label class="cursor-pointer">
                                        <input type="radio" name="type" value="warning" class="peer sr-only">
                                        <div
                                            class="p-3 rounded-xl bg-gray-900 border border-gray-700 text-center peer-checked:border-yellow-500 peer-checked:bg-yellow-500/10 peer-checked:text-yellow-400 transition hover:bg-gray-700">
                                            Warning
                                        </div>
                                    </label>
                                </div>
                            </div>

                            <!-- Judul -->
                            <div>
                                <label for="title" class="block text-sm font-medium text-gray-300 mb-2">Judul
                                    Pesan</label>
                                <input type="text" name="title" id="title" required
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-red-500 focus:border-transparent outline-none transition"
                                    placeholder="Contoh: Pemeliharaan Sistem">
                            </div>

                            <!-- Pesan -->
                            <div>
                                <label for="message" class="block text-sm font-medium text-gray-300 mb-2">Isi
                                    Pesan</label>
                                <textarea name="message" id="message" rows="4" required
                                    class="w-full px-4 py-3 rounded-lg bg-gray-900 border border-gray-700 text-white focus:ring-2 focus:ring-red-500 focus:border-transparent outline-none transition"
                                    placeholder="Tulis pesan yang akan dikirim ke semua user..."></textarea>
                            </div>

                            <div
                                class="bg-yellow-500/10 border border-yellow-500/20 rounded-lg p-4 text-sm text-yellow-300">
                                <strong>Perhatian:</strong> Pesan ini akan dikirimkan ke <u>seluruh pengguna</u> yang
                                terdaftar di aplikasi. Aksi ini tidak dapat dibatalkan.
                            </div>

                            <!-- Submit Button -->
                            <div class="pt-2">
                                <button type="submit" onclick="return confirm('Kirim broadcast ke seluruh user?');"
                                    class="w-full px-8 py-3 bg-red-600 hover:bg-red-700 text-white font-bold rounded-xl shadow-lg hover:shadow-red-500/30 transition transform hover:-translate-y-0.5 flex justify-center items-center gap-2">
                                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M11 5.882V19.24a1.76 1.76 0 01-3.417.592l-2.147-6.15M18 13a3 3 0 100-6M5.436 13.683A4.001 4.001 0 017 6h1.832c4.1 0 7.625-1.234 9.168-3v14c-1.543-1.766-5.067-3-9.168-3H7a3.988 3.988 0 01-1.564-.317z" />
                                    </svg>
                                    Kirim Broadcast
                                </button>
                            </div>

                        </form>
                    </div>
                </div>
        </body>

        </html>