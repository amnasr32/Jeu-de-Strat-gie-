package view;

import custom.GameMenu;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import custom.GameButton;


public class WelcomeInterface extends Pane {

    private GameButton option;
    private GameMenu optionMenu;
    private GameButton quitter;
	private Controller ctrl;
    private GameButton play;

    int width;
    int height;

    int nbOfButtons=0;

    WelcomeInterface(int width, int height, Controller controller){
     
        this.width=width;
        this.height=height;
        ctrl=controller;

        play = makeButton("Lancer");
        addButton(play);
        play.setTranslateX(width/2 - 85);

        optionMenu = new GameMenu(300,400);
        createBackground();
        initOptionButton();
        initOptionMenu();
        initButtonListeners();

        ctrl.getStage().show();
    }
    
    private void addButton(Button b) {
        getChildren().add(b);
    }

    private GameButton makeButton(String name){
        GameButton b = new GameButton(name);
        b.setFont(new Font(30));
        b.setTranslateY(height-100);
        b.initStyle();
        return b;
    }

    private void initOptionButton(){
        option = new GameButton("Options");
        getChildren().add(option);
        option.setTranslateY(0);
        option.setTranslateX(width-170);
        option.initStyle();
    }

    private void initOptionMenu(){
        getChildren().add(optionMenu);
        optionMenu.fadeOutScene();


        quitter = new GameButton("Quitter");
        quitter.initStyle();
        quitter.setLayoutX(65);
        quitter.setLayoutY(300);
        optionMenu.getPane().getChildren().add(quitter);
    }

    private void initButtonListeners(){
        play.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            ctrl.getMainView().setChosenAction(-2);
            ctrl.view.setMainGroup( ( Group)new BuyEntityInterface(width, height, ctrl));
            Scene buyScene= new Scene(ctrl.getMainGroup(),width,height);
            buyScene.setFill((Paint)(Color.SANDYBROWN));
            ctrl.getStage().setScene(buyScene);

            ctrl.initializePlayer();
            ctrl.loadLevel();
            ctrl.mkGameGrid();
            ctrl.initBotPlayer();
            ctrl.getMainView().allowGridViewControls(true);

        });

        option.setOnMouseClicked(e -> {
            optionMenu.animation();
        });

        quitter.setOnAction(e -> {
            ctrl.getMainView().getPrimaryStage().close();
        });
    }

    private void createBackground(){
        Image backgroundImage = new Image("icons/Welcome-Page-BG.png", 1080, 720, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null);
        setBackground(new Background(background));
    }
}
