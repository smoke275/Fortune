package com.fortune.datastructures;

import com.fortune.datastructures.DCELEdge;
import com.fortune.datastructures.DCELFace;
import com.fortune.datastructures.DCELVertex;
import com.fortune.util.Vector2D;

import java.util.ArrayList;

public class DoublyConnectedEdgeList {
    private ArrayList<DCELEdge> edges;
    private ArrayList<DCELVertex> vertices;
    private ArrayList<DCELFace> faces;

    public DoublyConnectedEdgeList(){
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
        faces = new ArrayList<>();
    }

    public DCELVertex createVertex(Vector2D vertex){
        DCELVertex dcelVertex = new DCELVertex();
        dcelVertex.setCoordinates(vertex);
        dcelVertex.setLabelIndex(vertices.size());
        addVectex(dcelVertex);
        return dcelVertex;
    }

    public ArrayList<DCELEdge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<DCELEdge> edges) {
        this.edges = edges;
    }

    public void addEdge(DCELEdge edge){
        this.edges.add(edge);
    }

    public ArrayList<DCELVertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<DCELVertex> vertices) {
        this.vertices = vertices;
    }

    public void addVectex(DCELVertex vertex){
        this.vertices.add(vertex);
    }

    public ArrayList<DCELFace> getFaces() {
        return faces;
    }

    public void setFaces(ArrayList<DCELFace> faces) {
        this.faces = faces;
    }

    public void addFace(DCELFace face){
        this.faces.add(face);
    }

    public void printDesciption() {

        System.out.println("---------Vertices---------");

        for (int i = 0; i < vertices.size(); i++) {
            vertices.get(i).setLabelIndex(i + 1);
        }

        for (DCELVertex vertex: vertices) {
            System.out.printf("%s (%d, %d) %s\n",
                    vertex.toString(),
                    vertex.getCoordinates().x,
                    vertex.getCoordinates().y,
                    vertex.getIncidentEdge().toString());
        }

        System.out.println("---------Faces---------");

        for (int i = 0; i < faces.size(); i++) {
            faces.get(i).setLabelIndex(i + 1);
        }

        for (DCELFace face : faces) {
            StringBuilder innerComponentString = new StringBuilder();

            if (face.getInnerComponent() == null || face.getInnerComponent().size() == 0){
                innerComponentString.append("nil");
            }

            for (DCELEdge innerComponentEdge : face.getInnerComponent()) {
                if (innerComponentString.length() != 0){
                    innerComponentString.append(" ");
                }
                innerComponentString.append(innerComponentEdge.toString());
            }

            String outerComponentString =
                    (face.getOuterComponent() == null)? "nil" : face.getOuterComponent().toString();

            System.out.printf("%s %s %s\n",
                    face.toString(),
                    outerComponentString,
                    innerComponentString);
        }

        System.out.println("---------Edges---------");

        for (DCELEdge edge : edges) {
            System.out.printf("%s %s %s %s %s %s\n",
                    edge.toString(),
                    edge.getOrigin().toString(),
                    edge.getTwin().toString(),
                    edge.getIncidentFace().toString(),
                    edge.getNext().toString(),
                    edge.getPrev().toString());
        }

    }

}
