package custom;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GameLabel extends Label {

    private Label label;

    private final String BACKGROUND = "-fx-background-image: url('boxes/bar_ready.png'); " +
            "-fx-background-position: center; -fx-background-size: 250 100; -fx-font-family: 'Cinzel Decorative';" +
            "src: url('src/main/resources/style/CinzelDecorative-Bold.ttf'); -fx-font-size: 10;" +
            "-fx-line-spacing: 0.7; -fx-padding: 0 0 5 0;";
    private final String BACKGROUND2 = "-fx-background-image: url('boxes/name_bar2.png'); " +
            "-fx-background-position: center; -fx-background-size: 180 90; -fx-font-family: 'Cinzel Decorative';" +
            "src: url('src/main/resources/style/CinzelDecorative-Bold.ttf'); -fx-font-size: 18;" +
            "-fx-line-spacing: 0.7; -fx-padding: 0 0 5 0;";
    private final String FONT = "src/main/resources/style/CinzelDecorative-Bold.ttf";

    public GameLabel(String s){
        super(s);
        label = new Label(s);
        label.setEffect(labelEffect());
        initLabelFont();
        setEffect(labelEffect());
    }

    public GameLabel(){
        super();
        initLabelFont();
    }

    public void initstyle2(){
        setPrefSize(180,90);
        setAlignment(Pos.CENTER);
        setStyle(BACKGROUND2);
    }

    public void initstyle(){
        setPrefSize(250,105);
        setAlignment(Pos.CENTER);
        setStyle(BACKGROUND);
    }

    private void initLabelFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT), 10));
            setTextFill(Paint.valueOf("white"));
            setLineSpacing(0.7);
        } catch (FileNotFoundException e){
            setFont(Font.font("Verdana", 10));
        }
    }

    private DropShadow labelEffect(){
        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.GAUSSIAN);
        ds.setColor(Color.valueOf("black"));
        ds.setRadius(4);
        ds.setOffsetY(2);
        ds.setOffsetX(2);
        return ds;
    }


}
