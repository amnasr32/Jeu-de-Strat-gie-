package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

public class Hexagon extends Group {

    Cylinder cylinder;

    public Hexagon(int i, int j, int h) {
        super();
        cylinder = new Cylinder(8, 1+h*3, 6);
        getChildren().add(cylinder);
        float f = (i%2==0) ? 0 : 7.5f;
        setTranslateZ(i*-13);
        setTranslateX(f+j*15);
        setTranslateY(h*-1.5);
        setHighlight(false);
    }

    public void setHighlight(boolean bool) {
        PhongMaterial material = new PhongMaterial();
        if (bool) {
            material.setDiffuseColor(Color.LAWNGREEN);
        } else {
            material.setDiffuseColor(Color.LIGHTYELLOW);
        }
        material.setSpecularColor(Color.BLACK);
        cylinder.setMaterial(material);
    }

    public void allowHighlight(boolean bool) {
        if (bool) {
            setOnMouseEntered(event -> {
                setHighlight(true);
            });
            setOnMouseExited(event -> {
                setHighlight(false);
            });
        } else {
            setOnMouseEntered( event -> {});
            setOnMouseExited( event -> {});
            setHighlight(false);
        }

    }
}
