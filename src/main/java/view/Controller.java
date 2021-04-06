package view;

import javafx.scene.Group;
import javafx.stage.Stage;
import model.Player;


public class Controller {

    Player player;
    MainView view;

    public Controller(MainView view) {
        this.view = view;
    }

    public void initializePlayer() {
        player=new Player(view);
    }

    public void loadLevel() {
        player.loadLevel();
    }

    public void mkGameGrid() {
        byte[][] heightGrid = player.getHeightGrid();
        view.makeGameScene(heightGrid);
    }

    public void startGame() { 
        player.start();
        System.out.println(view.chosenAction);
    }

    public void endTurn() {
        player.endTurn();
    }

    public void makePath(int x, int y) {
        player.makePath(x,y);
    }

    public void move(byte[] path) {
        player.move(path);
    }

    public void doAction(int actionNb, int x, int y) {
        player.doAction(actionNb, x, y);
    }

    public void selectAction(int actionNb) {
        player.selectAction(actionNb);
    }

    public void cancelAction() {
        player.cancelAction();
    }

    public void addEntityToGame( int x, int y, int playerNb,int entity_type) {
    	player.addEntityTogame( x,  y,  playerNb,entity_type);
    
    }
    public int nbEntity() {
    	return player.getnbEntity();
    }
    //ajouter une méthode qui permet d'ajouter une entity dans game
     //vérifier si je peux l'ajouter 
   /* public void addEntityToView(Entity e) {
    	player.addEntityToView(e);
    }*/
    //on récupere le primaryStage de mainView
    public Stage getStage() {
    	return this.view.getPrimaryStage();
    }
    //On récupere le mainGroup dans le quel on va make la grid
    public Group getmainGroup() {
    	return this.view.getmainGroup();
    }
    public MainView getMainView() {
    	return this.view;
    }
 }
 

