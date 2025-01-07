package dxfRead.dxfElements.objects;

import dxfRead.dxfElements.entities.Entity;
import fileHandlers.DXFLoad;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Objects {
    private String Linetxt;
    private String groupHandle;
    private Entity entity;

    static Map<String, Object> Objects = new LinkedHashMap<>();
    private static int endSecIndex;

    public Objects(Integer currentline, Integer length, DXFLoad file) throws IOException {
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
                                dxfObject.ObjectValues.put(aryLines[i+1],aryLines[i+3]);
                                break;
                            case "340":
                                dxfObject.GroupObjects.put(aryLines[i+1],aryLines[i+1]);
                                break;
                            case "  0":
                                break innerloop;
                        }
                    }
                    Objects.put(dxfObject.getHandle(), dxfObject);
                    break;
                case "ENDSEC": //end of Object section
                    endSecIndex = i;
                    break loop;
            }
        }
    }
    public static int getEndSecIndex() { return endSecIndex;}

    public static class DXFObject {
        private String handle;
        private String ObjectType;
        private Integer LineIndex;
        private Integer DXFIndex;
        Map<String, String> GroupObjects = new HashMap<>();
        Map<String, String> ObjectValues = new HashMap<>();

        public Integer getDXFIndex() {
            return DXFIndex;
        }

        public void setDXFIndex(Integer DXFIndex) {
            this.DXFIndex = DXFIndex;
        }

        public String getHandle() {
            return handle;
        }

        public void setHandle(String handle) {
            this.handle = handle;
        }

        public String getObjectType() {
            return ObjectType;
        }

        public void setObjectType(String objectType) {
            ObjectType = objectType;
        }

        public Integer getLineIndex() {
            return LineIndex;
        }

        public void setLineIndex(Integer lineIndex) {
            LineIndex = lineIndex;
        }
    }
}
