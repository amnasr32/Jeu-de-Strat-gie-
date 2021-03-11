package model.action;
import model.Cell;
import model.entity.Entity;

public class Attack extends Action {
    public Attack(String name, int min, int max, int dmg, int cost) {
        super.name=name;
        super.minRange=min;
        super.maxRange=max;
        super.damage=dmg;
        super.cost=cost;
    }

    @Override
    public boolean doAction(Cell c) {
        Entity e = c.getEntity();
        if (e==null) return false;
        e.damage(damage);
        return true;
    }

    @Override
    public String getDescription() {
        StringBuilder bld = new StringBuilder();
        bld.append("dégats: ").append(damage).append("\n");
        bld.append("portée: ");
        if (minRange==maxRange) bld.append(minRange).append("\n");
        else bld.append(minRange).append("-").append(maxRange).append("\n");
        bld.append("coût: ").append(cost).append("\n");
        return bld.toString();
    }
}
