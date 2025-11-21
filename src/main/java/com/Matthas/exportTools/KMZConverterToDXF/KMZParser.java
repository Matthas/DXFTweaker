package com.Matthas.exportTools.KMZConverterToDXF;

import com.Matthas.exportTools.KMZConverterToDXF.KMLParsers.KMLParser;
import com.Matthas.exportTools.KMZConverterToDXF.KMZObjects.KMLObject;
import com.Matthas.fileHandlers.FileChooser;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class KMZParser {

    public static void KMZParser() {
        String fileName = FileChooser.selectFilePath();
        Map<String, List<KMLObject>> markersMap = new LinkedHashMap<>();

        try {
            Map<String, String> kmlFiles = extractKMLFiles(fileName);
            System.out.println("Found " + kmlFiles.size() + " KML file(s).");

            for (String kmlFileName : kmlFiles.keySet()) {
                String xmlContent = kmlFiles.get(kmlFileName);
                List<KMLObject> parsedObjects = KMLParser.parse(xmlContent);

                String outerKey = kmlFileName.replace(".kml", "").trim();
                markersMap.put(outerKey, parsedObjects);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

    public static Map<String, String> extractKMLFiles(String kmzPath) throws IOException {
        Map<String, String> kmlFilesMap = new LinkedHashMap<>();

        try (ZipFile zipFile = new ZipFile(kmzPath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".kml")) {
                    try (InputStream inputStream = zipFile.getInputStream(entry);
                         ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            baos.write(buffer, 0, bytesRead);
                        }

                        String content = new String(baos.toByteArray(), "UTF-8");
                        kmlFilesMap.put(entry.getName(), content);
                    }
                }
            }
        }

        return kmlFilesMap;
    }


    public static void main(String[] args){
        KMZParser();
    }
}