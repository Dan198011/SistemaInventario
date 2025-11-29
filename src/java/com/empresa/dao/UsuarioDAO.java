package com.empresa.dao;

import com.empresa.model.Usuario;
import com.empresa.util.DBUtil;

import java.sql.*;

public class UsuarioDAO {

    private String sha256(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    // devuelve Usuario si login correcto, o null si falla
    public Usuario login(String username, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement("SELECT id, nombre, usuario, password, rol FROM usuario WHERE usuario = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                String hashBD = rs.getString("password");
                String hashUser = sha256(password);
                if (hashUser != null && hashUser.equals(hashBD)) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setPassword(hashBD);
                    u.setRol(rs.getString("rol"));
                    return u;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(con);
        }
        return null;
    }

    // registrar usuario (almacena hash SHA-256)
    public boolean registrar(Usuario u) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement("INSERT INTO usuario(nombre, usuario, password, rol) VALUES (?, ?, ?, ?)");
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsuario());
            ps.setString(3, sha256(u.getPassword()));
            ps.setString(4, u.getRol());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps);
            DBUtil.close(con);
        }
        return false;
    }
}
