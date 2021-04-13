package com.fortune.algorithm;

import com.fortune.datastructures.DCELEdge;
import com.fortune.datastructures.DCELFace;
import com.fortune.datastructures.DCELSite;
import com.fortune.datastructures.DCELVertex;
import com.fortune.util.*;
import edu.princeton.cs.introcs.StdDraw;
import javafx.util.Pair;

import java.util.*;

public class Fortune {


    public static double THRESHOLD = 5;
    public static double MIN_DRAW_DIM = -100;
    public static double MAX_DRAW_DIM = 100;

    private static double MAX_DIM = 100;
    private static double MIN_DIM = -100;
    public class LinkedVertex {
        public LinkedVertex(DCELEdge prevHalfEdge, DCELVertex vertex, DCELEdge nextHalfEdge) {
            this.prevHalfEdge = prevHalfEdge;
            this.vertex = vertex;
            this.nextHalfEdge = nextHalfEdge;
        }

        DCELEdge prevHalfEdge;
        DCELEdge nextHalfEdge;
        DCELVertex vertex;
    }

    private DCELEdge outerRing;
    private DCELVertex box[];
    private VoronoiDiagram voronoiDiagram;
    private PriorityQueue<Event> mEvents;
    private HashSet<BreakPoint> mBreakPoints;
    private TreeMap<ArcKey, CircleEvent> mArcs;
    private ArrayList<Edge> rawEdges;

    private double sweepLineLoc;

    public Fortune(List<Vector2D> points) {
        voronoiDiagram = new VoronoiDiagram(points);
    }

    public void construct() {
        mEvents = new PriorityQueue<>(Event::compareTo);
        mBreakPoints = new HashSet<>();
        mArcs = new TreeMap<>();
        rawEdges = new ArrayList<>();

        StdDraw.setCanvasSize(700, 700);

        Pair<Vector2D, Vector2D> boundingBox = findBoundingBox(0.6);
        StdDraw.setXscale(boundingBox.getKey().x - THRESHOLD, boundingBox.getValue().x + THRESHOLD);
        StdDraw.setYscale(boundingBox.getKey().y - THRESHOLD, boundingBox.getValue().y + THRESHOLD);

        show();
        createBoundingBox();

        boolean collinear = true;
        for (int i = 0; i < voronoiDiagram.getSites().size() - 2; i++) {
            if (Vector2D.ccw(voronoiDiagram.getSites().get(i).getCoordinates(),
                    voronoiDiagram.getSites().get(i+1).getCoordinates(),
                    voronoiDiagram.getSites().get(i+2).getCoordinates()) != 0){
                collinear = false;
            }
        }

        if (collinear) {
            voronoiDiagram.getSites().sort(new Comparator<DCELSite>() {
                @Override
                public int compare(DCELSite site, DCELSite t1) {
                    if (site.getCoordinates().x != t1.getCoordinates().x){
                        if (site.getCoordinates().x < t1.getCoordinates().x) return -1;
                        if (site.getCoordinates().x> t1.getCoordinates().x) return 1;
                        return 0;
                    }
                    if (site.getCoordinates().y < t1.getCoordinates().y) return -1;
                    if (site.getCoordinates().y> t1.getCoordinates().y) return 1;
                    return 0;
                }
            });
            for (int i = 0; i < voronoiDiagram.getSites().size() - 1; i++) {
                Edge edge = new Edge(voronoiDiagram.getSites().get(i).getCoordinates(),
                        voronoiDiagram.getSites().get(i+1).getCoordinates());
                Vector2D mid = Vector2D.midpoint(edge.site1, edge.site2);
                if (edge.isVertical){
                    edge.p1 = new Vector2D(mid.x, -100);
                    edge.p2 = new Vector2D(mid.x, 100);
                } else {
                    edge.p1 = new Vector2D(-100, edge.m * -100 + edge.b);
                    edge.p2 = new Vector2D(100, edge.m * 100 + edge.b);
                }
                this.rawEdges.add(edge);
            }

        } else {
            for (DCELSite site : voronoiDiagram.getSites()) {
                Event event = new SiteEvent(site);
                mEvents.add(event);
            }

            sweepLineLoc = 1000;

            while (!mEvents.isEmpty()) {
                Event event = mEvents.poll();
                sweepLineLoc = event.getPoint().y;
                draw();
                if (event instanceof SiteEvent) {
                    handleSiteEvent(event);
                } else {
                    handleCircleEvent(event);
                }
            }
            this.sweepLineLoc = MIN_DIM;
            for (BreakPoint bp : mBreakPoints) {
                bp.finish();
            }
        }

        draw();


//        for (BreakPoint bp : mBreakPoints){
//            DCELEdge temp = outerRing;
//
//            Vector2D in = bp.getEdge().p1;
//            Vector2D out = bp.getEdge().p2;
//            if (inBox(out)){
//                Vector2D t = in;
//                in = out;
//                out = t;
//            }
//
//            do {
//                Vector2D boxVertex1 = temp.getOrigin().getCoordinates();
//                Vector2D boxVertex2 = temp.getTwin().getOrigin().getCoordinates();
//                Vector2D edgeVertex1 = bp.getEdge().p1;
//                Vector2D edgeVertex2 = bp.getEdge().p2;
//                Vector2D intersection = getIntesection(edgeVertex1, edgeVertex2, boxVertex1, boxVertex2);
//                if (intersection != null){
//
//                    DCELVertex v1 = temp.getOrigin();
//                    DCELVertex v2 = temp.getTwin().getOrigin();
//
//                    DCELVertex v3 = new DCELVertex();
//                    DCELVertex v4 = bp.getEdge().startVertex;
//
//                    v3.setCoordinates(intersection);
//                    v3.setLabelIndex(getVoronoiDiagram().getDcel().getVertices().size());
//
//                    DCELEdge edge1 = new DCELEdge();
//                    DCELEdge edge2 = new DCELEdge();
//                    DCELEdge edge3 = new DCELEdge();
//                    DCELEdge edge4 = new DCELEdge();
//                    DCELEdge edge5 = new DCELEdge();
//                    DCELEdge edge6 = new DCELEdge();
//
//                    edge1.setTwin(edge2);
//                    edge2.setTwin(edge1);
//
//                    edge3.setTwin(edge4);
//                    edge4.setTwin(edge3);
//
//                    edge5.setTwin(edge6);
//                    edge6.setTwin(edge5);
//
//
//                    edge1.setOrigin(v3);
//                    edge2.setOrigin(v4);
//
//                    edge3.setOrigin(v3);
//                    edge4.setOrigin(v4);
//
//                    edge1.setOrigin(v3);
//                    edge2.setOrigin(v4);
//
//                    v3.setIncidentEdge(edge1);
//                    v4.setIncidentEdge(edge2);
//
//                    voronoiDiagram.getDcel().addEdge(edge1);
//                    voronoiDiagram.getDcel().addEdge(edge2);
//
//
//
//
//                }
//                temp = temp.getNext();
//            } while (temp != outerRing);
//        }

        show();

    }

