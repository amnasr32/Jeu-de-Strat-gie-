package view;

import model.Player;
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

    @Override
    public void start(Stage primaryStage) throws Exception {


        GameGrid grid = new GameGrid(10, 10);

        Scene scene = new Scene(grid, width, height);
        GameCamera camera = new GameCamera();
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
        ctrl = new Controller(scene);
        ctrl.setCameraControls(camera);

        primaryStage.setTitle("jeu de stratégie");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
