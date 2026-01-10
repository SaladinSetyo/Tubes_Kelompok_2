<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="id" class="dark">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Kelola Konten - Admin Paul Quiz</title>
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

                <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                    <div class="flex justify-between items-center mb-8">
                        <div>
                            <h1 class="text-2xl font-bold">Manajemen Konten</h1>
                            <p class="text-gray-400">Atur konten materi (artikel, video, infografis) pada modul.</p>
                        </div>
                        <div class="flex gap-4">
                            <a href="${pageContext.request.contextPath}/admin/dashboard"
                                class="px-4 py-2 bg-gray-700 hover:bg-gray-600 rounded-lg text-white font-medium transition">
                                Kembali
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/contents/create"
                                class="px-4 py-2 bg-purple-600 hover:bg-purple-700 rounded-lg text-white font-medium transition flex items-center gap-2">
                                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M12 4v16m8-8H4" />
                                </svg>
                                Tambah Konten
                            </a>
                        </div>
                    </div>

                    <div class="bg-gray-800 rounded-2xl border border-gray-700 overflow-hidden">
                        <div class="overflow-x-auto">
                            <table class="w-full text-left text-sm text-gray-400">
                                <thead class="bg-gray-900/50 text-gray-100 uppercase font-bold text-xs">
                                    <tr>
                                        <th scope="col" class="px-6 py-4">ID</th>
                                        <th scope="col" class="px-6 py-4">Modul / Tipe</th>
                                        <th scope="col" class="px-6 py-4">Judul Konten</th>
                                        <th scope="col" class="px-6 py-4">Urutan</th>
                                        <th scope="col" class="px-6 py-4 text-center">Status</th>
                                        <th scope="col" class="px-6 py-4 text-center">Aksi</th>
                                    </tr>
                                </thead>
                                <tbody class="divide-y divide-gray-700">
                                    <c:forEach var="content" items="${listContents}">
                                        <tr class="hover:bg-gray-700/50 transition">
                                            <td class="px-6 py-4 font-medium text-gray-300">#${content.id}</td>
                                            <td class="px-6 py-4">
                                                <div class="flex flex-col">
                                                    <span
                                                        class="text-blue-400 font-medium text-xs mb-1 uppercase tracking-wide">${content.moduleName
                                                        != null ? content.moduleName : '-'}</span>
                                                    <span
                                                        class="inline-flex items-center px-2 py-0.5 rounded text-xs font-medium bg-gray-700 text-gray-300 w-fit">
                                                        ${content.type}
                                                    </span>
                                                </div>
                                            </td>
                                            <td class="px-6 py-4 font-semibold text-white">
                                                ${content.title}
                                                <div class="text-xs text-gray-500 font-normal truncate max-w-xs mt-1">
                                                    ${content.description}</div>
                                            </td>
                                            <td class="px-6 py-4 text-center">${content.order}</td>
                                            <td class="px-6 py-4 text-center">
                                                <c:if test="${content.isFeatured}">
                                                    <span
                                                        class="px-2 py-1 bg-yellow-500/10 text-yellow-400 border border-yellow-500/20 rounded-full text-xs">Featured</span>
                                                </c:if>
                                            </td>
                                            <td class="px-6 py-4 text-center">
                                                <div class="inline-flex rounded-md shadow-sm" role="group">
                                                    <a href="${pageContext.request.contextPath}/admin/contents/edit?id=${content.id}"
                                                        class="px-3 py-2 text-sm font-medium text-white bg-blue-600 rounded-l-lg hover:bg-blue-700 border-r border-blue-500 transition">
                                                        Edit
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/contents/delete?id=${content.id}"
                                                        onclick="return confirm('Apakah Anda yakin ingin menghapus konten ini?');"
                                                        class="px-3 py-2 text-sm font-medium text-white bg-red-600 rounded-r-lg hover:bg-red-700 transition">
                                                        Hapus
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty listContents}">
                                        <tr>
                                            <td colspan="6" class="px-6 py-12 text-center text-gray-500 italic">
                                                Belum ada konten materi yang tersedia.
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
        </body>

        </html>