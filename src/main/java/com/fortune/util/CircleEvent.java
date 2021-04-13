package com.fortune.util;

public class CircleEvent extends Event {

    public final Arc arc;
    public final Vector2D vertex;

    public CircleEvent(Vector2D point, Arc arc, Vector2D vertex) {
        super(point);
        this.arc = arc;
        this.vertex = vertex;
    }

    public Arc getArc() {
        return arc;
    }

    public Vector2D getVertex() {
        return vertex;
    }
}
