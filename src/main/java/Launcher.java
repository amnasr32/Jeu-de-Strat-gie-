import view.*;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Launcher extends Application {

    private int height = 720;
    private int width = 1080;

    private double AnchorX, AnchorY;

    @Override
    public void start(Stage primaryStage) throws Exception {

        GameCamera camera = new GameCamera();
        Box thingIn3D = new Box(40, 10, 20);

        Group group = new GameGrid(10, 10);
        //group.getChildren().add(thingIn3D);

        Scene scene = new Scene(group, width, height);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        camera.zoom(-200);
        camera.rotateX(-20);

        primaryStage.addEventHandler(ScrollEvent.SCROLL,  event -> {
            double delta = event.getDeltaY()/2;
            camera.zoom(delta);
        });

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event ->  {
            switch (event.getCode()) {
                case Z:
                    camera.rotateX(5);
                    break;
                case S:
                    camera.rotateX(-5);
                    break;
                case Q:
                    camera.rotateY(-5);
                    break;
                case D:
                    camera.rotateY(5);
                    break;
                case UP:
                    camera.translate(0,-5);
                    break;
                case DOWN:
                    camera.translate(0,5);
                    break;
                case LEFT:
                    camera.translate(5,0);
                    break;
                case RIGHT:
                    camera.translate(-5,0);
                    break;
            }
        });

        primaryStage.setTitle("3D test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
