package model.entity;

import model.Player;
import model.action.*;

public class Druid extends Entity {

    private String ICON = "icons/portraits/Druid.png";

    public Druid(int x, int y, Player player) {
        super(player, 8, 5, 1, 3);
        super.x=x;
        super.y=y;
        super.actions = new Action[3];
        actions[0]=new Attack("Attaque de baton", "physique",1,2,2,0,1);
        actions[1]=new Poisoning("Empoisonnement", "statut",2,4,5,0,5);
        actions[2]=new Rooting("Enracinement", "statut",2,4,1,0,3);
    }

    public Druid(Player player) {
        this(-1,-1,player);
    }

    @Override
    public String toString() {
        return "Druide";
    }

    @Override
    public Entity copy() {
        return new Druid(x,y,player);
    }

    public String getICON() {
        return ICON;
    }
}