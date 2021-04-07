package model;
import model.action.Action;
import model.entity.Entity;
import view.MainView;

import java.util.LinkedList;

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

    public void setGame(Game game) {
        this.game = game;
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
        /**ici on set la grille du niveau =>on la sérialise  **/
        //level.SetGrid(grid);
        //level.createLevel();
        /**ici on affiche la grille qu'on a sérialisé => on deserialise**/
        PlayerBot pb = new PlayerBot();
        game = new Game(level.showLevel(), this, pb);
        pb.setGame(game);
    }

    public void start() {
        game.start();
    }
    public int getnbEntity() {
    	return game.nb;
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

    public void doAction(int actionNb, int x, int y) {
        game.doAction(this, actionNb, x, y);
    }

    public void selectAction(int actionNb) {
        game.selectAction(this, actionNb);
    }

    public void cancelAction() {
        game.cancelAction(this);
    }

    public void addEntityToGame(int x, int y, int entity_type) {
        game.tryToAddEntityToGame(this, x,y,entity_type);
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
        Action[] actions = e.getActions();
        String[][] array = new String[actions.length][2];
        for (int i = 0; i < actions.length; i++) {
            array[i][0]=actions[i].getName();
            array[i][1]=actions[i].getDescription();
        }
        view.addEntity(e.getX(), e.getY(), e.getPlayer()==this,e.toString(), e.getHp(), e.getMp(), array);
    }

    protected void focusFirstEntity(int i, boolean isCurrentPlayer) {
        view.focusFirstEntity(i);
        view.allowGridViewControls(isCurrentPlayer);
        view.showActionButtons(isCurrentPlayer);
        view.resetAction();
    }

    protected void focusNextEntity(int i, boolean isCurrentPlayer) {
        view.focusNextEntity(i);
        view.allowGridViewControls(isCurrentPlayer);
        view.showActionButtons(isCurrentPlayer);
        view.resetAction();
    }

    protected void moveEntityInView(byte direction) {
        view.moveViewEntity(direction);
    }

    protected void updateHpView(int i, int newHp) {
        view.updateHp(i, newHp);
    }

    protected void resetAction() {
        view.resetAction();
    }

    protected void updateActionRangeView(LinkedList<int[]> coordList) {
        int[][] coords = new int[coordList.size()][2];
        for (int i = 0; i < coords.length; i++) {
            coords[i][0]=coordList.get(i)[0];
            coords[i][1]=coordList.get(i)[1];
        }
        view.updateActionRange(coords);
    }

    protected void removeEntity(int i) {
        view.removeEntity(i);
    }

    protected void endGame(boolean hasWon) {
        view.endGame(hasWon);
    }

    public void canPressReadyButton(boolean b) {
        view.canPressReadyButton(b);
    }

}
