package com.Matthas.cadObjects.CadBlocks;

import com.Matthas.dxfRead.DXFDrawing;
import com.Matthas.dxfRead.dxfElements.blocks.Block;
import com.Matthas.dxfRead.dxfElements.blocks.BlockElement;
import com.Matthas.dxfRead.dxfElements.coords.Coords;
import com.Matthas.dxfRead.dxfElements.entities.Entity;
import com.Matthas.dxfRead.dxfElements.tables.Tables;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.Matthas.cadObjects.CADObjects.deleteThings;
import static com.Matthas.dxfRead.dxfElements.entities.Entities.EntitiesMap;


//This is Main class for all CADObjects. For more adjustment create subClasses for each invidual or group of Blocks.

@Getter
@Setter
public class CADBlock {
    private String Handle;
    private String Ref; //this would be unique name of your block (as in attribute of block)
    private String BlockName;  //this would be your block name
    private Integer Colour;  //color as int, DXF has unusual format of saving color as index or as RGB converted to DEX (not HEX)
    private Coords coords;
    private String Layer;
    private double ScaleX;
    private double ScaleY;
    //Hashmap used to easy read, store & write attributes from DXF
    private Map<String,String> Attributes = new LinkedHashMap<>();
    //Hashmap used to store all elements that make up CADBlock (including attributes)
    private Map<String, BlockElement> BlockElements = new LinkedHashMap<>();

    private void addAttribute(String AttribName, String AttribValue) {
        Attributes.put(AttribName, AttribValue);
    }

    //this is function to populate all attributes of your block.
    public void BlockAttribs(CADBlock cadBlock, Entity attObject) {
        if (attObject.getBlockname().equals("ATTRIB")) {
            cadBlock.addAttribute(attObject.getName(),attObject.getTextVal());
        }
    }

    public static void CreateCADBlock(Entity insert, CADBlock cadBlock, DXFDrawing DXF) {
        String handle = insert.getHandle();
        cadBlock.setHandle(insert.getHandle());
        cadBlock.setCoords(insert.getCoords());
        cadBlock.setLayer(insert.getLayer());
        cadBlock.setColour(insert.getColour());

        if (insert.getScaleX() == null ) {
            cadBlock.setScaleX(1);
        }else {
            cadBlock.setScaleX(insert.getScaleX());
        }
        if (insert.getScaleY() == null) {
            cadBlock.setScaleY(1);
        } else {
            cadBlock.setScaleY(insert.getScaleY());
        }
        //Hashmap to store elements we used, so we can delete later and save loop iteration for next blocks
        Map<String, String> todelete = new HashMap<>();

        GetAttributes(cadBlock,handle,DXF, todelete);

        FindBlock(cadBlock, handle, DXF);

        deleteThings(EntitiesMap,todelete);
    }
    private static void GetAttributes(CADBlock cadBlock, String handle, DXFDrawing DXF, Map<String, String> todelete ) {
        for (String keyATT : EntitiesMap.keySet()) {
            Entity entity = DXF.getEntities().getEntities().get(keyATT);
            if (handle.equals(entity.getParentHandle())) {
                cadBlock.BlockAttribs(cadBlock, entity);
                //add to the list to delete later
                todelete.put(entity.getHandle(), entity.getHandle()); //remove object after using it so next loop is a little faster
            }
        }
    }
    private static void FindBlock(CADBlock cadBlock, String handle, DXFDrawing DXF) {
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
                    FindBlockRecord(cadBlock,BlockRecordHandle, DXF);
                }
            }
        }
    }
    private static void FindBlockRecord(CADBlock cadblock, String BlockRecordHandle, DXFDrawing DXF) {
        for (String keyBlock : DXF.getBlocks().getBlocks().keySet()) {
            //iterate through Blocks ATTDEF, SOLID, WIPEOUT
            Block block = DXF.getBlocks().getBlocks().get(keyBlock);
            //find blockRecord by its parentHandle
            if (BlockRecordHandle.equals(block.getParentHandle())){
                for (String keyBlockElement : block.getBlockElements().keySet()) {
                    cadblock.setBlockElements(block.getBlockElements());
                }
            }
        }
    }
}
