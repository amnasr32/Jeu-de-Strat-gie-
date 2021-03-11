package model;
import model.entity.Entity;
import view.MainView;

/**
 * La classe Player représente un joueur
 * Elle reçoit directement les inputs de la View, et
 * est responsable de transmettre l'état du jeu à la View
 * */
public class Player {
    private MainView view;
    private Game game;
    private Level level;

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
        Level level = new Level();
        /**ici on set la grille du niveau et on la sérialise**/
        //level.SetGrid(grid);
        //level.createLevel();
        /**ici on affiche la grille qu'on a sérialisé => on deserialise**/
        game = new Game(level.showLevel(), this, new PlayerBot());
    }

    public void start() {
        game.start();
    }

    public void endTurn() {
        game.nextRound(this);
    }

    public void makePath(int x, int y) {
        byte[] path = game.makePath(x,y);
        view.drawPath(path);
    }

    public void move(byte[] path) {
        game.move(this, path);
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

    protected void addEntityToView(Entity e) {
        view.addEntity(e.getX(), e.getY(), e.getPlayer()==this);
    }

    protected void focusFirstEntity(int i, boolean isCurrentPlayer) {
        view.focusFirstEntity(i);
        view.allowGridViewControls(isCurrentPlayer);
    }

    protected void focusNextEntity(int i, boolean isCurrentPlayer) {
        view.focusNextEntity(i);
        view.allowGridViewControls(isCurrentPlayer);
    }

    protected void moveEntityInView(byte direction) {
        view.moveViewEntity(direction);
    }




}
