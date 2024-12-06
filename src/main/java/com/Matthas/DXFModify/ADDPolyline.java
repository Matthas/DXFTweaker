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
    //example funnction how to create polyline as DXF Code
public void CreateDXFPolylineLine(Polyline polyline, ArrayList<String> DXFpolyline) {
        DXFpolyline.add("  0");
        DXFpolyline.add("LWPOLYLINE");
        DXFpolyline.add("  5");
        DXFpolyline.add(polyline.getHandle());  // HANDLE
        //DXFpolyline.add("102");
        //DXFpolyline.add("{ACAD_REACTORS");
        //DXFpolyline.add("102");
        //DXFpolyline.add("}");
        DXFpolyline.add("330");
        DXFpolyline.add("70");
        DXFpolyline.add("100");
        DXFpolyline.add("AcDbEntity");
        DXFpolyline.add("  8");
        DXFpolyline.add(polyline.getLayer());  // Layer
        DXFpolyline.add(" 48");
        DXFpolyline.add(String.valueOf(polyline.getLineTypeScale()));// Line Type Scale
        DXFpolyline.add("100");
        DXFpolyline.add("AcDbPolyline");
        DXFpolyline.add(" 90");
        DXFpolyline.add(String.valueOf(polyline.getCoords().size()));  // Number of vertices
        DXFpolyline.add(" 70");
        DXFpolyline.add("128");  // Flags
        DXFpolyline.add(" 43");
        DXFpolyline.add(String.valueOf(polyline.getWidth()));  // Width
        for (int i = 0; i < cable.getCoords().size(); i++) {
            DXFpolyline.add(" 10");
            DXFpolyline.add(String.valueOf(polyline.getCoords().getrawNCoordX(i)));
            DXFpolyline.add(" 20");
            DXFpolyline.add(String.valueOf(polyline.getCoords().getrawNCoordY(i)));
        }
        //System.out.println("created cable");
    }

}

