package custom;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameIcon extends Button {
    private final String name;

    private String ICON_FREE;
    private String ICON_PRESSED;

    private final String LABEL_BG = "boxes/barmid_ready.png";

    private Label label;

    public GameIcon(String name){
        this.name = name;
        setPrefSize(100,100);
        setIcon();
        initButtonFont();
        setEffect(buttonEffect());
        label = setLabelText(name);
        setLabelImage();
        mouseListeners();
    }

    private void setIcon() {
        ICON_FREE = "icons/portraits/"+ name +"_free.png";
        ICON_PRESSED = "icons/portraits/"+ name +"_pressed.png";
    }

    public void setBackgroundFree(){
        BackgroundImage image = new BackgroundImage(new Image(ICON_FREE, 100, 100, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        setBackground(new Background(image));
    }

    public void setBackgroundPressed(){
        BackgroundImage image = new BackgroundImage(new Image(ICON_PRESSED, 100, 100, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        setBackground(new Background(image));
    }

    private void setLabelImage(){
        label.setPrefSize(70,30);
        label.setTranslateY(getTranslateY() - 90);
        BackgroundImage image = new BackgroundImage(new Image(LABEL_BG, 70, 30, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        label.setBackground(new Background(image));
        label.setEffect(labelEffect());
        setGraphic(label);
        label.setTextAlignment(TextAlignment.CENTER);
    }

    public void mouseListeners(){
        label.setVisible(false);
        setBackgroundFree();
        setOnMouseEntered(e ->{
            setBackgroundPressed();
            setCursor(Cursor.HAND);
            label.setVisible(true);
        });

        setOnMouseExited(e ->{
            setBackgroundFree();
            setCursor(Cursor.DEFAULT);
            label.setVisible(false);
        });

        setOnMousePressed(e -> setBackgroundPressed());

        setOnMouseReleased(e -> setBackgroundFree());
    }

    private void initButtonFont(){
        try {
            String FONT = "style/CinzelDecorative-Bold.ttf";
            setTextFill(Paint.valueOf("#140101"));
            Font f = Font.loadFont(new FileInputStream(FONT), 15);
            label.setFont(f);
        } catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

    private DropShadow labelEffect(){
        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.GAUSSIAN);
        ds.setColor(Color.valueOf("#140101"));
        ds.setRadius(2);
        ds.setWidth(3);
        ds.setSpread(0.8);
        ds.setOffsetY(1);
        return ds;
    }

    private DropShadow buttonEffect(){
        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.GAUSSIAN);
        ds.setColor(Color.valueOf("black"));
        ds.setRadius(4);
        ds.setOffsetY(2);
        ds.setOffsetX(2);
        return ds;
    }

    private Label setLabelText(String s){
        Label res = new Label();
        switch (s){
            case "Soldier":
                 res.setText("Soldat");
                break;
            case "Knight":
                res.setText("Chevalier");
                break;
            case "Wizard":
                res.setText("Sorcier");
                break;
            case "Druid":
                res.setText("Druide");
                break;
            case "Cleric":
                res.setText("Clerc");
                break;
        }
        return res;
    }
}
