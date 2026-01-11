<%@ page import="com.paulquiz.model.User" %>
    <% User navUser=(User) session.getAttribute("user"); boolean navIsLoggedIn=navUser !=null; boolean
        navIsAdmin=navIsLoggedIn && navUser.isAdmin(); String currentPath=request.getRequestURI(); String
        contextPath=request.getContextPath(); boolean isHome=currentPath.equals(contextPath + "/" ) ||
        currentPath.equals(contextPath + "/index" ); boolean isModules=currentPath.contains("/modules"); boolean
        isLeaderboard=currentPath.contains("/leaderboard"); boolean isProfile=currentPath.contains("/profile"); boolean
        isAdmin=currentPath.contains("/admin"); String activeClass="text-blue-400 bg-blue-500/10" ; String
        inactiveClass="text-gray-400 hover:text-white hover:bg-gray-800" ; String
        adminActiveClass="text-red-400 bg-red-500/20 border border-red-500/30" ; String
        adminInactiveClass="text-red-400 bg-red-500/10 border border-red-500/20 hover:bg-red-500/20" ; String
        homeClass=isHome ? activeClass : inactiveClass; String modulesClass=isModules ? activeClass : inactiveClass;
        String leaderboardClass=isLeaderboard ? activeClass : inactiveClass; String profileClass=isProfile ? activeClass
        : inactiveClass; String adminClass=isAdmin ? adminActiveClass : adminInactiveClass; %>

        <!-- Unified Premium Navbar -->
        <nav class="bg-gray-900 border-b border-gray-800 sticky top-0 z-50 backdrop-blur-xl">
            <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div class="flex items-center justify-between h-16">
                    <!-- Logo -->
                    <a href="<%= contextPath %>/" class="flex items-center gap-2 group">
                        <div
                            class="w-10 h-10 rounded-lg bg-gradient-to-br from-indigo-500 to-purple-600 flex items-center justify-center shadow-lg group-hover:shadow-indigo-500/50 transition-all">
                            <svg class="w-6 h-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
                            </svg>
                        </div>
                        <span class="font-bold text-xl text-white">Paul Quiz</span>
                    </a>

                    <!-- Navigation Links -->
                    <div class="hidden md:flex items-center gap-2">
                        <a href="<%= contextPath %>/"
                            class="px-4 py-2 rounded-lg text-sm font-medium transition <%= homeClass %>">
                            Home
                        </a>
                        <a href="<%= contextPath %>/modules"
                            class="px-4 py-2 rounded-lg text-sm font-medium transition <%= modulesClass %>">
                            Modul
                        </a>
                        <% if (navIsLoggedIn) { %>
                            <a href="<%= contextPath %>/leaderboard"
                                class="px-4 py-2 rounded-lg text-sm font-medium transition <%= leaderboardClass %>">
                                Leaderboard
                            </a>
                            <a href="<%= contextPath %>/profile"
                                class="px-4 py-2 rounded-lg text-sm font-medium transition <%= profileClass %>">
                                Profile
                            </a>
                            <% } %>
                                <% if (navIsAdmin) { %>
                                    <a href="<%= contextPath %>/admin"
                                        class="px-4 py-2 rounded-lg text-sm font-semibold transition <%= adminClass %>">
                                        Admin Panel
                                    </a>
                                    <% } %>
                    </div>

                    <!-- Right Section -->
                    <div class="flex items-center gap-4">
                        <% if (navIsLoggedIn) { %>
                            <!-- Notifications -->
                            <div class="relative" id="notif-dropdown-container">
                                <% com.paulquiz.dao.NotificationDAO navNotifyDAO=new com.paulquiz.dao.NotificationDAO();
                                    int unreadCount=0; java.util.List<com.paulquiz.model.Notification> navNotifications
                                    = new java.util.ArrayList<>();
                                        try {
                                        unreadCount = navNotifyDAO.countUnread(navUser.getId());
                                        navNotifications = navNotifyDAO.findByUserId(navUser.getId(), 5); // Get last 5
                                        } catch (Exception e) {
                                        e.printStackTrace();
                                        }
                                        %>
                                        <button id="notif-btn" onclick="toggleNotifDropdown(event)"
                                            class="relative p-2 text-gray-400 hover:text-white transition rounded-full hover:bg-gray-800 focus:outline-none">
                                            <!-- Bell Icon -->
                                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                    d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9">
                                                </path>
                                            </svg>

                                            <!-- Red Dot Badge -->
                                            <% if (unreadCount> 0) { %>
                                                <span id="notif-badge"
                                                    class="absolute top-1 right-1 block h-2.5 w-2.5 rounded-full bg-red-500 ring-2 ring-gray-900"></span>
                                                <% } else { %>
                                                    <span id="notif-badge"
                                                        class="absolute top-1 right-1 h-2.5 w-2.5 rounded-full bg-red-500 ring-2 ring-gray-900 hidden"></span>
                                                    <% } %>
                                        </button>

                                        <!-- Dropdown Panel (Vanilla JS Toggle) -->
                                        <div id="notif-dropdown"
                                            class="absolute right-0 mt-2 w-80 bg-gray-800 rounded-xl shadow-xl border border-gray-700 overflow-hidden hidden z-50">
                                            <div class="p-4 border-b border-gray-700 flex justify-between items-center">
                                                <h3 class="text-sm font-semibold text-white">Notifikasi</h3>
                                                <div class="flex items-center gap-3">
                                                    <span class="text-xs text-red-400 font-medium"
                                                        id="notif-count-text">
                                                        <% if (unreadCount> 0) { %>
                                                            <%= unreadCount %> Baru
                                                                <% } %>
                                                    </span>

                                                    <form action="<%= contextPath %>/notifications/clear-read"
                                                        method="POST">
                                                        <button type="submit"
                                                            class="text-[10px] text-gray-400 hover:text-white transition underline decoration-gray-600 underline-offset-2">
                                                            Bersihkan
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                            <div class="max-h-96 overflow-y-auto">
                                                <% if (navNotifications.isEmpty()) { %>
                                                    <div class="p-4 text-center text-gray-500 text-sm">
                                                        Tidak ada notifikasi.
                                                    </div>
                                                    <% } else { %>
                                                        <% for (com.paulquiz.model.Notification n : navNotifications) {
                                                            %>
                                                            <div
                                                                class='p-4 border-b border-gray-700/50 hover:bg-gray-700/50 transition <%= n.isUnread() ? "bg-gray-700/20" : "" %>'>
                                                                <div class="flex gap-3">
                                                                    <div class="flex-shrink-0 mt-1">
                                                                        <% if ("success".equals(n.getType())) { %>
                                                                            <div
                                                                                class="w-2 h-2 rounded-full bg-green-500">
                                                                            </div>
                                                                            <% } else if ("warning".equals(n.getType()))
                                                                                { %>
                                                                                <div
                                                                                    class="w-2 h-2 rounded-full bg-yellow-500">
                                                                                </div>
                                                                                <% } else { %>
                                                                                    <div
                                                                                        class="w-2 h-2 rounded-full bg-blue-500">
                                                                                    </div>
                                                                                    <% } %>
                                                                    </div>
                                                                    <div class="flex-1">
                                                                        <h4
                                                                            class='text-sm font-semibold text-gray-200 <%= n.isUnread() ? "text-white" : "" %>'>
                                                                            <%= n.getTitle() %>
                                                                        </h4>
                                                                        <p
                                                                            class="text-xs text-gray-400 mt-1 leading-relaxed">
                                                                            <%= n.getMessage() %>
                                                                        </p>
                                                                        <div
                                                                            class="flex justify-between items-center mt-2">
                                                                            <span class="text-[10px] text-gray-500">
                                                                                <%= n.getTimeAgo() %>
                                                                            </span>
                                                                            <% if (n.isUnread()) { %>
                                                                                <form
                                                                                    action="<%= contextPath %>/notifications/mark-read"
                                                                                    method="POST">
                                                                                    <input type="hidden" name="id"
                                                                                        value="<%= n.getId() %>">
                                                                                    <button type="submit"
                                                                                        class="text-[10px] text-blue-400 hover:text-blue-300 font-medium">
                                                                                        Tandai dibaca
                                                                                    </button>
                                                                                </form>
                                                                                <% } %>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <% } %>
                                                                <% } %>
                                            </div>
                                        </div>
                            </div>

                            <!-- User Info -->
                            <div class="flex items-center gap-3">
                                <div class="text-right hidden md:block">
                                    <div class="text-sm font-semibold text-white">
                                        <%= navUser.getName() %>
                                    </div>
                                    <div class="text-xs text-gray-400">
                                        <% if (navIsAdmin) { %>
                                            Administrator
                                            <% } else { %>
                                                <%= navUser.getPoints() %> XP
                                                    <% } %>
                                    </div>
                                </div>
                                <div
                                    class="w-10 h-10 rounded-full bg-gradient-to-br from-blue-500 to-purple-600 flex items-center justify-center text-white font-bold shadow-lg">
                                    <%= navUser.getName().substring(0, 1).toUpperCase() %>
                                </div>
                            </div>
                            <!-- Logout Form -->
                            <form action="<%= contextPath %>/logout" method="POST" class="inline">
                                <button type="submit"
                                    class="px-4 py-2 text-sm font-medium text-gray-400 hover:text-white transition">
                                    Logout
                                </button>
                            </form>
                            <script>
                                // GLOBAL function - dapat dipanggil dari onclick
                                window.toggleNotifDropdown = function (event) {
                                    event.stopPropagation();
                                    const dropdown = document.getElementById('notif-dropdown');
                                    if (dropdown) {
                                        dropdown.classList.toggle('hidden');
                                    }
                                };

                                document.addEventListener('DOMContentLoaded', function () {
                                    const notifBadge = document.getElementById('notif-badge');
                                    const notifList = document.getElementById('notif-list');
                                    const notifCountText = document.getElementById('notif-count-text');
                                    let lastUnreadCount = 0;
                                    let isFirstLoad = true;
                                    let audioCtx = null;

                                    // Audio functions
                                    window.initAudio = function () {
                                        if (!audioCtx) {
                                            audioCtx = new (window.AudioContext || window.webkitAudioContext)();
                                        }
                                        if (audioCtx.state === 'suspended') {
                                            audioCtx.resume();
                                        }
                                        return audioCtx;
                                    };

                                    function playNotificationSound() {
                                        try {
                                            const ctx = window.initAudio();
                                            const osc = ctx.createOscillator();
                                            const gain = ctx.createGain();
                                            osc.type = 'sine';
                                            osc.frequency.setValueAtTime(880, ctx.currentTime);
                                            osc.frequency.exponentialRampToValueAtTime(440, ctx.currentTime + 0.2);
                                            gain.gain.setValueAtTime(0.1, ctx.currentTime);
                                            gain.gain.exponentialRampToValueAtTime(0.01, ctx.currentTime + 0.5);
                                            osc.connect(gain);
                                            gain.connect(ctx.destination);
                                            osc.start();
                                            osc.stop(ctx.currentTime + 0.5);
                                        } catch (e) { }
                                    }

                                    // Close dropdown when clicking outside
                                    document.addEventListener('click', function (e) {
                                        const dropdown = document.getElementById('notif-dropdown');
                                        const btn = document.getElementById('notif-btn');
                                        if (dropdown && btn) {
                                            if (!dropdown.classList.contains('hidden')) {
                                                if (!btn.contains(e.target) && !dropdown.contains(e.target)) {
                                                    dropdown.classList.add('hidden');
                                                }
                                            }
                                        }
                                        window.initAudio();
                                    });

                                    // Toast Container
                                    const toast = document.createElement('div');
                                    toast.className = "fixed top-24 right-5 z-[60] transform transition-all duration-500 translate-x-[150%] opacity-0";
                                    toast.innerHTML = `
                                        <div class="bg-gray-800/90 backdrop-blur-xl border border-gray-700/50 text-white p-4 rounded-xl shadow-2xl flex items-start gap-4 max-w-sm ring-1 ring-white/10">
                                            <div class="flex-shrink-0 w-10 h-10 rounded-full bg-gradient-to-br from-blue-500/20 to-purple-500/20 flex items-center justify-center border border-white/5">
                                                <svg class="w-5 h-5 text-blue-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
                                                </svg>
                                            </div>
                                            <div class="flex-1 min-w-0">
                                                <h4 class="font-bold text-sm text-transparent bg-clip-text bg-gradient-to-r from-blue-200 to-purple-200" id="toast-title">Notifikasi Baru</h4>
                                                <p class="text-xs text-gray-400 mt-1 leading-relaxed line-clamp-2" id="toast-message">Anda memiliki pesan baru.</p>
                                            </div>
                                            <button onclick="this.parentElement.parentElement.classList.add('translate-x-[150%]')" class="text-gray-500 hover:text-white transition">
                                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/></svg>
                                            </button>
                                        </div>
                                    `;
                                    document.body.appendChild(toast);

                                    function showToast(title, message) {
                                        const toastTitle = document.getElementById('toast-title');
                                        const toastMessage = document.getElementById('toast-message');
                                        if (title) toastTitle.textContent = title;
                                        if (message) toastMessage.textContent = message;
                                        toast.classList.remove('translate-x-[150%]', 'opacity-0');
                                        playNotificationSound();
                                        setTimeout(() => {
                                            toast.classList.add('translate-x-[150%]', 'opacity-0');
                                        }, 10000);
                                    }

                                    function checkNotifications() {
                                        fetch('<%= contextPath %>/api/notifications/check')
                                            .then(response => response.json())
                                            .then(data => {
                                                if (isFirstLoad) {
                                                    lastUnreadCount = data.unreadCount;
                                                    isFirstLoad = false;
                                                }
                                                if (data.unreadCount > 0) {
                                                    if (notifBadge) notifBadge.classList.remove('hidden');
                                                } else {
                                                    if (notifBadge) notifBadge.classList.add('hidden');
                                                }
                                                if (data.unreadCount > lastUnreadCount) {
                                                    const newest = data.notifications[0];
                                                    if (newest) {
                                                        showToast(newest.title, newest.message);
                                                    }
                                                }
                                                lastUnreadCount = data.unreadCount;
                                                if (notifCountText) {
                                                    if (data.unreadCount > 0) {
                                                        notifCountText.innerHTML = '<span class="text-xs text-red-400 font-medium">' + data.unreadCount + ' Baru</span>';
                                                    } else {
                                                        notifCountText.innerHTML = '';
                                                    }
                                                }
                                                if (notifList && data.notifications) {
                                                    let html = '';
                                                    if (data.notifications.length === 0) {
                                                        html = '<div class="p-4 text-center text-gray-500 text-sm">Tidak ada notifikasi.</div>';
                                                    } else {
                                                        data.notifications.forEach(n => {
                                                            let colorClass = 'bg-blue-500';
                                                            if (n.type === 'success') colorClass = 'bg-green-500';
                                                            if (n.type === 'warning') colorClass = 'bg-yellow-500';
                                                            let unreadBg = n.isUnread ? 'bg-gray-700/20' : '';
                                                            let titleColor = n.isUnread ? 'text-white' : 'text-gray-200';
                                                            html += '<div class="p-4 border-b border-gray-700/50 hover:bg-gray-700/50 transition ' + unreadBg + '">';
                                                            html += '<div class="flex gap-3">';
                                                            html += '<div class="flex-shrink-0 mt-1"><div class="w-2 h-2 rounded-full ' + colorClass + '"></div></div>';
                                                            html += '<div class="flex-1">';
                                                            html += '<h4 class="text-sm font-semibold ' + titleColor + '">' + n.title + '</h4>';
                                                            html += '<p class="text-xs text-gray-400 mt-1 leading-relaxed">' + n.message + '</p>';
                                                            html += '<div class="flex justify-between items-center mt-2">';
                                                            html += '<span class="text-[10px] text-gray-500">' + n.timeAgo + '</span>';
                                                            if (n.isUnread) {
                                                                html += '<form action="<%= contextPath %>/notifications/mark-read" method="POST">';
                                                                html += '<input type="hidden" name="id" value="' + n.id + '">';
                                                                html += '<button type="submit" class="text-[10px] text-blue-400 hover:text-blue-300 font-medium">Tandai dibaca</button>';
                                                                html += '</form>';
                                                            }
                                                            html += '</div></div></div></div>';
                                                        });
                                                    }
                                                    notifList.innerHTML = html;
                                                }
                                            })
                                            .catch(err => console.error('Polling error:', err));
                                    }
                                    setInterval(checkNotifications, 5000);
                                });
                            </script>
                            <% } else { %>
                                <!-- Login/Register -->
                                <a href="<%= contextPath %>/login"
                                    class="px-4 py-2 text-sm font-medium text-white rounded-lg border border-gray-700 hover:border-gray-600 transition">
                                    Login
                                </a>
                                <a href="<%= contextPath %>/register"
                                    class="px-4 py-2 text-sm font-medium text-white rounded-lg bg-gradient-to-r from-indigo-600 to-purple-600 hover:from-indigo-700 hover:to-purple-700 transition shadow-lg">
                                    Register
                                </a>
                                <% } %>
                    </div>
                </div>
            </div>
        </nav>