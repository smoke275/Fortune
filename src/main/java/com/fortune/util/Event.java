package com.fortune.util;

import org.jetbrains.annotations.NotNull;

public abstract class Event implements Comparable<Event>{

    private final Vector2D point;

    public Event(Vector2D point) {
        this.point = point;
    }

    public Vector2D getPoint() {
        return point;
    }

    @Override
    public int compareTo(@NotNull Event event) {
        return this.point.minYOrderedCompareTo(event.point);
    }
}
