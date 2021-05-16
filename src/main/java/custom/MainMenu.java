package custom;

import javafx.animation.FadeTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class MainMenu extends SubScene {
    public final String BG_IMAGE_1 = "icons/menu_bg.png";

    private boolean isHidden;

    private GameMenu layout;

    private int width, height;

    private FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.1));

    private FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.1));

    private GameButton multi, bot, edit, quit;


    public MainMenu(int width, int height){
        super(new AnchorPane(), width, height);
        prefWidth(width);
        prefHeight(height);

        this.width = width;
        this.height = height;

        BackgroundImage image = new BackgroundImage(new Image(BG_IMAGE_1, width, height, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));

        //setLayoutX(540 - (width/2));
        //setLayoutY(100);
        isHidden = true;

        setVisible(false);

        initLayoutMenu();
    }

    private void initLayoutMenu(){
        layout = new GameMenu(300, 600);
        layout.setLayoutX((width/2)- 150);
        layout.setLayoutY((height/2) - 300);

        multi = layout.addButton("Multijoueur");
        bot = layout.addButton("Jouer contre\nun robot");
        edit = layout.addButton("Editer un\nniveau");
        quit = layout.addButton("Quitter");

        getPane().getChildren().add(layout);
        layout.setVisible(true);
        layout.setHidden(false);
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

    // force l'apparaition en fonction de bool
    public void animation(boolean bool){
        if (bool){
            fadeInScene();
        } else {
            fadeOutScene();
        }
    }

    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }

    public GameButton getBot() {
        return bot;
    }

    public GameButton getEdit() {
        return edit;
    }

    public GameButton getMulti() {
        return multi;
    }

    public GameButton getQuit() {
        return quit;
    }
}