    private Vector2D getIntesection(Vector2D p1, Vector2D p2, Vector2D b1, Vector2D b2){
        boolean horizontalB = false;
        if (b1.y == b2.y) {
            horizontalB = true;
        }
        if(horizontalB){
            if ((p1.y > b1.y && p2.y > b1.y) || (p1.y < b1.y && p2.y < b1.y))
                return null;
        } else {
            if ((p1.x > b1.x && p2.x > b1.x) || (p1.x < b1.x && p2.x < b1.x))
                return null;
        }
        if (p1.y == p2.y) {
            //horizontal
            if (!horizontalB) {
                if ((b1.y < p1.y && p1.y < b2.y ) || (b2.y < p1.y && p1.y < b1.y))
                    return new Vector2D(b1.x, p1.y);
            }
        } else if (p1.x == p2.x) {
            //vertical
            if (horizontalB) {
                if ((b1.x < p1.x && p1.x < b2.x ) || (b2.x < p1.x && p1.x < b1.x ))
                    return new Vector2D(b1.x, b1.y);
            }
        } else {
            //Valid Intersection
            double m = (p1.y - p2.y) / (p1.x - p2.x);
            double b = p2.y - m * p2.x;

            if (horizontalB){
                double x = (b1.y - b) / m;
                if ((b1.x < x && x < b2.x) || (b2.x < x && x < b1.x)) {
                    return new Vector2D(x, b1.y);
                }

            } else {
                double y = m * b1.x + b;
                if ((b1.y < y && y < b2.y) || (b2.y < y && y < b1.y)){
                    return new Vector2D(b1.x, y);
                }
            }

        }
        return null;
    }

