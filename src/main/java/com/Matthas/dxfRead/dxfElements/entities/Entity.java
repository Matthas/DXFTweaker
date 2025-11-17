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
    private Double Radius;
    private Double Dimension;
    private Double Transparency;
    private Double LineTypeScale;
    private String Justify;
    private String ParentHandle;
    private String SoftPointer;
    private String TextVal;
    private Double Rotation;
    private Double TextHeight;
    private String TextStyle;
    private Double TextWidthFactor;
    private Integer PolylineFlag;
    private String EntityType;
    private Double PatternScale;
    private Double ArrowSize;
    private Double ViewHeight;
    private Double CellHeight;
    private Double EndWidth;
    private Double StartAngle;
    private Double Degree;
    private Double EndAngle;
    private Double StartParameter;
    private Double EndParameter;
    private Double ExtensionLength;
    private Double Bulge;
    private Double AxisRatio; //Ratio of minor axis to major axis
    private Double ObliqueAngle;
    Coords coords = new Coords();
    Coords fitPoints = new Coords();
    Coords tangentPoints = new Coords();
    private Coords TextAlignmentPoint;
    private String ErrorString;
    private Double ScaleX;
    private Double ScaleY;
    private Integer LineIndex;
    private Integer BackgroundColor;
    private Integer DXFIndex;
    private Map<Integer, Double> FitParameters = new LinkedHashMap<>();
    private Map<Integer, Double> Knots = new LinkedHashMap<>();
    Map<String, String> ATTribs = new HashMap<>();
    Map<String, Entity> ATTribEnt = new HashMap<>();
    private Map<String, String> Attributes = new LinkedHashMap<>();
    private Map<String, Map<String, String>> ExtendedData = new LinkedHashMap<>();

    public void addFitParameter(Double fitParaVal){
        FitParameters.put(FitParameters.size(), fitParaVal);
    }

    public void addKnot(Double knotVal){
        Knots.put(Knots.size(),knotVal);
    }

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
