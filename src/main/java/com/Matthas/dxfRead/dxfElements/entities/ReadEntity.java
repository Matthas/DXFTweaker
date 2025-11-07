package com.Matthas.dxfRead.dxfElements.entities;

import com.Matthas.dxfRead.dxfElements.coords.Coords;
import com.Matthas.dxfRead.dxfElements.tables.Tables;

import java.util.LinkedHashMap;

public class ReadEntity {

    //function to read Entity data and return is back to com.Matthas.main function Entities
    public Entity readEntity(Integer currentline, Integer length, String[] aryLines, Entity entity, Entities entities) {
        Coords coords = new Coords();
        Coords fitPoints = new Coords();
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
                    case "420":
                        entity.setColour(Integer.parseInt(aryLines[i + 1].trim()));
                        break;
                    case "440":
                        entity.setTransparency(Double.parseDouble(aryLines[i+1].trim()));
                        break;
                    case " 10": //coords
                        coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                        entity.setCoords(coords);
                        break;
                    case " 11": //endpoints (I think)
                        if (entity.getBlockname().equals("LINE")) {
                            //Coords coords = new Coords();
                            coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                            entity.setCoords(coords);
                        } else if (entity.getBlockname().equals("SPLINE")){
                            fitPoints.addCoords(Double.parseDouble(aryLines[i+1]), 0);
                        }
                        break;
                    case " 12": //coords
                        if (entity.getBlockname().equals("SPLINE")){
                            fitPoints.addCoords(Double.parseDouble(aryLines[i+1]), 0);
                            entity.getFitPoints().replaceCoordsXY(coords.size()-1 , coords.getrawNCoordX(coords.size()-1), Double.parseDouble(aryLines[i+1]));
                            entity.setFitPoints(fitPoints);
                        } else {
                            coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                            entity.setCoords(coords);
                        }
                        break;
                    case " 13": //coords
                        coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                        entity.setCoords(coords);
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
        }
    }

}
