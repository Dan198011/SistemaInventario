package com.empresa.servlet;

import com.empresa.dao.MaterialDAO;
import com.empresa.model.Material;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/materiales")
public class MaterialServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            MaterialDAO dao = new MaterialDAO();
            req.setAttribute("materiales", dao.listarTodos());
            req.getRequestDispatcher("/materiales.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String tipo = req.getParameter("tipo");
            String descripcion = req.getParameter("descripcion");
            double existencia = Double.parseDouble(req.getParameter("existencia"));
            double minimo = Double.parseDouble(req.getParameter("minimo"));
            String unidad = req.getParameter("unidad");
            String lote = req.getParameter("lote");

            Material m = new Material();
            m.setTipo(tipo);
            m.setDescripcion(descripcion);
            m.setExistencia(existencia);
            m.setMinimo(minimo);
            m.setUnidad(unidad);
            m.setLote(lote);

            MaterialDAO dao = new MaterialDAO();
            dao.insertar(m);
            resp.sendRedirect(req.getContextPath() + "/materiales");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
