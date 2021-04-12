package model.action;
import model.Cell;
import model.entity.Entity;
import model.Player;

public abstract class Action {
    protected String name;
    protected int minRange; // 0 = peut être fait sur soi même
    protected int maxRange;
    protected int amount; // quantité de dégats, soins, etc
    protected int roundCooldown; // temps de récupération en tour
    protected int cooldown;

    // doit renvoyer false si l'action échoue
    public abstract boolean doAction(Cell c);

    public abstract String getDescription();

    public abstract Boolean isAlly(Player player);

    public abstract void startCooldown(int cooldown);

    public abstract void reduceCooldown();

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
