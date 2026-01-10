<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.paulquiz.model.Module" %>
        <%@ page import="com.paulquiz.model.Content" %>
            <%@ page import="java.util.List" %>
                <% Module module=(Module) request.getAttribute("module"); List<Content> contents = (List<Content>)
                        request.getAttribute("contents");
                        boolean isLoggedIn = (Boolean) request.getAttribute("isLoggedIn");
                        %>
                        <!DOCTYPE html>
                        <html lang="id" class="dark">

                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>
                                <%= module.getTitle() %> - Paul Quiz
                            </title>
                            <script src="https://cdn.tailwindcss.com"></script>
                            <link
                                href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap"
                                rel="stylesheet">
                            <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/styles.css">
                            <style>
                                * {
                                    font-family: 'Inter', sans-serif;
                                }

                                .content-card {
                                    background: rgba(255, 255, 255, 0.05);
                                    backdrop-filter: blur(10px);
                                    border: 1px solid rgba(255, 255, 255, 0.1);
                                    transition: all 0.3s ease;
                                }

                                .content-card:hover {
                                    transform: translateX(10px);
                                    border-color: rgba(99, 102, 241, 0.5);
                                }
                            </style>
                        </head>

                        <body class="bg-gray-900 text-gray-100 min-h-screen">
                            <!-- Navigation -->
                            <%@ include file="../components/navbar.jsp" %>

                                <!-- Module Header -->
                                <div class="bg-gradient-to-r from-indigo-900 via-purple-900 to-pink-900 py-16">
                                    <div class="container mx-auto px-4">
                                        <a href="<%= request.getContextPath() %>/modules"
                                            class="text-gray-300 hover:text-white mb-4 inline-flex items-center">
                                            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor"
                                                viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                    d="M15 19l-7-7 7-7" />
                                            </svg>
                                            Kembali ke Modul
                                        </a>
                                        <h1 class="text-5xl font-bold mb-4">
                                            <%= module.getTitle() %>
                                        </h1>
                                        <% if (module.getDescription() !=null) { %>
                                            <p class="text-xl text-gray-300 max-w-3xl">
                                                <%= module.getDescription() %>
                                            </p>
                                            <% } %>
                                    </div>
                                </div>

                                <!-- Contents List -->
                                <div class="container mx-auto px-4 py-12">
                                    <div class="max-w-4xl mx-auto">
                                        <h2 class="text-3xl font-bold mb-8">📚 Materi Pembelajaran</h2>

                                        <% if (contents !=null && !contents.isEmpty()) { %>
                                            <div class="space-y-4">
                                                <% int contentNumber=1; for (Content content : contents) { String
                                                    icon="📄" ; String bgColor="bg-blue-500" ; if
                                                    ("video".equals(content.getType())) { icon="🎥" ;
                                                    bgColor="bg-red-500" ; } else if
                                                    ("infographic".equals(content.getType())) { icon="📊" ;
                                                    bgColor="bg-green-500" ; } else if
                                                    ("quiz".equals(content.getType())) { icon="❓" ;
                                                    bgColor="bg-purple-500" ; } %>
                                                    <div class="content-card rounded-xl p-6 flex items-center">
                                                        <div
                                                            class="w-12 h-12 rounded-lg <%= bgColor %> flex items-center justify-center text-2xl mr-4 flex-shrink-0">
                                                            <%= icon %>
                                                        </div>
                                                        <div class="flex-grow">
                                                            <h3 class="text-xl font-semibold mb-1">
                                                                <%= content.getTitle() %>
                                                            </h3>
                                                            <p class="text-sm text-gray-400">
                                                                <%= content.getType().substring(0, 1).toUpperCase() +
                                                                    content.getType().substring(1) %>
                                                            </p>
                                                        </div>
                                                        <% if ("quiz".equals(content.getType()) && content.getQuizId()
                                                            !=null) { %>
                                                            <a href="<%= request.getContextPath() %>/quizzes/<%= content.getQuizId() %>"
                                                                class="px-6 py-3 rounded-lg bg-purple-600 hover:bg-purple-700 transition font-semibold">
                                                                Mulai Quiz
                                                            </a>
                                                            <% } else { %>
                                                                <button
                                                                    class="px-6 py-3 rounded-lg bg-indigo-600 hover:bg-indigo-700 transition font-semibold"
                                                                    onclick="viewContent(<%= content.getId() %>)">
                                                                    Lihat Materi
                                                                </button>
                                                                <% } %>
                                                    </div>
                                                    <% contentNumber++; } %>
                                            </div>
                                            <% } else { %>
                                                <div class="text-center py-16 content-card rounded-xl">
                                                    <div class="text-6xl mb-4">📝</div>
                                                    <h3 class="text-2xl font-bold mb-2">Belum Ada Konten</h3>
                                                    <p class="text-gray-400">Konten untuk modul ini akan segera
                                                        ditambahkan</p>
                                                </div>
                                                <% } %>
                                    </div>
                                </div>

                                <script src="<%= request.getContextPath() %>/assets/js/theme.js"></script>
                                <script>
                                    function viewContent(contentId) {
                                        // TODO: Implement content viewer modal or navigate to content page
                                        alert('Content viewer will be implemented. Content ID: ' + contentId);
                                    }
                                </script>
                        </body>

                        </html>