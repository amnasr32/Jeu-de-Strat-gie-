package view;

import javafx.scene.Group;
import javafx.stage.Stage;
import model.Player;
import model.entity.Entity;


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
    }

    public void endTurn() {
        player.endTurn();
    }

    public void makePath(int x, int y) {
        player.makePath(x,y);
    }

    public void move(byte[] path) {
        player.move(path);
    }// to game 
    public void addEntityToView(Entity e) {
    	player.addEntityToView(e);
    
    }
    //on récupere le primaryStage de mainView
    public Stage getStage() {
    	return this.view.getPrimaryStage();
    }
    //On récupere le mainGroup dans le quel on va make la grid
    public Group getmainGroup() {
    	return this.view.getmainGroup();
    }
 }
 

