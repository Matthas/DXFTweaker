package com.Matthas.cadObjects.CadElements;

import com.Matthas.dxfRead.DXFDrawing;
import com.Matthas.dxfRead.dxfElements.coords.Coords;
import com.Matthas.dxfRead.dxfElements.entities.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class CADLine {
    private String Handle;
    private String Name;
    private Integer Color;  //color as int, DXF has unusual format of saving color as index or as RGB converted to DEX (not HEX)
    private Coords coords;
    private String Layer;
    private Double Radius;
    private Double StartAngle;
    private Double EndAngle;
    private Double Thickness;
    private Double Elevation;
    private Boolean isClosed; //1 or 129 = true
    private Coords FitPoints;
    private Map<String, Map<String, String>> ExtendedData = new LinkedHashMap<>();
    //extendeddata is only generic and very simple implementation
    //it has not been tested properly

    public static void createCADLine(Entity entity, CADLine cadLine, DXFDrawing DXF) {
        String handle = entity.getHandle();
        cadLine.setHandle(entity.getHandle());
        cadLine.setName(entity.getName());
        cadLine.setLayer(entity.getLayer());
        cadLine.setColor(entity.getColour());
        cadLine.setRadius(entity.getRadius());
        cadLine.setStartAngle(entity.getStartAngle());
        cadLine.setEndAngle(entity.getEndAngle());
        cadLine.setThickness(entity.getThickness());
        cadLine.setCoords(entity.getCoords());
        cadLine.setFitPoints(entity.getFitPoints());
        //cadLine.setElevation(entity.getElevation); //not supported for 2D

        if (entity.getPolylineFlag() == 1 || entity.getPolylineFlag() == 129) {
            cadLine.setIsClosed(Boolean.TRUE);
        } else {
            cadLine.setIsClosed(Boolean.FALSE);
        }
        cadLine.setExtendedData(entity.getExtendedData());

    }
}
