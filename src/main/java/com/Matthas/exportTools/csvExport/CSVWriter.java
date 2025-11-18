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
                writer.write(String.join(",", headers));
                writer.newLine();

                // Write rows
                for (Map<String, String> row : csvDataRows) {
                    List<String> rowValues = new ArrayList<>();

                    for (String column : headers) {
                        String value = row.getOrDefault(column, "");
                        if (value == null) {
                            value = "";
                        } else {
                            if (value.contains(",") || value.contains("\"")) {
                                value = "\"" + value.replace("\"", "\"\"") + "\"";
                            }
                        }

                        // Special handling for Coordinates - Excel has limit of 32767 characters per cell
                        if (column.equalsIgnoreCase("Coordinates") && value.length() > 32700) {
                            // Split into two parts
                            String part1 = value.substring(0, 32700);
                            String part2 = value.substring(32700);

                            rowValues.add(part1);

                            // Add Coordinates2 value immediately after
                            int coord2Index = headers.indexOf("Coordinates2");
                            if (coord2Index != -1) {
                                // Fill Coordinates2 later in correct position
                                row.put("Coordinates2", part2);
                            }
                        } else {
                            rowValues.add(value);
                        }
                    }

                    // Ensure Coordinates2 is added if present
                    if (headers.contains("Coordinates2") && row.get("Coordinates2") != null) {
                        // If we already added Coordinates, Coordinates2 will be added in its position
                        // If not, add empty or actual value
                        int coord2Index = headers.indexOf("Coordinates2");
                        if (rowValues.size() <= coord2Index) {
                            // Fill missing columns
                            while (rowValues.size() < headers.size()) {
                                rowValues.add("");
                            }
                        }
                        rowValues.set(coord2Index, row.get("Coordinates2"));
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