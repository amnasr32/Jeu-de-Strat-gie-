package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class GameGrid extends Group {

    MeshView[][] hexagons;

    public GameGrid(int height, int width) {
        super();
        hexagons = new MeshView[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                hexagons[i][j]=makeHexagon(i,j);
                getChildren().add(hexagons[i][j]);
            }
        }
    }

    private MeshView makeHexagon(int i, int j) {
        TriangleMesh p = new TriangleMesh();
        p.getPoints().addAll(0,0,0,
                3.5f,0,2,
                7,0,0,
                7,0,-4,
                3.5f,0,-6,
                0,0,-4
        );
        p.getTexCoords().addAll(
                0,0.5f,
                0.33f,1,
                0.66f,1,
                1,0.5f,
                0.66f,0,
                0.33f,0
        );
        p.getFaces().addAll(
            0,0,2,2,1,1,
                0,0,5,5,2,2,
                2,2,5,5,3,3,
                3,3,5,5,4,4
        );
        System.out.println(p.getTexCoordElementSize());
        MeshView m=new MeshView(p);
        float f = (i%2==0) ? 0 : 4f;
        m.setTranslateZ(i*-7);
        m.setTranslateX(f+j*8);
        return m;
    }


}
