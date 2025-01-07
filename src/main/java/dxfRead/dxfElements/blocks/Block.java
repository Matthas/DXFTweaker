package dxfRead.dxfElements.blocks;

import dxfRead.dxfElements.coords.Coords;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Block {
    private String Handle;
    private String Layer;
    private String SecondHandle;
    private int Colour;
    private String ParentHandle;
    private String SubclassMarket;
    private String DynBlockName2;
    private String DynBlockName3;
    private String Thinkness;
    private Integer DXFIndex;
    Coords coords = new Coords();
    private Map<String, BlockElement> BlockElements = new LinkedHashMap<>();

    public Map<String, BlockElement> getBlockElements() {
        return BlockElements;
    }
    public void addBlockElements(BlockElement blockElement) {
        BlockElements.put(blockElement.getHandle(), blockElement);
    }
    public BlockElement getBlockElements(String handle) {
        return BlockElements.get(handle);
    }
    public Integer getDXFIndex() {
        return DXFIndex;
    }
    public void setDXFIndex(Integer DXFIndex) {
        this.DXFIndex = DXFIndex;
    }
    public Set<String> getKeySetBlockElements() {
        return BlockElements.keySet();
    }
    public int getColor() {
        return Colour;
    }
    public void setColour(int colour) {
        Colour = colour;
    }
    public String getLayer() {
        return Layer;
    }
    public void setLayer(String layer) {
        Layer = layer;
    }
    public Coords getCoords() {
        return coords;
    }
    public void setCoords(Coords coords) {
        this.coords = coords;
    }
    public String getHandle() {
        return Handle;
    }

    public void setHandle(String handle) {
        if (Handle == null) {
            Handle = handle;
        } else {
            SecondHandle = handle;
        }
    }

    public String getParentHandle() {
        return ParentHandle;
    }
    public void setParentHandle(String parentHandle) {
        ParentHandle = parentHandle;
    }
    public String getSubclassMarket() {
        return SubclassMarket;
    }
    public void setSubclassMarket(String subclassMarket) {
        SubclassMarket = subclassMarket;}
    public String getDynBlockName2() {return DynBlockName2;}
    public void setDynBlockName2(String dynBlockName2) {DynBlockName2 = dynBlockName2;}
    public String getDynBlockName3() {return DynBlockName3;}
    public void setDynBlockName3(String dynBlockName3) {DynBlockName3 = dynBlockName3;}
    public String getThinkness() {return Thinkness;}
    public void setThinkness(String thinkness) {Thinkness = thinkness;}
}