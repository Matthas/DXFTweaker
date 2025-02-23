package com.Matthas.dxfRead.dxfElements.tables;

import com.Matthas.fileHandlers.DXFLoad;
import lombok.Getter;
import lombok.Setter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Tables {

    static Map<String, BlockRecord> BlockRecord = new LinkedHashMap<>();

    public static Map<String, Tables.BlockRecord> getBlockRecord() {
        return BlockRecord;
    }

    public static void setBlockRecord(Map<String, Tables.BlockRecord> blockRecord) {
        BlockRecord = blockRecord;
    }

    private static int endSecIndex;

    public Tables (Integer currentline, Integer length, DXFLoad file) throws IOException {
        String[] aryLines = file.OpenFile();
        outerloop:
        loop: for (int i = currentline; i < length ; i++) {
            switch (aryLines[i]) {
                case "BLOCK_RECORD":
                    BlockRecord block = new BlockRecord(); //create new instance of block and set paremeters
                    block.setDXFIndex(i);
                    innerloop: for (; i < length ; i++) {
                        switch (aryLines[i]) {
                            case "  5":
                                block.setHandle(aryLines[i + 1]);
                                block.setLineIndex(i);
                                break;
                            case "1005":
                                block.setAlias(aryLines[i + 1]);
                                break;
                            case "  2":
                                block.setName(aryLines[i + 1]);
                                break;
                            case "331":
                                block.addChildHandle(aryLines[i+1]);
                                break;
                            case "360":
                                block.setSoftHandle(aryLines[i+1]);
                                break;
                            case "  0":
                                break innerloop;

                        }
                    }
                    BlockRecord.put(block.getHandle(), block);
                    break;
                case "ENDSEC":
                    endSecIndex = i;
                    break loop;
            }
        }
    }
    public int size() {
        return BlockRecord.size();
    }
    public static int getEndSecIndex() {
        return endSecIndex;
    }

    @Getter
    @Setter
    public static class BlockRecord {
        private String Handle;
        private String Alias;
        private String Name;
        //private String ChildHandle;
        private String SoftHandle;
        private Integer LineIndex;
        private Integer DXFIndex;
        private Map<Integer,String> ChildHandles = new LinkedHashMap<>();

        public void addChildHandle(String childhandle){
            int i = ChildHandles.size()+1;
            ChildHandles.put(i,childhandle);
        }
        public String getChildHandle(int i) {
            return ChildHandles.get(i);
        }
    }
}