package com.empresa.servlet;

import com.empresa.dao.UsuarioDAO;
import com.empresa.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username"); // o "usuario" según tu form
        String password = req.getParameter("password");

        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.login(username, password);

        if (u != null) {
            HttpSession session = req.getSession();
            session.setAttribute("usuarioObj", u); // almacena objeto completo
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            req.setAttribute("error", "Usuario o contraseña incorrectos");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}

