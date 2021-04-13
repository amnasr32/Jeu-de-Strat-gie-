package model.action;
import model.Cell;
import model.entity.Entity;

public abstract class Action {
    protected String name;
    protected int minRange; // 0 = peut être fait sur soi même
    protected int maxRange;
    protected int amount; // quantité de dégats, soins, etc
    protected int cost; // coût en point d'action

    // doit renvoyer false si l'action échoue
    public abstract boolean doAction(Cell c);

    public abstract String getDescription();

    public String getName() {
        return name;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }
}
