package com.fortune.util;

import com.fortune.algorithm.Fortune;
import com.fortune.datastructures.DCELEdge;
import com.fortune.datastructures.DCELSite;
import com.fortune.datastructures.DCELVertex;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class BreakPoint {
    public final DCELSite pi, pj;
    private final Fortune fortune;
    private Edge edge;
    private boolean isEdgeLeft;
    public final DCELVertex edgeBegin;

    private double cacheSweepLoc = Double.MAX_VALUE;
    private DCELVertex cachePoint;

    public DCELVertex getPoint() {
        double l = fortune.getSweepLineLoc();
        if (l == cacheSweepLoc) {
            return cachePoint;
        }
        cacheSweepLoc = l;

        double x,y;
        // Handle the vertical line case
        if (pi.getCoordinates().y == pj.getCoordinates().y) {
            x = (pi.getCoordinates().x + pj.getCoordinates().x) / 2;
            y = (sq(x - pi.getCoordinates().x) + sq(pi.getCoordinates().y) - sq(l)) / (2* (pi.getCoordinates().y - l));
        }
        else {
            double px = (pi.getCoordinates().y > pj.getCoordinates().y) ? pi.getCoordinates().x : pj.getCoordinates().x;
            double py = (pi.getCoordinates().y > pj.getCoordinates().y) ? pi.getCoordinates().y : pj.getCoordinates().y;
            double m = edge.m;
            double b = edge.b;

            double d = 2*(py - l);

            double A = 1;
            double B = -2*px - d*m;
            double C = sq(px) + sq(py) - sq(l) - d*b;
            int sign = (pi.getCoordinates().y > pj.getCoordinates().y) ? -1 : 1;
            double det = sq(B) - 4 * A * C;
            // deteminant error
            if (det <= 0) {
                x = -B / (2 * A);
            }
            else {
                x = (-B + sign * Math.sqrt(det)) / (2 * A);
            }
            y = m*x + b;
        }
        cachePoint = new DCELVertex();
        cachePoint.setCoordinates(new Vector2D(x, y));

        return cachePoint;
    }

    public void finish(DCELVertex vert) {

        if(edge.startVertex != null){
            DCELEdge edge1 = new DCELEdge();
            DCELEdge edge2 = new DCELEdge();
            edge1.setTwin(edge2);
            edge2.setTwin(edge1);
            edge1.setOrigin(this.edge.startVertex);
            edge2.setOrigin(vert);
            this.edge.startVertex.setIncidentEdge(edge1);
            vert.setIncidentEdge(edge2);
            this.fortune.getVoronoiDiagram().getDcel().addEdge(edge1);
            this.fortune.getVoronoiDiagram().getDcel().addEdge(edge2);
        }

        if (isEdgeLeft) {
            this.edge.p1 = vert.getCoordinates();

        }
        else {
            this.edge.p2 = vert.getCoordinates();

        }
    }

    public void finish() {
        DCELVertex p = this.getPoint();
        if (isEdgeLeft) {
            this.edge.p1 = p.getCoordinates();
            /**
             * should be replace by creation and addition of DCELEdge
             */
        }
        else {
            this.edge.p2 = p.getCoordinates();//needs work
        }
    }


    public BreakPoint(DCELSite left, DCELSite right, Edge edge, boolean isEdgeLeft, Fortune fortune) {
        this.fortune = fortune;
        this.pi = left;
        this.pj = right;
        this.edge = edge;
        this.isEdgeLeft = isEdgeLeft;
        this.edgeBegin = this.getPoint();
    }

    public void draw() {
        Vector2D p = this.getPoint().getCoordinates();
        p.draw(Color.BLUE);
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.line(edgeBegin.getCoordinates().x, edgeBegin.getCoordinates().y, p.x, p.y);
        StdDraw.setPenColor();
        if (isEdgeLeft && edge.p2 != null) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.line(edgeBegin.getCoordinates().x, edgeBegin.getCoordinates().y, edge.p2.x, edge.p2.y);
        }
        else if (!isEdgeLeft && edge.p1 != null) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.line(edgeBegin.getCoordinates().x, edgeBegin.getCoordinates().y, edge.p1.x, edge.p1.y);
        }
    }

    private static double sq(double d) {
        return d * d;
    }

    public Edge getEdge() {
        return edge;
    }
}
