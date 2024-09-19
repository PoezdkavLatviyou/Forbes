package org.example;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
public class FromCSVToPersonParser {
        public static ArrayList<Person> readCsv(String filePath) {
            ArrayList<Person> personList = new ArrayList<>();
            try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
                String[] values;
                csvReader.readNext();
                while ((values = csvReader.readNext()) != null) {
                    try {
                        int rank = Integer.parseInt(values[0].trim());
                        String name = values[1].trim();
                        double networth = Double.parseDouble(values[2].trim());
                        int age = Integer.parseInt(values[3].trim());
                        String country = values[4].trim();
                        String source = values[5].trim();
                        String industry = values[6].trim();

                        Person person = new Person(rank, name, networth, age, country, source, industry);
                        personList.add(person);
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка преобразования данных: " + String.join(",", values));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Недостаточно данных в строке: " + String.join(",", values));
                    }
                }
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
            }
            return personList;
        }
}

