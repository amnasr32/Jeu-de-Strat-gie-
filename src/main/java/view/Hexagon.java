package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Hexagon extends Group {

    MeshView meshView;
    Cylinder cylinder;

    public Hexagon(int i, int j, int h) {
        super();
        cylinder = new Cylinder(8, 0.5+h*3, 6);
        //meshView=new MeshView(makeTriangleMesh());
        getChildren().add(cylinder);
        float f = (i%2==0) ? 0 : 7.5f;
        setTranslateZ(i*-13);
        setTranslateX(f+j*15);
        setTranslateY(h*-1.5);
        setHighlight(false);
    }

    private TriangleMesh makeTriangleMesh() {
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
        return p;
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
