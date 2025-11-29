package com.empresa.util;

import java.sql.*;

public class DBUtil {

    // Ajusta estos datos a tu entorno
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_inventario?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL Connector/J moderno
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver JDBC no encontrado", ex);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void close(ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
    }

    public static void close(Statement st) {
        try { if (st != null) st.close(); } catch (Exception ignored) {}
    }

    public static void close(Connection con) {
        try { if (con != null) con.close(); } catch (Exception ignored) {}
    }
}
