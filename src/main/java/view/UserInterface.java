package view;

import javafx.scene.Group;
import javafx.scene.control.Button;
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

        addButton(endTurn);
        addButton(attack);

        endTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ctrl.endTurn();
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
