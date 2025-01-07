package dxfModify;

import cadObjects.Polyline;
import dxfRead.DXFDrawing;
import java.util.ArrayList;
import java.util.Map;

//class to add new groups to the DXF file
//its called by ADDGroups
// Arraylist of Strings of DXF Code for groups),
// aryLines = dxf as text,
// object of CAD drawing

public class AddGroups {
    static String[] AddGroups(ArrayList<String> DXFGroups, String[] aryLines, DXFDrawing CAD) {
        int ObjectsEnd = CAD.getObjectsEndIndex()-1;

        if (ObjectsEnd >= 0 && ObjectsEnd < aryLines.length) {
            String[] newLines = new String[aryLines.length + DXFGroups.size()];

            System.arraycopy(aryLines,0,newLines,0,ObjectsEnd +1);

            //insert dXF code for Groups
            for (int i = 0; i < DXFGroups.size(); i++) {
                newLines[ObjectsEnd + 1 + i] = (String) DXFGroups.get(i);
            }
            System.arraycopy(aryLines, ObjectsEnd + 1, newLines, ObjectsEnd + 1 + DXFGroups.size(), aryLines.length - ObjectsEnd - 1 );
            CAD.updateIndexes(ObjectsEnd, DXFGroups.size());
            return newLines;
        } else {
            System.out.println("ADDGroups - out of bounds");
            return aryLines;
        }
    }

     public void CreateDXFGroup(Polyline polyline, ArrayList<String> DXFGroups) {
        String handle = DXFDrawing.getNextSeed();
        DXFGroups.add("  0");
        DXFGroups.add("GROUP");
        DXFGroups.add("  5");
        DXFGroups.add(handle);
        DXFGroups.add("102");
        DXFGroups.add("{ACAD_REACTORS");
        DXFGroups.add("330");
        DXFGroups.add("D");
        DXFGroups.add("102");
        DXFGroups.add("}");
        DXFGroups.add("330");
        DXFGroups.add("D");
        DXFGroups.add("100");
        DXFGroups.add("AcDbGroup");
        DXFGroups.add("300");
        DXFGroups.add("");
        DXFGroups.add(" 70");
        DXFGroups.add("     1");
        DXFGroups.add(" 71");
        DXFGroups.add("     1");
        DXFGroups.add("340");
        DXFGroups.add(polyline.getHandle());
         //assuming your polyline has already been build as Object and has Hashmap Grouped where other object are located (that you want to group with). Key should be Handle of the element.
        for (Map.Entry<String, Object> entry : polyline.getGrouped().entrySet()) {
            DXFGroups.add("340");
            DXFGroups.add(String.valueOf(entry.getKey()));
        }

    }
}
