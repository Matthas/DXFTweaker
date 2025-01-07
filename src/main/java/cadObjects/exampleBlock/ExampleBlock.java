package cadObjects.exampleBlock;

import dxfRead.DXFDrawing;
import dxfRead.dxfElements.blocks.Block;
import dxfRead.dxfElements.blocks.BlockElement;
import dxfRead.dxfElements.coords.Coords;
import dxfRead.dxfElements.entities.Entity;
import dxfRead.dxfElements.tables.Tables;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static cadObjects.CADObjectsReader.deleteThings;
import static dxfRead.dxfElements.entities.Entities.EntitiesMap;


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
    //Hashmap used to easy read, store & write attributes from DXF
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
    public Map<String, BlockElement> getBlockElements() {
        return BlockElements;
    }
    public void setBlockElements(Map<String, BlockElement> blockElements) {
        BlockElements = blockElements;
    }

    //this is function to populate all attributes of your block.
    public void ExampleBlockAttribs(ExampleBlock exampleBlock, Entity attObject) {
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
    public static void CreateExampleBlock(Entity insert, ExampleBlock exampleBlock, DXFDrawing DXF) {
        String handle = insert.getHandle();
        exampleBlock.setHandle(insert.getHandle());
        exampleBlock.setCoords(insert.getCoords());
        if (insert.getScaleX() == null ) {
            exampleBlock.setScaleX(1);
        }else {
            exampleBlock.setScaleX(insert.getScaleX());
        }
        if (insert.getScaleY() == null) {
            exampleBlock.setScaleY(1);
        } else {
            exampleBlock.setScaleY(insert.getScaleY());
        }
        Map<String, String> todelete = new HashMap<>();

        CreateExampleBlockGetAttributes(exampleBlock,handle,DXF, todelete);

        CreateExampleBlockFindBlock(exampleBlock, handle, DXF);

        deleteThings(EntitiesMap,todelete);
    }

    private static void CreateExampleBlockGetAttributes(ExampleBlock exampleBlock, String handle, DXFDrawing DXF, Map<String, String> todelete ) {
        for (String keyATT : EntitiesMap.keySet()) {
            Entity entity = DXF.getEntities().getEntities().get(keyATT);
            if (handle.equals(entity.getParentHandle())) {
                //call DP function to get Attributes called by key of object matching its parenthandle
                exampleBlock.ExampleBlockAttribs(exampleBlock, entity);
                //add to the list to delete later
                todelete.put(entity.getHandle(), entity.getHandle()); //remove object after using it so next loop is a little faster
            }
        }
    }
    private static void CreateExampleBlockFindBlock(ExampleBlock exampleBlock, String handle, DXFDrawing DXF) {
        for (String keyBlockRecords : DXF.getTables().getBlockRecord().keySet()){
            Tables.BlockRecord blockRecord = DXF.getTables().getBlockRecord().get(keyBlockRecords);
            String BlockRecordHandle = null;
            //System.out.println("BRH "+ blockRecord.getHandle());
            if (blockRecord.getChildHandles().size()== 0){
                continue;
            }
            for (int i = 1; i <= blockRecord.getChildHandles().size(); i++) {
                //find block_record with handle of our insert
                if ((blockRecord.getChildHandle(i).equals(handle))) {
                    BlockRecordHandle = blockRecord.getHandle();
                    //now find Block that has Parent Handle = block_record handle
                    CreateExampleBlockFindBlockRecord(exampleBlock,BlockRecordHandle, DXF);
                }
            }
        }
    }
    private static void CreateExampleBlockFindBlockRecord(ExampleBlock exampleBlock, String BlockRecordHandle,DXFDrawing DXF) {
        for (String keyBlock : DXF.getBlocks().getBlocks().keySet()) {
            //iterate through Blocks ATTDEF, SOLID, WIPEOUT
            Block block = DXF.getBlocks().getBlocks().get(keyBlock);
            //find blockRecord by its parentHandle
            if (BlockRecordHandle.equals(block.getParentHandle())){
                for (String keyBlockElement : block.getBlockElements().keySet()) {
                    exampleBlock.setBlockElements(block.getBlockElements());
                }
            }
        }
    }
}
