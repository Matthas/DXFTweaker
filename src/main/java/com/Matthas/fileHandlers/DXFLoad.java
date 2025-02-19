package com.Matthas.fileHandlers;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class DXFLoad {
    private String path;
    public DXFLoad(String file_path) {
        path = file_path;
    }

    public String[] OpenFile() throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader textReader = new BufferedReader(fr);
        int numberOfLines = readLines();
        String[] textData = new String[numberOfLines];

        for (int i = 0; i <numberOfLines; i++) {
            textData[i] = textReader.readLine();
        }
        textReader.close();
        return textData;
    }

    int readLines() throws IOException {
        FileReader file_to_read = new FileReader(path);
        BufferedReader bf = new BufferedReader(file_to_read);

        String aLine;
        int numberofLines = 0;

        while ((aLine = bf.readLine()) !=null) {
            numberofLines++;
        }
        bf.close();
        return numberofLines;
    }
}