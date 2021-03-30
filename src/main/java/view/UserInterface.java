package view;



import java.util.Optional;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

/**
 * L'interface utilisateur est tout les éléments
 * (interactifs ou non) en 2D lors du jeu, comme les
 * boutons clickables
 */
public class UserInterface extends Group {
    Controller ctrl;
    Button endTurn;
    Button attack;
    Button start;
    Button buy;

    int width;
    int height;

    int nbOfButtons=0;

    UserInterface(int width, int height, Controller controller) {
        super();
        this.width=width;
        this.height=height;
        ctrl=controller;

        endTurn = makeButton("Fin du tour");
        attack = makeButton("attaque !");
        start = makeButton("Commencer ");
        buy=makeButton("acheter");
        addButton(start);
        addButton(endTurn);
        addButton(attack);
        addButton(buy);

        endTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ctrl.endTurn();
        });
        start.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        	ctrl.startGame();
            System.out.println("went back");
        });
        buy.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        	//Alert choice = new Alert (AlertType.CONFIRMATION);
        	String [] entity= {"Sphére   100€","Autre"};
        	ChoiceDialog<String> choice= new ChoiceDialog<>(entity[0], entity);
        	choice.setTitle("Buy entity");
        	choice.setHeaderText("Select an entity to buy");
        	choice.setContentText("Entity:");
        	Optional<String> selection=  choice.showAndWait();
        	
        	choice.show();
        	selection.ifPresent(str-> System.out.println("Selection:"+ str));
           
        });
    }
    
    private void addButton(Button b) {
        getChildren().add(b);
        b.setTranslateX(nbOfButtons*200);
        nbOfButtons++;
    }

    private Button makeButton(String name) {
        Button b = new Button(name);
        b.setFont(new Font(20));
        b.setTranslateX(160); // hard codé, à changer
        b.setTranslateY(height-100); // hard codé, à changer
        return b;
    }
}
