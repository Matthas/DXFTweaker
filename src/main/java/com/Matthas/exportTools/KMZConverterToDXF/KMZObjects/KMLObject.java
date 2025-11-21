package com.Matthas.exportTools.KMZConverterToDXF.KMZObjects;

import lombok.Getter;
import lombok.Setter;
import com.Matthas.dxfRead.dxfElements.coords.Coords;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class KMLObject {
    private String id; // Placemark ID
    private Coords coords = new Coords(); // Structured coordinates
    private Map<String, String> attributes = new LinkedHashMap<>();

    public KMLObject(String id) {
        this.id = id;
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public void addCoordinate(double x, double y) {
        coords.addCoords(x, y);
    }
}
