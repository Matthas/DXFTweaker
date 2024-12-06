package DXFRead.DXFElements.Blocks;

import DXFRead.DXFElements.Coords.Coords;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Blocks {
    private Integer i;
    private String[] aryLines;
    private Integer length;
    public static Map<String, Block> getBlocks() {return Blocks;}

    static Map<String,Block> Blocks = new LinkedHashMap<>();

    private static int endSecIndex;

    public Blocks(Integer currentline, Integer plength, FileHandlers.DXFLoad file) throws IOException {
        this.aryLines = file.OpenFile();
        this.i = currentline;
        this.length = plength;
        loop:for (; i < length; i++) {
            switch (aryLines[i]) {
                //loop for inside of BLOCKS
                case "BLOCK": //look for solid hatches and create new loop (reuse i)
                    Block block = new Block();
                    block.setDXFIndex(i);
                    Coords coords = new Coords();
                    //loop for inside of Block
                    innerloop:for (; i < length ; i++) {
                        switch(aryLines[i]){
                            case "  5":
                                block.setHandle(aryLines[i+1]);
                                break;
                            case "330":
                                block.setParentHandle(aryLines[i+1]);
                                break;
                            case "  2":
                                block.setDynBlockName2(aryLines[i+1]);
                                break;
                            case "  3":
                                block.setDynBlockName3(aryLines[i+1]);
                                break;
                            case "  8":
                                block.setLayer(aryLines[i+1]);
                                break;
                            case " 62":
                                block.setColour(Integer.parseInt(aryLines[i + 1].trim()));
                                break;
                            case "ATTDEF":
                                createBlock(block, "ATTDEF");
                                break;
                            case "WIPEOUT":
                                createBlock(block, "WIPEOUT");
                                break;
                            case "SOLID":
                                createBlock(block, "SOLID");
                                break;
                            case "LWPOLYLINE":
                                createBlock(block, "LWPOLYLINE");
                                break;
                            case "LINE":
                                createBlock(block, "LINE");
                                break;
                            case " 10": //coords
                                coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                                block.setCoords(coords);
                                break;
                            case " 11": //coords
                                coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                                block.setCoords(coords);
                                break;
                            case " 12": //coords
                                coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                                block.setCoords(coords);
                                break;
                            case " 13": //coords
                                coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                                block.setCoords(coords);
                                break;
                            case "ENDBLK":
                                break innerloop;
                        }
                    }
                    if (block.getHandle() != null) {
                        Blocks.put(block.getHandle(), block);
                    }
                case "ENDBLK":
                    break;
                case "ENDSEC":
                    endSecIndex = i;
                    break loop;
            }
        }
    }

    public static int getEndSecIndex() {return endSecIndex;}

    private void createBlock(Block block, String name) {
        BlockElement blockElement = new BlockElement();
        blockElement.setBlockName(name);
        blockElement.setDXFIndex(i);
        i=readBlockElement(length,aryLines,blockElement);
        block.addBlockElements(blockElement);
    }


    public int readBlockElement(Integer length, String[] aryLines, BlockElement blockElement){
        Coords coords = new Coords();
        try {
            blockloop: for(; i<length; i++){
                switch (aryLines[i]) {
                    case "  5":
                        blockElement.setHandle(aryLines[i+1]);
                        blockElement.setLineIndex(i);
                        break;
                    case "360":
                        blockElement.setSoftPointer(aryLines[i+1]);
                        break;
                    case "330":
                        blockElement.setParentHandle(aryLines[i+1]);
                        break;
                    case "  3":
                        blockElement.setName(aryLines[i+1]);
                        break;
                    case "  1":
                        blockElement.setVal(aryLines[i+1]);
                        break;
                    case "  6":
                        blockElement.setLineType(aryLines[i+1]);
                        break;
                    case " 48":
                        blockElement.setLineType(aryLines[i+1]);
                        break;
                    case " 62":
                        blockElement.setColour(Integer.parseInt(aryLines[i+1].trim()));
                        break;
                    case " 440":
                        blockElement.setVisibility(Integer.parseInt(aryLines[i+1].trim()));
                        break;
                    case "331":
                        blockElement.setChildHandle(aryLines[i+1]);
                        break;
                    case "  8":
                        blockElement.setLayer(aryLines[i+1]);
                        break;
                    case " 70":
                        blockElement.setPolylineFlag(Integer.parseInt(aryLines[i+1].trim()));
                        break;
                    case " 10": //coords
                        coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                        blockElement.setCoords(coords);
                        break;
                    case " 11":
                        coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                        blockElement.setCoords(coords);
                        break;
                    case " 12": //coords
                        coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                        blockElement.setCoords(coords);
                        break;
                    case " 13": //coords
                        coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
                        blockElement.setCoords(coords);
                        break;
//                    case " 14": //coords
//                        coords.addCoords(Double.parseDouble(aryLines[i + 1]), Double.parseDouble(aryLines[i + 3]));
//                            blockElement.setCoords(coords);
//                        break;
                    case "  0":
                        break blockloop;
                }
            }
        }// end try
        catch (Exception e) {
            System.out.println("Error at Dxf line: " + i);
        }
        return i;
    }
}