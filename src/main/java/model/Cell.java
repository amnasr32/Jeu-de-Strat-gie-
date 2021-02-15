package model;

import model.entity.Entity;

public class Cell {
    Entity entity;
    int height;

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
}


