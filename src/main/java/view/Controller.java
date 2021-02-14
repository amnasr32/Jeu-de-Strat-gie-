package view;

import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class Controller {

    Scene scene;

    private double anchorX=0, anchorY=0;

    public Controller(Scene scene) {
        this.scene=scene;
    }

    public void setCameraControls(GameCamera camera) {

        // permet de zoomer avec la roue de la souris
        scene.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY()/2;
            camera.zoom(delta);
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event ->  {
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
                    camera.translate(0,5);
                    break;
                case DOWN:
                    camera.translate(0,-5);
                    break;
                case LEFT:
                    camera.translate(-5,0);
                    break;
                case RIGHT:
                    camera.translate(5,0);
                    break;
            }
        });

        scene.setOnMousePressed(event -> {
            anchorX=event.getSceneX();
            anchorY=event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {

            double x=event.getSceneY()-anchorY;
            double y=anchorX-event.getSceneX();

            anchorX=event.getSceneX();
            anchorY=event.getSceneY();

            if (event.isSecondaryButtonDown()) {
                camera.translate(y/4,x/4);
            }
            else if (event.isMiddleButtonDown()) {
                camera.rotateY(-y/4);
                camera.rotateX(-x/4);
            }
        });

    }
}
