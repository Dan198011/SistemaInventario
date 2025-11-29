package com.empresa.servlet;

import com.empresa.dao.MovimientoDAO;
import com.empresa.model.Movimiento;
import com.empresa.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/movimiento")
public class MovimientoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/movimiento_form.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int materialId = Integer.parseInt(req.getParameter("materialId"));
            String tipo = req.getParameter("tipo");
            double cantidad = Double.parseDouble(req.getParameter("cantidad"));
            String referencia = req.getParameter("referencia");
            String proveedor = req.getParameter("proveedor");
            String lote = req.getParameter("lote");
            String ordenIdStr = req.getParameter("ordenId");
            Integer ordenId = (ordenIdStr == null || ordenIdStr.isEmpty()) ? null : Integer.parseInt(ordenIdStr);

            HttpSession session = req.getSession(false);
            Usuario u = (Usuario) session.getAttribute("usuario");
            if (u == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            Movimiento mov = new Movimiento();
            mov.setMaterialId(materialId);
            mov.setTipo(tipo);
            mov.setCantidad(cantidad);
            mov.setReferencia(referencia);
            mov.setProveedor(proveedor);
            mov.setLote(lote);
            mov.setOrdenId(ordenId);
            mov.setUsuarioId(u.getId());

            MovimientoDAO dao = new MovimientoDAO();
            boolean ok = dao.registrarMovimiento(mov);
            if (ok) resp.sendRedirect(req.getContextPath() + "/materiales");
            else {
                req.setAttribute("error", "No se pudo registrar el movimiento");
                req.getRequestDispatcher("/movimiento_form.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
