package com.Matthas.dxfRead.dxfElements.entities;

import com.Matthas.dxfRead.dxfElements.coords.Coords;
import com.Matthas.dxfRead.dxfElements.tables.Tables;

import java.util.LinkedHashMap;

public class ReadEntity {

    //function to read Entity data and return is back to com.Matthas.main function Entities
    public Entity readEntity(Integer currentline, Integer length, String[] aryLines, Entity entity, Entities entities) {
        Coords coords = new Coords();
        Coords fitPoints = new Coords();
        Coords tangentPoints = new Coords();
        Coords alignmentPoints = new Coords();
        Coords EllipseAxis = new Coords();
        int i = currentline;
        String currentAppName = null;
        try { //print line where errors happens
            innerloop:
            for (; i < length; i++) {
                // System.out.println(i);
                switch (aryLines[i]) {
                    case "  1":
                        entity.setTextVal(aryLines[i + 1]);
                        break;
                    case "  2":
                        //check if block is dynamic and find real block name using function FindBlockName
                        if (left(aryLines[i + 1], 2).equals("*U")) {
                            entity.setName(aryLines[i + 1]);
                            FindBlockName(entity);
                        } else {
                            entity.setName(aryLines[i + 1]);
                        }
                        break;
                    case "  3": //for MTEXt entity if string is longer than 250, code 3 is used to store excess characters (multiple entries possible)
                        readEntityName(entity, aryLines, i);
                        break;
                    case "  5":
                        entity.setHandle(aryLines[i + 1]);
                        entity.setLineIndex(i);
                        break;
                    case "  6":
                        entity.setLineType(aryLines[i + 1]);
                        break;
                    case "  7":
                        entity.setTextStyle(aryLines[i + 1]);
                        break;
                    case "  8":
                        entity.setLayer(aryLines[i + 1]);
                        break;
                    case " 39":
                        entity.setThickness(Double.parseDouble(aryLines[i + 1]));
                        break;
                    case " 40":
                        code40(entity, aryLines,i);
                        break;
                    case " 41":
                        code41(entity,aryLines,i);
                        break;
                    case " 42":
                        code42(entity,aryLines,i);
                    case " 43":
                        entity.setWidth(Double.parseDouble(aryLines[i + 1].trim()));
                        break;
                    case " 48":
                        entity.setLineTypeScale(Double.parseDouble(aryLines[i + 1].trim()));
                        break;
                    case " 50":
                        code50(entity,aryLines,i);
                        break;
                    case " 51":
                        code51(entity,aryLines,i);
                        break;
                    case " 62":
                        //save color from 62 code only when value is empty, otherwise it should be overwritten by 420 code
                        if (entity.getColour() == null) {
                            entity.setColour(Integer.parseInt(aryLines[i + 1].trim()));
                        }
                        break;
                    case " 63":
                        entity.setBackgroundColor(Integer.parseInt(aryLines[i+1].trim()));
                        break;
                    case " 67":
                        entity.setSpace(Integer.parseInt(aryLines[i + 1].trim()));
                        break;
                    case " 70":
                        entity.setPolylineFlag(Integer.parseInt(aryLines[i + 1].trim()));
                        break;
                    case " 71":
                        entity.setJustify(aryLines[i + 1]);
                        break;
                    case " 74":
                        entity.setDegree(Double.parseDouble(aryLines[i+1].trim()));
                        break;
                    case " 90": //why code is used differently for different objects :<
                        if (entity.getBlockname().equals("MTEXT")) {
                            entity.setBackground(aryLines[i+1].trim());
                        } else {
                            entity.setEntityType(aryLines[i + 1]);
                        }
                        break;
                    case "330":
                        if (!aryLines[i + 1].equals("1F")) {
                            entity.setParentHandle(aryLines[i + 1]);
                            break;
                        }
                        break;
                    case "360":
                        entity.setSoftPointer(aryLines[i + 1]);
                        break;
                    case "370":
                        entity.setLineWeight(Double.parseDouble(aryLines[i+1].trim()));
                        break;
                    case "420":
                        entity.setColour(Integer.parseInt(aryLines[i + 1].trim()));
                        break;
                    case "440":
                        entity.setTransparency(Double.parseDouble(aryLines[i+1].trim()));
                        break;
                    case " 10": //X coord
                        code10(coords, aryLines, i);
                        break;
                    case " 20": //Y coords
                        code20(coords, aryLines, i);
                        break;
                    case " 11": //endpoints (Line) or fitpoints for spline
                        code11(entity, coords, fitPoints, aryLines, i, alignmentPoints, EllipseAxis);
                        break;
                    case " 21": //endpoints(Line) or fitpoints for spline
                        code21(entity, coords, fitPoints, aryLines, i, alignmentPoints);
                        break;
                    case " 12": //tangentPoint start X
                        code12(tangentPoints, aryLines, i);
                        break;
                    case " 22": //tangentPoint start Y
                        code22(tangentPoints, aryLines, i);
                        break;
                    case " 13": //tangentPoint END X
                        code13(tangentPoints, aryLines, i);
                        break;
                    case " 23": //tangentPoint END Y
                        code23(tangentPoints, aryLines, i);
                        break;
                    case "1001":
                        currentAppName = aryLines[i + 1].trim();
                        entity.getExtendedData().putIfAbsent(currentAppName, new LinkedHashMap<>());
                        break;
                    case "1000":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("String", aryLines[i + 1].trim());
                        break;
                    case "1002":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("ControlString", aryLines[i + 1].trim());
                        break;
                    case "1003":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("LayerName", aryLines[i + 1].trim());
                        break;
                    case "1004":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("BinaryData", aryLines[i + 1].trim());
                        break;
                    case "1005":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("Handle", aryLines[i + 1].trim());
                        break;
                    case "1010":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("PointX", aryLines[i + 1].trim());
                        break;

                    case "1011":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("PointY", aryLines[i + 1].trim());
                        break;

                    case "1012":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("PointZ", aryLines[i + 1].trim());
                        break;

                    case "1040":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("RealValue1", aryLines[i + 1].trim());
                        break;

                    case "1041":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("RealValue2", aryLines[i + 1].trim());
                        break;

                    case "1042":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("RealValue3", aryLines[i + 1].trim());
                        break;

                    case "1070":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("ShortInt", aryLines[i + 1].trim());
                        break;

                    case "1071":
                        entity.getExtendedData()
                                .computeIfAbsent(currentAppName, k -> new LinkedHashMap<>())
                                .put("LongInt", aryLines[i + 1].trim());
                        break;
                    case "  0":  //finish current entity
                        entity.setCoords(coords);
                        entity.setFitPoints(fitPoints);
                        entity.setTextAlignmentPoint(alignmentPoints);
                        entity.setEllipseAxis(EllipseAxis);
                        fixColor(entity);
                        break innerloop;
                }

                entities.setI(i); //set currentline for com.Matthas.main loop too
            }
        }
        catch (Exception e) {
            System.out.println("error at dxf line:"+ i + " " + aryLines[i]);
        }

        return entity;
    }

