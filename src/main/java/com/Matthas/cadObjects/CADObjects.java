package com.Matthas.cadObjects;

import java.util.*;
import java.util.function.BiConsumer;

import com.Matthas.cadObjects.CadElements.CADBlock;
import com.Matthas.cadObjects.CadElements.CADLine;
import com.Matthas.cadObjects.CadElements.CADText;
import com.Matthas.dxfRead.DXFDrawing;
import com.Matthas.dxfRead.dxfElements.entities.Entity;
import lombok.Getter;

import static com.Matthas.cadObjects.CadElements.CADBlock.createCADBlock;
import static com.Matthas.dxfRead.dxfElements.entities.Entities.Inserts;

@Getter
public class CADObjects {

    private final Map<String, Map<String, CADBlock>> ObjectsDatabase = new LinkedHashMap<>();
    private final Map<String, Map<String, CADLine>> LinesDatabase = new LinkedHashMap<>();
    private final Map<String, Map<String, CADLine>> ArcDatabase = new LinkedHashMap<>();
    private final Map<String, Map<String, CADLine>> SplineDatabase = new LinkedHashMap<>();
    private final Map<String, Map<String, CADLine>> CircleDatabase = new LinkedHashMap<>();
    private final Map<String, Map<String, CADLine>> EllipseDatabase = new LinkedHashMap<>();
    private final Map<String, Map<String, CADText>> TextDatabase = new LinkedHashMap<>();

    public void CADObjects(DXFDrawing DXF) {
        //Map<String, Map<String, CADBlock>> ObjectsDatabase = new LinkedHashMap<>();
        //iterate through all block and create Hashmap for each unique definition of block
        //store all Hashmap is Main hashmap ObjectsDatabase
        gatherObjects(DXF, ObjectsDatabase);
        gatherEntities(DXF);

        System.out.println("end of cadblocks");
    }

    public static void deleteThings(Map<String, ?> Data, Map<String, String> toDelete){
        for (String deletekey: toDelete.keySet()) {
            Data.remove(deletekey);
        }
    }


    private void gatherEntities(DXFDrawing DXF) {
        Map<String, BiConsumer<Entity, DXFDrawing>> handlers = new HashMap<>();

        handlers.put("LINE", (entity, dxf) -> gatherLines(LinesDatabase, entity, dxf));
        handlers.put("LWPOLYLINE", (entity, dxf) -> gatherLines(LinesDatabase, entity, dxf));
        handlers.put("POLYLINE", (entity, dxf) -> gatherLines(LinesDatabase, entity, dxf));
        handlers.put("ARC", (entity, dxf) -> gatherArcs(ArcDatabase, entity, dxf));
        handlers.put("SPLINE", (entity, dxf) -> gatherSplines(SplineDatabase, entity, dxf));
        handlers.put("CIRCLE", (entity, dxf) -> gatherCircles(CircleDatabase, entity, dxf));
        handlers.put("TEXT", (entity,dxf) -> gatherTexts(TextDatabase, entity, dxf));

        DXF.getEntities().getEntitiesMap().forEach((key, entity) -> {
            BiConsumer<Entity, DXFDrawing> handler = handlers.get(entity.getBlockname());
            if (handler != null) {
                handler.accept(entity, DXF);
            }
        });
    }

    private static void gatherTexts(Map<String,Map<String,CADText>> TextDatabase, Entity entity, DXFDrawing DXF) {
        CADText cadText = new CADText();
        cadText.createCADText(entity, cadText, DXF);

        String layerName = entity.getLayer();
        if (TextDatabase.containsKey(layerName)){
            TextDatabase.get(layerName).put(cadText.getHandle(),cadText);
        } else {
            Map<String,CADText> subMap = new LinkedHashMap<>();
            subMap.put(cadText.getHandle(), cadText);
            TextDatabase.put(layerName, subMap);
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

    private static void gatherEllipses(Map<String,Map<String,CADLine>> EllipseDatabase, Entity entity, DXFDrawing DXF) {
        CADLine cadLine = new CADLine();
        cadLine.createCADLine(entity, cadLine, DXF);

        String layerName = entity.getLayer();
        if (EllipseDatabase.containsKey(layerName)){
            EllipseDatabase.get(layerName).put(cadLine.getHandle(),cadLine);
        } else {
            Map<String,CADLine> subMap = new LinkedHashMap<>();
            subMap.put(cadLine.getHandle(), cadLine);
            EllipseDatabase.put(layerName, subMap);
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

    private static void gatherCircles(Map<String,Map<String,CADLine>> CircleDatabase, Entity entity, DXFDrawing DXF) {
        CADLine cadLine = new CADLine();
        CADLine.createCADLine(entity, cadLine, DXF);

        String layerName = entity.getLayer();
        if (CircleDatabase.containsKey(layerName)){
            CircleDatabase.get(layerName).put(cadLine.getHandle(),cadLine);
        } else {
            Map<String,CADLine> subMap = new LinkedHashMap<>();
            subMap.put(cadLine.getHandle(), cadLine);
            CircleDatabase.put(layerName, subMap);
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
