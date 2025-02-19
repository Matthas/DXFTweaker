package com.Matthas.tools.dxfCreators.dxfPolyline;

import com.Matthas.cadObjects.Polyline;
import com.Matthas.dxfRead.DXFDrawing;
import com.Matthas.tools.dxfCreators.DXFGroupCreator;
import com.Matthas.tools.dxfCreators.DXFPolylineCreator;
import java.util.ArrayList;
import java.util.Map;

public class CableCreator {
    public static void createFibreCable(Polyline cable, ArrayList<String> DXFCables, ArrayList<String> DXFGroups, ArrayList<String> DXFDictionaries, Map<String, String> GroupsHandles){
        String groupHandle = DXFDrawing.getNextSeed();
        DXFPolylineCreator.createDXFPolylineLine(cable, DXFCables, groupHandle);

        DXFGroupCreator.createDXFPolylineGroup(cable, DXFGroups, groupHandle);
        DXFGroupCreator.addGroupToDictionary(groupHandle, DXFDictionaries);
    }
}

