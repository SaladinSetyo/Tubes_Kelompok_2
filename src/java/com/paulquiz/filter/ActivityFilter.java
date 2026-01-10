package com.paulquiz.filter;

import com.paulquiz.dao.UserDAO;
import com.paulquiz.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter(filterName = "ActivityFilter", urlPatterns = { "/*" })
public class ActivityFilter implements Filter {

    private UserDAO userDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session = httpRequest.getSession(false);

            if (session != null) {
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    // Update activity only if it hasn't been updated in this session specifically
                    // Or we can just update it in DB directly. To avoid spamming DB on every static
                    // file request,
                    // we could check the URL or use a session attribute to throttle.
                    // For simplicity and "real-time" feel, we'll update every minute max.

                    Long lastUpdateParam = (Long) session.getAttribute("lastActivityUpdate");
                    long currentTime = System.currentTimeMillis();

                    if (lastUpdateParam == null || (currentTime - lastUpdateParam > 60000)) { // 1 minute throttle
                        try {
                            userDAO.updateLastSeen(user.getId());
                            session.setAttribute("lastActivityUpdate", currentTime);
                        } catch (SQLException e) {
                            // Log error silently, don't block user
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
