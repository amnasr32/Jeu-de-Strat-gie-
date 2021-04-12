package model.action;
import model.Cell;
import model.Player;
import model.entity.Entity;

public class ArmorBuff extends Action {
    public ArmorBuff(String name, int min, int max, int dmg, int roundCD, int cooldown) {
        super.name=name;
        super.minRange=min;
        super.maxRange=max;
        super.amount =dmg;
        super.roundCooldown=roundCD;
        super.cooldown=cooldown;
    }

    @Override
    public boolean doAction(Cell c) {
        Entity e = c.getEntity();
        if (e==null || !isAlly(e.getPlayer()) || roundCooldown != 0) return false;
        e.armorBuff(amount);
        startCooldown(cooldown);
        return true;
    }

    @Override
    public String getDescription() {
        StringBuilder bld = new StringBuilder();
        bld.append("bonus d'armure : ").append(amount).append("\n");
        bld.append("portee: ");
        if (minRange==maxRange) bld.append(minRange).append("\n");
        else bld.append(minRange).append("-").append(maxRange).append("\n");
        bld.append("temps de recuperation : ").append(cooldown).append(" tours \n");
        bld.append("temps restant avant utilisation : ").append(roundCooldown).append(" tours");
        return bld.toString();
    }

    @Override
    public Boolean isAlly(Player player){
        if(player!=player.getGame().getCurrentPlayer()){
            return false;
        }
        return true;
    }

    @Override
    public void startCooldown(int cd) {
        roundCooldown = cd;
    }

    @Override
    public void reduceCooldown() {
        if(roundCooldown>0){
            roundCooldown-=1;
        }
    }
}