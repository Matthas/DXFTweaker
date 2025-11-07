package com.Matthas.exportTools.csvExport;

import com.Matthas.cadObjects.CADObjects;
import com.Matthas.cadObjects.CadElements.CADBlock;
import com.Matthas.dxfRead.DXFDrawing;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVExport {
    private static Map<String,String> header = new LinkedHashMap<>();

    public CSVExport(CADObjects cad, DXFDrawing dxf) {
        //predefine header
//        header.put("1", "Handle");
//        header.put("2", "Name");
//        header.put("3", "BlockName");
//        header.put("4", "Color");
//        header.put("5", "LineType");
//        header.put("6", "Lineweight");
//        header.put("7", "Linetype Scale");
//        header.put("8", "GlobalWidth");
//        header.put("9", "Rotation");
//        header.put("10", "Length");
//        header.put("11", "Thickness");
//        header.put("12", "Coordinates");
        header.put("Handle","1");
        header.put("Name","2");
        header.put("BlockName","3");
        header.put("Color","4");
        header.put("LineType","5");
        header.put("Lineweight","6");
        header.put("Linetype Scale","7");
        header.put("GlobalWidth","8");
        header.put("Rotation","9");
        header.put("Length","10");
        header.put("Thickness","11");
        header.put("Coordinates","12");

        //add all possible attributes
        Map<String,String> aTTlist = dxf.getEntities().getATTList();
        for (int i = 0; i<aTTlist.size(); i++) {
            header.put(aTTlist.get(i), Integer.toString(header.size() + 1));
        }
    }

    public static void createCSV(CADObjects cad, DXFDrawing dxf){
        List<Map<String, String>> csvDataRows = buildCSVData(cad, dxf);
        System.out.println("Rows to write: " + csvDataRows.size());
        CSVWriter.saveCSV(csvDataRows, header);

    }

    public static List<Map<String, String>> buildCSVData(CADObjects cad, DXFDrawing dxf) {
        List<Map<String, String>> csvDataRows = new ArrayList<>();

        // Build header
        //Map<String, String> header = new LinkedHashMap<>();
        header.put("Handle", "1");
        header.put("Name", "2");
        header.put("BlockName", "3");
        header.put("Color", "4");
        header.put("Coordinates", "5");
        //header.put("LineType", "5");
        //header.put("Lineweight", "6");
        //header.put("Linetype Scale", "7");
        //header.put("GlobalWidth", "8");
        //header.put("Rotation", "9");
        //header.put("Length", "10");
        //header.put("Thickness", "11");

        if (cad.getLinesDatabase().size() > 0) {
            header.put("LineType", String.valueOf(header.size() +1 ));
            header.put("Lineweight", "6");
            header.put("Linetype Scale", "7");
            header.put("GlobalWidth", "8");
            header.put("Rotation", "9");
            header.put("Length", "10");
            header.put("Thickness", "11");
        }


        exportBlocksToCSV(csvDataRows, cad, dxf);

        return csvDataRows;
    }
    private static void exportBlocksToCSV(List<Map<String, String>> csvDataRows, CADObjects cad, DXFDrawing dxf) {
        // Add dynamic attributes from DXF
        Map<String, String> attList = dxf.getEntities().getATTList();
        for (String attName : attList.keySet()) {
            header.put(attName, Integer.toString(header.size() + 1));
        }

        // Iterate through CAD objects
        Map<String, Map<String, CADBlock>> objectsDatabase = cad.getObjectsDatabase();

        for (Map<String, CADBlock> innerDatabase : objectsDatabase.values()) {
            for (CADBlock block : innerDatabase.values()) {
                Map<String, String> row = new LinkedHashMap<>();

                // Predefined fields
                row.put("Handle", block.getHandle());
                row.put("Name", block.getName());
                row.put("BlockName", block.getBlockName());
                row.put("Color", Integer.toString(block.getColor()));
                row.put("Coordinates", "(" + Double.toString(block.getCoords().getrawNCoordX(0)) + " " + Double.toString(block.getCoords().getrawNCoordY(0)) + ")");
                // Dynamic attributes
                Map<String, String> attributes = block.getAttributes();
                for (String attKey : attributes.keySet()) {
                    row.put(attKey, attributes.get(attKey));
                }

                csvDataRows.add(row);
            }
        }
    }
}
