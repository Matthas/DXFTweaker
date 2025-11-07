package com.Matthas.cadObjects;

import java.util.*;

import com.Matthas.cadObjects.CadElements.CADBlock;
import com.Matthas.cadObjects.CadElements.CADLine;
import com.Matthas.dxfRead.DXFDrawing;
import com.Matthas.dxfRead.dxfElements.entities.Entities;
import com.Matthas.dxfRead.dxfElements.entities.Entity;
import lombok.Getter;

import static com.Matthas.cadObjects.CadElements.CADBlock.createCADBlock;
import static com.Matthas.dxfRead.DXFDrawing.getEntities;
import static com.Matthas.dxfRead.dxfElements.entities.Entities.Inserts;

@Getter
public class CADObjects {
    Map<String,Map<String,CADBlock>> ObjectsDatabase = new LinkedHashMap<>();
    Map<String,Map<String, CADLine>> LinesDatabase = new LinkedHashMap<>();
    Map<String,Map<String, CADLine>> ArcDatabase = new LinkedHashMap<>();
    Map<String,Map<String, CADLine>> SplineDatabase = new LinkedHashMap<>();

    public void CADObjects(DXFDrawing DXF) {
        //Map<String, Map<String, CADBlock>> ObjectsDatabase = new LinkedHashMap<>();
        //iterate through all block and create Hashmap for each unique definition of block
        //store all Hashmap is Main hashmap ObjectsDatabase
        gatherObjects(DXF, ObjectsDatabase);
        gatherEntities(DXF, LinesDatabase, ArcDatabase, SplineDatabase);

        System.out.println("end of cadblocks");
    }

    public static void deleteThings(Map<String, ?> Data, Map<String, String> toDelete){
        for (String deletekey: toDelete.keySet()) {
            Data.remove(deletekey);
        }
    }

    private static void gatherEntities(DXFDrawing DXF, Map<String,Map<String,CADLine>> LinesDatabase,Map<String,Map<String,CADLine>> ArcDatabase, Map<String,Map<String,CADLine>> SplineDatabase) {
        for (String key : DXF.getEntities().getEntitiesMap().keySet()) {
            Entity entity = DXF.getEntities().getEntitiesMap().get(key);
            if (entity.getBlockname().equals("LWPOLYLINE")) {
                gatherLines(LinesDatabase,entity, DXF);
            }
            if (entity.getBlockname().equals("POLYLINE")) {
                gatherLines(LinesDatabase,entity,DXF);
            }
            if (entity.getBlockname().equals("LINE")) {
                gatherLines(LinesDatabase,entity,DXF);
            }
            if (entity.getBlockname().equals("ARC")) {
                gatherArcs(ArcDatabase,entity,DXF);
            }
            if (entity.getBlockname().equals("SPLINE")) {
                gatherSplines(SplineDatabase,entity,DXF);
            }
        }
    }
    private static void gatherArcs(Map<String,Map<String,CADLine>> ArcDatabase, Entity entity, DXFDrawing DXF) {
        CADLine cadLine = new CADLine();
        cadLine.createCADLine(entity, cadLine, DXF);

        String layerName = entity.getLayer();
        if (ArcDatabase.containsKey(layerName)){
            ArcDatabase.get(layerName).put(cadLine.getHandle(),cadLine);
        } else {
            Map<String,CADLine> subMap = new LinkedHashMap<>();
            subMap.put(cadLine.getHandle(), cadLine);
            ArcDatabase.put(layerName, subMap);
        }
    }

    private static void gatherSplines(Map<String,Map<String,CADLine>> SplineDatabase, Entity entity, DXFDrawing DXF) {
        CADLine cadLine = new CADLine();
        cadLine.createCADLine(entity, cadLine, DXF);

        String layerName = entity.getLayer();
        if (SplineDatabase.containsKey(layerName)){
            SplineDatabase.get(layerName).put(cadLine.getHandle(),cadLine);
        } else {
            Map<String,CADLine> subMap = new LinkedHashMap<>();
            subMap.put(cadLine.getHandle(), cadLine);
            SplineDatabase.put(layerName, subMap);
        }
    }

    private static void gatherLines(Map<String,Map<String,CADLine>> LinesDatabase, Entity entity, DXFDrawing DXF) {
        CADLine cadLine = new CADLine();
        cadLine.createCADLine(entity, cadLine, DXF);

        String layerName = entity.getLayer();
        if (LinesDatabase.containsKey(layerName)){
            LinesDatabase.get(layerName).put(cadLine.getHandle(),cadLine);
        } else {
            Map<String,CADLine> subMap = new LinkedHashMap<>();
            subMap.put(cadLine.getHandle(), cadLine);
            LinesDatabase.put(layerName, subMap);
        }
    }

    private static void gatherObjects(DXFDrawing DXF, Map<String,Map<String,CADBlock>> ObjectsDatabase) {
        for (String key : Inserts.keySet()) {
            Entity insert = Inserts.get(key);
            CADBlock cadBlock = new CADBlock();
            cadBlock.setBlockName(insert.getName());
            createCADBlock(insert,cadBlock,DXF);

            if (ObjectsDatabase.containsKey(insert.getName())) {
                ObjectsDatabase.get(cadBlock.getBlockName()).put(cadBlock.getHandle(),cadBlock);
            } else { //if Hashmap yet to exist, create it  key = block name
                Map<String,CADBlock> SubHashmap = new LinkedHashMap<>();
                ObjectsDatabase.put(cadBlock.getBlockName(), SubHashmap);
                //AND PUT AN OBJECT INTO IT
                ObjectsDatabase.get(cadBlock.getBlockName()).put(cadBlock.getHandle(),cadBlock);
            }
        }
    }

}
