package DXFRead.DXFElements.Entities;

import DXFRead.DXFElements.Coords.Coords;
import DXFRead.DXFElements.Tables.Tables;

public class ReadEntity {

    //function to read Entity data and return is back to main function Entities
    public Entities.Entity readEntity(Integer currentline, Integer length, String[] aryLines, Entities.Entity entity, Entities entities) {
        Coords coords = new Coords();
        int i = currentline;
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
                            FindBlockName(entity);
                        } else {
                            entity.setName(aryLines[i + 1]);
                        }
                        break;
                    case "  3": //WHY it shares codes
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
                        if (entity.getBlockname().equals("LWPOLYLINE")){
                            entity.setWidth(Double.parseDouble(aryLines[i + 1].trim()));
                        } else {
                            entity.setTextHeight(Double.parseDouble(aryLines[i + 1].trim()));
                        }
                        break;
                    case " 41":
                        if  (!entity.getBlockname().equals("ATTRIB") && !entity.getBlockname().matches(".*LINE")) {
                            entity.setScaleX(Double.parseDouble(aryLines[i + 1].trim()));
                            entity.setScaleY(Double.parseDouble(aryLines[i + 3].trim()));
                        }
                        break;
                    case " 43":
                        entity.setWidth(Double.parseDouble(aryLines[i + 1]));
                        break;
                    case " 48":
                        entity.setLineTypeScale(Double.parseDouble(aryLines[i + 1].trim()));
                        break;
                    case " 50":
                        entity.setRotation(Double.parseDouble(aryLines[i + 1]));
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
                    case "1000":
                        entity.addAtrribute(aryLines[i+1]);
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
                        }
                        break;
                    case " 12": //coords
                        coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                        entity.setCoords(coords);
                        break;
                    case " 13": //coords
                        coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                        entity.setCoords(coords);
                        break;
                    case "1001":
                        break;
                    case "  0":  //finish current entity
                        break innerloop;
                }

                entities.setI(i); //set currentline for main loop too
            }
        }
        catch (Exception e) {
            System.out.println("error at dxf line:"+ i + " " + aryLines[i]);
        }

        return entity;
    }

    private void readEntityName(Entities.Entity entity, String[] aryLines, int i) {
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
    public void FindBlockName(Entities.Entity entity){
        Tables.BlockRecord BlockRef;
        Tables.BlockRecord BlockRef2;
        //for (int f = 0; f < tables.size() ; f++) {
        for (String key : Tables.getBlockRecord().keySet()) {
            BlockRef = Tables.getBlockRecord().get(key);
            //BlockRef = tables.BlockRecord.get(f);
            if (BlockRef.getName() == null) {
                continue;
            }
            if (BlockRef.getName().equals(entity.getName())) {
                for (String key2 : Tables.getBlockRecord().keySet()) {
                    //for (int g = 0; g < tables.size() ; g++) {
                    BlockRef2 = Tables.getBlockRecord().get(key2);

                    if (BlockRef2.getHandle().equals(entity.getHandle())) {
                        entity.setName(BlockRef2.getName());
                        System.out.println(entity.getHandle() + entity.getName());
                    }
                }
            }
        }
    }
    public String left(String input, int length) {
        if (length <0){
            throw new IllegalArgumentException("Left function - Length cannot be less than 0");
        }
        return input.substring(0, Math.min(length, input.length()));
    }
}
