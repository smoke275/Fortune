package com.fortune.util;

import com.fortune.algorithm.Fortune;
import com.fortune.datastructures.DCELSite;
import com.fortune.datastructures.DCELVertex;

public class Arc extends ArcKey {

    private final Fortune fortune;
    public BreakPoint left, right;
    public final DCELSite site;

    public Arc(DCELSite site, Fortune fortune) {
        this.fortune = fortune;
        this.left = null;
        this.right = null;
        this.site = site;
    }

    public Arc(BreakPoint left, BreakPoint right, Fortune fortune) {
        this.fortune = fortune;
        this.left = left;
        this.right = right;
        this.site = (left != null) ? left.pj : right.pi;
    }

    public DCELSite getSite() {
        return site;
    }

    public void draw() {
        Vector2D l = getLeft().getCoordinates();
        Vector2D r = getRight().getCoordinates();

        Parabola par = new Parabola(site.getCoordinates(), fortune.getSweepLineLoc());
        double min = (l.x == Double.NEGATIVE_INFINITY) ? fortune.MIN_DRAW_DIM : l.x;
        double max = (r.x == Double.POSITIVE_INFINITY) ? fortune.MAX_DRAW_DIM : r.x;
        par.draw(min, max);
    }

    public Vector2D checkCircle() {
        if ((this.left == null) || (this.right == null)) return null;
        if (Vector2D.ccw(this.left.pi.getCoordinates(), this.site.getCoordinates(),
                this.right.pj.getCoordinates()) != -1) return null;
        return (this.left.getEdge().intersection(this.right.getEdge()));
    }

    @Override
    public DCELVertex getLeft() {
        if (left != null) return left.getPoint();
        DCELVertex infiniteVertex = new DCELVertex();
        infiniteVertex.setCoordinates(new Vector2D(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
        return infiniteVertex;
    }

    @Override
    public DCELVertex getRight() {
        if (right != null) return right.getPoint();
        DCELVertex infiniteVertex = new DCELVertex();
        infiniteVertex.setCoordinates(new Vector2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        return infiniteVertex;
    }

    @Override
    public String toString() {
        Vector2D l = getLeft().getCoordinates();
        Vector2D r = getRight().getCoordinates();



        return String.format("{%.4f, %.4f}", l.x, r.x);
    }
}