    private void readEntityName(Entity entity, String[] aryLines, int i) {
        if (entity.getBlockname().equals("MTEXT")) {
            if (entity.getTextVal() != null) {
                entity.setTextVal(entity.getTextVal() + aryLines[i + 1]);
            } else {
                entity.setTextVal(aryLines[i + 1]);
            }
        } else {
            entity.setName3(aryLines[i + 1]);
        }
    }
    //look for block name
    public void FindBlockName(Entity entity){
        Tables.BlockRecord BlockRef;
        Tables.BlockRecord BlockRef2;
        for (String key : Tables.getBlockRecord().keySet()) {
            BlockRef = Tables.getBlockRecord().get(key);
            if (BlockRef.getName() == null) {
                continue;
            }
            if (BlockRef.getAlias() == null) {
                continue;
            }
            if (BlockRef.getName().equals(entity.getName())) {
                String correctName = Tables.getBlockRecord().get(BlockRef.getAlias()).getName();
                entity.setName(correctName);
            }
        }
    }
    public String left(String input, int length) {
        if (length <0){
            throw new IllegalArgumentException("Left function - Length cannot be less than 0");
        }
        return input.substring(0, Math.min(length, input.length()));
    }

    private void fixColor(Entity entity){
        //if color is null set it to Bylayer = 256
        if (entity.getColour() == null){
            entity.setColour(256);
        }
    }

    private void code10(Coords coords,String[] aryLines, int i) {
        //now this can be summed to just one line, but I was trying to cover case where 10 20 30 are in random order
        //however it only has chance to appear when dxf is created by some boogus software, so it's now useless
        if (coords.size() > 0) {
            if (coords.getrawNCoordX(coords.size()-1) == 0) {
                coords.replaceCoordsXY(coords.size()-1,Double.parseDouble(aryLines[i+1]),coords.getrawNCoordY(coords.size()-1));
            } else {
                coords.addCoords(Double.parseDouble(aryLines[i + 1]), 0);
            }
        } else {
            coords.addCoords(Double.parseDouble(aryLines[i + 1]), 0);
        }
    }

