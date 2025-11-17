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
    private Coords TangentPoints;
    private Double Length;
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
        cadLine.setTangentPoints(entity.getTangentPoints());
        //cadLine.setElevation(entity.getElevation); //not supported for 2D
        if (entity.getPolylineFlag() != null) {
            if (entity.getPolylineFlag() == 1 || entity.getPolylineFlag() == 129) {
                cadLine.setIsClosed(Boolean.TRUE);
            } else {
                cadLine.setIsClosed(Boolean.FALSE);
            }
            cadLine.setExtendedData(entity.getExtendedData());
        }
        cadLine.setLength(cadLine.calculateLength(cadLine.coords));
    }
    //calculate length of polyline
    public Double calculateLength(Coords coords) {
        Double length = 0.0;
        Double x1 = 0.0, x2 = 0.0;
        Double y1 = 0.0, y2 = 0.0;
        if (coords.size() > 1) {
            for (int i = 0; i < coords.size() - 1; i++) {
                x1 = coords.getNCoordX(i);
                x2 = coords.getNCoordX(i+1);
                y1 = coords.getNCoordY(i);
                y2 = coords.getNCoordY(i+1);

                double seqmentlength = Math.sqrt(Math.pow(x2 - x1,2) + Math.pow(y2 - y1,2));
                length += seqmentlength;
            }
        }
        return length;
    }
}
