package CADObjects;


import DXFRead.DXFDrawing;
import DXFRead.DXFElements.Coords.Coords;
import DXFRead.DXFElements.Entities.Entities;

import java.util.HashMap;
import java.util.Map;

import static CADObjects.CADObjects.*;

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


    public Map<String, Object> getGrouped() {return Grouped;}
    public void setGrouped(Map<String, Object> grouped) {Grouped = grouped;}
    public void addGrouped(String handle, Object object){
        Grouped.put(handle, object);
    }
    public Double getLineTypeScale() {return LineTypeScale;}
    public String getBlockName() {return BlockName;}
    public void setBlockName(String blockname) {BlockName = blockname;}
    public String getHandle() {return Handle;}
    public void setHandle(String handle) {Handle = handle;}
    public Double getWidth() {return Width;}
    public void setWidth(Double width) {Width = width;}
    public Double getLength() {return Length;}
    public void setLength(Double length) {Length = length;}
    public String getLayer() {return Layer;}
    public void setLayer(String layer) {Layer = layer;}
    public String getLineType() {return LineType;}
    public void setLineType(String lineType) {LineType = lineType;}
    public Double getTransparency() {return Transparency;}
    public void setTransparency(Double transparency) {Transparency = transparency;}
    public Double getLinetypeScale() {return LineTypeScale;}
    public void setLineTypeScale(Double lineTypeScale) {LineTypeScale = lineTypeScale;}
    public Integer getPolylineFlag() {return PolylineFlag;}
    public void setPolylineFlag(Integer polylineFlag) {PolylineFlag = polylineFlag;}
    public Double getThickness() {return Thickness;}
    public void setThickness(Double thickness) {Thickness = thickness;}
    public Integer getColour() {return Colour;}
    public void setColour(Integer colour) {Colour = colour;}
    public Coords getCoords() {return coords;}
    public void setCoords(Coords coords) {this.coords = coords;}

    public void polylineAttribs(Polyline polyline, Entities.Entity entity){

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

    public void createCables(Entities.Entity entity, Polyline polyline, DXFDrawing DXF) {
        polyline.polylineAttribs(polyline, entity);
        ExampleCables.put(polyline.getHandle(), polyline);
    }

}