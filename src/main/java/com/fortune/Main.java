package com.fortune;

import com.fortune.algorithm.Fortune;
import com.fortune.util.ReadSites;
import com.fortune.util.Vector2D;
import com.fortune.util.VoronoiDiagram;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //new Visualizer().run();

        List<Vector2D> pointList = new ReadSites().getSites();
        Fortune fortune = new Fortune(pointList);
        fortune.construct();
        VoronoiDiagram voronoiDiagram = fortune.getVoronoiDiagram();

        System.out.println(voronoiDiagram.toString());
        //Test.testScript();
        //System.out.println(Arrays.toString(new ReadSites().getSites().toArray()));
    }
}
