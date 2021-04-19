package model.action;
import model.Cell;
import model.entity.Entity;

public class Heal extends Action {
    public Heal(String name, int min, int max, int heal, int cost) {
        super.name=name;
        super.minRange=min;
        super.maxRange=max;
        super.amount =heal;
        super.cost=cost;
    }

    @Override
    public boolean doAction(Cell c) {
        Entity e = c.getEntity();
        if (e==null) return false;
        e.heal(amount);
        return true;
    }

    @Override
    public String getDescription() {
        StringBuilder bld = new StringBuilder();
        bld.append("Soin: ").append(amount).append("\n");
        bld.append("Portée: ");
        if (minRange==maxRange) bld.append(minRange).append("\n");
        else bld.append(minRange).append("-").append(maxRange).append("\n");
        bld.append("Coût: ").append(cost).append("\n");
        return bld.toString();
    }
}
