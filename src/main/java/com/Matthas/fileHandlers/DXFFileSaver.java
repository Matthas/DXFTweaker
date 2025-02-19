package com.Matthas.fileHandlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DXFFileSaver {
    public static void saveDXFFile(String[] aryLines, String filename) {
        // Create a File object from the full path
        File originalFile = new File(filename);

        // Ensure the filename does not end with .dxf
        String baseFilename = originalFile.getName();
        if (baseFilename.endsWith(".dxf")) {
            baseFilename = baseFilename.substring(0, baseFilename.length() - 4);
        }

        // Get the directory of the loaded file
        String directory = originalFile.getParent(); // Get the parent directory

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");
        String formattedDateTime = now.format(formatter);

        // Construct the new file name with the directory
        String newFileName = directory + File.separator + baseFilename + "-" + formattedDateTime + ".dxf";
        int counter = 1;

        // Check if files exist
        File file = new File(newFileName);
        while (file.exists()) {
            newFileName = directory + File.separator + baseFilename + "-" + formattedDateTime + "(" + counter + ").dxf";
            file = new File(newFileName);
            counter++;
        }
        // Write to the new DXF file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName))) {
            for (String line : aryLines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("DXF file saved as: " + newFileName);
        } catch (IOException e) {
            System.err.println("Error writing to DXF file: " + e.getMessage());
        }
    }
}