    //similar to code10 but I gave up in middle
    private void code11(Entity entity, Coords coords, Coords fitPoints, String[] aryLines, int i, Coords alignmentPoints, Coords ellipseAxis){
        if (entity.getBlockname().equals("LINE")) {
            coords.addCoords(Double.parseDouble(aryLines[i + 1]), 0);
        } else if (entity.getBlockname().equals("SPLINE")){
            fitPoints.addCoords(Double.parseDouble(aryLines[i+1]), 0);
            //entity.getFitPoints().replaceCoordsXY(coords.size()-1 , Double.parseDouble(aryLines[i+1]), coords.getrawNCoordY(coords.size()-1));
            entity.setFitPoints(fitPoints);
        } else if (entity.getBlockname().equals("Text") || entity.getBlockname().equals("MText")) {
            alignmentPoints.addCoords(Double.parseDouble(aryLines[i+1]), Double.parseDouble(aryLines[i+3]));
        } else if (entity.getBlockname().equals("ELLIPSE")){
            ellipseAxis.addCoords(Double.parseDouble(aryLines[i+1]), Double.parseDouble(aryLines[i+3]));
        }
    }

    private void code12(Coords tangentPoints, String[] aryLines, int i) {
        double x = Double.parseDouble(aryLines[i + 1]);
        if (tangentPoints.size() > 0) {
            tangentPoints.replaceCoordsXY(0, x, tangentPoints.getrawNCoordY(0));
        } else {
            tangentPoints.addCoords(x, 0); // Start tangent at index 0
        }
    }

    private void code13(Coords tangentPoints, String[] aryLines, int i) {
        double x = Double.parseDouble(aryLines[i + 1]);
        if (tangentPoints.size() > 1) {
            tangentPoints.replaceCoordsXY(1, x, tangentPoints.getrawNCoordY(1));
        } else if (tangentPoints.size() == 1) {
            tangentPoints.addCoords(x, 0); // End tangent at index 1
        } else {
            tangentPoints.addCoords(0, 0); // Ensure index 0 exists
            tangentPoints.addCoords(x, 0);
        }
    }

    private void code20(Coords coords, String[] aryLines, int i){
        if (coords.size() > 0) {
            if (coords.getrawNCoordY(coords.size() - 1) == 0 ) {
                coords.replaceCoordsXY(coords.size()-1, coords.getrawNCoordX(coords.size()-1), Double.parseDouble(aryLines[i+1]));
            } else {
                coords.addCoords(0, Double.parseDouble(aryLines[i + 1]));
            }
        } else {
            coords.addCoords(0, Double.parseDouble(aryLines[i + 1]));
        }
    }

    private void code21(Entity entity, Coords coords, Coords fitPoints, String[] aryLines, int i, Coords alignmentPoints) {
        if (entity.getBlockname().equals("LINE")) {
            coords.addCoords(coords.getrawNCoordX(coords.size()-1), Double.parseDouble(aryLines[i + 1]));
        } else if (entity.getBlockname().equals("SPLINE")){
            fitPoints.addCoords(fitPoints.getrawNCoordX(fitPoints.size()-1), Double.parseDouble(aryLines[i+1]));
            //entity.getFitPoints().replaceCoordsXY(coords.size()-1 , Double.parseDouble(aryLines[i+1]), coords.getrawNCoordY(coords.size()-1));
            entity.setFitPoints(fitPoints);
        } //else if (entity.getBlockname().equals("Text") || entity.getBlockname().equals("MText")) {
           //no need, covered under code 11
        //}
    }

    private void code22(Coords tangentPoints, String[] aryLines, int i) {
        double y = Double.parseDouble(aryLines[i + 1]);
        if (tangentPoints.size() > 0) {
            tangentPoints.replaceCoordsXY(0, tangentPoints.getrawNCoordX(0), y);
        } else {
            tangentPoints.addCoords(0, y); // Start tangent at index 0
        }
    }

