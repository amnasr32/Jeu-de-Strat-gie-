package model.action;
import model.entity.Entity;

public class Attack extends Action {
    public Attack(int min, int max, int dmg, int cost) {
        super.minRange=min;
        super.maxRange=max;
        super.damage=dmg;
        super.cost=cost;
    }

    @Override
    public void doAction(Entity e) {
        e.damage(damage);
    }
}
