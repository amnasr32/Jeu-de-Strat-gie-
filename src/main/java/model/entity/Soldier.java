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
        super.maxHp=10;
        super.mp=4;
        super.maxMp=4;
        super.actions = new Action[2];
        actions[0]=new Attack("attaque de mélée", 1,1,4,0);
        actions[1]=new Attack("attaque à distance", 2,6,6,0);
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
