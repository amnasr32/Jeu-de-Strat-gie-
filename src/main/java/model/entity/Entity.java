package model.entity;

import model.Player;

public abstract class Entity {
    int x; //position x
    int y; //position y
    int hp; // points de vie
    Player player;


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
}
