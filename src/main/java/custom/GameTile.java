package custom;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;


public class GameTile extends Cylinder {
    private PhongMaterial material;
    private final Image tileFillStone = new Image("tiles/stone.png");
    private final Image tileFillDirt = new Image("tiles/dirt.png");
    private final Image tileFillSand = new Image("tiles/sand.png");
    private final Image tileFillGrass = new Image("tiles/grass.png");

    public GameTile() {
        super();
    }

    public GameTile(double v, double v1, int i) {
        super(v, v1, i);
    }

    public void initImageStone(){
        material = new PhongMaterial();
        material.setDiffuseMap(tileFillStone);
        setMaterial(material);
    }

    public void initImageDirt(){
        material = new PhongMaterial();
        material.setDiffuseMap(tileFillDirt);
        setMaterial(material);
    }

    public void initImageSand(){
        material = new PhongMaterial();
        material.setDiffuseMap(tileFillSand);
        setMaterial(material);
    }

    public void initImageGrass(){
        material = new PhongMaterial();
        material.setDiffuseMap(tileFillGrass);
        setMaterial(material);
    }

    public Image getTileFillStone() {
        return tileFillStone;
    }

    public Image getTileFillDirt() {
        return tileFillDirt;
    }

    public Image getTileFillGrass() {
        return tileFillGrass;
    }

    public Image getTileFillSand() {
        return tileFillSand;
    }
}
