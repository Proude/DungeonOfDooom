package com.dod.service.model;

import com.dod.models.Point;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A simpler Tile model just for JSON communication
 */
@XmlRootElement
public class TileModel {
    private int type;
    private Point position;

    public TileModel(int type, Point position) {
        this.type = type;
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
