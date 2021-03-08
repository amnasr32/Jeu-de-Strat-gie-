package view;

import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

/**
 * Camera dynamique, avec des fonctions de transformations internes qui
 * permettent des rotations / translations / zoom sur une plage de valeur donnée.
 *
 * setTranslationLimit() doit être appellée au moment de sa création,
 * en fonction de la taille du plateau de jeu
 */
public class GameCamera extends PerspectiveCamera {

    private Transform tf = new Affine();

    private double posX=0;
    private double posZ=0;

    private double latitude=0;
    //private double longitude=0; // n'est jamais utilisé

    private double zoom=0;

    private double posXMin=-300;
    private double posXMax=300;
    private double posZMin=-300;
    private double posZMax=300;

    public GameCamera() {
        super(true);
        setFarClip(10000);
        setNearClip(0.05);
        setTranslateX(0);
        setTranslateY(0);
        setTranslateZ(0);
        zoom(-200);
        rotateX(-20);
    }

    public void zoom(double distance) {
        double zoomMin = -1000;
        double zoomMax = -50;
        if (zoom+distance> zoomMax) {
            distance = zoomMax -zoom;
        } else if (zoom+distance< zoomMin) {
            distance = zoomMin -zoom;
        }
        zoom+=distance;
        Translate tl = new Translate(0,0,distance);
        tf = tf.createConcatenation(tl);
        this.getTransforms().clear();
        this.getTransforms().add(tf);
    }

    public void rotateY(double angle) {
        double x = -this.getTranslateX();
        double y = -this.getTranslateY();
        double z = -this.getTranslateZ()-zoom;
        Rotate r1 = new Rotate(-latitude, x,y,z, Rotate.X_AXIS);
        Rotate r = new Rotate(angle, x,y,z, Rotate.Y_AXIS);
        Rotate r2 = new Rotate(latitude, x,y,z, Rotate.X_AXIS);

        tf = tf.createConcatenation(r1);
        tf = tf.createConcatenation(r);
        tf = tf.createConcatenation(r2);

        this.getTransforms().clear();
        this.getTransforms().addAll(tf);

        //longitude = (360+longitude +angle)%360;
    }

    public void rotateX(double angle) {
        double latitudeMin = -90;
        double latitudeMax = -5;
        if (angle+latitude> latitudeMax) {
            angle= latitudeMax -latitude;
        } else if (angle+latitude< latitudeMin) {
            angle= latitudeMin -latitude;
        }
        double x = -this.getTranslateX();
        double y = -this.getTranslateY();
        double z = -this.getTranslateZ()-zoom;
        Rotate r = new Rotate(angle, x,y,z, Rotate.X_AXIS);

        tf = tf.createConcatenation(r);

        this.getTransforms().clear();
        this.getTransforms().addAll(tf);

        latitude = (latitude+angle)%360;
    }

    public void translate(double x, double z) {
        if (x+posX>posXMax) {
            x=posXMax-posX;
        } else if (x+posX<posXMin) {
            x=posXMin-posX;
        }
        if (z+posZ>posZMax) {
            z=posZMax-posZ;
        } else if (z+posZ<posZMin) {
            z=posZMin-posZ;
        }

        posX+=x;
        posZ+=z;

        double x2 = -this.getTranslateX();
        double y2 = -this.getTranslateY();
        double z2 = -this.getTranslateZ()-zoom;

        Rotate r1 = new Rotate(-latitude, x2,y2,z2, Rotate.X_AXIS);
        Rotate r2 = new Rotate(latitude, x2,y2,z2, Rotate.X_AXIS);
        Translate tl = new Translate(x, 0, z);

        tf=tf.createConcatenation(r1);
        tf=tf.createConcatenation(tl);
        tf=tf.createConcatenation(r2);
        this.getTransforms().clear();
        this.getTransforms().addAll(tf);
    }

    public void setTranslationLimits(int xMin, int xMax, int zMin, int zMax) {
        posXMin=xMin;
        posXMax=xMax;
        posZMin=zMin;
        posZMax=zMax;
    }

}

