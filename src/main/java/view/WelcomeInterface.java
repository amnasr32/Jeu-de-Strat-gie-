package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class WelcomeInterface extends Group {
	
	Controller ctrl;
    Button play;

    int width;
    int height;

    int nbOfButtons=0;

    WelcomeInterface(int width, int height, Controller controller) {
     
        this.width=width;
        this.height=height;
        ctrl=controller;

        play = makeButton("jouer");
        addButton(play);
        
        Text gc = new Text ();
        gc.setText ("Bienvenue dans notre jeu de stratégie");
        gc.setFont (new Font ("Verdana", 50));
        TextFlow textFlow = new TextFlow();
        textFlow.getChildren().add(gc);//cree une zone de texte vide
        getChildren().addAll(textFlow);//on ajoute le texte à la zone crée

        play.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
        	
        	ctrl.view.setMainGroup( ( Group)new BuyEntityInterface(width, height, ctrl));
        
        	Scene buyScene= new Scene(ctrl.getmainGroup(),width,height);
        	buyScene.setFill((Paint)(Color.SANDYBROWN));
        	ctrl.getStage().setScene(buyScene);
        	
        	ctrl.initializePlayer();
            ctrl.loadLevel();
            ctrl.mkGameGrid();
        
	        	
         
        });
        ctrl.getStage().show();
    }
    
    private void addButton(Button b) {
        getChildren().add(b);
        b.setTranslateX(nbOfButtons*200);
        nbOfButtons++;
    }

    private Button makeButton(String name) {
        Button b = new Button(name);
        b.setFont(new Font(30));
        b.setTranslateX(300); 
        b.setTranslateY(height-100); 
        return b;
    }
}
