package com.fortune.util;

import com.fortune.datastructures.DCELVertex;

public class ArcQuery extends ArcKey {

    private final Vector2D p;

    public ArcQuery(Vector2D p) {
        this.p = p;
    }

    @Override
    protected DCELVertex getLeft() {
        DCELVertex dcelVertex = new DCELVertex();
        dcelVertex.setCoordinates(p);
        return dcelVertex;
    }

    @Override
    protected DCELVertex getRight() {
        DCELVertex dcelVertex = new DCELVertex();
        dcelVertex.setCoordinates(p);
        return dcelVertex;
    }
}
