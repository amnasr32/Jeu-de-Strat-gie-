package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.LinkedList;

public class MainView extends Application {

    private Controller ctrl;
    private Scene mainScene; // tout ce qui est en 2D : les boutons, les menus, etc
    private SubScene scene3D; // tout ce qui est en 3D est ici
    private Group mainGroup;

    private int height = 720;
    private int width = 1080;

    LinkedList<EntityView> entityViews;
    private EntityView currentEntityView;
    private int unitInd;

    GridView gridView =null;
    UserInterface ui=null;

    int pointedX=-1;
    int pointedY=-1;
    int chosenAction=-1;

    byte[] path=null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainGroup = new Group();
        mainScene=new Scene(mainGroup,width,height);
        ctrl = new Controller(this);


        primaryStage.setTitle("jeu de stratégie");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        ctrl.initializePlayer();
        ctrl.loadLevel();
        ctrl.mkGameGrid();
        ctrl.startGame();
    }

    public int getChosenAction() {
        return chosenAction;
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
                if(h!=null) h.color(1);
            }
        }
    }

    public void cleanPath() {
        Hexagon h= gridView.getHexagon(currentEntityView.getX(), currentEntityView.getY());
        if (path!=null) {
            for (byte dir:path) {
                h= gridView.getAdjHexagon(h,dir);
                if(h!=null) h.color(0);
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
        currentEntityView.decreaseMp();
    }

    public void makeGameScene(byte[][] heightGrid) {

        entityViews =new LinkedList<>();
        gridView = new GridView(heightGrid, this);
        scene3D=new SubScene(gridView, width, height, true, SceneAntialiasing.BALANCED);
        GameCamera camera = new GameCamera();
        scene3D.setCamera(camera);
        camera.initialiseControls(scene3D);
        ui = new UserInterface(width, height, ctrl, this);

        mainGroup.getChildren().add(scene3D);
        mainGroup.getChildren().add(ui);
    }

    public void addEntity(int x, int y, boolean isAlly, int hp, int mp, String[][]actions) {
        EntityView u = new EntityView(this, x,y,isAlly, hp, mp, actions);
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

    public void showActionButtons(boolean bool) {
        if (bool) ui.updateActionButtons(currentEntityView);
        ui.showActionButtons(bool);
    }

    public void allowGridViewControls(boolean bool) {
        gridView.allowControls(bool);
        for (EntityView e: entityViews) {
            e.showInfoOnHover(bool);
        }
    }

    public void highlightHexagon(int x, int y, boolean b) {
        gridView.getHexagon(x,y).setHighlight(b);
    }

    public void showEntityDetails(EntityView e) {
        ui.updateEntityDetails(e);
        ui.showEntityDetails(true);
    }

    public void hideEntityDetails() {
        ui.showEntityDetails(false);
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void setAction(int actionNb) {
        chosenAction=actionNb;
        ctrl.selectAction(actionNb);
        allowActionOnEntities(true);
    }

    public void resetAction() {
        ctrl.cancelAction();
        chosenAction=-1;
        allowActionOnEntities(false);
        ui.resetActionButtons();
        gridView.clearSelectedHex();
    }

    public void doAction() {
        ctrl.doAction(chosenAction, pointedX, pointedY);
    }

    public void updateHp(int i, int newHp) {
        entityViews.get(i).setHp(newHp);
        ui.updateEntityDetails(entityViews.get(i));
    }

    private void allowActionOnEntities(boolean bool) {
        for (EntityView e:entityViews) {
            e.allowActionOnClick(bool);
        }
    }

    public void updateActionRange(int[][] newCoords) {
        gridView.clearSelectedHex();
        gridView.setCoords(newCoords);
        gridView.updateSelectedHex();
    }
}
