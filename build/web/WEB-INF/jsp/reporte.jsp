<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><meta name="viewport" content="width=device-width, initial-scale=1"><title>Reportes</title></head>
<body>
<h2>Reportes</h2>
<p>package com.empresa.servlet;

import com.empresa.dao.ReporteDAO;
import com.empresa.model.Movimiento;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ReporteServlet")
public class ReporteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("exportar".equals(accion)) {
            exportarCSV(response);
        } else {
            mostrarReporte(request, response);
        }
    }

    private void mostrarReporte(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ReporteDAO dao = new ReporteDAO();
        List<Movimiento> lista = dao.obtenerBitacora();

        request.setAttribute("lista", lista);
        request.getRequestDispatcher("reporte.jsp").forward(request, response);
    }

    private void exportarCSV(HttpServletResponse response) throws IOException {
        ReporteDAO dao = new ReporteDAO();
        List<Movimiento> lista = dao.obtenerBitacora();

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_inventario.csv");

        PrintWriter out = response.getWriter();

        // Encabezados
        out.println("ID,Material,Tipo,Cantidad,Fecha,Usuario");

        // Filas
        for (Movimiento m : lista) {
            out.println(
                    m.getId() + "," +
                    m.getNombreMaterial() + "," +
                    m.getTipo() + "," +
                    m.getCantidad() + "," +
                    m.getFecha() + "," +
                    m.getUsuario()
            );
        }

        out.flush();
        out.close();
    }
}