package DXFRead;

import DXFRead.DXFElements.Blocks.Blocks;
import DXFRead.DXFElements.Entities.Entities;
import DXFRead.DXFElements.Objects.Objects;
import DXFRead.DXFElements.Tables.Tables;
import FileHandlers.DXFLoad;
import FileHandlers.FileChooser;
import Windows.MainMenu;
import CADObjects.CADObjects;
import java.io.IOException;


//this is class that Starts reading DXF Files

public class DXFRead  {
    private MainMenu mainMenu;

    public DXFRead(MainMenu mainMenu) {
       this.mainMenu = mainMenu;
        //Function to promp user to select file
        String file_name = FileChooser.selectFilePath();
        if (file_name == null) {
            System.out.println("No file selected");
            return;
        }

        DXFDrawing cadDrawing = new DXFDrawing();

        try {
            //open file as String of text
            DXFLoad file = new DXFLoad(file_name);
            String[] aryLines = file.OpenFile();
            //this loop goes through file and looks for DXF Sections as listed. Then it trigger proper classes to interpret these sections
            for (int i = 0; i < aryLines.length; i++) {
                switch (aryLines[i]){
                    case "HEADER": //drawing properties and extends coords
                        System.out.println("Reading HEADER section");
                        //mainMenu.writeInfo("Reading Header Section");
                        cadDrawing = new DXFDrawing(i,aryLines.length, file);
                        i = cadDrawing.getEndSecIndex(); //get new index i
                        break; //HEADER
                    case "CLASSES": //Not supported
                        break; //CLASSES
                    case "TABLES":
                        System.out.println("Reading TABLES section");
                        cadDrawing.setTablesStartIndex(i);
                        Tables tables = new Tables(i,aryLines.length, file);
                        cadDrawing.setTables(tables);
                        i = Tables.getEndSecIndex(); //get new index i
                        cadDrawing.setTablesEndIndex(i);
                        break; //TABLES
                    case "BLOCKS": //get only SOLIDs info
                        System.out.println("Reading BLOCKS section");
                        cadDrawing.setBlocksStartIndex(i);
                        Blocks blocks = new Blocks(i,aryLines.length, file);
                        cadDrawing.setBlocks(blocks);
                        cadDrawing.setBlocksEndIndex(i);
                        i = Blocks.getEndSecIndex();
                        break; //BLOCKS
                    case "ENTITIES":
                        System.out.println("Reading ENTITIES section");
                        cadDrawing.setEntitiesStartIndex(i);
                        Entities entities = new Entities(i,aryLines.length, file);
                        cadDrawing.setEntities(entities);
                        i = Entities.getEndSecIndex(); //get new index i
                        cadDrawing.setEntitiesEndIndex(i);
                        cadDrawing.SetPolylinesVertexes(cadDrawing);
                        break; //ENTITIES
                    case "OBJECTS":
                        System.out.println("Reading OBJECT section");
                        cadDrawing.setObjectsStartIndex(i);
                        Objects objects = new Objects(i,aryLines.length,file);
                        cadDrawing.setObjects(objects);
                        i = Objects.getEndSecIndex();
                        cadDrawing.setObjectsEndIndex(i);
                        break; //OBJECTS
                        //case "EOF":
                         //break loop;
                    default:
                        break;
                }
            }//EOL for reading DXF file

            //now we need to recreate CADBlocks in our code
            System.out.println("Recreating CADBlocks from DXF");
            CADObjects cad = new CADObjects();
            cad.CADObjects(cadDrawing);

            /*NetworkCreation networkCreation = new NetworkCreation();
            networkCreation.CreateNetwork(DXFcopy);

            RouteSeeker routeSeeker = new RouteSeeker();*/
            //aryLines = routeSeeker.RouteSeekerCables(aryLines, cadDrawing);
            //saveDXFFile(aryLines, file_name);*/

        }//end of try

        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //System.out.println("costam" + cadDrawing.Entities.getEntities("139D171"));
        System.out.println("koniec");
    }


}
