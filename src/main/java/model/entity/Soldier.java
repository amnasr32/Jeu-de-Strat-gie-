package model.entity;
import model.action.Action;
import model.action.Attack;
import model.Player;

public class Soldier extends Entity {

    private String ICON = "icons/portraits/Soldier.png";

    public Soldier(int x, int y, Player player) {
        super(player, 10, 4, 2, 2);
        super.x=x;
        super.y=y;
        super.actions = new Action[2];
        actions[0]=new Attack("Frappe courte", "physique",1,1,4,0,1);
        actions[1]=new Attack("Frappe longue", "physique",2,6,6,0,2);

    }

    public Soldier(Player player) {
        this(-1,-1,player);
    }

    @Override
    public String toString() {
        return "Soldat";
    }

    @Override
    public Entity copy() {
        return new Soldier(x,y,player);
    }

    public String getICON() {
        return ICON;
    }

}

