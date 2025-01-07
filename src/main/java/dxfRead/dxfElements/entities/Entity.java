package dxfRead.dxfElements.entities;

import dxfRead.dxfElements.coords.Coords;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Entity {
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
