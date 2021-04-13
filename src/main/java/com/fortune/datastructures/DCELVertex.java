package com.fortune.datastructures;

import com.fortune.util.Vector2D;

public class DCELVertex {
    private Vector2D coordinates;
    private DCELEdge incidentEdge;
    private int labelIndex;

    public DCELVertex(){
        coordinates = null;
        incidentEdge = null;
    }

    public Vector2D getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Vector2D coordinates) {
        this.coordinates = coordinates;
    }

    public DCELEdge getIncidentEdge() {
        return incidentEdge;
    }

    public void setIncidentEdge(DCELEdge incidentEdge) {
        this.incidentEdge = incidentEdge;
    }

    public int getLabelIndex() {
        return labelIndex;
    }

    public void setLabelIndex(int labelIndex) {
        this.labelIndex = labelIndex;
    }

    @Override
    public String toString(){
        return String.format("v%d", getLabelIndex());
    }
}
