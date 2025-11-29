package com.empresa.servlet;

import com.empresa.dao.ReporteDAO;
import com.empresa.model.Movimiento;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/reporte")
public class ReporteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si llamas /reporte?accion=exportar -> descarga CSV
        String accion = request.getParameter("accion");
        if ("exportar".equalsIgnoreCase(accion)) {
            exportarCSV(response);
            return;
        }

        // Si no, muestra la página de reportes
        ReporteDAO dao = new ReporteDAO();
        List<Movimiento> lista = dao.obtenerBitacora();
        request.setAttribute("lista", lista);
        request.getRequestDispatcher("/reporte.jsp").forward(request, response);
    }

    private void exportarCSV(HttpServletResponse resp) throws IOException {
        ReporteDAO dao = new ReporteDAO();
        List<Movimiento> lista = dao.obtenerBitacora();

        resp.setContentType("text/csv; charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=reporte_inventario.csv");

        PrintWriter out = resp.getWriter();

        // Encabezado
        out.println("ID,Material,Tipo,Cantidad,Fecha,Usuario");

        for (Movimiento m : lista) {
            String material = safe(m.getReferencia());   // en ReporteDAO guardamos nombre_material en referencia
            String tipo = safe(m.getTipo());
            String cantidad = String.format("%.2f", m.getCantidad());
            String fecha = (m.getFecha() != null) ? m.getFecha().toString() : "";
            String usuario = safe(m.getProveedor());    // en ReporteDAO guardamos usuario en proveedor

            out.println(
                csvEscape(String.valueOf(m.getId())) + "," +
                csvEscape(material) + "," +
                csvEscape(tipo) + "," +
                csvEscape(cantidad) + "," +
                csvEscape(fecha) + "," +
                csvEscape(usuario)
            );
        }

        out.flush();
        out.close();
    }

    // protege contra null
    private String safe(String s) {
        return s == null ? "" : s;
    }

    // Escapa comas y comillas para CSV: encierra en comillas si contiene comas/quotes/newlines,
    // y duplica comillas internas.
    private String csvEscape(String field) {
        if (field == null) return "";
        boolean needQuotes = field.contains(",") || field.contains("\"") || field.contains("\n") || field.contains("\r");
        String escaped = field.replace("\"", "\"\"");
        return needQuotes ? "\"" + escaped + "\"" : escaped;
    }
}
