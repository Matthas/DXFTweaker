package DXFRead.DXFElements.Entities;

import DXFRead.DXFElements.Coords.*;
import FileHandlers.DXFLoad;
import java.io.IOException;
import java.util.HashMap;
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
    public Map<String, Entity> getEntities() {
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

    public static class Entity {
        private String Blockname;
        private String Handle;
        private String LineType;
        private String Layer;
        private String Type;
        private String Background;
        private Integer Colour;
        private int Space;
        private String Name;
        private String Name3;
        private Double Thickness;
        private Double Width;
        private Double Transparency;
        private Double LineTypeScale;
        private String Justify;
        private String ParentHandle;
        private String SoftPointer;
        private String TextVal;
        private Double Rotation;
        private Double TextHeight;
        private String TextStyle;
        private int PolylineFlag;
        private String EntityType;
        Coords coords = new Coords();
        private String ErrorString;
        private Double ScaleX;
        private Double ScaleY;
        private Integer LineIndex;
        private Integer BackgroundColor;
        private Integer DXFIndex;
        Map<String, String> ATTribs = new HashMap<>();
        Map<String, Entity> ATTribEnt = new HashMap<>();
        Map<String, String> Attributes = new LinkedHashMap<>();

        public Integer getDXFIndex() {return DXFIndex;}
        public void setDXFIndex(Integer DXFIndex) {this.DXFIndex = DXFIndex;}
        public Map<String, String> getAttributes() {return Attributes;}

        public Integer getBackgroundColor() {return BackgroundColor;}
        public void setBackgroundColor(Integer backgroundColor) {BackgroundColor = backgroundColor;}
        public String getBackground() {return Background;}
        public void setBackground(String background) {Background = background;}
        public String getType() {return Type;}
        public void setType(String type) {Type = type;}
        public String getJustify() {return Justify;}
        public void setJustify(String justify) {Justify = justify;}
        public String getTextStyle() {return TextStyle;}
        public void setTextStyle(String textStyle) {TextStyle = textStyle;}
        public Double getTextHeight() {return TextHeight;}
        public void setTextHeight(Double textHeight) {TextHeight = textHeight;}
        public String getBlockname() {return Blockname;}
        public void setBlockname(String blockname) {Blockname = blockname;}
        public Double getLineTypeScale() {return LineTypeScale;}
        public void setLineTypeScale(Double lineTypeScale) {LineTypeScale = lineTypeScale;}
        public Double getTransparency() {return Transparency;}
        public void setTransparency(Double transparency) {Transparency = transparency;}
        public Double getScaleX() {return ScaleX;}
        public void setScaleX(Double scaleX) {ScaleX = scaleX;}
        public Double getScaleY() {return ScaleY;}
        public void setScaleY(Double scaleY) {ScaleY = scaleY;}
        public String getName3() {
            return Name3;
        }
        public void setName3(String name3) {
            Name3 = name3;
        }
        public String getErrorString() {
            return ErrorString;
        }
        public void setErrorString(String errorString) {
            ErrorString = errorString;
        }
        public Coords getCoords() {
            return coords;
        }
        public void setCoords(Coords coords) {
            this.coords = coords;
        }
        public String getLayer() {return Layer;}
        public void setLayer(String layer) {
            Layer = layer;
        }
        public Integer getColour() {
            return Colour;
        }
        public void setColour(Integer colour) {
            Colour = colour;
        }
        public int getSpace() {
            return Space;
        }
        public void setSpace(int space) {
            Space = space;
        }
        public Double getThickness() {
            return Thickness;
        }
        public void setThickness(double thickness) {
            Thickness = thickness;
        }
        public Double getWidth() {
            return Width;
        }
        public void setWidth(double width) {
            Width = width;
        }
        public String getParentHandle() {
            return ParentHandle;
        }
        public void setParentHandle(String parentHandle) {
            ParentHandle = parentHandle;
        }
        public String getSoftPointer() {
            return SoftPointer;
        }
        public void setSoftPointer(String softPointer) {
            SoftPointer = softPointer;
        }
        public String getTextVal() {
            return TextVal;
        }
        public void setTextVal(String textVal) {
            TextVal = textVal;
        }
        public Double getRotation() {
            return Rotation;
        }
        public void setRotation(Double rotation) {
            Rotation = rotation;
        }
        public Integer getPolylineFlag() {
            return PolylineFlag;
        }
        public void setPolylineFlag(int polylineFlag) {PolylineFlag = polylineFlag;}
        public String getEntityType() {return EntityType;}
        public void setEntityType(String entityType) {EntityType = entityType;}
        public String getLineType() {return LineType;}
        public void setLineType(String lineType) {LineType = lineType;}
        public String getHandle() {return Handle;}
        public void setHandle(String handle) {Handle = handle;}
        public String getName() {return Name;}
        public void setName(String name) {Name = name;}
        public Integer getLineIndex() {return LineIndex;}
        public void setLineIndex(Integer lineIndex) {LineIndex = lineIndex;}
        public void setAttributes(Map<String, String> attributes) {Attributes = attributes;}
        //we split string to attribute with = being delimiter.
        public void addAttribute(String attribute) {
            int equalloc = attribute.indexOf('=');
            if (equalloc > 0 ) {
                String name = attribute.substring(0, equalloc);
                String value = attribute.substring(equalloc + 1);
                Attributes.put(name, value);
            }
        }
    }
}