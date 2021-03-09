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
    UserInterface(int width, int height, Controller controller) {
        super();
        ctrl=controller;

        endTurn = new Button("Fin du tour");
        endTurn.setFont(new Font(20));
        endTurn.setTranslateX(width/2-70); // hard codé, à changer
        endTurn.setTranslateY(height-100); // hard codé, à changer
        getChildren().add(endTurn);

        endTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ctrl.endTurn();
        });
    }
}
