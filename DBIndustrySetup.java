package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

class DBIndustrySetup {
    private static final String URL = "jdbc:sqlite:D:/SQLite/mydatabase.db";
    private static String filePath = "src/main/resources/Forbes.csv";
    private Connection connection;
    public DBIndustrySetup(String dbUrl) throws SQLException {
        connection = DriverManager.getConnection(dbUrl);
    }
    public void insertIndusrtys(ArrayList<Person> people) throws SQLException {
        HashSet<String> industrys = new HashSet<>();
        for (Person person : people) {
            industrys.add(person.getIndustry());
        }
        String sql = "INSERT INTO Industry (IndustryName) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (String industry : industrys) {
                pstmt.setString(1, industry);
                pstmt.executeUpdate();
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Person> people = FromCSVToPersonParser.readCsv(filePath);
        try {
            DBIndustrySetup dbManager = new DBIndustrySetup(URL);
            dbManager.insertIndusrtys(people);
            System.out.println("Таблица Industry успешно заполнена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

