package org.example;

import java.sql.*;
import java.util.ArrayList;

public class DBInsertion {
        private static final String URL = "jdbc:sqlite:D:/SQLite/mydatabase.db"; // Укажите путь к вашей базе данных
        private static String filePath = "src/main/resources/Forbes.csv";

    public static void main(String[] args) throws SQLException {
        ArrayList<Person> people = FromCSVToPersonParser.readCsv(filePath);
        Connection connection = DriverManager.getConnection(URL);
        String sql = "INSERT INTO Person (Rank, Name, Networth, Age, CountryID, SourceID, IndustryID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Person person : people) {
                pstmt.setInt(1, person.getRank());
                pstmt.setString(2, person.getName());
                pstmt.setDouble(3, person.getNetworth());
                pstmt.setInt(4, person.getAge());
                pstmt.setInt(5, getCountryID(person.getCountry(),connection));
                pstmt.setInt(6, getSourceID(person.getSource(), connection));
                pstmt.setInt(7, getIndustryID(person.getIndustry(),connection));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            System.out.println("Ошибка при вставке данных: " + e.getMessage());
        }
    }

    private static int getCountryID(String country, Connection connection) throws SQLException {
        String query = "SELECT CountryID FROM Country WHERE CountryName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, country);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("CountryID");
            }
        }
        return -1;
    }

    private static int getSourceID(String source, Connection connection) throws SQLException {
        String query = "SELECT SourceID FROM Source WHERE SourceName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, source);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("SourceID");
            }
        }
        return -1;
    }

    private static int getIndustryID(String industry, Connection connection) throws SQLException {
        String query = "SELECT IndustryID FROM Industry WHERE IndustryName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, industry);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("IndustryID");
            }
        }
        return -1;
    }
}

