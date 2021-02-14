package model.entity;

import model.action.Action;
import model.Player;

public abstract class Entity {
    protected int x; //position x
    protected int y; //position y
    protected int hp; // points de vie
    protected Action[] actions;
    protected Player player;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHp() {
        return hp;
    }

    public Player getPlayer() {
        return player;
    }

    public void damage(int dmg) {
        if (dmg>0) {
            hp-=dmg;
        }
    }
}