    private boolean inBox(Vector2D point){
        double xmin, xmax, ymin, ymax;
        xmin = box[3].getCoordinates().x;
        ymin = box[3].getCoordinates().y;
        xmax = box[1].getCoordinates().x;
        ymax = box[1].getCoordinates().y;

        if (xmin < point.x && point.x < xmax && ymin < point.y && point.y < ymax)
            return true;
        else
            return false;
    }

    private Pair<Vector2D, Vector2D> findBoundingBox(double borderFactor){
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for (DCELSite dcelSite: voronoiDiagram.getSites()){
            minX = Math.min(minX, dcelSite.getCoordinates().x);
            minY = Math.min(minY, dcelSite.getCoordinates().y);
            maxX = Math.max(maxX, dcelSite.getCoordinates().x);
            maxY = Math.max(maxY, dcelSite.getCoordinates().y);
        }
        double centerX = (maxX + minX)/2;
        double centerY = (maxY + minY)/2;

        double maxSeparation = Math.max((maxX - minX), (maxY - minY));

        return new Pair<>(new Vector2D(centerX - maxSeparation * borderFactor, centerY - maxSeparation * borderFactor),
                new Vector2D(centerX + maxSeparation * borderFactor, centerY + maxSeparation * borderFactor));
    }

    private Pair<Vector2D, Vector2D> findBoundingBox(){
        return findBoundingBox(0.6);
    }

    private void handleSiteEvent(Event event) {

        SiteEvent siteEvent = (SiteEvent)event;
        // 1. Check if the bleachline is empty
        if (mArcs.isEmpty()) {
            mArcs.put(new Arc(siteEvent.getSite(), this), null);
            return;
        }
        // 2. Look for the arc above the site
        Map.Entry<ArcKey, CircleEvent> arcEntryAbove = mArcs.floorEntry(new ArcQuery(event.getPoint()));
        Arc arcAbove = (Arc) arcEntryAbove.getKey();


        // Deal with the degenerate case where the first two points are at the same y value
        if (mArcs.size() == 0 && arcAbove.getSite().getCoordinates().y == event.getPoint().y) {
            Edge newEdge = new Edge(arcAbove.site.getCoordinates(), event.getPoint());
            newEdge.p1 = new Vector2D((event.getPoint().x + arcAbove.site.getCoordinates().x)/2, Double.POSITIVE_INFINITY);
            BreakPoint newBreak = new BreakPoint(arcAbove.site, new DCELSite(event.getPoint()),
                    newEdge, false, this);
            mBreakPoints.add(newBreak);
            this.rawEdges.add(newEdge);
            Arc arcLeft = new Arc(null, newBreak, this);
            Arc arcRight = new Arc(newBreak, null, this);
            mArcs.remove(arcAbove);
            mArcs.put(arcLeft, null);
            mArcs.put(arcRight, null);
            return;
        }

        CircleEvent falseCircleEvent = arcEntryAbove.getValue();
        if (falseCircleEvent  != null) {
            mEvents.remove(falseCircleEvent);
        }

        BreakPoint breakL = arcAbove.left;
        BreakPoint breakR = arcAbove.right;
        Edge newEdge = new Edge(arcAbove.site.getCoordinates(), event.getPoint());
        this.rawEdges.add(newEdge);
        BreakPoint newBreakL = new BreakPoint(arcAbove.site, siteEvent.getSite(), newEdge, true, this);
        BreakPoint newBreakR = new BreakPoint(siteEvent.getSite(), arcAbove.site, newEdge, false, this);
        mBreakPoints.add(newBreakL);
        mBreakPoints.add(newBreakR);

        Arc arcLeft = new Arc(breakL, newBreakL, this);
        Arc center = new Arc(newBreakL, newBreakR, this);
        Arc arcRight = new Arc(newBreakR, breakR, this);

        mArcs.remove(arcAbove);
        mArcs.put(arcLeft, null);
        mArcs.put(center, null);
        mArcs.put(arcRight, null);

        checkForCircleEvent(arcLeft);
        checkForCircleEvent(arcRight);
    }

