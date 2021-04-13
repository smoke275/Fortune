package com.fortune;

import com.fortune.datastructures.DCELEdge;
import com.fortune.datastructures.DCELFace;
import com.fortune.datastructures.DCELVertex;
import com.fortune.datastructures.DoublyConnectedEdgeList;
import com.fortune.util.*;

public class Test {
    public static void testScript(){
        DoublyConnectedEdgeList dcel = new DoublyConnectedEdgeList();
        DCELVertex v1 = new DCELVertex();
        DCELVertex v2 = new DCELVertex();
        DCELVertex v3 = new DCELVertex();

        DCELFace f1 = new DCELFace();
        DCELFace f2 = new DCELFace();


        DCELEdge e12 = new DCELEdge();
        DCELEdge e21 = new DCELEdge();
        DCELEdge e13 = new DCELEdge();
        DCELEdge e31 = new DCELEdge();
        DCELEdge e23 = new DCELEdge();
        DCELEdge e32 = new DCELEdge();

        v1.setCoordinates(new Vector2D(0,0));
        v1.setIncidentEdge(e12);
        v2.setCoordinates(new Vector2D(1,0));
        v2.setIncidentEdge(e23);
        v3.setCoordinates(new Vector2D(0,1));
        v3.setIncidentEdge(e32);

        f1.setOuterComponent(e23);
        f1.setInnerComponent(null);

        f2.setOuterComponent(null);
        f2.getInnerComponent().add(e13);

        e12.setOrigin(v1);
        e12.setTwin(e21);
        e12.setIncidentFace(f1);
        e12.setNext(e23);
        e12.setPrev(e31);

        e21.setOrigin(v2);
        e21.setTwin(e12);
        e21.setIncidentFace(f2);
        e21.setNext(e13);
        e21.setPrev(e32);

        e13.setOrigin(v1);
        e13.setTwin(e31);
        e13.setIncidentFace(f2);
        e13.setNext(e32);
        e13.setPrev(e21);


        e31.setOrigin(v3);
        e31.setTwin(e13);
        e31.setIncidentFace(f1);
        e31.setNext(e12);
        e31.setPrev(e23);

        e23.setOrigin(v2);
        e23.setTwin(e32);
        e23.setIncidentFace(f1);
        e23.setNext(e31);
        e23.setPrev(e12);

        e32.setOrigin(v3);
        e32.setTwin(e23);
        e32.setIncidentFace(f2);
        e32.setNext(e21);
        e32.setPrev(e13);

        dcel.addVectex(v1);
        dcel.addVectex(v2);
        dcel.addVectex(v3);

        dcel.addFace(f1);
        dcel.addFace(f2);

        dcel.addEdge(e12);
        dcel.addEdge(e21);
        dcel.addEdge(e13);
        dcel.addEdge(e31);
        dcel.addEdge(e23);
        dcel.addEdge(e32);

        dcel.printDesciption();

    }
}
