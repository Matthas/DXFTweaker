package com.Matthas.dxfRead;

import com.Matthas.dxfRead.dxfElements.blocks.Blocks;
import com.Matthas.dxfRead.dxfElements.coords.Coords;
import com.Matthas.dxfRead.dxfElements.entities.Entity;
import com.Matthas.dxfRead.dxfElements.tables.Tables;
import com.Matthas.dxfRead.dxfElements.entities.Entities;
import com.Matthas.dxfRead.dxfElements.objects.DXFObjects;
import com.Matthas.cadObjects.CADObjects;
import com.Matthas.fileHandlers.DXFLoad;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DXFDrawing {
    private static String LastSavedBy;
    static Map<String, String> DrawingProperties = new HashMap<>();
    private int endSecIndex;
    private static Tables Tables;
    private static Entities Entities;
    private static String HandSeed;
    private static DXFObjects DXFObjects;
    public static CADObjects CADObjects;
    public static Blocks Blocks;
    private static Integer HeaderStartIndex;
    private static Integer HeaderEndIndex;
    private static Integer ClassesStartIndex;
    private static Integer ClassesEndIndex;
    private static Integer TablesStartIndex;
    private static Integer TablesEndIndex;
    private static Integer BlocksStartIndex;
    private static Integer BlocksEndIndex;
    private static Integer EntitiesStartIndex;
    private static Integer EntitiesEndIndex;
    private static Integer ObjectsStartIndex;
    private static Integer ObjectsEndIndex;
    private static Integer HandSeedIndex;
    private Coords coords = new Coords();

    public static String getHandSeed() {
        return HandSeed;
    }
    public static void setHandSeed(String handSeed) {
        HandSeed = handSeed;
    }
    public static Blocks getBlocks() {
        return Blocks;
    }
    public static void setBlocks(Blocks blocks) {
        Blocks = blocks;
    }
    public static CADObjects getCADObjects() {
        return CADObjects;
    }
    public static void setCADBlocks(CADObjects cadObjects) {
        CADObjects = cadObjects;
    }
    public static DXFObjects getDXFObjects() {
        return DXFObjects;
    }
    public static void setDXFObjects(DXFObjects objects) {
        DXFObjects = objects;
    }
    public static Entities getEntities() {
        return Entities;
    }
    public static void setEntities(Entities entities) {
        Entities = entities;
    }
    public static Tables getTables() {
        return Tables;
    }
    public static void setTables(Tables tables) {
        Tables = tables;
    }
    public static Integer getHeaderStartIndex() {
        return HeaderStartIndex;
    }
    public static void setHeaderStartIndex(Integer headerStartIndex) {
        HeaderStartIndex = headerStartIndex;
    }
    public static Integer getObjectsEndIndex() {
        return ObjectsEndIndex;
    }
    public static void setObjectsEndIndex(Integer objectsEndIndex) {
        ObjectsEndIndex = objectsEndIndex;
    }
    public static Integer getObjectsStartIndex() {
        return ObjectsStartIndex;
    }
    public static void setObjectsStartIndex(Integer objectsStartIndex) {
        ObjectsStartIndex = objectsStartIndex;
    }
    public static Integer getEntitiesEndIndex() {
        return EntitiesEndIndex;
    }
    public static void setEntitiesEndIndex(Integer entitiesEndIndex) {
        EntitiesEndIndex = entitiesEndIndex;
    }
    public static Integer getEntitiesStartIndex() {
        return EntitiesStartIndex;
    }
    public static void setEntitiesStartIndex(Integer entitiesStartIndex) {
        EntitiesStartIndex = entitiesStartIndex;
    }
    public static Integer getBlocksEndIndex() {
        return BlocksEndIndex;
    }
    public static void setBlocksEndIndex(Integer blocksEndIndex) {
        BlocksEndIndex = blocksEndIndex;
    }
    public static Integer getTablesEndIndex() {
        return TablesEndIndex;
    }
    public static void setTablesEndIndex(Integer tablesEndIndex) {
        TablesEndIndex = tablesEndIndex;
    }
    public static Integer getBlocksStartIndex() {
        return BlocksStartIndex;
    }
    public static void setBlocksStartIndex(Integer blocksStartIndex) {
        BlocksStartIndex = blocksStartIndex;
    }
    public static Integer getTablesStartIndex() {
        return TablesStartIndex;
    }
    public static void setTablesStartIndex(Integer tablesStartIndex) {
        TablesStartIndex = tablesStartIndex;
    }
    public static Integer getClassesEndIndex() {
        return ClassesEndIndex;
    }
    public static void setClassesEndIndex(Integer classesEndIndex) {
        ClassesEndIndex = classesEndIndex;
    }
    public static Integer getClassesStartIndex() {
        return ClassesStartIndex;
    }
    public static void setClassesStartIndex(Integer classesStartIndex) {
        ClassesStartIndex = classesStartIndex;
    }
    public static Integer getHeaderEndIndex() {
        return HeaderEndIndex;
    }
    public static void setHeaderEndIndex(Integer headerEndIndex) {
        HeaderEndIndex = headerEndIndex;
    }
    public static Integer getHandSeedIndex() {
        return HandSeedIndex;
    }
    public static void setHandSeedIndex(Integer handSeedIndex) {
        HandSeedIndex = handSeedIndex;
    }

    public DXFDrawing() {
    }

    public void updateIndexes(Integer Index, Integer IncrementIndex) {
        if (this.HandSeedIndex != null && Index <= this.HandSeedIndex) {
            this.HandSeedIndex += IncrementIndex;
        }
        if (this.HeaderStartIndex != null && Index <= this.HeaderStartIndex) {
            this.HeaderStartIndex += IncrementIndex;
        }
        if (this.HeaderEndIndex != null && Index <= this.HeaderEndIndex) {
            this.HeaderEndIndex += IncrementIndex;
        }
        if (this.ClassesStartIndex != null && Index <= this.ClassesStartIndex) {
            this.ClassesStartIndex += IncrementIndex;
        }
        if (this.ClassesEndIndex != null && Index <= this.ClassesEndIndex) {
            this.ClassesEndIndex += IncrementIndex;
        }
        if (this.TablesStartIndex != null && Index <= this.TablesStartIndex) {
            this.TablesStartIndex += IncrementIndex;
        }
        if (this.TablesEndIndex != null && Index <= this.TablesEndIndex) {
            this.TablesEndIndex += IncrementIndex;
        }
        if (this.BlocksStartIndex != null && Index <= this.BlocksStartIndex) {
            this.BlocksStartIndex += IncrementIndex;
        }
        if (this.BlocksEndIndex != null && Index <= this.BlocksEndIndex) {
            this.BlocksEndIndex += IncrementIndex;
        }
        if (this.EntitiesStartIndex != null && Index <= this.EntitiesStartIndex) {
            this.EntitiesStartIndex += IncrementIndex;
        }
        if (this.EntitiesEndIndex != null && Index <= this.EntitiesEndIndex) {
            this.EntitiesEndIndex += IncrementIndex;
        }
        if (this.ObjectsStartIndex != null && Index <= this.ObjectsStartIndex) {
            this.ObjectsStartIndex += IncrementIndex;
        }
        if (this.ObjectsEndIndex != null && Index <= this.ObjectsEndIndex) {
            this.ObjectsEndIndex += IncrementIndex;
        }
    }

    public DXFDrawing(Integer currentline, Integer length, DXFLoad file) throws IOException {
        String[] aryLines = file.OpenFile();
        setHeaderStartIndex(currentline);
        outerloop:
        loop:
        for (int i = currentline; i < length; i++) {
            switch (aryLines[i]) {
                case "$LASTSAVEDBY":
                    LastSavedBy = aryLines[i + 1];
                    break;
                case "$CUSTOMPROPERTYTAG":
                    DrawingProperties.put(aryLines[i + 2], aryLines[i + 6]);
                    break;
                case "$EXTMIN": //load extents bottom-left coordinates (need to parse them from string to double)
                    coords.addCoords(Double.parseDouble(aryLines[i + 2]), Double.parseDouble(aryLines[i + 4]));
                    break;
                case "$EXTMAX": //load extents top-right coordinates (need to parse them from string to double)
                    coords.addCoords(Double.parseDouble(aryLines[i + 2]), Double.parseDouble(aryLines[i + 4]));
                    break;
                case "$HANDSEED":
                    setHandSeed(aryLines[i + 2].trim());
                    setHandSeedIndex(i);
                    break;
                case "ENDSEC":
                    endSecIndex = i;
                    setHeaderEndIndex(i);
                    break loop;
            }
        }
    }

    public static String getNextSeed() {
        Integer curseed = Integer.parseInt(getHandSeed(), 16);
        curseed++;
        String nextseed = Integer.toHexString(curseed).toUpperCase();
        setHandSeed(nextseed);
        return nextseed;
    }

    public static void SetPolylinesVertexes(DXFDrawing DXF) {
        for (String polyline : DXF.Entities.getEntities().keySet()) {
            Entity entity = DXF.Entities.getEntities().get(polyline);
            if (entity.getBlockname().equals("POLYLINE")) {
                //SetPolylinesVertexesRemoveEmptyFirstCoords(entity);
                SetPolylinesVertexesGetVertexes(entity, DXF);
                SetPolylinesVertexesRemoveEmptyFirstCoords((entity));
                entity.getCoords().reIndexElements();
            }
        }
    }

    private static void SetPolylinesVertexesGetVertexes(Entity entity, DXFDrawing DXF) {
        for (String vertexes : DXF.Entities.getEntities().keySet()) {
            Entity vertex = DXF.Entities.getEntities().get(vertexes);
            if (vertex.getBlockname().equals("VERTEX")) {
                if (vertex.getParentHandle().equals(entity.getHandle())) {
                    entity.getCoords().addCoords(vertex.getCoords().getrawNCoordX(0), vertex.getCoords().getrawNCoordY(0));
                }
            }
        }
    }

    public static void SetPolylinesVertexesRemoveEmptyFirstCoords(Entity entity) {
        if (entity.getCoords().getrawNCoordX(0) == 0.0 && entity.getCoords().getrawNCoordY(0) == 0.0) {
            entity.getCoords().removeCoords(0);
        }
    }
    //return new index of iteration through DXF
    public int getEndSecIndex() {
        return endSecIndex;
    }
}