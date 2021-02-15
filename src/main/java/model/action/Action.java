package model.action;
import model.entity.Entity;

public abstract class Action {
    protected int minRange; // 0 = peut être fait sur soi même
    protected int maxRange;
    protected int damage;
    protected int cost; // coût en point d'action

    public abstract void doAction(Entity e);

}
