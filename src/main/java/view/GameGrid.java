package view;

import javafx.scene.Group;
import javafx.scene.shape.*;

import java.util.LinkedList;

public class GameGrid extends Group {

    MeshView[][] hexagons;
    LinkedList<Sphere> entities;

    public GameGrid(byte[][] heightGrid) {
        super();
        int height = heightGrid.length;
        int width = heightGrid[0].length;
        hexagons = new MeshView[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                hexagons[i][j]=makeHexagon(i,j, heightGrid[i][j]);
                getChildren().add(hexagons[i][j]);
            }
        }
        this.setTranslateZ(((float)height/2)*13);
        this.setTranslateX(((float)width/2)*-15);
        entities=new LinkedList<>();
    }

    public GameGrid(int height, int width) {
        super();
        hexagons = new MeshView[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                hexagons[i][j]=makeHexagon(i,j, 0);
                getChildren().add(hexagons[i][j]);
            }
        }
        this.setTranslateZ(((float)height/2)*13);
        this.setTranslateX(((float)width/2)*-15);
    }

    private MeshView makeHexagon(int i, int j, int h) {
        TriangleMesh p = new TriangleMesh();
        p.getPoints().addAll(0,0,0,
                7,0,4,
                14,0,0,
                14,0,-8,
                7,0,-12,
                0,0,-8
        );
        // je sais pas à quoi ça sert, ni si c'est correct
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
        float f = (i%2==0) ? 0 : 7.5f;
        m.setTranslateZ(i*-13);
        m.setTranslateX(f+j*15);
        m.setTranslateY(h*-3);
        return m;
    }

    public void addEntity(int x, int z) {
        Sphere s = new Sphere(5);
        float f = (x%2==0) ? 0 : 7.5f;
        s.setTranslateZ(hexagons[x][z].getTranslateZ()-3.6f);
        s.setTranslateX(hexagons[x][z].getTranslateX()+7.2f);
        s.setTranslateY(hexagons[x][z].getTranslateY()-5);
        entities.add(s);
        getChildren().add(s);
    }


}
