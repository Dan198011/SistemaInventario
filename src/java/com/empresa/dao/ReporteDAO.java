package com.empresa.dao;

import com.empresa.model.Movimiento;
import com.empresa.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    public List<Movimiento> obtenerBitacora() {
        List<Movimiento> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            String sql = "SELECT m.id, m.material_id, mat.tipo AS nombre_material, m.tipo, m.cantidad, m.fecha, u.usuario AS usuario " +
                         "FROM movimiento m " +
                         "LEFT JOIN material mat ON m.material_id = mat.id " +
                         "LEFT JOIN usuario u ON m.usuario_id = u.id " +
                         "ORDER BY m.fecha DESC";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Movimiento m = new Movimiento();
                m.setId(rs.getInt("id"));
                m.setMaterialId(rs.getInt("material_id"));
                // guardamos nombreMaterial en referencia para compatibilidad si es necesario
                m.setReferencia(rs.getString("nombre_material"));
                m.setTipo(rs.getString("tipo"));
                m.setCantidad(rs.getDouble("cantidad"));
                m.setFecha(rs.getTimestamp("fecha"));
                // reutiliza usuarioId o guarda el usuario en proveedor (según diseño)
                // Para reporte CSV usaremos m.getReferencia() para nombreMaterial y usuario en proveedor
                // guardamos usuario en proveedor para que ReporteServlet lo muestre vía getProveedor()
                m.setProveedor(rs.getString("usuario"));
                lista.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(con);
        }
        return lista;
    }
}

