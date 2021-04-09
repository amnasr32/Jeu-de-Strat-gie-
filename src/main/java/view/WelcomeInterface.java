package view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import style.GameButton;


public class WelcomeInterface extends Pane {

    private final String BACKGROUND = "-fx-background-image: url('icons/Welcome-page-BG.png'); " +
            "-fx-background-position: center; ";
	
	Controller ctrl;
    GameButton play;

    int width;
    int height;

    int nbOfButtons=0;

    WelcomeInterface(int width, int height, Controller controller) {
     
        this.width=width;
        this.height=height;
        ctrl=controller;

        play = makeButton("Lancer");
        addButton(play);
        play.setTranslateX(width/2 - 85);
        createBackground();


        play.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
        	ctrl.getMainView().setChosenAction(-3);
        	ctrl.view.setMainGroup( ( Group)new BuyEntityInterface(width, height, ctrl));
        	Scene buyScene= new Scene(ctrl.getMainGroup(),width,height);
        	buyScene.setFill((Paint)(Color.SANDYBROWN));
        	ctrl.getStage().setScene(buyScene);
        	
        	ctrl.initializePlayer();
            ctrl.loadLevel();
            ctrl.mkGameGrid();
            ctrl.getMainView().allowGridViewControls(true);
        
	    });
        ctrl.getStage().show();
    }
    
    private void addButton(Button b) {
        getChildren().add(b);
    }

    private GameButton makeButton(String name) {
        GameButton b = new GameButton(name);
        b.setFont(new Font(30));
        b.setTranslateY(height-100);
        b.initStyle();
        return b;
    }

    private void createBackground(){
        Image backgroundImage = new Image("icons/Welcome-Page-BG.png", 1080, 720, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null);
        setBackground(new Background(background));
    }
}
