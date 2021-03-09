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
    protected void focusNextEntity(int i, boolean isCurrentPlayer) {}
    @Override
    protected void moveEntityInView(byte direction) {}
}
