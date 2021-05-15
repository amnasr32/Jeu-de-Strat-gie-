package view;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.util.HashMap;

// Affichage des unités
public class EntityView extends Group {

    private MainView view;

    private Sphere sphere;
    private Sphere aboveMarker;
    private Cylinder cylinder;
    private int x, y;

    private final int maxHp;
    private int hp;

    private int armor;

    private int poisonStatut;
    private int rootStatut;

    private final int maxMp;
    private int mp;

    private final String[] actionNames;
    private final String[] actionDesc;
    private String[] actionCd;
    private final String name;

    private final static HashMap<String, String> frToEnNames= new HashMap<>();

    static {
        frToEnNames.put("Soldat", "Soldier");
        frToEnNames.put("Chevalier", "Knight");
        frToEnNames.put("Magicien", "Wizard");
        frToEnNames.put("Druide", "Druid");
        frToEnNames.put("Clerc", "Cleric");
    }

    EntityView(MainView view, int x, int y, boolean isAlly, String name, int hp, int mp, int armor, String[][] actions) {
        super();
        this.view=view;
        this.hp=hp;
        maxHp=hp;
        this.armor=armor;
        this.mp=mp;
        maxMp=mp;
        this.name=name;
        poisonStatut=0;
        rootStatut=0;

        //initSphere();
        //getChildren().add(sphere);

        initMarker(isAlly);
        getChildren().add(aboveMarker);
        initCylinder(isAlly);
        getChildren().add(cylinder);

        this.x=x;
        this.y=y;

        initImage(name);

        int length=actions.length;
        actionNames=new String[length];
        actionDesc=new String[length];
        actionCd=new String[length];
        for (int i = 0; i < length; i++) {
            actionNames[i]=actions[i][0];
            actionDesc[i]=actions[i][1];
            actionCd[i]=actions[i][2];
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void setXY(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public void decreaseMp() {
        mp--;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setArmor(int armor) {this. armor = armor; }

    public void setPoisonStatut(int poisonStatut){
        this.poisonStatut=poisonStatut;
    }

    public void setRootStatut(int rootStatut){
        this.rootStatut=rootStatut;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getArmor() { return armor;}

    public int getPoisonStatut() { return poisonStatut;}

    public int getRootStatut() { return rootStatut;}

    public int getMp() {
        return mp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public String[] getActionNames() {
        return actionNames;
    }

    public String[] getActionDesc() {
        return actionDesc;
    }

    public String[] getActionCd() { return actionCd; }

    public String getName() {
        return name;
    }

    public void updateCoords(int direction) {
        boolean odd = x%2==0;
        switch (direction) {
            case 0:
                x--;
                if (!odd) y++;
                break;
            case 1:
                y++;
                break;
            case 2:
                x++;
                if (!odd) y++;
                break;
            case 3:
                x++;
                if (odd) y--;
                break;
            case 4:
                y--;
                break;
            case 5:
                x--;
                if (odd) y--;
                break;
        }
    }

    private void initSphere() {
        sphere = new Sphere(5);
        PhongMaterial m = new PhongMaterial();
        m.setDiffuseColor(Color.LIGHTBLUE);
        sphere.setMaterial(m);
    }

    private void initCylinder(boolean isAlly) {
        cylinder = new Cylinder(5, 1);
        cylinder.setTranslateY(5.5);
        PhongMaterial m = new PhongMaterial();
        if (isAlly) {
            m.setDiffuseColor(Color.GREEN);
        } else {
            m.setDiffuseColor(Color.DARKRED);
        }
        cylinder.setMaterial(m);

    }

    private void initMarker(boolean isAlly) {
        aboveMarker = new Sphere(1.5);
        PhongMaterial m = new PhongMaterial();
        if (isAlly) {
            m.setDiffuseColor(Color.LIGHTGREEN);
            try {
                m.setSelfIlluminationMap(new Image("greenPixel.jpg"));
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            m.setDiffuseColor(Color.RED);
            try {
                m.setSelfIlluminationMap(new Image("redPixel.jpg"));
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        aboveMarker.setMaterial(m);
        //outerSphere.setCullFace(CullFace.FRONT);
        aboveMarker.setTranslateY(-13);
        aboveMarker.setVisible(false);
    }

    public void highlight(boolean bool) {
        aboveMarker.setVisible(bool);
    }

    public void showInfoOnHover(boolean bool) {
        if (bool) {
            setOnMouseEntered(event -> {
                view.showEntityDetails(this);
                view.highlightHexagon(x, y, true);
                view.setPointedXY(x, y);
            });
            setOnMouseExited(event -> {
                view.hideEntityDetails();
                view.highlightHexagon(x, y, false);
            });
        } else {
            setOnMouseEntered(event -> {
                view.showEntityDetails(this);
            });
            setOnMouseExited(event -> {
                view.hideEntityDetails();
            });
        }
    }

    public void allowActionOnClick(boolean bool) {
        if (bool) {
            setOnMouseClicked(event -> {
                view.doAction();
            });
        } else {
            setOnMouseClicked(event -> {});
        }
    }

    private void initImage(String name) {
        Image img = new Image("icons/portraits/"+frToEnNames.get(name)+".png");
        ImageView imgv = new ImageView(img);
        imgv.setPreserveRatio(true);
        imgv.setFitWidth(14);
        imgv.setTranslateY(-10);
        imgv.setTranslateX(-7);
        double x = this.getTranslateX()+7;
        double y = this.getTranslateY();
        double z = this.getTranslateZ();
        imgv.getTransforms().add(new Rotate(-35, x, y, z, Rotate.Y_AXIS));
        getChildren().add(imgv);
    }

}
