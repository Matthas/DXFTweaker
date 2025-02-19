package com.Matthas.dxfRead.dxfElements.blocks;

import com.Matthas.dxfRead.dxfElements.coords.Coords;
import lombok.Getter;
import lombok.Setter;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Block {
    private String Handle;
    private String Layer;
    private String SecondHandle;
    private int Colour;
    private String ParentHandle;
    private String SubclassMarker;
    private String DynBlockName2;
    private String DynBlockName3;
    private String Thinkness;
    private Integer DXFIndex;
    Coords coords = new Coords();
    private Map<String, BlockElement> BlockElements = new LinkedHashMap<>();

    public void addBlockElements(BlockElement blockElement) {
        BlockElements.put(blockElement.getHandle(), blockElement);
    }

    public void setHandle(String handle) {
        if (Handle == null) {
            Handle = handle;
        } else {
            SecondHandle = handle;
        }
    }


}