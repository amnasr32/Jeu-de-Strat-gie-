package model;

import model.entity.Entity;

public class Cell {
    Entity entity;
    byte height;

    Cell() {
        entity=null;
        height=0;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public byte getHeight() {
        return height;
    }

    public void setHeight(byte height) {
        this.height = height;
    }
}


