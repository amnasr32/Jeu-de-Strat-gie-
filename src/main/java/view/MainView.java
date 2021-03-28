package view;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.util.LinkedList;
import javafx.scene.paint.Color;

public class MainView extends Application {

    private Controller ctrl;
    private Scene mainScene; // tout ce qui est en 2D : les boutons, les menus, etc
    private SubScene scene3D; // tout ce qui est en 3D est ici //le menuu  classe qui extend goupe 
    private Group mainGroup;
    private Stage primaryStage;
    private WelcomeInterface welcomeinterface;

    private int height = 720;
    private int width = 1080;

    LinkedList<EntityView> entityViews;
    private EntityView currentEntityView;
    private int unitInd;

    GridView gridView =null;
    UserInterface ui=null;

    int pointedX=-1;
    int pointedY=-1;

    byte[] path=null;

    @Override
    public void start(Stage primaryStage) throws Exception {
    	mainGroup= new Group();
    	this.primaryStage=primaryStage;
        ctrl = new Controller(this);
     
        
        welcomeinterface = new WelcomeInterface(width, height, ctrl);
        mainScene=new Scene((Group)welcomeinterface,width,height);
        mainScene.setFill((Paint)(Color.SANDYBROWN));
        
        primaryStage.setTitle("jeu de stratégie");
        primaryStage.setScene(mainScene);
        primaryStage.show();
       
     
       
    }
    public void setMainGroup(Group g) {
    	this.mainGroup=g;
    }
    //Getter pour le mainGroup 
    public Group getmainGroup() {
    	return this.mainGroup;
    }
    
    
    //Getter pour le primaryStage du MainView
    
    public Stage getPrimaryStage() {
    	return this.primaryStage;
    }
    

    public void setPointedXY(int x, int y) {
        pointedX = x;
        pointedY = y;
    }

    public void makePath(int x, int y) {
        ctrl.makePath(x,y);
    }

    public void drawPath(byte[] newPath) {
        cleanPath();
        path=newPath;
        Hexagon h= gridView.getHexagon(currentEntityView.getX(), currentEntityView.getY());
        if (path!=null) {
            for (byte dir:path) {
                h= gridView.getAdjHexagon(h,dir);
                if(h!=null) h.makeGreen(true);
            }
        }
    }

    public void cleanPath() {
        Hexagon h= gridView.getHexagon(currentEntityView.getX(), currentEntityView.getY());
        if (path!=null) {
            for (byte dir:path) {
                h= gridView.getAdjHexagon(h,dir);
                if(h!=null) h.makeGreen(false);
            }
        }
    }

    public void moveModelEntity() {
        cleanPath();
        ctrl.move(path);
        path=null;
    }

    public void moveViewEntity(byte direction) {
        gridView.moveEntity(currentEntityView, direction);
    }

    public void makeGameScene(byte[][] heightGrid) {

        entityViews =new LinkedList<>();
        gridView = new GridView(heightGrid, this);
        scene3D=new SubScene(gridView, width, height, true, SceneAntialiasing.BALANCED);
        GameCamera camera = new GameCamera();
        scene3D.setCamera(camera);
        camera.initialiseControls(scene3D);
        ui = new UserInterface(width, height, ctrl);

        mainGroup.getChildren().add(scene3D);
        mainGroup.getChildren().add(ui);
    }

    public void addEntity(int x, int y, boolean isAlly) {
        EntityView u = new EntityView(x,y,isAlly);
        entityViews.add(u);
        gridView.addEntity(u,x,y);
    }

    public void focusFirstEntity(int i) {
        currentEntityView = entityViews.get(i);
        currentEntityView.highlight(true);
    }

    public void focusNextEntity(int i) {
        currentEntityView.highlight(false);
        currentEntityView = entityViews.get(i);
        currentEntityView.highlight(true);
    }

    public void allowGridViewControls(boolean bool) {
        gridView.allowControls(bool);
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
