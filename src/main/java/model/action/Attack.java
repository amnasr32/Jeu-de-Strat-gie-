package model.action;
import model.*;
import model.entity.Entity;

public class Attack extends Action {
    public Attack(String name, String type, int min, int max, int dmg, int roundCD, int cooldown) {
        super.name=name;
        super.type=type;
        super.minRange=min;
        super.maxRange=max;
        super.amount =dmg;
        super.roundCooldown=roundCD;
        super.cooldown=cooldown;
        //super.cost=cost;
    }

    @Override
    public boolean doAction(Player p, Cell c) {
        Entity e = c.getEntity();
        if (e==null || e.getPlayer()==p || roundCooldown != 0) return false;
        if(type.equals("physique")){
            e.damage(amount);
        }
        else{
            e.magicDamage(amount);
        }
        startCooldown(cooldown);
        return true;
    }

    @Override
    public String getDescription() {
        StringBuilder bld = new StringBuilder();
        bld.append("dégats: ").append(amount).append("\n");
        bld.append("portée: ");
        if (minRange==maxRange) bld.append(minRange).append("\n");
        else bld.append(minRange).append("-").append(maxRange).append("\n");
        bld.append("temps de récupération : ").append(cooldown).append(" tours \n");
        //bld.append("temps restant avant utilisation :");
        return bld.toString();
    }

}
