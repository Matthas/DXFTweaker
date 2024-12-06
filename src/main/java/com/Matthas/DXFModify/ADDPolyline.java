package DXFModify;

import DXFRead.DXFDrawing;
import java.util.ArrayList;

//class to add new groups to the DXF file
//its called by ADDGroups
// Arraylist of Strings of DXF Code for polylines,
// aryLines = dxf as text,
// object of CAD drawing

public class ADDPolyline {
    static String[] AddPolyline(ArrayList<String> DXFCables, String[] aryLines, DXFDrawing CAD) {
        int EntitiesEnd = CAD.getEntitiesEndIndex();

        if (EntitiesEnd >= 0 && EntitiesEnd < aryLines.length) {
            String[] newLines = new String[aryLines.length + DXFCables.size()];

            // Copy lines before the insertion point (up to but not including ENDSEC)
            System.arraycopy(aryLines, 0, newLines, 0, EntitiesEnd - 1);

            // Insert DXF code for new cables
            for (int i = 0; i < DXFCables.size(); i++) {
                newLines[EntitiesEnd - 1 + i] = (String) DXFCables.get(i);
            }

            // Add the rest of lines (after insertion point, including ENDSEC)
            System.arraycopy(aryLines, EntitiesEnd - 1, newLines, EntitiesEnd - 1 + DXFCables.size(), aryLines.length - (EntitiesEnd - 1));

            CAD.updateIndexes(EntitiesEnd, DXFCables.size());
            return newLines;
        } else {
            System.out.println("ADDPolyline - out of bounds");
            return aryLines; // Return original lines if out of bounds
        }
    }
}
