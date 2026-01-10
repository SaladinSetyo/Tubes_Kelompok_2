<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
    <!DOCTYPE html>
    <html lang="id">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>500 - Server Error</title>
        <script src="https://cdn.tailwindcss.com"></script>
    </head>

    <body class="bg-gray-100 min-h-screen flex items-center justify-center">
        <div class="text-center max-w-2xl mx-auto p-8">
            <h1 class="text-6xl font-bold text-red-600 mb-4">500</h1>
            <p class="text-xl text-gray-800 mb-4">Terjadi kesalahan pada server</p>
            <p class="text-gray-600 mb-8">Mohon maaf atas ketidaknyamanannya. Tim kami sedang memperbaikinya.</p>

            <% if (exception !=null) { %>
                <div class="bg-red-50 border border-red-200 rounded-lg p-4 mb-8 text-left">
                    <p class="text-sm text-red-800 font-mono">
                        <%= exception.getMessage() %>
                    </p>
                </div>
                <% } %>

                    <a href="${pageContext.request.contextPath}/index"
                        class="px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition inline-block">
                        Kembali ke Beranda
                    </a>
        </div>
    </body>

    </html>