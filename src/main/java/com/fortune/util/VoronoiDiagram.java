package com.fortune.util;

import com.fortune.datastructures.*;

import java.util.ArrayList;
import java.util.List;

public class VoronoiDiagram {
    private DoublyConnectedEdgeList dcel;
    private List<DCELSite> sites;

    public VoronoiDiagram(List<Vector2D> points) {
        sites = new ArrayList<>(points.size());
        dcel = new DoublyConnectedEdgeList();
        dcel.setFaces(new ArrayList<>(points.size()));
        for (int i = 0; i < points.size(); i++) {
            DCELSite site = new DCELSite();
            site.setCoordinates(new Vector2D(points.get(i).x, points.get(i).y));
            site.setLabelIndex(i);
            sites.add(site);
            DCELFace face = new DCELFace();
            face.setLabelIndex(i);
            face.setSite(site);
            site.setFace(face);
            dcel.getFaces().add(face);
        }
    }

    public DCELVertex createVertex(Vector2D point) {
        DCELVertex vertex = new DCELVertex();
        vertex.setCoordinates(point);
        vertex.setLabelIndex(dcel.getVertices().size());
        dcel.getVertices().add(vertex);
        return vertex;
    }

    public DCELEdge createHalfEdge(DCELFace face) {
        DCELEdge halfEdge = new DCELEdge();
        halfEdge.setIncidentFace(face);
        if (face.getOuterComponent() == null) {
            face.setOuterComponent(halfEdge);
        }
        return halfEdge;
    }



    public DoublyConnectedEdgeList getDcel() {
        return dcel;
    }

    public void setDcel(DoublyConnectedEdgeList dcel) {
        this.dcel = dcel;
    }

    public List<DCELSite> getSites() {
        return sites;
    }

    public void setSites(List<DCELSite> sites) {
        this.sites = sites;
    }

    @Override
    public String toString() {
        return "VoronoiDiagram{" +
                "dcel=" + dcel +
                ", sites=" + sites +
                '}';
    }
}
