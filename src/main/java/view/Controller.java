package view;

import javafx.scene.SubScene;
import javafx.scene.input.ScrollEvent;
import model.Player;

public class Controller {

    Player player;
    MainView view;

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

    public void setGameGridControls(GridView grid) {
        Hexagon[][] hexagons = grid.getHexagons();
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[0].length; j++) {
                hexagons[i][j].allowHighlight(true);
                hexagons[i][j].allowMovement(true);
            }
        }
    }

    public void makePath(int x, int y) {
        player.makePath(x,y);
    }

    public void move(byte[] path) {
        player.move(path);
    }
}
