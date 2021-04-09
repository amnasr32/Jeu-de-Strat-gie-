package style;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GameLabel extends Label {

    private final String BACKGROUND = "-fx-background-image: url('boxes/bar_ready.png'); " +
            "-fx-background-position: center; -fx-background-size: 170 100";
    private final String BACKGROUND2 = "-fx-background-image: url('boxes/name_bar2.png'); " +
            "-fx-background-position: center; -fx-background-size: 170 90";
    private final String FONT = "style/CinzelDecorative-Regular.ttf";

    public GameLabel(String s){
        super(s);
        initLabelFont();
    }

    public GameLabel(){
        super();
        initLabelFont();
    }

    public void initstyle2(){
        setPrefSize(170,90);
        setAlignment(Pos.CENTER);
        setStyle(BACKGROUND2);
    }

    public void initstyle(){
        setPrefSize(170,105);
        setAlignment(Pos.CENTER);
        setStyle(BACKGROUND);
    }

    private void initLabelFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT), 20));
        } catch (FileNotFoundException e){
            setFont(Font.font("Verdana", 20));
            System.out.println("why?");
        }
    }
}
