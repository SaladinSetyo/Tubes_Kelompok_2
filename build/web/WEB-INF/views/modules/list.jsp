<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.paulquiz.model.Module" %>
        <%@ page import="java.util.List" %>
            <% List<Module> modules = (List<Module>) request.getAttribute("modules");
                    boolean isLoggedIn = session != null && session.getAttribute("user") != null;
                    %>
                    <!DOCTYPE html>
                    <html lang="id" class="dark">

                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Modul Pembelajaran - Paul Quiz</title>
                        <script src="https://cdn.tailwindcss.com"></script>
                        <link
                            href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700;800&display=swap"
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
                                        },
                                        animation: {
                                            'float': 'float 6s ease-in-out infinite',
                                            'pulse-slow': 'pulse 4s cubic-bezier(0.4, 0, 0.6, 1) infinite',
                                        },
                                        keyframes: {
                                            float: {
                                                '0%, 100%': { transform: 'translateY(0)' },
                                                '50%': { transform: 'translateY(-20px)' },
                                            }
                                        }
                                    }
                                }
                            }
                        </script>
                        <style>
                            .glass-card {
                                background: rgba(30, 41, 59, 0.7);
                                backdrop-filter: blur(12px);
                                border: 1px solid rgba(255, 255, 255, 0.08);
                                box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
                            }

                            .animated-bg {
                                background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
                                background-size: 400% 400%;
                                animation: gradient 15s ease infinite;
                            }

                            @keyframes gradient {
                                0% {
                                    background-position: 0% 50%;
                                }

                                50% {
                                    background-position: 100% 50%;
                                }

                                100% {
                                    background-position: 0% 50%;
                                }
                            }
                        </style>
                    </head>

                    <body
                        class="bg-[#0B1120] text-gray-100 min-h-screen font-sans selection:bg-indigo-500 selection:text-white">
                        <!-- Navigation -->
                        <%@ include file="../components/navbar.jsp" %>

                            <!-- Hero Section -->
                            <div class="relative overflow-hidden py-24 sm:py-32">
                                <div class="absolute inset-0 z-0">
                                    <div
                                        class="absolute top-0 right-0 -mr-24 -mt-24 w-96 h-96 rounded-full bg-purple-500/20 blur-3xl filter animate-float">
                                    </div>
                                    <div class="absolute bottom-0 left-0 -ml-24 -mb-24 w-96 h-96 rounded-full bg-blue-500/20 blur-3xl filter animate-float"
                                        style="animation-delay: 2s"></div>
                                </div>

                                <div class="relative z-10 container mx-auto px-4 text-center">
                                    <span
                                        class="inline-block py-1 px-3 rounded-full bg-indigo-500/10 border border-indigo-500/20 text-indigo-400 text-sm font-semibold mb-6 backdrop-blur-sm">
                                        📚 Learning Center
                                    </span>
                                    <h1 class="text-5xl md:text-7xl font-extrabold mb-6 tracking-tight">
                                        Modul
                                        <span
                                            class="text-transparent bg-clip-text bg-gradient-to-r from-indigo-400 via-purple-400 to-pink-400">
                                            Pembelajaran
                                        </span>
                                    </h1>
                                    <p class="text-xl text-gray-400 text-center max-w-2xl mx-auto leading-relaxed">
                                        Tingkatkan literasi keuangan Anda dengan modul-modul interaktif yang dirancang
                                        khusus untuk era digital.
                                    </p>
                                </div>
                            </div>

                            <!-- Modules Grid -->
                            <div class="container mx-auto px-4 pb-24 relative z-10">
                                <% if (modules !=null && !modules.isEmpty()) { %>
                                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                                        <% int moduleNumber=1; String[] gradients={ "from-blue-500 to-indigo-600"
                                            , "from-purple-500 to-pink-600" , "from-emerald-500 to-teal-600"
                                            , "from-orange-500 to-red-600" , "from-cyan-500 to-blue-600" }; for (Module
                                            module : modules) { String gradient=gradients[(moduleNumber - 1) %
                                            gradients.length]; %>
                                            <a href="<%= request.getContextPath() %>/modules/<%= module.getId() %>"
                                                class="group relative glass-card rounded-3xl p-1 transition-all duration-300 hover:-translate-y-2 hover:shadow-2xl hover:shadow-indigo-500/20 block h-full">

                                                <div
                                                    class="absolute inset-0 bg-gradient-to-r <%= gradient %> opacity-0 group-hover:opacity-100 transition-opacity duration-300 rounded-3xl blur-xl -z-10">
                                                </div>

                                                <div
                                                    class="bg-dark-800/80 rounded-[1.3rem] p-6 h-full flex flex-col backdrop-blur-xl relative overflow-hidden">
                                                    <!-- Background Pattern -->
                                                    <div
                                                        class="absolute top-0 right-0 w-32 h-32 bg-gradient-to-br <%= gradient %> opacity-5 rounded-bl-full group-hover:scale-150 transition-transform duration-700 ease-out">
                                                    </div>

                                                    <!-- Top Row: Number & Status -->
                                                    <div class="flex items-center justify-between mb-6">
                                                        <div
                                                            class="w-12 h-12 rounded-2xl bg-gradient-to-br <%= gradient %> shadow-lg flex items-center justify-center text-xl font-bold text-white transform group-hover:rotate-12 transition-transform duration-300">
                                                            <%= moduleNumber++ %>
                                                        </div>
                                                        <span
                                                            class="px-3 py-1 rounded-full text-xs font-semibold bg-gray-700/50 text-gray-300 border border-gray-600/30">
                                                            Start Learning
                                                        </span>
                                                    </div>

                                                    <!-- Content -->
                                                    <div class="flex-grow">
                                                        <h3
                                                            class="text-2xl font-bold mb-3 text-white group-hover:text-indigo-300 transition-colors">
                                                            <%= module.getTitle() %>
                                                        </h3>
                                                        <% if (module.getDescription() !=null) { %>
                                                            <p
                                                                class="text-gray-400 text-sm leading-relaxed line-clamp-3 mb-6">
                                                                <%= module.getDescription() %>
                                                            </p>
                                                            <% } %>
                                                    </div>

                                                    <!-- Footer Info -->
                                                    <div
                                                        class="flex items-center justify-between pt-6 border-t border-gray-700/50 mt-auto">
                                                        <div class="flex space-x-4">
                                                            <div
                                                                class="flex items-center space-x-1.5 text-xs font-medium text-gray-400">
                                                                <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24"
                                                                    stroke="currentColor">
                                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                                        stroke-width="2"
                                                                        d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10" />
                                                                </svg>
                                                                <span>Contents</span>
                                                            </div>
                                                            <div
                                                                class="flex items-center space-x-1.5 text-xs font-medium text-gray-400">
                                                                <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24"
                                                                    stroke="currentColor">
                                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                                        stroke-width="2"
                                                                        d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
                                                                </svg>
                                                                <span>Quiz</span>
                                                            </div>
                                                        </div>

                                                        <!-- Arrow Button -->
                                                        <div
                                                            class="w-8 h-8 rounded-full bg-gray-700/50 flex items-center justify-center text-gray-300 group-hover:bg-indigo-500 group-hover:text-white transition-all duration-300">
                                                            <svg class="w-4 h-4 transform group-hover:translate-x-0.5 transition-transform"
                                                                fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                                <path stroke-linecap="round" stroke-linejoin="round"
                                                                    stroke-width="2" d="M9 5l7 7-7 7" />
                                                            </svg>
                                                        </div>
                                                    </div>
                                                </div>
                                            </a>
                                            <% } %>
                                    </div>
                                    <% } else { %>
                                        <div
                                            class="text-center py-20 bg-dark-800/50 rounded-3xl border border-gray-700/50 backdrop-blur-md">
                                            <div
                                                class="inline-flex items-center justify-center w-20 h-20 rounded-full bg-gray-800/50 mb-6 border border-gray-700">
                                                <svg class="w-10 h-10 text-gray-400" fill="none" viewBox="0 0 24 24"
                                                    stroke="currentColor">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                        stroke-width="2"
                                                        d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
                                                </svg>
                                            </div>
                                            <h3 class="text-2xl font-bold mb-2 text-white">Belum Ada Modul</h3>
                                            <p class="text-gray-400 mb-8 max-w-md mx-auto">Modul pembelajaran saat ini
                                                belum tersedia. Silakan cek kembali nanti atau hubungi administrator.
                                            </p>
                                            <% if (isLoggedIn && session.getAttribute("user") !=null &&
                                                ((com.paulquiz.model.User)session.getAttribute("user")).isAdmin()) { %>
                                                <a href="<%= request.getContextPath() %>/admin/modules/create"
                                                    class="inline-flex items-center px-6 py-3 rounded-xl bg-gradient-to-r from-indigo-500 to-purple-600 hover:from-indigo-600 hover:to-purple-700 text-white font-bold transition-all shadow-lg shadow-indigo-500/30 group">
                                                    <svg class="w-5 h-5 mr-2 group-hover:rotate-90 transition-transform"
                                                        fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                        <path stroke-linecap="round" stroke-linejoin="round"
                                                            stroke-width="2" d="M12 4v16m8-8H4" />
                                                    </svg>
                                                    Buat Modul Pertama
                                                </a>
                                                <% } %>
                                        </div>
                                        <% } %>
                            </div>

                            <script src="<%= request.getContextPath() %>/assets/js/theme.js"></script>
                    </body>

                    </html>