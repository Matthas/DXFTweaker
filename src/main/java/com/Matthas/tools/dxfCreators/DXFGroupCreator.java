package com.Matthas.tools.dxfCreators;

import com.Matthas.cadObjects.Polyline;
import com.Matthas.dxfRead.DXFDrawing;
import com.Matthas.dxfRead.dxfElements.objects.DXFObject;

import java.util.ArrayList;
import java.util.Map;

public class DXFGroupCreator {
    public static void createDXFPolylineGroup(Polyline cable, ArrayList<String> DXFGroups, String groupHandle) {
        DXFGroups.add("  0");
        DXFGroups.add("GROUP");
        DXFGroups.add("  5");
        DXFGroups.add(groupHandle);
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
        DXFGroups.add(cable.getHandle());
        for (Map.Entry<String, Object> entry : cable.getGrouped().entrySet()) {
            DXFGroups.add("340");
            DXFGroups.add(String.valueOf(entry.getKey()));
        }

    }
    //GROUP elements need to also be in dictionary. From testing it appears to always be Dictionary with handle "D"
    //code need to first check if this exist and append new elements to it, if not create anew.
    public static void createDXFDictionaryForGroup(ArrayList<String> DXFDictionaries) {
        //check if this dictionary already exist, if so just exit
        if (checkForDictionary()){
            return;
        }
        String mainDictHandle = DXFDrawing.getNextSeed();
        DXFDictionaries.add("  0");
        DXFDictionaries.add("DICTIONARY");
        DXFDictionaries.add("  5");
        DXFDictionaries.add("D"); //lets try with static handle
        DXFDictionaries.add("102");
        DXFDictionaries.add("{ACAD_REACTORS");
        DXFDictionaries.add("330");
        DXFDictionaries.add("C");
        DXFDictionaries.add("102");
        DXFDictionaries.add("}");
        DXFDictionaries.add("330");
        DXFDictionaries.add("C");
        DXFDictionaries.add("100");
        DXFDictionaries.add("AcDbDictionary");
        DXFDictionaries.add("281");
        DXFDictionaries.add("     1");
    }
    //use this to create append string to already existing dictionary
    public static void addGroupToDictionary(String groupHandle, ArrayList<String> DXFDictionaries) {
        DXFDictionaries.add("  3");
        DXFDictionaries.add("Group" + groupHandle);
        DXFDictionaries.add("350");
        DXFDictionaries.add(groupHandle);
    }

    public static boolean checkForDictionary(){
        Map<String, DXFObject> DXFObjects = DXFDrawing.getDXFObjects().getDXFObjectsMap();
        if (DXFObjects.containsKey("D")) {
            return true;
        }
        return false;
    }
}
