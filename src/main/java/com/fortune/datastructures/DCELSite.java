package com.fortune.datastructures;

import com.fortune.util.Vector2D;

public class DCELSite {
    private Vector2D coordinates;
    private DCELFace face;
    private int labelIndex;

    public DCELSite(Vector2D coordinates) {
        this.coordinates = coordinates;
    }

    public DCELSite() {
    }

    public Vector2D getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Vector2D coordinates) {
        this.coordinates = coordinates;
    }

    public DCELFace getFace() {
        return face;
    }

    public void setFace(DCELFace face) {
        this.face = face;
    }

    public int getLabelIndex() {
        return labelIndex;
    }

    public void setLabelIndex(int labelIndex) {
        this.labelIndex = labelIndex;
    }

    @Override
    public String toString() {
        return "Sites{" +
                "coordinates=" + coordinates +
                ", face=" + face +
                ", labelIndex=" + labelIndex +
                '}';
    }
}
