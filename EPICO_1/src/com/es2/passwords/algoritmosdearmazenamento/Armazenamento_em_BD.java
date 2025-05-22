package com.es2.passwords.algoritmosdearmazenamento;

import com.es2.passwords.ArmazenamentoBridge;

import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class Armazenamento_em_BD extends ArmazenamentoBridge {
    private final String DB_URL = "jdbc:sqlite:passwords.db";

    public Armazenamento_em_BD() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
             String sql = "CREATE TABLE IF NOT EXISTS senhas (site TEXT PRIMARY KEY, categoria TEXT, password TEXT)";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }

    @Override
    public void guardar(String categoria, String site, String password) {
        String sql = "REPLACE INTO senhas (site, categoria, password) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, site);
            pstmt.setString(2, categoria);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao guardar no BD: " + e.getMessage());
        }
    }


    @Override
    public void remover(String site) {
        String sql = "DELETE FROM senhas WHERE site = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, site);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao remover do BD: " + e.getMessage());
        }
    }

    @Override
    public Map<String, String[]> mostrar_tudo() {
        Map<String, String[]> data = new HashMap<>();
        String sql = "SELECT site, categoria, password FROM senhas";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                data.put(rs.getString("site"),
                        new String[] {rs.getString("categoria"), rs.getString("password")});
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar da BD: " + e.getMessage());
        }
        return data;
    }

}
