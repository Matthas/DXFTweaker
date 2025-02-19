package com.Matthas.dxfModify;

import com.Matthas.dxfRead.DXFDrawing;
import java.util.Map;

//class to change EXISTING attributes in DXF Drawing
//Called with
//String with name of attribute (case-sensitive)
//Map of Handle ATTRIB element in dxf to change, new value
//aryLines = dxf as String[]
//DXF Drawing object

public class ChangeAttributes {
    static String[] ChangeAttributes(String type, Map<String, String> DXFAttrib, String[] aryLines, DXFDrawing CAD) {
        Integer EntitiesStart = CAD.getEntitiesStartIndex();
        Integer EntitiesEnd = CAD.getEntitiesEndIndex();

        if (EntitiesStart == 0 || EntitiesStart == null || EntitiesEnd == 0 || EntitiesEnd == null) {
            System.out.println("ChangeAttributes - out of bounds");
            return aryLines;
        }
        for (int i = EntitiesStart; i < EntitiesEnd ; i++) {
            //look for attrib objects only
            if (aryLines[i].equals("  5") && aryLines[i-1].equals("ATTRIB")) {
                FindAttributeToChange(aryLines, i, EntitiesEnd, type, DXFAttrib);
            }
        }
        return aryLines;
    }

    private static void FindAttributeToChange(String[] aryLines, Integer i, Integer EntitiesEnd, String type, Map<String,String> DXFAttrib) {

        for (int j = i; j < EntitiesEnd ; j++) {
            //check if this is attribute we want to change
            if (aryLines[j].equals("330") && DXFAttrib.containsKey(aryLines[j+1].trim())) {
                FindAttributeToChangeCheckForAttributeType(aryLines, j, EntitiesEnd, type, DXFAttrib, aryLines[j+1]);
            } else if (aryLines[j].equals("  0")){
                return; //if we encounter 0 it means it is end of ATTRIB and we should look for another ATTRib
            }
        }
    }
    private static void FindAttributeToChangeCheckForAttributeType(String[] aryLines, Integer i, Integer EntitiesEnd, String type, Map<String, String> DXFAttrib, String handle){
        //inside ATTRib and parenthandle that exist in map of elemenets to change
        boolean found = false;
        for (int j = i; j < EntitiesEnd; j++) {
            //check if this is the collect ATTRIB we want to change (e.g "LENGTH")
            if (aryLines[j].equals("  2") && aryLines[j+1].equals(type) && found == false) { //check if we have the correct type
                //name of attribute appears AFTER attribute value so we need to go back to start of definition of our ATTRIB
                j=i;
                found = true;
            }  else if (aryLines[j].equals("  0")) {
                return;
            }
            //once we found our attribute now we can find the value and change it
            if (aryLines[j].equals("  1") && found == true) {
                String newValue = DXFAttrib.get(handle);
                if (newValue != null) {
                    aryLines[j + 1] = DXFAttrib.get(handle); //get the value we need to change to
                }
            } else if (aryLines[j].equals("  0")) {
                return;
            }
        }
    }
}
