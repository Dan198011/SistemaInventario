package com.empresa.dao;

import com.empresa.model.Material;
import com.empresa.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {
    public List<Material> listarTodos() throws Exception {
        String sql = "SELECT * FROM material";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Material> lista = new ArrayList<>();
            while (rs.next()) {
                Material m = new Material();
                m.setId(rs.getInt("id"));
                m.setTipo(rs.getString("tipo"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setExistencia(rs.getDouble("existencia"));
                m.setMinimo(rs.getDouble("minimo"));
                m.setUnidad(rs.getString("unidad"));
                m.setLote(rs.getString("lote"));
                lista.add(m);
            }
            return lista;
        }
    }

    public Material buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM material WHERE id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Material m = new Material();
                    m.setId(rs.getInt("id"));
                    m.setTipo(rs.getString("tipo"));
                    m.setDescripcion(rs.getString("descripcion"));
                    m.setExistencia(rs.getDouble("existencia"));
                    m.setMinimo(rs.getDouble("minimo"));
                    m.setUnidad(rs.getString("unidad"));
                    m.setLote(rs.getString("lote"));
                    return m;
                }
            }
        }
        return null;
    }

    public void insertar(Material m) throws Exception {
        String sql = "INSERT INTO material(tipo, descripcion, existencia, minimo, unidad, lote) VALUES(?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getTipo());
            ps.setString(2, m.getDescripcion());
            ps.setDouble(3, m.getExistencia());
            ps.setDouble(4, m.getMinimo());
            ps.setString(5, m.getUnidad());
            ps.setString(6, m.getLote());
            ps.executeUpdate();
        }
    }

    public void actualizarExistencia(int id, double nuevaCantidad) throws Exception {
        String sql = "UPDATE material SET existencia = ? WHERE id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, nuevaCantidad);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public List<Material> listarBajoMinimo() throws Exception {
        String sql = "SELECT * FROM material WHERE existencia <= minimo";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Material> lista = new ArrayList<>();
            while (rs.next()) {
                Material m = new Material();
                m.setId(rs.getInt("id"));
                m.setTipo(rs.getString("tipo"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setExistencia(rs.getDouble("existencia"));
                m.setMinimo(rs.getDouble("minimo"));
                m.setUnidad(rs.getString("unidad"));
                m.setLote(rs.getString("lote"));
                lista.add(m);
            }
            return lista;
        }
    }
}
