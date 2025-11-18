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
    private String LineType;
    private Double StartAngle;
    private Double EndAngle;
    private Double Thickness;
    private Double Elevation;
    private Double LineWeight;
    private Double LineTypeScale;
    private Double GlobalWidth;
    private Boolean isClosed; //1 or 129 = true
    private Coords FitPoints;
    private Coords TangentPoints;
    private Double Length;
    private Coords EllipseAxis;
    private Double AxisRatio;
    private Map<String, Map<String, String>> ExtendedData = new LinkedHashMap<>();
    //extendeddata is only generic and very simple implementation
    //it has not been tested properly

    public static void createCADLine(Entity entity, CADLine cadLine, DXFDrawing DXF) {
        String handle = entity.getHandle();
        cadLine.setHandle(entity.getHandle());
        cadLine.setName(entity.getBlockname());
        cadLine.setLayer(entity.getLayer());
        cadLine.setColor(entity.getColour());
        cadLine.setRadius(entity.getRadius());
        cadLine.setStartAngle(entity.getStartAngle());
        cadLine.setEndAngle(entity.getEndAngle());
        cadLine.setThickness(entity.getThickness()); //optional
        cadLine.setCoords(entity.getCoords());
        cadLine.setFitPoints(entity.getFitPoints());
        cadLine.setLineType(entity.getLineType());
        cadLine.setLineWeight(entity.getLineWeight());
        cadLine.setLineTypeScale(entity.getLineTypeScale());
        cadLine.setTangentPoints(entity.getTangentPoints());
        cadLine.setGlobalWidth(entity.getWidth());
        cadLine.setEllipseAxis(entity.getEllipseAxis());
        cadLine.setAxisRatio(entity.getAxisRatio());
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
        cadLine.setDefaultValues();
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

    private void setDefaultValues() {

// Common defaults for all entities
        if (this.Color == null) this.Color = 256; // BYLAYER
        if (this.Layer == null) this.Layer = "0"; // Default layer
        if (this.LineType == null) this.LineType = "BYLAYER"; // DXF uses text, but consistent
        if (this.LineTypeScale == null) this.LineTypeScale = 1.0;
        if (this.LineWeight == null) this.LineWeight = -1.0; // BYLAYER
        if (this.Thickness == null) this.Thickness = 0.0;
        if (this.Elevation == null) this.Elevation = 0.0;

        // Entity-specific defaults
        switch (this.getName().toUpperCase()) {
            case "LINE":
                // Only coords are required, others remain null
                this.setGlobalWidth(0.0);
                break;

            case "LWPOLYLINE":
            case "POLYLINE":
                if (this.GlobalWidth == null) this.GlobalWidth = 0.0;
                break;

            case "ARC":
                if (this.Radius == null) this.Radius = 0.0;
                if (this.StartAngle == null) this.StartAngle = 0.0;
                if (this.EndAngle == null) this.EndAngle = 0.0;
                break;

            case "CIRCLE":
                if (this.Radius == null) this.Radius = 0.0;
                break;

            case "ELLIPSE":
                //if (this.EllipseAxis == null) this.EllipseAxis = new Coords();
                if (this.AxisRatio == null) this.AxisRatio = 0.0;
                if (this.StartAngle == null) this.StartAngle = 0.0;
                if (this.EndAngle == null) this.EndAngle = 0.0;
                break;

            case "SPLINE":
                //if (this.FitPoints == null) this.FitPoints = new Coords();
                //if (this.TangentPoints == null) this.TangentPoints = new Coords();
                break;
        }


    }
}