    private void code23(Coords tangentPoints, String[] aryLines, int i) {
        double y = Double.parseDouble(aryLines[i + 1]);
        if (tangentPoints.size() > 1) {
            tangentPoints.replaceCoordsXY(1, tangentPoints.getrawNCoordX(1), y);
        } else if (tangentPoints.size() == 1) {
            tangentPoints.addCoords(0, y); // End tangent at index 1
        } else {
            tangentPoints.addCoords(0, 0); // Ensure index 0 exists
            tangentPoints.addCoords(0, y);
        }
    }
    private void code40(Entity entity, String[] aryLines, int i) {
        switch (entity.getBlockname()) {
            case "LWPOLYLINE":
            case "POLYLINE":
            //case "LINE"://line does not use code 40
                entity.setWidth(Double.parseDouble(aryLines[i+1].trim()));
            break;
            case "TEXT":
            case "MTEXT":
                entity.setTextHeight(Double.parseDouble(aryLines[i+1].trim()));
            break;
            case "CIRCLE":
            case "ARC":
                entity.setRadius(Double.parseDouble(aryLines[i+1].trim()));
            break;
            case "DIMENSION":
                entity.setDimension(Double.parseDouble(aryLines[i+1].trim()));
                break;
            //case "VERTEX": //vertex does not appear as standalone element
            case "SPLINE":
                entity.addKnot(Double.parseDouble(aryLines[i+1].trim()));
            break;
            case "HATCH":
                entity.setPatternScale(Double.parseDouble(aryLines[i+1].trim()));
            break;
            case "LEADER":
                entity.setArrowSize(Double.parseDouble(aryLines[i+1].trim()));
                break;
            case "VIEWPORT":
                entity.setViewHeight(Double.parseDouble(aryLines[i+1].trim()));
                break;
            case "DIMSTYLE":
                entity.setTextHeight(Double.parseDouble(aryLines[i+1].trim()));
                break;
            case "TABLE":
                entity.setCellHeight(Double.parseDouble(aryLines[i+1].trim()));
                break;
            case "ELLIPSE":
                entity.setAxisRatio(Double.parseDouble(aryLines[i+1].trim()));
                break;
        }
    }

    private void code41(Entity entity, String[] aryLines, int i) {
        switch (entity.getBlockname()) {
            case "INSERT":
                entity.setScaleX(Double.parseDouble(aryLines[i+1].trim()));
                break;
            case "POLYLINE":
            case "LWPOLYLINE":
            case "VERTEX":
                entity.setEndWidth(Double.parseDouble(aryLines[i+1].trim()));
                break;
            case "ELLIPSE":
                entity.setStartParameter(Double.parseDouble(aryLines[i+1].trim()));
                break;
            case "SPLINE":
                entity.addFitParameter(Double.parseDouble(aryLines[i+1].trim()));
                break;
            case "DIMSTYLE":
                entity.setExtensionLength(Double.parseDouble(aryLines[i+1].trim()));
                break;
            case "Text":
            case "MTEXT":
                entity.setTextWidthFactor(Double.parseDouble(aryLines[i+1].trim()));
                break;
        }
    }

    private void code42(Entity entity, String[] aryLines, int i){
        switch (entity.getBlockname()) {
            case "INSERT":
                entity.setScaleY(Double.parseDouble(aryLines[i+1].trim()));
                break;
            case "VERTEX":
            case "LWPOLYLINE":
                entity.setBulge(Double.parseDouble(aryLines[i+1].trim())); // arc bulge factor
                break;
            case "ELLIPSE":
                entity.setEndParameter(Double.parseDouble(aryLines[i+1].trim())); // radians
                break;
            case "SPLINE":
                entity.addFitParameter(Double.parseDouble(aryLines[i+1].trim())); // if applicable
                break;
        }
    }

    private void code50(Entity entity, String[] aryLines, int i){
        entity.setRotation(Double.parseDouble(aryLines[i + 1].trim()));
        switch (entity.getBlockname()) {
            case "ARC":
                entity.setStartAngle(Double.parseDouble(aryLines[i+1].trim())); // degrees
                break;
            case "TEXT":
            case "MTEXT":
            case "DIMENSION":
            case "INSERT":
                entity.setRotation(Double.parseDouble(aryLines[i+1].trim())); // rotation angle in degrees
                break;
            case "ELLIPSE":
                entity.setStartParameter(Double.parseDouble(aryLines[i+1].trim())); // radians
                break;
        }
    }

    private void code51(Entity entity, String[] aryLines, int i) {
        switch (entity.getBlockname()) {
            case "ARC":
                entity.setEndAngle(Double.parseDouble(aryLines[i + 1].trim())); // degrees
                break;
            case "ELLIPSE":
                entity.setEndParameter(Double.parseDouble(aryLines[i + 1].trim())); // radians
                break;
            case "DIMENSION":
                entity.setEndAngle(Double.parseDouble(aryLines[i + 1].trim())); // if used for arc dimension
                break;
            case "Text":
            case "MTEXT":
                entity.setObliqueAngle(Double.parseDouble(aryLines[i+1].trim()));
                break;
        }
    }

}
