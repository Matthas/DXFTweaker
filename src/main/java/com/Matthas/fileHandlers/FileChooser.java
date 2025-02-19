package com.Matthas.fileHandlers;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {

    public static String selectFilePath()  {
        Settings settings = Settings.getInstance();
        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();

        // Set the file filter to only allow .dxf files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("DXF Files", "dxf");
        fileChooser.setFileFilter(filter);


        if (settings.getValue("FILECHOOSER") == null || !new File(settings.getValue("FILECHOOSER")).exists() || !new File(settings.getValue("FILECHOOSER")).isDirectory()) {
            System.out.println("no directory found - default to user.home folder");
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        } else {
            fileChooser.setCurrentDirectory(new File(settings.getValue("FILECHOOSER")));
        }

        // Show the dialog box and prompt the user to select a file
        int result = fileChooser.showDialog(null, "Please Select the File");

        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();

            // Return the full path
            return selectedFile.getAbsolutePath();
        } else {
            return null; // No file selected
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filePath = selectFilePath();
        if (filePath != null) {
            //System.out.println(filePath);
        } else {
            System.out.println("No file selected.");
        }
    }
}