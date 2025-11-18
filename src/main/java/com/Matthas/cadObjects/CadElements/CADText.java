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
    private Coords coords;
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
        cadText.setCoords(entity.getCoords());

        cadText.setDefaultValues();
    }
    private void setDefaultValues() {
        // Common DXF defaults
        if (this.Color == null) this.Color = 256; // BYLAYER
        if (this.Layer == null) this.Layer = "0"; // Default layer
        if (this.TextValue == null) this.TextValue = ""; // Empty text
        if (this.Height == null) this.Height = 1.0; // Default text height
        if (this.Rotation == null) this.Rotation = 0.0; // Default rotation
        if (this.WidthFactor == null) this.WidthFactor = 1.0; // Default width factor
        if (this.Style == null) this.Style = "STANDARD"; // Default text style
        //if (this.AlignmentPoint == null) this.AlignmentPoint = new Coords(0.0, 0.0, 0.0); // Default alignment
        if (this.ObliqueAngle == null) this.ObliqueAngle = 0.0; // Default oblique angle

        // Coords for insertion point (TEXT uses 10,20,30)
        //if (this.coords == null) this.coords = new Coords(0.0, 0.0, 0.0);

        // Extended data
        //if (this.ExtendedData == null) this.ExtendedData = new LinkedHashMap<>();
    }
}
