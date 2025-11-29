package com.empresa.servlet;

import com.empresa.dao.OrdenDAO;
import com.empresa.model.OrdenProduccion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ordenes")
public class OrdenServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            OrdenDAO dao = new OrdenDAO();
            req.setAttribute("ordenes", dao.listarTodas());
            req.getRequestDispatcher("/ordenes.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String codigo = req.getParameter("codigo");
            String descripcion = req.getParameter("descripcion");
            String fechaInicio = req.getParameter("fechaInicio");
            String fechaEntrega = req.getParameter("fechaEntrega");

            OrdenProduccion o = new OrdenProduccion();
            o.setCodigo(codigo);
            o.setDescripcion(descripcion);
            o.setFechaInicio(fechaInicio);
            o.setFechaEntrega(fechaEntrega);
            o.setEstado("PENDIENTE");

            OrdenDAO dao = new OrdenDAO();
            dao.crearOrden(o);
            resp.sendRedirect(req.getContextPath() + "/ordenes");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
