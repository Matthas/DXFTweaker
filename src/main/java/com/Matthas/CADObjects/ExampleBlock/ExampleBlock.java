package CADObjects.ExampleBlock;

import DXFRead.DXFElements.Blocks.BlockElement;
import DXFRead.DXFElements.Coords.Coords;
import DXFRead.DXFElements.Entities.Entities;
import java.util.LinkedHashMap;
import java.util.Map;


//This is example class of how to build one for your CAD Blocks

public class ExampleBlock {
    private String Handle;
    private String Ref; //this would be unique name of your block (as in attribute of block)
    private String BlockName;  //this would be your block name
    private Integer Color;  //color as int, DXF has unusual format of saving color as index or as RGB converted to DEX (not HEX)
    private Coords coords;
    private double ScaleX;
    private double ScaleY;
    private String Attribute1;  //attribute of your block (visible in Properties window at bottom), duplicate for other attributes
    //Hashmap used to store easy read, store & write attributes from DXF
    private Map<String, BlockElement> BlockElements = new LinkedHashMap<>();

    public String getHandle() {return Handle;}
    public void setHandle(String handle) {Handle = handle;}
    public String getRef() {return Ref;}
    public void setRef(String ref) {Ref = ref;}
    public String getBlockName() {return BlockName;}
    public void setBlockName(String blockName) {BlockName = blockName;}
    public Integer getColor() {return Color;}
    public void setColor(Integer color) {Color = color;}
    public Coords getCoords() {return coords;}
    public void setCoords(Coords coords) {this.coords = coords;}
    public double getScaleX() {return ScaleX;}
    public void setScaleX(double scaleX) {ScaleX = scaleX;}
    public double getScaleY() {return ScaleY;}
    public void setScaleY(double scaleY) {ScaleY = scaleY;}
    public String getAttribute1() {return Attribute1;}
    public void setAttribute1(String attribute1) {Attribute1 = attribute1;}

    //this is function to populate all attributes of your block.
    public void ExampleBlockAttribs(ExampleBlock exampleBlock, Entities.Entity attObject) {
        switch (attObject.getName()) {
            //one case for one attribute, case-sensitive
            //each attribute will need its own variable (or you can do Hashmap of variables)
            case "ATTRIBUTE1":
                exampleBlock.setAttribute1(attObject.getTextVal());
                break;//ATTRIBUTE1
            default:
                break;
        }
    }
}
