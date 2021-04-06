package model;

import model.entity.Entity;
import view.MainView;

// pour le moment cette classe ne sert que à override les fonctions
// qui causeraient un NullPointerException
public class PlayerBot extends Player {
    public PlayerBot() {
        super(null);
    }

    @Override
    public void addEntityToView(Entity e) {}
    @Override
    protected void focusFirstEntity(int i, boolean isCurrentPlayer) {}
    @Override
    protected void focusNextEntity(int i, boolean isCurrentPlayer) {
        if (isCurrentPlayer) endTurn();
    }
    @Override
    protected void moveEntityInView(byte direction) {}
    @Override
    protected void updateHpView(int i, int newHp) {}
    @Override
    protected void removeEntity(int i) {}
    @Override
    protected void endGame(boolean hasWon) {
        if (hasWon) System.out.println("git gud casul");
    }

}
