package com.Matthas.cadObjects;

import java.util.*;
import com.Matthas.dxfRead.DXFDrawing;
import com.Matthas.cadObjects.exampleBlock.ExampleBlock;
import com.Matthas.dxfRead.dxfElements.entities.Entity;
import static com.Matthas.dxfRead.dxfElements.entities.Entities.Inserts;

public class CADObjects {
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