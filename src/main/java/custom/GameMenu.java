package custom;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class GameMenu extends SubScene {

    private final String BG_IMAGE_1 = "boxes/bar_ready.png";

    private boolean isHidden;

    private FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.1));

    private FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.1));


    public GameMenu(int width, int height){
        super(new AnchorPane(), width, height);
        prefWidth(width);
        prefHeight(height);

        BackgroundImage image = new BackgroundImage(new Image(BG_IMAGE_1, width, height, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));

        setLayoutX(540 - (width/2));
        setLayoutY(100);
        isHidden = true;

        setVisible(false);
    }

    public void moveSubScene(){
        TranslateTransition transition = new TranslateTransition();

        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        transition.setToX(-1000);

        transition.play();
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

    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }

}
