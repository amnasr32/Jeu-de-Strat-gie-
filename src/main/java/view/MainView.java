package view;

import model.Player;
import model.entity.Entity;
import view.*;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainView extends Application {

    Player player;
    Controller ctrl;

    private int height = 720;
    private int width = 1080;

    GameGrid gameGrid=null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        player=new Player(this);
        player.loadLevel();
        byte[][] heightGrid = player.getHeightGrid();

        Scene scene = makeGameScene(heightGrid);

        primaryStage.setTitle("jeu de stratégie");
        primaryStage.setScene(scene);
        primaryStage.show();

        player.start();
    }

    public Scene makeGameScene(byte[][] heightGrid) {
        gameGrid = new GameGrid(heightGrid);
        Scene scene = new Scene(gameGrid, width, height);
        GameCamera camera = new GameCamera();
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
        ctrl = new Controller(scene);
        ctrl.setCameraControls(camera);
        return scene;
    }

    public void addEntity(Entity e) {
        gameGrid.addEntity(e.getX(), e.getY());
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
