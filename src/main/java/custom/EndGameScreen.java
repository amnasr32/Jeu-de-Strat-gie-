package custom;

import javafx.animation.FadeTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class EndGameScreen extends SubScene {

    private final String BG_IMAGE_1 = "screen/end-screen-victoire1080.png";

    private final String BG_IMAGE_2 = "screen/end-screen-defaite1080.png";

    private int w, h;

    private final BackgroundImage VICTORY = new BackgroundImage(new Image(BG_IMAGE_1, w, h, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);

    private final BackgroundImage DEFAITE = new BackgroundImage(new Image(BG_IMAGE_2, w, h, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);

    private AnchorPane root2 = (AnchorPane) this.getRoot();

    public EndGameScreen(int width, int height){
        super(new AnchorPane(), width, height);
        w = width;
        h = height;
        prefWidth(width);
        prefHeight(height);

        setVisible(false);
    }

    public void initVictory(){
        root2.setBackground(new Background(VICTORY));

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3));
        setVisible(true);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1);
        fadeIn.setNode(this);
        fadeIn.playFromStart();
    }

    public void initDefaite(){
        root2.setBackground(new Background(DEFAITE));

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3));
        setVisible(true);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1);
        fadeIn.setNode(this);
        fadeIn.playFromStart();
    }

    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }
}
