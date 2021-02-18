package model.entity;
import model.action.Action;
import model.action.Attack;
import model.Player;

public class Soldier extends Entity {

    public Soldier(int x, int y, Player player) {
        super.x=x;
        super.y=y;
        super.player=player;
        super.hp=10;
        super.actions = new Action[2];
        actions[0]=new Attack(1,1,5,0);
        actions[1]=new Attack(2,10,7,0);
    }

    @Override
    public String toString() {
        return "soldier";
    }

    @Override
    public Entity copy() {
        return new Soldier(x,y,player);
    }
}
