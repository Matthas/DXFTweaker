package DXFRead.DXFElements.Objects;

import DXFRead.DXFElements.Entities.Entities;
import FileHandlers.DXFLoad;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Objects {
    private String Linetxt;
    private String groupHandle;
    private Entities.Entity entity;

    static Map<String, Object> Objects = new HashMap<>();
    private static int endSecIndex;

    public Objects(Integer currentline, Integer length, DXFLoad file) throws IOException {
        String[] aryLines = file.OpenFile();
        loop:
        for (int i = currentline; i < length; i++) {
            switch (aryLines[i]){
                case "GROUP":
                case "FIELD":
                case "DICTIONARY":
                    Object object = new Object();
                    object.setDXFIndex(i);
                    if (aryLines[i] != "GROUP") {
                        object.setObjectType(aryLines[i]);
                    }else {
                        object.setObjectType("");
                    }
                    innerloop: for (; i < length; i++) {
                        switch (aryLines[i]) {
                            case "  5":
                                object.setHandle(aryLines[i+1]);
                                object.setLineIndex(i);
                                break;
                            case "  1":
                            case "  2":
                            case "  3":
                                object.ObjectValues.put(aryLines[i+1],aryLines[i+1]);
                                break;
                            case "  0":
                                break innerloop;
                            default:
                                object.ObjectData.put(aryLines[i], aryLines[i+1]);
                                break;
                        }
                    }
                    Objects.put(object.getHandle(), object);
                case "ENDSEC": //end of Object section
                    endSecIndex = i;
                    break loop;
            }
        }
    }
    public static int getEndSecIndex() { return endSecIndex;}

    public static class Object {
        private String handle;
        private String ObjectType;
        private Integer LineIndex;
        private Integer DXFIndex;
        Map<String, String> ObjectData = new HashMap<>();
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
