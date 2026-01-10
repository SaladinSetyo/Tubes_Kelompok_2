<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.paulquiz.model.User" %>
        <% User user=(User) request.getAttribute("user"); %>
            <!DOCTYPE html>
            <html lang="id" class="dark">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Profile - Paul Quiz</title>
                <script src="https://cdn.tailwindcss.com"></script>
                <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700;800&display=swap"
                    rel="stylesheet">
                <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/styles.css">
                <script>
                    tailwind.config = {
                        darkMode: 'class',
                        theme: {
                            extend: {
                                fontFamily: {
                                    sans: ['Outfit', 'sans-serif'],
                                },
                                colors: {
                                    dark: {
                                        900: '#0F172A',
                                        800: '#1E293B',
                                        700: '#334155',
                                    }
                                }
                            }
                        }
                    }
                </script>
            </head>

            <body class="bg-[#0B1120] text-gray-100 min-h-screen font-sans">
                <!-- Navigation -->
                <%@ include file="components/navbar.jsp" %>

                    <div class="container mx-auto px-4 py-12">
                        <div class="max-w-3xl mx-auto">
                            <!-- Profile Header -->
                            <div
                                class="bg-gray-800/50 backdrop-blur-xl border border-gray-700/50 rounded-3xl p-8 mb-8 flex flex-col md:flex-row items-center md:space-x-8 text-center md:text-left shadow-xl">
                                <div
                                    class="w-32 h-32 rounded-full bg-gradient-to-br from-indigo-500 to-purple-600 flex items-center justify-center text-4xl font-bold text-white shadow-lg mb-6 md:mb-0">
                                    <%= user.getName().substring(0, 1).toUpperCase() %>
                                </div>
                                <div>
                                    <h1 class="text-3xl font-bold mb-2">
                                        <%= user.getName() %>
                                    </h1>
                                    <p class="text-gray-400 mb-4">
                                        <%= user.getEmail() %>
                                    </p>
                                    <div class="flex flex-wrap justify-center md:justify-start gap-3">
                                        <span
                                            class="px-4 py-1.5 rounded-full bg-indigo-500/10 text-indigo-400 border border-indigo-500/20 text-sm font-semibold">
                                            <%= user.getRole().toUpperCase() %>
                                        </span>
                                        <span
                                            class="px-4 py-1.5 rounded-full bg-yellow-500/10 text-yellow-400 border border-yellow-500/20 text-sm font-semibold flex items-center">
                                            <svg class="w-4 h-4 mr-1" fill="none" viewBox="0 0 24 24"
                                                stroke="currentColor">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                    d="M13 10V3L4 14h7v7l9-11h-7z" />
                                            </svg>
                                            <%= user.getPoints() %> Poin
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <!-- Stats & Recent Activity (Placeholder) -->
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <!-- Stats -->
                                <div class="bg-gray-800/50 backdrop-blur-xl border border-gray-700/50 rounded-3xl p-6">
                                    <h3 class="text-xl font-bold mb-6 flex items-center">
                                        <svg class="w-5 h-5 mr-2 text-indigo-400" fill="none" viewBox="0 0 24 24"
                                            stroke="currentColor">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                                        </svg>
                                        Statistik Belajar
                                    </h3>
                                    <div class="space-y-4">
                                        <div class="flex justify-between items-center p-4 bg-gray-700/30 rounded-xl">
                                            <span class="text-gray-400">Total Kuis</span>
                                            <span class="font-bold">0</span>
                                        </div>
                                        <div class="flex justify-between items-center p-4 bg-gray-700/30 rounded-xl">
                                            <span class="text-gray-400">Rata-rata Nilai</span>
                                            <span class="font-bold text-indigo-400">0%</span>
                                        </div>
                                    </div>
                                </div>

                                <!-- Account Settings -->
                                <div class="bg-gray-800/50 backdrop-blur-xl border border-gray-700/50 rounded-3xl p-6">
                                    <h3 class="text-xl font-bold mb-6 flex items-center">
                                        <svg class="w-5 h-5 mr-2 text-indigo-400" fill="none" viewBox="0 0 24 24"
                                            stroke="currentColor">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                                        </svg>
                                        Pengaturan Akun
                                    </h3>
                                    <div class="space-y-3">
                                        <button
                                            class="w-full text-left px-4 py-3 rounded-xl bg-gray-700/30 hover:bg-gray-700/50 transition flex justify-between items-center group">
                                            <span>Ubah Password</span>
                                            <svg class="w-4 h-4 text-gray-500 group-hover:text-white transition"
                                                fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                    d="M9 5l7 7-7 7" />
                                            </svg>
                                        </button>
                                        <button
                                            class="w-full text-left px-4 py-3 rounded-xl bg-gray-700/30 hover:bg-gray-700/50 transition flex justify-between items-center group">
                                            <span>Edit Profil</span>
                                            <svg class="w-4 h-4 text-gray-500 group-hover:text-white transition"
                                                fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                    d="M9 5l7 7-7 7" />
                                            </svg>
                                        </button>
                                        <form action="<%= request.getContextPath() %>/logout" method="POST">
                                            <button type="submit"
                                                class="w-full text-left px-4 py-3 rounded-xl bg-red-500/10 text-red-400 hover:bg-red-500/20 transition flex justify-between items-center group border border-red-500/20 mt-4">
                                                <span>Logout</span>
                                                <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24"
                                                    stroke="currentColor">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                        stroke-width="2"
                                                        d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                                                </svg>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <script src="<%= request.getContextPath() %>/assets/js/theme.js"></script>
            </body>

            </html>