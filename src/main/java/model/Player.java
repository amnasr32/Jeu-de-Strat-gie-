package model;
import view.MainView;

public class Player {
    MainView view;
    Game game;

    public Player(MainView view) {
        this.view=view;
        game=null;
    }

    // ---------------------------------
    //  fonctions qui modifient le jeu :
    // ---------------------------------

    public void loadLevel() { //todo : permettre de charger une grille
        Grid grid = new Grid(10, 15);
        game = new Game(grid, this, null);
    }

    // ---------------------------------
    //  fonctions qui modifient la vue :
    // ---------------------------------
}
