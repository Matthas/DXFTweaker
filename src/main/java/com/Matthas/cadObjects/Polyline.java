package com.Matthas.cadObjects;

import com.Matthas.dxfRead.DXFDrawing;
import com.Matthas.dxfRead.dxfElements.coords.Coords;
import com.Matthas.dxfRead.dxfElements.entities.Entity;
import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;
import static com.Matthas.cadObjects.CADObjects.*;

@Getter
@Setter
public class Polyline {
    private String Handle;
    private Double Width;
    private Double Length;
    private String Layer;
    private String LineType;
    private Double Transparency;
    private Double LineTypeScale;
    private Integer PolylineFlag;
    private Double Thickness;
    private Integer Colour;
    private String BlockName;
    Map<String,Object> Grouped = new HashMap<>();
    private Coords coords = new Coords();

    public void addGrouped(String handle, Object object){
        Grouped.put(handle, object);
    }

    public void polylineAttribs(Polyline polyline, Entity entity){

        if (entity.getHandle() != null ) { polyline.setHandle(entity.getHandle());}
        if (entity.getWidth() != null ) { polyline.setWidth(entity.getWidth());}
        if (entity.getLayer() != null ) { polyline.setLayer(entity.getLayer());}
        if (entity.getLineType() != null ) { polyline.setLineType(entity.getLineType());}
        if (entity.getTransparency() != null ) { polyline.setTransparency(entity.getTransparency());}
        if (entity.getLineTypeScale() != null ) { polyline.setLineTypeScale(entity.getLineTypeScale());}
        if (entity.getPolylineFlag() != null)  { polyline.setPolylineFlag(entity.getPolylineFlag());}
        if (entity.getThickness() != null ) { polyline.setThickness(entity.getThickness());}
        if (entity.getColour() != null ) { polyline.setColour(entity.getColour());}
        if (entity.getCoords() != null ) { polyline.setCoords(entity.getCoords());}

        if (polyline.getPolylineFlag() != null) {
            if (polyline.getPolylineFlag() != 1) {
                setLength(calculateLength(coords));
            }
        }
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