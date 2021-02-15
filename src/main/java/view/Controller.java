package view;

import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;

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

        scene.setOnMousePressed(event -> {
            anchorX=event.getSceneX();
            anchorY=event.getSceneY();
        });

        // permet de déplacer / trouner la caméra avec le click droit ou le click molette
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
