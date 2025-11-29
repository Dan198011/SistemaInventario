package com.empresa.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;

@WebServlet("/export")
public class ExportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Placeholder: dirigir a ReporteServlet u otros exports
        resp.sendRedirect(req.getContextPath() + "/reporte/inventario.xlsx");
    }
}
