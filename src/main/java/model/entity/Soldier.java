package model.entity;

import model.Player;

public class Soldier extends Entity {

    Soldier(int x, int y, Player player) {
        super.x=x;
        super.y=y;
        super.player=player;
        super.hp=10;
    }
}
