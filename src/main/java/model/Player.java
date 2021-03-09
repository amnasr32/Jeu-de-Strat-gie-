package model;
import model.entity.Entity;
import view.GameGrid;
import view.MainView;

/**
 * La classe Player représente un joueur
 * Elle reçoit directement les inputs de la View, et
 * est responsable de transmettre l'état du jeu à la View
 * */
public class Player {
    MainView view;
    Game game;
    boolean endTurn = false;

    public Player() {
        view=null;
        game=null;
    }

    public Player(MainView view) {
        this.view=view;
        game=null;
    }

    // ---------------------------------
    //  fonctions qui modifient le jeu :
    // ---------------------------------

    public void loadLevel() { //todo : permettre de charger une grille sérialisée
        // grille initialisée ici pour le test
        Grid grid = new Grid(10, 15);
        grid.getCell(3,4).setHeight((byte)1);
        grid.getCell(5,5).setHeight((byte)1);
        grid.getCell(5,6).setHeight((byte)2);
        game = new Game(grid, this, new PlayerBot());
    }

    public void start() {
        game.start();
    }

    // ---------------------------------
    //  fonctions qui modifient la vue :
    // ---------------------------------

    // permet d'envoyer à la view les paramètres necessaires
    // pour construire une grille de jeu
    public byte[][] getHeightGrid() {
        Grid grid = game.getGrid();
        int h=grid.getHeight();
        int w=grid.getWidth();
        byte[][] heightGrid = new byte[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                heightGrid[i][j]=grid.getCell(i,j).getHeight();
            }
        }
        return heightGrid;
    }

    public void addEntityToView(Entity e) {
        // une copie est envoyé car il ne fait pas que
        // l'utilisateur puisse modifier l'entité originale
        Entity copy = e.copy();
        view.addEntity(copy);
    }

    public void round(Entity e){

        //todo : event pour le clique sur l'entité e ->

        //todo : event pour le clique d'une entité autre que e ->
        if (e.isAlly(/* entité cliquée */ e)) {
            //todo : cliquer sur une entité allié montre certaines choses
        } else {
            //todo : attaquer etc
        }

        //le joueur appuie sur le button de fin de tour
         //todo : ajouter la possibilité au joueur de finir le tour en appuyant sur un bouton qui change juste
        //l'attribut endTurn

    }

    public boolean endTurn(){
        return endTurn = true;
    }

    boolean startTurn(){
        return endTurn = false;
    }


}
