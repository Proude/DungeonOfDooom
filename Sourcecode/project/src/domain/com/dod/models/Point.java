package com.dod.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean class for storing a point (or vertex) in the map.
 */
@XmlRootElement
public class Point {
    public int x;
    public int y;

    public Point() {}

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (obj instanceof Point) {
            Point point = (Point) obj;

            if (point.x == x && point.y == y) {
                result = true;
            }
        }

        return result;
    }
}
