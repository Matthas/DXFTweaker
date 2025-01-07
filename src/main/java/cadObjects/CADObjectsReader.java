package cadObjects;

import java.util.*;

import dxfRead.DXFDrawing;
import cadObjects.exampleBlock.ExampleBlock;
import dxfRead.dxfElements.entities.Entity;

import static dxfRead.dxfElements.entities.Entities.Inserts;

public class CADObjectsReader {
    protected static Map<String, Polyline> ExampleCables;

    public void CADObjects(DXFDrawing DXF) {
        // example way to load your blocks into hashmaps
        //block gathering should start by targeting their Insert
        for (String key : Inserts.keySet()) {
            Entity insert = Inserts.get(key);
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