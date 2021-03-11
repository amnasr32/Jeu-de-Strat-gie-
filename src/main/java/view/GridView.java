package view;

import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;

public class GridView extends Group {

    private final Hexagon[][] hexagons;
    private final MainView view;

    public GridView(byte[][] heightGrid, MainView view) {
        super();
        this.view=view;
        int height = heightGrid.length;
        int width = heightGrid[0].length;
        hexagons = new Hexagon[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                hexagons[i][j]=new Hexagon(i,j, heightGrid[i][j], view);
                getChildren().add(hexagons[i][j]);
            }
        }
        this.setTranslateZ(((float)height/2)*13);
        this.setTranslateX(((float)width/2)*-15);

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

    public void addEntity(EntityView u, int x, int z) {
        u.setTranslateX(hexagons[x][z].getTranslateX());
        u.setTranslateZ(hexagons[x][z].getTranslateZ());
        u.setTranslateY(hexagons[x][z].getTranslateY()-6.5);
        getChildren().add(u);
    }

    public void moveEntity(EntityView u, byte direction) {
        Hexagon destination = getAdjHexagon(u.getX(),u.getY(),direction);
        u.updateCoords(direction);
        u.setTranslateX(destination.getTranslateX());
        u.setTranslateZ(destination.getTranslateZ());
        u.setTranslateY(destination.getTranslateY()-6.5-destination.height*2);
    }

    public Hexagon getHexagon(int x, int y) {
        return hexagons[x][y];
    }

    // copie de la fonction getAdjCell de model.Cell.java
    public Hexagon getAdjHexagon(int h, int w, int direction) {
        boolean odd = h%2==0;
        int height = hexagons.length;
        int width = hexagons[0].length;
        switch (direction) {
            case 0:
                if (h<=0 || !odd && w>=width-1) return null;
                if (odd) return hexagons[h-1][w];
                else return hexagons[h-1][w+1];
            case 1:
                if (w>=width-1) return null;
                return hexagons[h][w+1];
            case 2:
                if (h>=height-1 || !odd && w>=width-1) return null;
                if (odd) return hexagons[h+1][w];
                else return hexagons[h+1][w+1];
            case 3:
                if (h>=height-1 || odd && w<=0) return null;
                if (odd) return hexagons[h+1][w-1];
                else return hexagons[h+1][w];
            case 4:
                if (w<=0) return null;
                return hexagons[h][w-1];
            case 5:
                if (h<=0 || odd && w<=0) return null;
                if (odd) return hexagons[h-1][w-1];
                else return hexagons[h-1][w];

            default:
                break;
        }
        return null;
    }

    public Hexagon getAdjHexagon(Hexagon h, int direction) {
        if (h==null) return null;
        return getAdjHexagon(h.getX(), h.getY(), direction);
    }

    public void allowControls(boolean bool) {
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[0].length; j++) {
                hexagons[i][j].allowHighlight(bool);
                hexagons[i][j].allowClickAction(bool);
            }
        }
    }

}
