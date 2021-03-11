package model;

import java.io.Serializable;

import model.entity.Entity;

public class Cell implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3335477979607629338L;
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


