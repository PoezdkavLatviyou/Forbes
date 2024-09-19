package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

class DBSourceSetup {
    private static final String URL = "jdbc:sqlite:D:/SQLite/mydatabase.db";
    private static String filePath = "src/main/resources/Forbes.csv";
    private Connection connection;
    public DBSourceSetup(String dbUrl) throws SQLException {
        connection = DriverManager.getConnection(dbUrl);
    }

    public void insertSource(ArrayList<Person> people) throws SQLException {
        HashSet<String> sources = new HashSet<>();
        for (Person person : people) {
            sources.add(person.getSource());
        }
        String sql = "INSERT INTO Source (SourceName) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (String source : sources) {
                pstmt.setString(1, source);
                pstmt.executeUpdate();
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Person> people = FromCSVToPersonParser.readCsv(filePath);
        try {
            DBSourceSetup dbManager = new DBSourceSetup(URL);
            dbManager.insertSource(people);
            System.out.println("Таблица Source успешно заполнена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