    private void handleCircleEvent(Event event) {
        CircleEvent circleEvent = (CircleEvent) event;
        mArcs.remove(circleEvent.getArc());

        DCELVertex dcelVertex = new DCELVertex();
        dcelVertex.setCoordinates(circleEvent.getVertex());
        dcelVertex.setLabelIndex(getVoronoiDiagram().getDcel().getVertices().size());
        getVoronoiDiagram().getDcel().addVectex(dcelVertex);

        circleEvent.arc.left.finish(dcelVertex);
        circleEvent.arc.right.finish(dcelVertex);
        mBreakPoints.remove(circleEvent.arc.left);
        mBreakPoints.remove(circleEvent.arc.right);

        Map.Entry<ArcKey, CircleEvent> entryRight = mArcs.higherEntry(circleEvent.arc);
        Map.Entry<ArcKey, CircleEvent> entryLeft = mArcs.lowerEntry(circleEvent.arc);
        Arc arcRight = null;
        Arc arcLeft = null;

        DCELVertex ceArcLeft = circleEvent.arc.getLeft();
        boolean cocircularJunction = circleEvent.arc.getRight().equals(ceArcLeft);

        if (entryRight != null) {
            arcRight = (Arc) entryRight.getKey();
            while (cocircularJunction && arcRight.getRight().equals(ceArcLeft)) {
                mArcs.remove(arcRight);
                arcRight.left.finish(dcelVertex);
                arcRight.right.finish(dcelVertex);
                mBreakPoints.remove(arcRight.left);
                mBreakPoints.remove(arcRight.right);

                CircleEvent falseCe = entryRight.getValue();
                if (falseCe != null) {
                    mBreakPoints.remove(falseCe);
                }

                entryRight = mArcs.higherEntry(arcRight);
                arcRight = (Arc) entryRight.getKey();
            }

            CircleEvent falseCe = entryRight.getValue();
            if (falseCe != null) {
                mEvents.remove(falseCe);
                mArcs.put(arcRight, null);
            }
        }
        if (entryLeft != null) {
            arcLeft = (Arc) entryLeft.getKey();
            while (cocircularJunction && arcLeft.getLeft().equals(ceArcLeft)) {
                mArcs.remove(arcLeft);
                arcLeft.left.finish(dcelVertex);
                arcLeft.right.finish(dcelVertex);
                mBreakPoints.remove(arcLeft.left);
                mBreakPoints.remove(arcLeft.right);

                CircleEvent falseCe = entryLeft.getValue();
                if (falseCe != null) {
                    mEvents.remove(falseCe);
                }

                entryLeft = mArcs.lowerEntry(arcLeft);
                arcLeft = (Arc) entryLeft.getKey();
            }

            CircleEvent falseCe = entryLeft.getValue();
            if (falseCe != null) {
                mEvents.remove(falseCe);
                mArcs.put(arcLeft, null);
            }
        }

        Edge e = new Edge(arcLeft.right.pi.getCoordinates(), arcRight.left.pj.getCoordinates());
        rawEdges.add(e);

        // Here we're trying to figure out if the org.ajwerner.voronoi.Voronoi vertex
        // we've found is the left
        // or right point of the new edge.
        // If the edges being traces out by these two arcs take a right turn then we
        // know
        // that the vertex is going to be above the current point
        boolean turnsLeft = Vector2D.ccw(arcLeft.right.edgeBegin.getCoordinates(),
                circleEvent.getPoint(), arcRight.left.edgeBegin.getCoordinates()) == 1;
        // So if it turns left, we know the next vertex will be below this vertex
        // so if it's below and the slow is negative then this vertex is the left point
        boolean isLeftPoint = (turnsLeft) ? (e.m < 0) : (e.m > 0);
        if (isLeftPoint) {
            e.p1 = circleEvent.getVertex();
        } else {
            e.p2 = circleEvent.getVertex();
        }
        e.startVertex = dcelVertex;

        BreakPoint newBP = new BreakPoint(arcLeft.right.pi, arcRight.left.pj, e, !isLeftPoint, this);
        mBreakPoints.add(newBP);

        arcRight.left = newBP;
        arcLeft.right = newBP;

        checkForCircleEvent(arcLeft);
        checkForCircleEvent(arcRight);
    }

    private void checkForCircleEvent(Arc a) {
        Vector2D circleCenter = a.checkCircle();
        if (circleCenter != null) {
            double radius = a.site.getCoordinates().distance(circleCenter);
            Vector2D circleEventPoint = new Vector2D(circleCenter.x, circleCenter.y - radius);
            CircleEvent ce = new CircleEvent(circleEventPoint, a, circleCenter);
            mArcs.put(a, ce);
            mEvents.add(ce);
        }
    }

    public double getSweepLineLoc() {
        return sweepLineLoc;
    }

