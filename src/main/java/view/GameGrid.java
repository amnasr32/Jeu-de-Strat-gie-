package view;

import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
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

        initLight();
        initSky();
        initGround();
    }

    // créé les lumières de la scène
    private void initLight() {
        AmbientLight ambientLight = new AmbientLight(Color.rgb(50,30,20));
        PointLight pointLight = new PointLight(Color.rgb(255,240,250));
        pointLight.setTranslateY(-1400);
        pointLight.setTranslateX(-300);
        getChildren().add(ambientLight);
        getChildren().add(pointLight);
    }

    // créé une sphère qui sert de ciel
    private void initSky() {
        Sphere sky = new Sphere(3000);
        PhongMaterial material = new PhongMaterial();
        material.setSpecularColor(Color.SKYBLUE);
        material.setDiffuseColor(Color.WHITE);
        material.setSpecularPower(5);
        sky.setMaterial(material);
        sky.setCullFace(CullFace.FRONT);
        getChildren().add(sky);
    }

    // créé un cube qui sert de sol
    private void initGround() {
        Box ground = new Box(30000, 2, 30000);
        PhongMaterial material = new PhongMaterial();
        material.setSpecularColor(Color.BLACK);
        material.setDiffuseColor(Color.SANDYBROWN);
        ground.setMaterial(material);
        ground.setTranslateY(8);
        getChildren().add(ground);
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
