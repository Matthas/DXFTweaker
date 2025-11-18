package com.Matthas.exportTools.csvExport;

import com.Matthas.cadObjects.CADObjects;
import com.Matthas.cadObjects.CadElements.CADBlock;
import com.Matthas.cadObjects.CadElements.CADLine;
import com.Matthas.cadObjects.CadElements.CADText;
import com.Matthas.dxfRead.DXFDrawing;
import com.Matthas.dxfRead.dxfElements.coords.Coords;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVExport {
    private static Map<String,String> header = new LinkedHashMap<>();

    public CSVExport(CADObjects cad, DXFDrawing dxf) {
    }

    public static void createCSV(CADObjects cad, DXFDrawing dxf){
        List<Map<String, String>> csvDataRows = buildCSVData(cad, dxf);
        System.out.println("Rows to write: " + csvDataRows.size());
        CSVWriter.saveCSV(csvDataRows, header);
    }

    public static List<Map<String, String>> buildCSVData(CADObjects cad, DXFDrawing dxf) {
        List<Map<String, String>> csvDataRows = new ArrayList<>();

        // Common headers
        header.put("Handle", "1");
        header.put("Name", "2");
        header.put("BlockName", "3");
        header.put("Color", "4");
        header.put("Coordinates", "5");
        header.put("Coordinates2", "6");
        header.put("Layer", "7");

        // Shared headers (appear if any relevant database is non-empty)
        if (!cad.getLinesDatabase().isEmpty() ||
                !cad.getArcDatabase().isEmpty() ||
                !cad.getSplineDatabase().isEmpty() ||
                !cad.getCircleDatabase().isEmpty() ||
                !cad.getEllipseDatabase().isEmpty()) {
            header.put("LineType", String.valueOf(header.size() + 1));
            header.put("LineWeight", String.valueOf(header.size() + 1));
            header.put("LineTypeScale", String.valueOf(header.size() + 1));
        }

        if (!cad.getLinesDatabase().isEmpty() ||
                !cad.getArcDatabase().isEmpty() ||
                !cad.getCircleDatabase().isEmpty() ||
                !cad.getEllipseDatabase().isEmpty()) {
            header.put("Thickness", String.valueOf(header.size() + 1));
        }

        if (!cad.getObjectsDatabase().isEmpty() ||
                !cad.getLinesDatabase().isEmpty() ||
                !cad.getTextDatabase().isEmpty()) {
            header.put("Rotation", String.valueOf(header.size() + 1));
        }

        // Unique headers per database
        if (!cad.getObjectsDatabase().isEmpty()) {
            header.put("BlockName", String.valueOf(header.size() + 1));
            header.put("BlockType", String.valueOf(header.size() + 1));
            header.put("ScaleX", String.valueOf(header.size() + 1));
            header.put("ScaleY", String.valueOf(header.size() + 1));
        }

        if (!cad.getLinesDatabase().isEmpty()) {
            header.put("GlobalWidth", String.valueOf(header.size() + 1));
            header.put("Length", String.valueOf(header.size() + 1));
            //calculatelength is only implemented for lines
        }

        if (!cad.getArcDatabase().isEmpty()) {
            header.put("Radius", String.valueOf(header.size() + 1));
            header.put("StartAngle", String.valueOf(header.size() + 1));
            header.put("EndAngle", String.valueOf(header.size() + 1));
        }

        if (!cad.getSplineDatabase().isEmpty()) {
            header.put("Degree", String.valueOf(header.size() + 1));
            header.put("KnotCount", String.valueOf(header.size() + 1));
            header.put("FitPoints", String.valueOf(header.size() + 1));
            header.put("ControlPoints", String.valueOf(header.size() + 1));
        }

        if (!cad.getCircleDatabase().isEmpty()) {
            header.put("Radius", String.valueOf(header.size() + 1));
            header.put("Diameter", String.valueOf(header.size() + 1));
        }

        if (!cad.getEllipseDatabase().isEmpty()) {
            header.put("MajorAxis", String.valueOf(header.size() + 1));
            header.put("MinorAxis", String.valueOf(header.size() + 1));
            header.put("Ratio", String.valueOf(header.size() + 1));
        }

        if (!cad.getTextDatabase().isEmpty()) {
            header.put("TextValue", String.valueOf(header.size() + 1));
            header.put("Height", String.valueOf(header.size() + 1));
            header.put("WidthFactor", String.valueOf(header.size() + 1));
            header.put("Style", String.valueOf(header.size() + 1));
            header.put("ObliqueAngle", String.valueOf(header.size() + 1));
            header.put("IsMText", String.valueOf(header.size() + 1));
        }

        exportBlocksToCSV(csvDataRows, cad, dxf);
        exportLinesToCSV(csvDataRows, cad);
        exportArcsToCSV(csvDataRows, cad);
        exportSplineToCSV(csvDataRows, cad);
        exportCircleToCSV(csvDataRows, cad);
        exportEllipseToCSV(csvDataRows, cad);
        exportTextToCSV(csvDataRows, cad);

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
                row.put("Layer", block.getLayer());
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

    private static void exportLinesToCSV(List<Map<String,String>> csvDataRows, CADObjects cad) {
        Map<String, Map<String, CADLine>> LinesDatabase = cad.getLinesDatabase();

        for (Map<String, CADLine> innerDatabase : LinesDatabase.values()) {
            for (CADLine line : innerDatabase.values()) {
                Map<String, String> row = new LinkedHashMap<>();

                row.put("Handle", line.getHandle());
                row.put("Name", line.getName());
                row.put("Layer", line.getLayer());
                row.put("Color", Integer.toString(line.getColor()));
                row.put("Coordinates", convertCoordsToText(line.getCoords()));

                row.put("LineType", line.getLineType());
                row.put("LineWeight", String.valueOf(line.getLineWeight()));
                row.put("LineTypeScale", String.valueOf(line.getLineTypeScale()));
                row.put("GlobalWidth", String.valueOf(line.getGlobalWidth()));
                row.put("Length", String.valueOf(line.getLength())); // Implement this method
                row.put("Thickness", String.valueOf(line.getThickness()));

                csvDataRows.add(row);
            }
        }
    }

    private static void exportArcsToCSV(List<Map<String, String>> csvDataRows, CADObjects cad) {
        Map<String, Map<String, CADLine>> arcDatabase = cad.getArcDatabase();

        for (Map<String, CADLine> layerMap : arcDatabase.values()) {
            for (CADLine arc : layerMap.values()) {
                Map<String, String> row = new LinkedHashMap<>();


                row.put("Handle", arc.getHandle());
                row.put("Name", arc.getName());
                row.put("Layer", arc.getLayer());
                row.put("Color", String.valueOf(arc.getColor()));
                row.put("Coordinates", convertCoordsToText(arc.getCoords()));

                row.put("LineType", arc.getLineType());
                row.put("Lineweight", String.valueOf(arc.getLineWeight()));
                row.put("LineTypeScale", String.valueOf(arc.getLineTypeScale()));
                row.put("Thickness", String.valueOf(arc.getThickness()));

                // Arc-specific fields
                row.put("Radius", String.valueOf(arc.getRadius()));
                row.put("StartAngle", String.valueOf(arc.getStartAngle()));
                row.put("EndAngle", String.valueOf(arc.getEndAngle()));
                //row.put("Length", calculateArcLength(arc)); // length not implemented for arc

                csvDataRows.add(row);
            }
        }
    }


    private static void exportSplineToCSV(List<Map<String, String>> csvDataRows, CADObjects cad) {
        Map<String, Map<String, CADLine>> splineDatabase = cad.getSplineDatabase();

        for (Map<String, CADLine> layerMap : splineDatabase.values()) {
            for (CADLine spline : layerMap.values()) {
                Map<String, String> row = new LinkedHashMap<>();


                row.put("Handle", spline.getHandle());
                row.put("Name", spline.getName());
                row.put("Layer", spline.getLayer());
                row.put("Color", String.valueOf(spline.getColor()));
                row.put("Coordinates", convertCoordsToText(spline.getCoords()));
                //fitpoints etc not implemented
                row.put("LineType", spline.getLineType());
                row.put("Lineweight", String.valueOf(spline.getLineWeight()));
                row.put("LineTypeScale", String.valueOf(spline.getLineTypeScale()));
                row.put("Thickness", String.valueOf(spline.getThickness()));

                // Arc-specific fields
                row.put("Radius", String.valueOf(spline.getRadius()));
                row.put("StartAngle", String.valueOf(spline.getStartAngle()));
                row.put("EndAngle", String.valueOf(spline.getEndAngle()));
                //row.put("Length", calculateArcLength(arc)); // length not implemented for arc

                csvDataRows.add(row);
            }
        }
    }

    private static void exportCircleToCSV(List<Map<String, String>> csvDataRows, CADObjects cad) {
        Map<String, Map<String, CADLine>> circleDatabase = cad.getCircleDatabase();

        for (Map<String, CADLine> layerMap : circleDatabase.values()) {
            for (CADLine circle : layerMap.values()) {
                Map<String, String> row = new LinkedHashMap<>();

                // Common fields
                row.put("Handle", circle.getHandle());
                row.put("Name", circle.getName());
                row.put("Layer", circle.getLayer());
                row.put("Color", String.valueOf(circle.getColor()));
                row.put("Coordinates", convertCoordsToText(circle.getCoords()));

                // Shared fields
                row.put("LineType", circle.getLineType());
                row.put("LineWeight", String.valueOf(circle.getLineWeight()));
                row.put("LineTypeScale", String.valueOf(circle.getLineTypeScale()));
                row.put("Thickness", String.valueOf(circle.getThickness()));

                // Circle-specific fields
                row.put("Radius", String.valueOf(circle.getRadius()));

                csvDataRows.add(row);
            }
        }
    }

    private static void exportEllipseToCSV(List<Map<String, String>> csvDataRows, CADObjects cad) {
        Map<String, Map<String, CADLine>> ellipseDatabase = cad.getEllipseDatabase();

        for (Map<String, CADLine> layerMap : ellipseDatabase.values()) {
            for (CADLine ellipse : layerMap.values()) {
                Map<String, String> row = new LinkedHashMap<>();

                // Common fields
                row.put("Handle", ellipse.getHandle());
                row.put("Name", ellipse.getName());
                row.put("Layer", ellipse.getLayer());
                row.put("Color", String.valueOf(ellipse.getColor()));
                row.put("Coordinates", convertCoordsToText(ellipse.getCoords()));

                // Shared fields
                row.put("LineType", ellipse.getLineType());
                row.put("LineWeight", String.valueOf(ellipse.getLineWeight()));
                row.put("LineTypeScale", String.valueOf(ellipse.getLineTypeScale()));
                row.put("Thickness", String.valueOf(ellipse.getThickness()));

                // Ellipse-specific fields
                row.put("MajorAxis", "(" + String.valueOf(ellipse.getEllipseAxis().getrawNCoordX(0)) + " " + String.valueOf(ellipse.getEllipseAxis().getrawNCoordY(0)) + ")");
                row.put("Ratio", String.valueOf(ellipse.getAxisRatio()));

                csvDataRows.add(row);
            }
        }
    }

    private static void exportTextToCSV(List<Map<String, String>> csvDataRows, CADObjects cad) {
        Map<String, Map<String, CADText>> textDatabase = cad.getTextDatabase();

        for (Map<String, CADText> layerMap : textDatabase.values()) {
            for (CADText text : layerMap.values()) {
                Map<String, String> row = new LinkedHashMap<>();

                // Common fields
                row.put("Handle", text.getHandle());
                row.put("Name", text.getName());
                row.put("Layer", text.getLayer());
                row.put("Color", String.valueOf(text.getColor()));
                row.put("Coordinates", convertCoordsToText(text.getCoords()));

                // Shared fields
                row.put("Rotation", String.valueOf(text.getRotation()));

                // Text-specific fields
                row.put("TextValue", text.getTextValue());
                row.put("Height", String.valueOf(text.getHeight()));
                row.put("WidthFactor", String.valueOf(text.getWidthFactor()));
                row.put("Style", text.getStyle());
                row.put("ObliqueAngle", String.valueOf(text.getObliqueAngle()));

                csvDataRows.add(row);
            }
        }
    }

    private static String convertCoordsToText(Coords coords){
        String CoordString = "";
        for (int i = 0; i < coords.size() - 1; i++) {
            if (i != 0){
                CoordString += " ";
            }
            CoordString += "(" + coords.getrawNCoordX(i) + " " + coords.getrawNCoordY(i) + ")";
        }
        return CoordString;
    }
}
