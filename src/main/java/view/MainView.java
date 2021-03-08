package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Player;
import model.entity.Entity;
import view.*;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;

public class MainView extends Application {

    private Controller ctrl;
    private Scene mainScene; // tout ce qui est en 2D : les boutons, les menus, etc
    private SubScene scene3D; // tout ce qui est en 3D est ici
    private Group mainGroup;

    private int height = 720;
    private int width = 1080;

    GameGrid gameGrid=null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainGroup = new Group();
        mainScene=new Scene(mainGroup,width,height);
        ctrl = new Controller(this);


        primaryStage.setTitle("jeu de stratégie");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        ctrl.initializePlayer();
        ctrl.loadLevel();
        ctrl.mkGameGrid();
        ctrl.startGame();
    }

    public void makeGameScene(byte[][] heightGrid) {

        gameGrid = new GameGrid(heightGrid);
        scene3D=new SubScene(gameGrid, width, height, true, SceneAntialiasing.BALANCED);
        GameCamera camera = new GameCamera();
        scene3D.setCamera(camera);

        ctrl.setCameraControls(camera, scene3D);
        ctrl.setGameGridControls(gameGrid);

        mainGroup.getChildren().add(scene3D);
        mainGroup.getChildren().add(new UserInterface(width, height));
    }

    public void addEntity(Entity e) {
        gameGrid.addEntity(e.getX(), e.getY());
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
