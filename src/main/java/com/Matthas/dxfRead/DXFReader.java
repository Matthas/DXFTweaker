package com.Matthas.dxfRead;

import com.Matthas.dxfRead.dxfElements.blocks.Blocks;
import com.Matthas.dxfRead.dxfElements.entities.Entities;
import com.Matthas.dxfRead.dxfElements.objects.DXFObjects;
import com.Matthas.dxfRead.dxfElements.tables.Tables;
import com.Matthas.fileHandlers.DXFLoad;
import com.Matthas.fileHandlers.FileChooser;
import com.Matthas.windows.MainMenu;
import com.Matthas.cadObjects.CADObjects;
import lombok.Getter;
import lombok.Setter;
import java.io.IOException;

@Getter
@Setter
public class DXFReader {

    private MainMenu mainMenu;
    private static String[] aryLines;
    private CADObjects cad;
    private DXFDrawing dxfDrawing;
    private String file_name;

    public static String[] getAryLines() {
        return aryLines;
    }
    public static void setAryLines(String[] aryLines) {
        DXFReader.aryLines = aryLines;
    }
    public DXFDrawing getDXFDrawing() {
        return dxfDrawing;
    }
    public void setDXFDrawing(DXFDrawing dxfDrawing) {
        this.dxfDrawing = dxfDrawing;
    }

    public DXFReader(MainMenu mainMenu) {
        this.mainMenu = mainMenu;

        //Function to promp user to select file
        file_name = FileChooser.selectFilePath();

        dxfDrawing = new DXFDrawing();

        try {
            //open file as String of text
            DXFLoad file = new DXFLoad(file_name);
            aryLines = file.OpenFile();
            //this loop goes through file and looks for DXF Sections as listed. Then it trigger proper classes to interpret these sections
            for (int i = 0; i < aryLines.length; i++) {
                switch (aryLines[i]){
                    case "HEADER": //drawing properties and extends coords
                        System.out.println("Reading HEADER section");
                        //mainMenu.writeInfo("Reading Header Section");
                        dxfDrawing = new DXFDrawing(i,aryLines.length, file);
                        i = dxfDrawing.getEndSecIndex(); //get new index i
                        break; //HEADER
                    case "CLASSES": //Not supported
                        break; //CLASSES
                    case "TABLES":
                        System.out.println("Reading TABLES section");
                        dxfDrawing.setTablesStartIndex(i);
                        Tables tables = new Tables(i,aryLines.length, file);
                        dxfDrawing.setTables(tables);
                        i = Tables.getEndSecIndex(); //get new index i
                        dxfDrawing.setTablesEndIndex(i);
                        break; //TABLES
                    case "BLOCKS": //get only SOLIDs info
                        System.out.println("Reading BLOCKS section");
                        dxfDrawing.setBlocksStartIndex(i);
                        Blocks blocks = new Blocks(i,aryLines.length, file);
                        dxfDrawing.setBlocks(blocks);
                        dxfDrawing.setBlocksEndIndex(i);
                        i = Blocks.getEndSecIndex();
                        break; //BLOCKS
                    case "ENTITIES":
                        System.out.println("Reading ENTITIES section");
                        dxfDrawing.setEntitiesStartIndex(i);
                        Entities entities = new Entities(i,aryLines.length, file);
                        dxfDrawing.setEntities(entities);
                        i = Entities.getEndSecIndex(); //get new index i
                        dxfDrawing.setEntitiesEndIndex(i);
                        dxfDrawing.SetPolylinesVertexes(dxfDrawing);
                        break; //ENTITIES
                    case "OBJECTS":
                        System.out.println("Reading OBJECT section");
                        dxfDrawing.setObjectsStartIndex(i);
                        DXFObjects objects = new DXFObjects(i,aryLines.length,file);
                        dxfDrawing.setDXFObjects(objects);
                        i = DXFObjects.getEndSecIndex();
                        dxfDrawing.setObjectsEndIndex(i);
                        break; //OBJECTS
                    default:
                        break;
                }
            }//EOL for reading DXF file

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Finished");
    }
}
