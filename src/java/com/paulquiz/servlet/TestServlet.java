package com.paulquiz.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Test servlet to verify deployment
 */
@WebServlet(name = "TestServlet", urlPatterns = { "/test" })
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Test Servlet</title>");
        out.println("<script src='https://cdn.tailwindcss.com'></script>");
        out.println("</head>");
        out.println("<body class='bg-gray-100 min-h-screen flex items-center justify-center'>");
        out.println("<div class='bg-white p-8 rounded-lg shadow-lg text-center'>");
        out.println("<h1 class='text-3xl font-bold text-green-600 mb-4'>✅ Servlet Works!</h1>");
        out.println("<p class='text-gray-700 mb-4'>Servlet berhasil ter-deploy dan berfungsi!</p>");
        out.println("<p class='text-sm text-gray-500'>Context Path: " + request.getContextPath() + "</p>");
        out.println("<p class='text-sm text-gray-500'>Servlet Path: " + request.getServletPath() + "</p>");
        out.println("<div class='mt-6'>");
        out.println("<a href='" + request.getContextPath()
                + "/index' class='px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition mr-2'>Try Homepage</a>");
        out.println("<a href='" + request.getContextPath()
                + "/login' class='px-6 py-3 bg-green-600 text-white rounded-lg hover:bg-green-700 transition'>Try Login</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}

