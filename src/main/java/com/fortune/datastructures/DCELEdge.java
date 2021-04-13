package com.fortune.datastructures;

public class DCELEdge {
    private DCELVertex origin;
    private DCELVertex destination;
    private DCELEdge twin;
    private DCELFace incidentFace;
    private DCELEdge next;
    private DCELEdge prev;

    public DCELEdge(){
        origin = null;
        twin = null;
        incidentFace = null;
        next = null;
        prev = null;

    }

    public DCELVertex getOrigin() {
        return origin;
    }

    public void setOrigin(DCELVertex origin) {
        this.origin = origin;
    }

    public DCELVertex getDestination() {
        return destination;
    }

    public void setDestination(DCELVertex destination) {
        this.destination = destination;
    }

    public DCELEdge getTwin() {
        return twin;
    }

    public void setTwin(DCELEdge twin) {
        this.twin = twin;
    }

    public DCELFace getIncidentFace() {
        return incidentFace;
    }

    public void setIncidentFace(DCELFace incidentFace) {
        this.incidentFace = incidentFace;
    }

    public DCELEdge getNext() {
        return next;
    }

    public void setNext(DCELEdge next) {
        this.next = next;
    }

    public DCELEdge getPrev() {
        return prev;
    }

    public void setPrev(DCELEdge prev) {
        this.prev = prev;
    }

    @Override
    public String toString(){
        return String.format("e%d,%d",
                getOrigin().getLabelIndex(),
                getTwin().getOrigin().getLabelIndex());
    }
}
