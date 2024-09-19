package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

class DBCoutrySetup {
    private static final String URL = "jdbc:sqlite:D:/SQLite/mydatabase.db";
    private static String filePath = "src/main/resources/Forbes.csv";
    private Connection connection;
    public DBCoutrySetup(String dbUrl) throws SQLException {
        connection = DriverManager.getConnection(dbUrl);
    }

    public void insertCountries(ArrayList<Person> people) throws SQLException {
        HashSet<String> countries = new HashSet<>();
        for (Person person : people) {
            countries.add(person.getCountry());
        }
        String sql = "INSERT INTO Country (CountryName) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (String country : countries) {
                pstmt.setString(1, country);
                pstmt.executeUpdate();
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Person> people = FromCSVToPersonParser.readCsv(filePath);
        try {
            DBCoutrySetup dbManager = new DBCoutrySetup(URL);
            dbManager.insertCountries(people);
            System.out.println("Таблица Country успешно заполнена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

