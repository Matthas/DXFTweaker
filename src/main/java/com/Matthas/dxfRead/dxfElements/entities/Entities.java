package com.Matthas.dxfRead.dxfElements.entities;

import com.Matthas.fileHandlers.DXFLoad;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Entities {
    public int i;
    public static Map<String, Entity> EntitiesMap = new LinkedHashMap<>();
    public static Map<String, Entity> BlockCollection = new LinkedHashMap<>();
    public static Map<String, String> ATTList = new LinkedHashMap<>();
    public static Map<String, Entity> Inserts = new LinkedHashMap<>();
    //Map<String, Entity> Vertexes = new HashMap<>();
    private static int endSecIndex;
    public static Map<String, Entity> getEntitiesMap() {
        return EntitiesMap;
    }
    public Map<String, Entity> getInserts() {
        return Inserts;
    }
    public Map<String, String> getATTList() {
        return ATTList;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public Entities (Integer currentline, Integer length, DXFLoad file) throws IOException {
        String[] aryLines = file.OpenFile();
        ReadEntity readEntity = new ReadEntity();
        loop: for (this.i = currentline; i < length ; i++) {
            //System.out.println(i);
            switch (aryLines[i]) {
                //Coords coords = new Coords();
                case "POLYLINE":
                    Entity polyline = new Entity();
                    polyline.setBlockname("POLYLINE");
                    polyline.setDXFIndex(i);
                    readEntity.readEntity(i,length, aryLines, polyline, this);
                    //check entity before adding to collection
                    if ((polyline.getPolylineFlag() == 1) || (polyline.getPolylineFlag() == 129)) {
                        //make end vertex same as first vertex for closed polylines
                        polyline.coords.addCoords(polyline.coords.getrawNCoordX(0), polyline.coords.getrawNCoordY(0));
                    }
                    //check for duplicated coordinates
                    polyline.setErrorString(polyline.coords.trimAndReturnDuplicates());
                    //add to the hashmap
                    if (polyline.getHandle() != null){
                        EntitiesMap.put(polyline.getHandle(), polyline);
                    }
                    break; //polyline
                case "VERTEX":
                    Entity vertex = new Entity();
                    vertex.setBlockname("VERTEX");
                    vertex.setDXFIndex(i);
                    readEntity.readEntity(i,length, aryLines, vertex, this);
                    if (vertex.getHandle() != null){
                        EntitiesMap.put(vertex.getHandle(), vertex);
                    }
                    break; //vertex
                case "LINE":
                    Entity line = new Entity();
                    line.setBlockname("LINE");
                    line.setDXFIndex(i);
                    readEntity.readEntity(i,length, aryLines, line, this);
                    //line cannot have more than 2 vertexes, do not do duplicate check, cannot be closed
                    if (line.getHandle() != null){
                        EntitiesMap.put(line.getHandle(), line);
                    }
                    break; // LINE
                case "LWPOLYLINE":
                    Entity lwpolyline = new Entity();
                    lwpolyline.setBlockname("LWPOLYLINE");
                    lwpolyline.setDXFIndex(i);
                    readEntity.readEntity(i,length, aryLines, lwpolyline, this);
                    //check entity before adding to collection
                    if ((lwpolyline.getPolylineFlag() == 1) || (lwpolyline.getPolylineFlag() == 129)) {
                        //make end vertex same as first vertex for closed polylines
                        lwpolyline.coords.addCoords(lwpolyline.coords.getrawNCoordX(0), lwpolyline.coords.getrawNCoordY(0));
                    }
                    //check for duplicated coordinates
                    //add to the hashmap
                    if (lwpolyline.getHandle() != null){
                        EntitiesMap.put(lwpolyline.getHandle(), lwpolyline);
                    }
                    break; //LWPOLYLINE
                case "INSERT":
                    Entity insert = new Entity();
                    insert.setBlockname("INSERT");
                    insert.setDXFIndex(i);
                    readEntity.readEntity(i,length, aryLines, insert, this);
                    if (insert.getColour() == null ){
                        insert.setColour(256); //if there is no color it means its ByLayer
                        //ByBlock(0) is not valid for inserts, its only valid for elemnts that make up Blocks
                    }
                    Inserts.put(insert.getHandle(), insert);
                    break; //INSERT
                case "ATTRIB":
                    Entity attrib = new Entity();
                    attrib.setBlockname("ATTRIB");
                    attrib.setDXFIndex(i);
                    readEntity.readEntity(i,length,aryLines,attrib, this);
                    ATTList.put(attrib.getName(),attrib.getTextVal()); //create list of [key]attributes and its values
                    attrib.ATTribs = ATTList;  //created list add to Entities hashmap
                    attrib.ATTribEnt.put(attrib.getName(),attrib);  //store attlist as child block in entity class
                    EntitiesMap.put(attrib.getHandle(), attrib);
                    break; //ATTRIB
                case "CIRCLE":
                    Entity circle = new Entity();
                    circle.setBlockname("CIRCLE");
                    circle.setDXFIndex(i);
                    readEntity.readEntity(i,length,aryLines,circle, this);
                    EntitiesMap.put(circle.getHandle(), circle);
                    break; //CIRCLE
                case "ELLIPSE":
                    Entity ellipse = new Entity();
                    ellipse.setBlockname("ELLIPSE");
                    ellipse.setDXFIndex(i);
                    readEntity.readEntity(i,length,aryLines, ellipse, this);
                    EntitiesMap.put(ellipse.getHandle(), ellipse);
                    break; //ELLIPSE
                case "HATCH":
                    Entity hatch = new Entity();
                    hatch.setBlockname("HATCH");
                    hatch.setDXFIndex(i);
                    readEntity.readEntity(i,length,aryLines, hatch, this);
                    EntitiesMap.put(hatch.getHandle(), hatch);
                    break; //HATCH
                case "SOLID":
                    Entity solid = new Entity();
                    solid.setBlockname("SOLID");
                    solid.setDXFIndex(i);
                    readEntity.readEntity(i,length,aryLines, solid, this);
                    EntitiesMap.put(solid.getHandle(), solid);
                    break; //SOLID
                case "WIPEOUT":
                    Entity wipeout = new Entity();
                    wipeout.setBlockname("WIPEOUT");
                    wipeout.setDXFIndex(i);
                    readEntity.readEntity(i,length,aryLines, wipeout, this);
                    EntitiesMap.put(wipeout.getHandle(), wipeout);
                    break; //WIPEOUT
                case "TEXT":
                    Entity text = new Entity();
                    text.setBlockname("TEXT");
                    text.setDXFIndex(i);
                    readEntity.readEntity(i,length,aryLines, text, this);
                    EntitiesMap.put(text.getHandle(), text);
                    break; //TEXT
                case "MTEXT":
                    Entity mtext = new Entity();
                    mtext.setBlockname("MTEXT");
                    mtext.setDXFIndex(i);
                    readEntity.readEntity(i,length,aryLines, mtext, this);
                    EntitiesMap.put(mtext.getHandle(), mtext);
                    break; //MTEXT
                case "POINT":
                    Entity point = new Entity();
                    point.setBlockname("POINT");
                    point.setDXFIndex(i);
                    readEntity.readEntity(i,length,aryLines, point, this);
                    EntitiesMap.put(point.getHandle(), point);
                    break; //POINT
                case "IMAGE":
                    Entity image = new Entity();
                    image.setBlockname("IMAGE");
                    image.setDXFIndex(i);
                    readEntity.readEntity(i,length,aryLines, image, this);
                    EntitiesMap.put(image.getHandle(), image);
                    break; //IMAGE
//                case "BLOCK":
//                    Entity block = new Entity();
//                    block.setBlockname("BLOCK");
//                    readEntity(i, length,file,aryLines,block);
//                    EntitiesMap.put(block.getHandle(),block);
//                    break; // block
                case "ENDSEC":
                    endSecIndex = i;
                    break loop;
            }
        }
    }


    public static int getEndSecIndex() {
        return endSecIndex;
    }

}