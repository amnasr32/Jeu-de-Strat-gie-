package view;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class UserInterface extends Group {
    Button endTurn;
    UserInterface(int width, int height) {
        super();
        endTurn = new Button("Fin du tour");
        endTurn.setFont(new Font(20));
        endTurn.setTranslateX(width/2-70); // hard codé, à changer
        endTurn.setTranslateY(height-100); // hard codé, à changer
        getChildren().add(endTurn);
    }
}
