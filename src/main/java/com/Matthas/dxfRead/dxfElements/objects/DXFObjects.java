package com.Matthas.dxfRead.dxfElements.objects;

import com.Matthas.dxfRead.dxfElements.entities.Entity;
import com.Matthas.fileHandlers.DXFLoad;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DXFObjects {
    private String Linetxt;
    private String groupHandle;
    private Entity entity;

    public Map<String, DXFObject> DXFObjectsMap = new HashMap<>();
    private static int endSecIndex;

    public Map<String, DXFObject> getDXFObjectsMap() {
        return DXFObjectsMap;
    }

    public void setDXFObjectsMap(Map<String, DXFObject> dxfObjects) {
        this.DXFObjectsMap = dxfObjects;
    }

    public DXFObjects(Integer currentline, Integer length, DXFLoad file) throws IOException {
        String[] aryLines = file.OpenFile();
        loop:
        for (int i = currentline; i < length; i++) {
            switch (aryLines[i]){
                case "GROUP":
                case "FIELD":
                case "DICTIONARY":
                    DXFObject dxfObject = new DXFObject();
                    dxfObject.setDXFIndex(i);
                    dxfObject.setObjectType(aryLines[i]);
                    innerloop: for (; i < length; i++) {
                        switch (aryLines[i]) {
                            case "  5":
                                dxfObject.setHandle(aryLines[i+1]);
                                dxfObject.setLineIndex(i);
                                break;
                            case "  1":
                            case "  2":
                            case "  3":
                                dxfObject.ObjectValues.put(aryLines[i + 1], aryLines[i + 3]);
                                break;
                            case "  0":
                                dxfObject.setEndOfDXFIndex(i);
                                break innerloop;
                        }
                    }
                    DXFObjectsMap.put(dxfObject.getHandle(), dxfObject);
                    break;
                case "ENDSEC": //end of DXFObject section
                    endSecIndex = i;
                    break loop;
            }
        }
    }
    public static int getEndSecIndex() { return endSecIndex;}

}
