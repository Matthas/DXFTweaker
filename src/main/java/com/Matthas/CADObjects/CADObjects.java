package CADObjects;

import java.util.*;

import DXFRead.DXFDrawing;
import DXFRead.DXFElements.Entities.Entities;
import CADObjects.ExampleBlock.ExampleBlock;

import static DXFRead.DXFElements.Entities.Entities.EntitiesMap;
import static DXFRead.DXFElements.Entities.Entities.Inserts;

public class CADObjects {
    protected static Map<String, ExampleBlock> DPs = new LinkedHashMap<>();

    public void CADObjects(DXFDrawing DXF) {
        // example way to load your blocks into hashmaps
        //block gathering should start by targeting their Insert
        for (String key : Inserts.keySet()) {
            Entities.Entity insert = Inserts.get(key);
            if ("Example-Layer".equals(insert.getLayer())) {
                ExampleBlock exampleBlock = new ExampleBlock();
                exampleBlock.setBlockName("DP");
                CreateExampleBlock(insert, exampleBlock, DXF);
            }

            System.out.println("end of cadblocks");
        }
    }

    private void CreateExampleBlock(Entities.Entity insert, ExampleBlock exampleBlock, DXFDrawing DXF) {
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
        for (String keyATT : EntitiesMap.keySet()) {
            Entities.Entity entity = DXF.getEntities().getEntities().get(keyATT);
            if (handle.equals(entity.getParentHandle())) {
                //call DP function to get Attributes called by key of object matching its parenthandle
                exampleBlock.ExampleBlockAttribs(exampleBlock, entity);
                //add to the list to delete later
                todelete.put(entity.getHandle(), entity.getHandle()); //remove object after using it so next loop is a little faster
            }
        }

        deleteThings(EntitiesMap,todelete);
    }
    public void deleteThings(Map<String, ?> Data, Map<String, String> toDelete){
        for (String deletekey: toDelete.keySet()) {
            Data.remove(deletekey);
        }
    }
}