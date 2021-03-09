package model;

import model.entity.Entity;
import view.MainView;

// on verra plus tard ce que fait cette classe
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
