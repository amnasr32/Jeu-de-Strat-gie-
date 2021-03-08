package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;

// Affichage des unités
public class Unit extends Group {

    private Sphere sphere;
    private Sphere outerSphere;
    private Cylinder cylinder;
    private int x, y;

    Unit(int x, int y, boolean isAlly) {
        super();
        initSphere();
        initCylinder(isAlly);
        initOuterSphere(isAlly);
        getChildren().add(sphere);
        getChildren().add(cylinder);
        getChildren().add(outerSphere);
        this.x=x;
        this.y=y;
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

    private void initOuterSphere(boolean isAlly) {
        outerSphere= new Sphere(6);
        PhongMaterial m = new PhongMaterial();
        if (isAlly) {
            m.setDiffuseColor(Color.LIGHTGREEN);
        } else {
            m.setDiffuseColor(Color.RED);
        }
        outerSphere.setMaterial(m);
        outerSphere.setCullFace(CullFace.FRONT);
        outerSphere.setVisible(false);
    }

    public void highlight(boolean bool) {
        outerSphere.setVisible(bool);
    }
}
