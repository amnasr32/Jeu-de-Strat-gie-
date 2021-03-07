package view;

import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.MeshView;
import model.Player;

public class Controller {

    Player player;
    MainView view;

    private double anchorX=0, anchorY=0;

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

    // initialise les contôles de la caméra
    public void setCameraControls(GameCamera camera, SubScene scene) {

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

    public void setGameGridControls(GameGrid grid) {
        Hexagon[][] hexagons = grid.getHexagons();
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[0].length; j++) {
                hexagons[i][j].allowHighlight(true);
            }
        }
    }
}