    public void createBoundingBox(){
        Pair<Vector2D, Vector2D> boundingBox = findBoundingBox(0.7);
        double minx = boundingBox.getKey().x,
                maxx = boundingBox.getValue().x,
                miny = boundingBox.getKey().y,
                maxy = boundingBox.getValue().y;
        DCELVertex TL = this.voronoiDiagram.getDcel().createVertex(new Vector2D(minx,maxy));
        DCELVertex TR = this.voronoiDiagram.getDcel().createVertex(new Vector2D(maxx,maxy));
        DCELVertex BL = this.voronoiDiagram.getDcel().createVertex(new Vector2D(minx,miny));
        DCELVertex BR = this.voronoiDiagram.getDcel().createVertex(new Vector2D(maxx,miny));


        /**
         * 0             1
         *
         *
         *
         *
         * 3             2
         *
         */

        box = new DCELVertex[]{TL, TR, BR, BL};

        DCELFace unboundedFace = new DCELFace();
        unboundedFace.setLabelIndex(voronoiDiagram.getDcel().getFaces().size());
        voronoiDiagram.getDcel().addFace(unboundedFace);

        for (int i = 0; i < 4; i++){
            DCELEdge edge1 = new DCELEdge();
            DCELEdge edge2 = new DCELEdge();
            edge1.setTwin(edge2);
            edge2.setTwin(edge1);
            edge1.setOrigin(box[i]);
            edge2.setOrigin(box[(i+1)%4]);

            edge1.setIncidentFace(unboundedFace);
            box[i].setIncidentEdge(edge1);
            box[(i+1)%4].setIncidentEdge(edge2);
            voronoiDiagram.getDcel().addEdge(edge1);
            voronoiDiagram.getDcel().addEdge(edge2);
        }

        for (int i = 0; i < 4; i++) {
            DCELEdge edge = box[i].getIncidentEdge();
            DCELEdge edge1 = box[(i+1)%4].getIncidentEdge().getTwin();
            edge.setNext(edge1.getTwin());
            edge1.setNext(edge.getTwin());

            edge.getNext().setPrev(edge);
            edge1.getNext().setPrev(edge1);
        }
        outerRing = box[0].getIncidentEdge();


    }

    private void show() {
        StdDraw.clear(StdDraw.BLACK);
        for (DCELSite p : voronoiDiagram.getSites()) {
            p.getCoordinates().draw(StdDraw.RED);
        }
        for (Edge e : rawEdges) {
            if (e.p1 != null && e.p2 != null) {
                double topY = (e.p1.y == Double.POSITIVE_INFINITY) ? MAX_DIM : e.p1.y; // HACK to draw from infinity
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.line(e.p1.x, topY, e.p2.x, e.p2.y);
                StdDraw.setPenColor(StdDraw.CYAN);
                StdDraw.line(e.site1.x, e.site1.y, e.site2.x, e.site2.y);

            }
        }

        /*for (DCELEdge e : voronoiDiagram.getDcel().getEdges()) {
            StdDraw.line(e.getOrigin().getCoordinates().x, e.getOrigin().getCoordinates().y,
                    e.getTwin().getOrigin().getCoordinates().x, e.getTwin().getOrigin().getCoordinates().y);
        }*/
        StdDraw.show();
    }

    private void draw() {
        StdDraw.clear(StdDraw.BLACK);
        for (DCELSite p : voronoiDiagram.getSites()) {
            p.getCoordinates().draw(StdDraw.RED);
        }
        for (BreakPoint bp : mBreakPoints) {
            bp.draw();
        }
        for (ArcKey a : mArcs.keySet()) {
            ((Arc) a).draw();
        }
        for (Edge e : rawEdges) {
            if (e.p1 != null && e.p2 != null) {
                double topY = (e.p1.y == Double.POSITIVE_INFINITY) ? MAX_DIM : e.p1.y; // HACK to draw from infinity
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.line(e.p1.x, topY, e.p2.x, e.p2.y);
                StdDraw.setPenColor(StdDraw.CYAN);
                StdDraw.line(e.site1.x, e.site1.y, e.site2.x, e.site2.y);
            }
        }
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.line(MIN_DIM, sweepLineLoc, MAX_DIM, sweepLineLoc);
        StdDraw.show(1000);
    }

    public VoronoiDiagram getVoronoiDiagram() {
        return voronoiDiagram;
    }
}
