package com.Matthas.cadObjects.CadElements;

import com.Matthas.dxfRead.DXFDrawing;
import com.Matthas.dxfRead.dxfElements.coords.Coords;
import lombok.Getter;
import lombok.Setter;
import com.Matthas.dxfRead.dxfElements.entities.Entity;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class CADText {
    private String Handle;
    private String Name;
    private Integer Color;
    private String Layer;
    private String TextValue;
    private Double Height;
    private Double Rotation;
    private Double WidthFactor;
    private String Style;
    private Coords AlignmentPoint;
    private Double ObliqueAngle;
    private Map<String, Map<String, String>> ExtendedData = new LinkedHashMap<>();

    public static void createCADText(Entity entity, CADText cadText, DXFDrawing DXF) {
        cadText.setHandle(entity.getHandle());
        cadText.setName(entity.getName());
        cadText.setLayer(entity.getLayer());
        cadText.setColor(entity.getColour());
        cadText.setTextValue(entity.getTextVal()); // TEXT or MTEXT content
        cadText.setHeight(entity.getTextHeight());
        cadText.setRotation(entity.getRotation());
        cadText.setWidthFactor(entity.getTextWidthFactor());
        cadText.setStyle(entity.getTextStyle());
        cadText.setAlignmentPoint(entity.getTextAlignmentPoint());
        cadText.setObliqueAngle(entity.getObliqueAngle());
        cadText.setExtendedData(entity.getExtendedData());
    }
}
