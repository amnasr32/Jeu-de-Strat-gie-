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

    //sert à savoir si une entité quelconque appartient au même joueur que l'entité source
    public boolean isAlly(Entity e){
        return e.getPlayer() == player;
    }

    public abstract Entity copy();

    // update les coordonnées x et y
    public void updateCoords(int direction) {
        boolean odd = x%2==0;
        switch (direction) {
            case 0:
                x--;
                if (!odd) y++;
                break;
            case 1:
                y++;
                break;
            case 2:
                x++;
                if (!odd) y++;
                break;
            case 3:
                x++;
                if (odd) y--;
                break;
            case 4:
                y--;
                break;
            case 5:
                x--;
                if (odd) y--;
                break;
        }
    }
}
