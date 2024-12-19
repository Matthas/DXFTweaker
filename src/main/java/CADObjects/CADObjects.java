package CADObjects;

import java.util.*;

import DXFRead.DXFDrawing;
import DXFRead.DXFElements.Blocks.Block;
import DXFRead.DXFElements.Entities.Entities;
import CADObjects.ExampleBlock.ExampleBlock;
import DXFRead.DXFElements.Tables.Tables;

import static DXFRead.DXFElements.Entities.Entities.EntitiesMap;
import static DXFRead.DXFElements.Entities.Entities.Inserts;

public class CADObjects {
    protected static Map<String, Polyline> ExampleCables;

    public void CADObjects(DXFDrawing DXF) {
        // example way to load your blocks into hashmaps
        //block gathering should start by targeting their Insert
        for (String key : Inserts.keySet()) {
            Entities.Entity insert = Inserts.get(key);
            if ("Example-Layer".equals(insert.getLayer())) {
                ExampleBlock exampleBlock = new ExampleBlock();
                exampleBlock.setBlockName("Table");
                ExampleBlock.CreateExampleBlock(insert, exampleBlock, DXF);
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