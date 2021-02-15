import view.*;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Launcher extends Application {

    Controller ctrl;

    private int height = 720;
    private int width = 1080;

    @Override
    public void start(Stage primaryStage) throws Exception {

        GameCamera camera = new GameCamera();
        GameGrid grid = new GameGrid(10, 10);

        Scene scene = new Scene(grid, width, height);
        ctrl = new Controller(scene);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
        ctrl.setCameraControls(camera);

        primaryStage.setTitle("jeu de stratégie");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
