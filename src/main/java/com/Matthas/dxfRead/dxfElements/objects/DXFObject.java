package com.Matthas.dxfRead.dxfElements.objects;

import java.util.LinkedHashMap;
import java.util.Map;

public class DXFObject {
    private String handle;
    private String ObjectType;
    private Integer LineIndex;
    private Integer DXFIndex;
    private Integer EndOfDXFIndex;
    Map<String, String> ObjectData = new LinkedHashMap<>();
    Map<String, String> ObjectValues = new LinkedHashMap<>();

    public Integer getDXFIndex() {
        return DXFIndex;
    }

    public void setDXFIndex(Integer DXFIndex) {
        this.DXFIndex = DXFIndex;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getObjectType() {
        return ObjectType;
    }

    public void setObjectType(String objectType) {
        ObjectType = objectType;
    }

    public Integer getLineIndex() {
        return LineIndex;
    }

    public void setLineIndex(Integer lineIndex) {
        LineIndex = lineIndex;
    }

    public Integer getEndOfDXFIndex() {
        return EndOfDXFIndex;
    }

    public void setEndOfDXFIndex(Integer endOfDXFIndex) {
        EndOfDXFIndex = endOfDXFIndex;
    }
}
