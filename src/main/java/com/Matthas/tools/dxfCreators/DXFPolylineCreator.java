package com.Matthas.tools.dxfCreators;

import com.Matthas.cadObjects.Polyline;
import java.util.ArrayList;

public class DXFPolylineCreator {
    public static void createDXFPolylineLine(Polyline cable, ArrayList<String> DXFCables, String grouphandle) {
        DXFCables.add("  0");
        DXFCables.add("LWPOLYLINE");
        DXFCables.add("  5");
        DXFCables.add(cable.getHandle());  // HANDLE
        DXFCables.add("102");
        DXFCables.add("{ACAD_REACTORS");
        DXFCables.add("330");
        DXFCables.add(grouphandle);
        DXFCables.add("102");
        DXFCables.add("}");
        DXFCables.add("330");
        DXFCables.add("70");
        DXFCables.add("100");
        DXFCables.add("AcDbEntity");
        DXFCables.add("  8");
        DXFCables.add(cable.getLayer());  // Layer
        DXFCables.add(" 48");
        DXFCables.add(String.valueOf(cable.getLineTypeScale()));// Line Type Scale
        DXFCables.add("100");
        DXFCables.add("AcDbPolyline");
        DXFCables.add(" 90");
        DXFCables.add(String.valueOf(cable.getCoords().size()));  // Number of vertices
        DXFCables.add(" 70");
        DXFCables.add("128");  // Flags
        DXFCables.add(" 43");
        DXFCables.add(String.valueOf(cable.getWidth()));  // Width
        for (int i = 0; i < cable.getCoords().size(); i++) {
            DXFCables.add(" 10");
            DXFCables.add(String.valueOf(cable.getCoords().getrawNCoordX(i)));
            DXFCables.add(" 20");
            DXFCables.add(String.valueOf(cable.getCoords().getrawNCoordY(i)));
        }
    }
}
