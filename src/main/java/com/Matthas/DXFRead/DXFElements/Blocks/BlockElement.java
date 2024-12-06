package DXFRead.DXFElements.Blocks;

import DXFRead.DXFElements.Coords.Coords;
import FileHandlers.DXFLoad;

public class BlockElement {
    private String BlockName;
    private String handle;
    private String ParentHandle;
    private String SoftPointer;
    private String Layer;
    private String LineType;
    private String ChildHandle;
    private Double Transparency;
    private int Brightness;
    private Coords coords;
    private String Name;
    private String Val;
    private int Colour;
    private int PolylineFlag;
    private int Visibility;
    private Integer LineIndex;
    private Integer DXFIndex;

    public Integer getDXFIndex() {
        return DXFIndex;
    }
    public void setDXFIndex(Integer DXFIndex) {
        this.DXFIndex = DXFIndex;
    }
    public int getVisibility() {return Visibility;}
    public void setVisibility(int visibility) {Visibility = visibility;}
    public int getPolylineFlag() {return PolylineFlag;}
    public void setPolylineFlag(int polylineFlag) {PolylineFlag = polylineFlag;}
    public Coords getCoords() {
        return coords;
    }
    public void setCoords(Coords coords) {
        this.coords = coords;
    }
    public int sizeCoords() {
        return coords.size();
    }
    public String getChildHandle() {
        return ChildHandle;
    }
    public void setChildHandle(String childHandle) {
        ChildHandle = childHandle;
    }
    public int getColour() {return Colour;}
    public void setColour(int colour) {
        Colour = colour;
    }
    public String getSoftPointer() {
        return SoftPointer;
    }
    public void setSoftPointer(String softPointer) {
        SoftPointer = softPointer;
    }
    public String getBlockName() {
        return BlockName;
    }
    public void setBlockName(String blockname) {
        BlockName = blockname;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getVal() {
        return Val;
    }
    public void setVal(String val) {
        Val = val;
    }
    public String getHandle() {
        return handle;
    }
    public void setHandle(String handle) {
        this.handle = handle;
    }
    public String getParentHandle() {
        return ParentHandle;
    }
    public void setParentHandle(String parentHandle) {
        ParentHandle = parentHandle;
    }
    public String getLayer() {return Layer;}
    public void setLayer(String layer) {
        Layer = layer;
    }
    public String getLineType() {
        return LineType;
    }
    public void setLineType(String lineType) {
        LineType = lineType;
    }
    public Double getTransparency() {
        return Transparency;
    }
    public void setTransparency(Double transparency) {
        Transparency = transparency;
    }
    public int getBrightness() {
        return Brightness;
    }
    public void setBrightness(int brightness) {
        Brightness = brightness;
    }
    public Integer getLineIndex() {
        return LineIndex;
    }
    public void setLineIndex(Integer lineIndex) {
        LineIndex = lineIndex;
    }


}
