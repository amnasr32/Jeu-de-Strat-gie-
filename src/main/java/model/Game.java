package model;
import model.entity.Entity;
import model.entity.Soldier;

import java.util.LinkedList;

public class Game {

    Grid grid;
    Player player1;
    Player player2;
    LinkedList<Entity> playableEntities; // liste de toutes les entités en jeu
    int entTeam1; // nombre d'entités de l'équipe 1 actuellement en jeu
    int entTeam2; // nombre d'entités de l'équipe 2 actuellement en jeu

    public Game(Grid grid, Player player1, Player player2) {
        this.grid=grid;
        this.player1=player1;
        this.player2=player2;

    }

    public Grid getGrid() {
        return grid;
    }

    void start() {
        initPlayableEntities();
        while (!gameIsOver()) {
            // les entités jouent les unes après les autres
            for (Entity e : playableEntities) {
                round(e);
            }
        }
    }

    // chaque joueur pose ses unités
    private void initPlayableEntities() {
        int h=grid.getHeight();
        int w=grid.getWidth();
        Entity e1 = new Soldier(1,1,player1);
        Entity e2 = new Soldier(h-2,w-2,player2);
        grid.getCell(1,1).setEntity(e1);
        grid.getCell(h-2,w-2).setEntity(e2);
        player1.addEntityToView(e1);
        player1.addEntityToView(e2);
        player2.addEntityToView(e1);
        player2.addEntityToView(e2);
    }

    // renvoie true si le jeu est fini
    private boolean gameIsOver() {
        return entTeam1<=0 || entTeam2<=0;
    }

    // déroulement d'un round pour l'entité e
    private void round(Entity e) {

    }

    // bouge l'entité e en suivant le chemin donné en paramètre
    private void move(Entity e, byte[] path) {
        for (byte b: path) {
            grid.move(e, b);
            // TODO : update view
        }

    }


}
