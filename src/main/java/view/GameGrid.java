package view;

import javafx.scene.Group;
import javafx.scene.shape.*;

import java.util.LinkedList;

public class GameGrid extends Group {

    Hexagon[][] hexagons;
    LinkedList<Sphere> entities;

    public GameGrid(byte[][] heightGrid) {
        super();
        int height = heightGrid.length;
        int width = heightGrid[0].length;
        hexagons = new Hexagon[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                hexagons[i][j]=new Hexagon(i,j, heightGrid[i][j]);
                getChildren().add(hexagons[i][j]);
            }
        }
        this.setTranslateZ(((float)height/2)*13);
        this.setTranslateX(((float)width/2)*-15);
        entities=new LinkedList<>();
    }

    public GameGrid(int height, int width) {
        super();
        hexagons = new Hexagon[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                hexagons[i][j]=new Hexagon(i,j, 0);
                getChildren().add(hexagons[i][j]);
            }
        }
        this.setTranslateZ(((float)height/2)*13);
        this.setTranslateX(((float)width/2)*-15);
    }

    public Hexagon[][] getHexagons() {
        return hexagons;
    }

    public void addEntity(int x, int z) {
        Sphere s = new Sphere(5);
        float f = (x%2==0) ? 0 : 7.5f;
        s.setTranslateZ(hexagons[x][z].getTranslateZ());
        s.setTranslateX(hexagons[x][z].getTranslateX());
        s.setTranslateY(hexagons[x][z].getTranslateY()-5);
        entities.add(s);
        getChildren().add(s);
    }


}
