package com.Matthas.dxfRead.dxfElements.entities;

import com.Matthas.dxfRead.dxfElements.coords.Coords;
import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Entity {
    private String Blockname;
    private String Handle;
    private String LineType;
    private String Layer;
    private String Type;
    private String Background;
    private Integer Colour;
    private int Space;
    private String Name;
    private String Name3;
    private Double Thickness;
    private Double Width;
    private Double Transparency;
    private Double LineTypeScale;
    private String Justify;
    private String ParentHandle;
    private String SoftPointer;
    private String TextVal;
    private Double Rotation;
    private Double TextHeight;
    private String TextStyle;
    private Integer PolylineFlag;
    private String EntityType;
    Coords coords = new Coords();
    private String ErrorString;
    private Double ScaleX;
    private Double ScaleY;
    private Integer LineIndex;
    private Integer BackgroundColor;
    private Integer DXFIndex;
    Map<String, String> ATTribs = new HashMap<>();
    Map<String, Entity> ATTribEnt = new HashMap<>();
    Map<String, String> Attributes = new LinkedHashMap<>();

    //we split string to attribute with = being delimiter.
    public void addAttribute(String attribute) {
        int equalloc = attribute.indexOf('=');
        if (equalloc > 0 ) {
            String name = attribute.substring(0, equalloc);
            String value = attribute.substring(equalloc + 1);
            Attributes.put(name, value);
        }
    }
}
