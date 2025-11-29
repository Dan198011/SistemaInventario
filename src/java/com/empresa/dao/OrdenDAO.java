package com.empresa.dao;

import com.empresa.model.OrdenProduccion;
import com.empresa.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrdenDAO {
    public void crearOrden(OrdenProduccion orden) throws Exception {
        String sql = "INSERT INTO orden_produccion(codigo, descripcion, fecha_inicio, fecha_entrega, estado) VALUES(?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, orden.getCodigo());
            ps.setString(2, orden.getDescripcion());
            ps.setString(3, orden.getFechaInicio());
            ps.setString(4, orden.getFechaEntrega());
            ps.setString(5, orden.getEstado());
            ps.executeUpdate();
        }
    }

    public List<OrdenProduccion> listarTodas() throws Exception {
        String sql = "SELECT * FROM orden_produccion ORDER BY fecha_inicio DESC";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<OrdenProduccion> lista = new ArrayList<>();
            while (rs.next()) {
                OrdenProduccion o = new OrdenProduccion();
                o.setId(rs.getInt("id"));
                o.setCodigo(rs.getString("codigo"));
                o.setDescripcion(rs.getString("descripcion"));
                o.setFechaInicio(rs.getString("fecha_inicio"));
                o.setFechaEntrega(rs.getString("fecha_entrega"));
                o.setEstado(rs.getString("estado"));
                lista.add(o);
            }
            return lista;
        }
    }

    public void actualizarEstado(int id, String estado) throws Exception {
        String sql = "UPDATE orden_produccion SET estado = ? WHERE id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    // Finaliza orden y genera/actualiza producto terminado (usa la misma conexión para atomicidad)
    public void finalizarOrdenYGenerarProducto(int ordenId, String productoCodigo, String productoDescripcion, double cantidadProd, Integer usuarioId) throws Exception {
        String updateOrden = "UPDATE orden_produccion SET estado = 'FINALIZADO' WHERE id = ?";
        String insertBit = "INSERT INTO bitacora (usuario_id, accion) VALUES (?, ?)";
        String updateProducto = "UPDATE producto_terminado SET cantidad = cantidad + ? WHERE codigo = ?";
        String insertProducto = "INSERT INTO producto_terminado (codigo, descripcion, cantidad) VALUES (?, ?, ?)";

        Connection c = null;
        try {
            c = DBUtil.getConnection();
            c.setAutoCommit(false);

            try (PreparedStatement ps = c.prepareStatement(updateOrden)) {
                ps.setInt(1, ordenId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps2 = c.prepareStatement(insertBit)) {
                if (usuarioId != null) ps2.setInt(1, usuarioId); else ps2.setNull(1, java.sql.Types.INTEGER);
                ps2.setString(2, "Finalizada orden id=" + ordenId + " -> producto " + productoCodigo + " cantidad=" + cantidadProd);
                ps2.executeUpdate();
            }

            try (PreparedStatement ps3 = c.prepareStatement(updateProducto)) {
                ps3.setDouble(1, cantidadProd);
                ps3.setString(2, productoCodigo);
                int affected = ps3.executeUpdate();
                if (affected == 0) {
                    try (PreparedStatement ps4 = c.prepareStatement(insertProducto)) {
                        ps4.setString(1, productoCodigo);
                        ps4.setString(2, productoDescripcion);
                        ps4.setDouble(3, cantidadProd);
                        ps4.executeUpdate();
                    }
                }
            }

            c.commit();
        } catch (Exception ex) {
            if (c != null) c.rollback();
            throw ex;
        } finally {
            if (c != null) c.setAutoCommit(true);
            if (c != null) c.close();
        }
    }
}
