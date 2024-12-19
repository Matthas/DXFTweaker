package DXFRead.DXFElements.Vertexes;

import FileHandlers.DXFLoad;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Vertexes {


    static Map<String, Vertex> Vertexes = new HashMap<>();
    private static int endSecIndex;

    public Vertexes (Integer currentline, Integer length, DXFLoad file) throws IOException {
        String[] aryLines = file.OpenFile();
        outerloop:
        loop: for (int i = currentline; i < length ; i++) {
            switch (aryLines[i]) {
                case "BLOCK":
                    innerloop: for (i = currentline; i < length ; i++) {
                        Vertex vertex = new Vertex();  //create new instance of block and set paremeters
                        vertex.setDXFIndex(i);
                        switch (aryLines[i]) {
                            case "  5":
                                vertex.setHandle(aryLines[i + 1]);
                                vertex.setLineIndex(i);
                                break;
                            case "1005":
                                vertex.setAlias(aryLines[i + 1]);
                                break;
                            case "  2":
                                vertex.setName(aryLines[i + 1]);
                                break;
                            case "ENDBLK":
                                break;
                        }
                        Vertexes.put(vertex.getHandle(), vertex);
                        break;
                    }
                case "ENDSEC":
                    endSecIndex = i;
                    break loop;
            }
        }
    }
    public static int getEndSecIndex() {
        return endSecIndex;
    }

    public static class Vertex {
        private String Handle;
        private String Alias;
        private String Name;
        private Integer LineIndex;
        private Integer DXFIndex;

        public Integer getDXFIndex() {
            return DXFIndex;
        }

        public void setDXFIndex(Integer DXFIndex) {
            this.DXFIndex = DXFIndex;
        }

        public String getHandle() {
            return Handle;
        }

        public String getName() {
            return Name;
        }

        public String getAlias() {
            return Alias;
        }

        public void setHandle(String handle) {
            Handle = handle;
        }

        public void setName(String name) {
            Name = name;
        }

        public void setAlias(String alias) {
            Alias = alias;
        }

        public Integer getLineIndex() {
            return LineIndex;
        }

        public void setLineIndex(Integer lineIndex) {
            LineIndex = lineIndex;
        }
    }
}