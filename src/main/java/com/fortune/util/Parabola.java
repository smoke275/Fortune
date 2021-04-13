package com.fortune.util;

import edu.princeton.cs.introcs.StdDraw;

public class Parabola {
    private final double a, b, c;

    public Parabola(Vector2D focus, double directrixY) {
        this.a = focus.x;
        this.b = focus.y;
        this.c = directrixY;
    }

    public void draw(double min, double max) {
        min = (min > -100) ? min : -100;
        max = (max < 100) ? max : 100;
        for (double x = min; x < max; x += .01) {
            double y = ((x-a)*(x-a) + (b*b) - (c*c)) / (2*(b-c));
            StdDraw.setPenColor(StdDraw.ORANGE);
            StdDraw.point(x, y);
        }
    }

    public void draw() {
        this.draw(0, 1);
    }
}
