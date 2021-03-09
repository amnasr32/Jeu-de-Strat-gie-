package view;

import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;

public class Hexagon extends Group {

    Cylinder cylinder;
    Cylinder outerCylinder;
    int x, y, height;

    MainView view;

    public Hexagon(int i, int j, int h, MainView view) {
        super();
        this.view=view;
        x=i;
        y=j;
        height=h;
        cylinder = new Cylinder(8, 1+h*3, 6);
        initOuterCylinder(h);
        getChildren().add(cylinder);
        float f = (i%2==0) ? 0 : 7.5f;
        setTranslateZ(i*-13);
        setTranslateX(f+j*15);
        setTranslateY(h*-1.5);
        makeGreen(false);
    }

    private void initOuterCylinder(int h) {
        outerCylinder = new Cylinder(9, 2+h*3, 6);
        outerCylinder.setVisible(false);
        PhongMaterial m = new PhongMaterial();
        m.setDiffuseColor(Color.YELLOW);
        try {
            m.setSelfIlluminationMap(new Image("yellowPixel.jpg"));
        } catch (Exception e) {
            System.out.println(e);
        }
        outerCylinder.setMaterial(m);
        outerCylinder.setCullFace(CullFace.FRONT);
        getChildren().add(outerCylinder);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public void makeGreen(boolean bool) {
        PhongMaterial material = new PhongMaterial();
        if (bool) {
            material.setDiffuseColor(Color.LAWNGREEN);
        } else {
            material.setDiffuseColor(Color.LIGHTYELLOW);
        }
        material.setSpecularColor(Color.BLACK);
        cylinder.setMaterial(material);
    }

    public void setHighlight(boolean bool) {
        outerCylinder.setVisible(bool);
    }

    public void allowHighlight(boolean bool) {
        if (bool) {
            setOnMouseEntered(event -> {
                setHighlight(true);
                view.setPointedXY(x,y);
                view.makePath(x,y);
            });
            setOnMouseExited(event -> {
                setHighlight(false);
                view.cleanPath();
            });
        } else {
            setOnMouseEntered( event -> {});
            setOnMouseExited( event -> {});
            makeGreen(false);
            setHighlight(false);
        }
    }

    public void allowMovement(boolean bool) {
        if (bool) {
            setOnMouseClicked( event -> {
                view.moveModelEntity();
            });
        } else {
            setOnMouseClicked( event -> {});
        }
    }

}
