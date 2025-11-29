package com.empresa.dao;

import com.empresa.model.Movimiento;
import com.empresa.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO {
    public boolean registrarMovimiento(Movimiento mov) throws Exception {
        String insertMov = "INSERT INTO movimiento (material_id, usuario_id, tipo, cantidad, referencia, proveedor, lote, orden_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String updateMat = mov.getTipo().equals("ENTRADA") ?
                "UPDATE material SET existencia = existencia + ? WHERE id = ?" :
                "UPDATE material SET existencia = existencia - ? WHERE id = ?";
        String insertBit = "INSERT INTO bitacora (usuario_id, accion) VALUES (?, ?)";

        try (Connection c = DBUtil.getConnection()) {
            try {
                c.setAutoCommit(false);

                try (PreparedStatement ps = c.prepareStatement(insertMov, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, mov.getMaterialId());
                    if (mov.getUsuarioId() != null) ps.setInt(2, mov.getUsuarioId()); else ps.setNull(2, Types.INTEGER);
                    ps.setString(3, mov.getTipo());
                    ps.setDouble(4, mov.getCantidad());
                    ps.setString(5, mov.getReferencia());
                    ps.setString(6, mov.getProveedor());
                    ps.setString(7, mov.getLote());
                    if (mov.getOrdenId() != null) ps.setInt(8, mov.getOrdenId()); else ps.setNull(8, Types.INTEGER);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps2 = c.prepareStatement(updateMat)) {
                    ps2.setDouble(1, mov.getCantidad());
                    ps2.setInt(2, mov.getMaterialId());
                    ps2.executeUpdate();
                }

                String accion = (mov.getTipo().equals("ENTRADA") ? "ENTRADA" : "SALIDA")
                        + " materialId=" + mov.getMaterialId() + " cantidad=" + mov.getCantidad() + (mov.getOrdenId()!=null?" orden="+mov.getOrdenId():"");

                try (PreparedStatement ps3 = c.prepareStatement(insertBit)) {
                    if (mov.getUsuarioId() != null) ps3.setInt(1, mov.getUsuarioId()); else ps3.setNull(1, Types.INTEGER);
                    ps3.setString(2, accion);
                    ps3.executeUpdate();
                }

                c.commit();
                return true;
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    public List<Movimiento> listarPorMaterial(int materialId) throws Exception {
        String sql = "SELECT * FROM movimiento WHERE material_id = ? ORDER BY fecha DESC";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, materialId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Movimiento> lista = new ArrayList<>();
                while (rs.next()) {
                    Movimiento m = new Movimiento();
                    m.setId(rs.getInt("id"));
                    m.setMaterialId(rs.getInt("material_id"));
                    m.setUsuarioId((Integer) rs.getObject("usuario_id"));
                    m.setTipo(rs.getString("tipo"));
                    m.setCantidad(rs.getDouble("cantidad"));
                    m.setReferencia(rs.getString("referencia"));
                    m.setProveedor(rs.getString("proveedor"));
                    m.setLote(rs.getString("lote"));
                    m.setOrdenId((Integer) rs.getObject("orden_id"));
                    m.setFecha(rs.getTimestamp("fecha"));
                    lista.add(m);
                }
                return lista;
            }
        }
    }

    public List<Movimiento> listarPorFechaYUsuario(String desde, String hasta, Integer usuarioId) throws Exception {
        String sql = "SELECT * FROM movimiento WHERE fecha BETWEEN ? AND ?" + (usuarioId!=null?" AND usuario_id = ?":"") + " ORDER BY fecha DESC";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, desde);
            ps.setString(2, hasta);
            if (usuarioId!=null) ps.setInt(3, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Movimiento> lista = new ArrayList<>();
                while (rs.next()) {
                    Movimiento m = new Movimiento();
                    m.setId(rs.getInt("id"));
                    m.setMaterialId(rs.getInt("material_id"));
                    m.setUsuarioId((Integer) rs.getObject("usuario_id"));
                    m.setTipo(rs.getString("tipo"));
                    m.setCantidad(rs.getDouble("cantidad"));
                    m.setReferencia(rs.getString("referencia"));
                    m.setProveedor(rs.getString("proveedor"));
                    m.setLote(rs.getString("lote"));
                    m.setOrdenId((Integer) rs.getObject("orden_id"));
                    m.setFecha(rs.getTimestamp("fecha"));
                    lista.add(m);
                }
                return lista;
            }
        }
    }
}
