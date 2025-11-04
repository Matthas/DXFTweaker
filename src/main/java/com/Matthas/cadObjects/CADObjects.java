package com.Matthas.cadObjects;

import java.util.*;

import com.Matthas.cadObjects.CadBlocks.CADBlock;
import com.Matthas.dxfRead.DXFDrawing;
import com.Matthas.dxfRead.dxfElements.entities.Entity;

import static com.Matthas.cadObjects.CadBlocks.CADBlock.CreateCADBlock;
import static com.Matthas.dxfRead.dxfElements.entities.Entities.Inserts;

public class CADObjects {
    protected static Map<String,Map<String,Object>> ObjectsDatabase;
    protected static Map<String, Polyline> ExampleCables;

    public void CADObjects(DXFDrawing DXF) {
        Map<String, Map<String, Object>> ObjectsDatabase = new LinkedHashMap<>();
        //iterate through all block and create Hashmap for each unique definition of block
        //store all Hashmap is Main hashmap ObjectsDatabase
        for (String key : Inserts.keySet()) {
            Entity insert = Inserts.get(key);
            CADBlock cadBlock = new CADBlock();
            cadBlock.setBlockName(insert.getName());
            CreateCADBlock(insert,cadBlock,DXF);

            if (ObjectsDatabase.containsKey(insert.getName())) {
                ObjectsDatabase.get(cadBlock.getBlockName()).put(cadBlock.getHandle(),cadBlock);
            } else { //if Hashmap yet to exist, create it  key = block name
                Map<String,Object> SubHashmap = new LinkedHashMap<>();
                ObjectsDatabase.put(cadBlock.getBlockName(), SubHashmap);
                //AND PUT AN OBJECT INTO IT
                ObjectsDatabase.get(cadBlock.getBlockName()).put(cadBlock.getHandle(),cadBlock);
            }
        }
        System.out.println("end of cadblocks");
    }

    public static void deleteThings(Map<String, ?> Data, Map<String, String> toDelete){
        for (String deletekey: toDelete.keySet()) {
            Data.remove(deletekey);
        }
    }
}
