package view;

import model.Player;


public class Controller {

    Player player;
    MainView view;

    public Controller() {}

    public Controller(MainView view) {
        this.view = view;
    }

    public void initializePlayer() {
        player=new Player(view);
    }

    public void loadLevel() {
        player.loadLevel();
    }

    public void mkGameGrid() {
        byte[][] heightGrid = player.getHeightGrid();
        view.makeGameScene(heightGrid);
    }

    public void startGame() {
        player.start();
    }

    public void endTurn() {
        player.endTurn();
    }

    public void makePath(int x, int y) {
        player.makePath(x,y);
    }

    public void move(byte[] path) {
        player.move(path);
    }

    public void doAction(int actionNb, int x, int y) {
        player.doAction(actionNb, x, y);
    }

    public void selectAction(int actionNb) {
        player.selectAction(actionNb);
    }

    public void cancelAction() {
        player.cancelAction();
    }
}
