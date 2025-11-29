package com.empresa.servlet;

import com.empresa.dao.MaterialDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            MaterialDAO mdao = new MaterialDAO();
            req.setAttribute("bajoMinimo", mdao.listarBajoMinimo());
            req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
