package model;
import model.entity.Entity;
import java.util.LinkedList;

public class Game {

    Grid grid;
    Player player1;
    Player player2;
    LinkedList<Entity> playableEntities; // liste de toutes les entités en jeu
    int entTeam1; // nombre d'entités de l'équipe 1 actuellement en jeu
    int entTeam2; // nombre d'entités de l'équipe 2 actuellement en jeu

    public Game(Grid grid, Player player1, Player player2) {

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

    }

    // renvoie true si le jeu est fini
    private boolean gameIsOver() {
        return entTeam1<=0 || entTeam2<=0;
    }

    // déroulement d'un round pour l'entité e
    private void round(Entity e) {

    }

    // bouge l'entité e au point x,y
    private void move(Entity e, int x, int y) {

    }


}
