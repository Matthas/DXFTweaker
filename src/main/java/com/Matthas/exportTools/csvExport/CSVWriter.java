package com.Matthas.exportTools.csvExport;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class CSVWriter {

    public static void saveCSV(List<Map<String, String>> csvDataRows, Map<String, String> header) {
        // Ask user for file location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save CSV File");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Ensure .csv extension
            if (!fileToSave.getName().toLowerCase().endsWith(".csv")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
            }


            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                // Write header
                List<String> headers = new ArrayList<>(header.keySet());
                //System.out.println("Header: " + header.keySet());
                writer.write(String.join(",", headers));
                writer.newLine();

                // Write rows
                for (Map<String, String> row : csvDataRows) {
                    List<String> rowValues = new ArrayList<>();
                    for (String column : headers) {
                        String value = row.getOrDefault(column, "");
                        // Escape commas and quotes
                        if (value.contains(",") || value.contains("\"")) {
                            value = "\"" + value.replace("\"", "\"\"") + "\"";
                        }
                        rowValues.add(value);
                    }
                    writer.write(String.join(",", rowValues));
                    writer.newLine();
                }


                System.out.println("CSV saved to: " + fileToSave.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Save command cancelled.");
        }
    }
}