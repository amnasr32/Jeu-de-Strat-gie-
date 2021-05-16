package custom;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;

public class GameMenu extends SubScene {

    private final String BG_IMAGE_1 = "boxes/bar_ready.png";

    private boolean isHidden;

    private FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.1));

    private FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.1));

    private List<GameButton> buttons;

    private int STARTING_Y_POINT = 20;


    public GameMenu(int width, int height){
        super(new AnchorPane(), width, height);
        prefWidth(width);
        prefHeight(height);

        buttons = new LinkedList<>();

        BackgroundImage image = new BackgroundImage(new Image(BG_IMAGE_1, width, height, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));

        setLayoutX(540 - (width/2));
        setLayoutY(100);
        isHidden = true;

        setVisible(false);
    }

    public void fadeOutScene(){

        setVisible(false);

        fadeOut.setFromValue(1);
        fadeOut.setToValue(0.0);
        fadeOut.setNode(this);
        fadeOut.playFromStart();

        isHidden = true;
    }

    public void fadeInScene(){

        setVisible(true);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1);
        fadeIn.setNode(this);
        fadeIn.playFromStart();

        isHidden = false;
    }

    public void animation(){
        if (isHidden){
            fadeInScene();
        } else {
            fadeOutScene();
        }
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }

    public GameButton addButton(String s){
        GameButton res = new GameButton(s);
        res.initStyle();
        buttons.add(res);
        res.setTranslateX(getWidth()/2 - 85);
        res.setLayoutY(STARTING_Y_POINT + buttons.toArray().length*100);
        getPane().getChildren().add(res);
        return res;
    }

    public GameButton addButton(String s, int offset){
        GameButton res = new GameButton(s);
        res.initStyle();
        buttons.add(res);
        res.setTranslateX(getWidth()/2 - 85);
        res.setLayoutY(STARTING_Y_POINT -offset + buttons.toArray().length*100);
        getPane().getChildren().add(res);
        return res;
    }

}
