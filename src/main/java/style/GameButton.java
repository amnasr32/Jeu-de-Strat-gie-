package style;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameButton extends Button {

    private final String BUTTON_FREE= "-fx-background-color: transparent;" +
            "-fx-background-image: url('buttons/button_ready_on.png'); -fx-background-size: 170 65;" +
            "-fx-background-position: center";
    private final String BUTTON_PRESSED = "-fx-background-color: transparent;" +
            "-fx-background-image: url('buttons/button_ready_off.png'); -fx-background-size: 170 65;" +
            "-fx-background-position: center";

    public GameButton(String s){
        super(s);
        initButtonFont();
    }

    public GameButton(){
        super();
        initButtonFont();
    }

    public void initStyle(){
        setStyle(BUTTON_FREE);
        setPrefSize(170,50);

        setOnMouseEntered(e -> {
            setCursor(Cursor.HAND);
            setStyle(BUTTON_PRESSED);
        });

        setOnMouseExited(e -> {
            setCursor(Cursor.DEFAULT);
            setStyle(BUTTON_FREE);
        });

        setOnMousePressed(e-> setStyle(BUTTON_PRESSED));

        setOnMouseReleased(e-> setStyle(BUTTON_FREE));
    }

    private void initButtonFont(){
        try {
            String FONT = "src/main/resources/style/CinzelDecorative-Regular.ttf";
            setTextFill(Paint.valueOf("white"));
            setFont(Font.loadFont(new FileInputStream(FONT), 20));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 20));
            System.out.println("why?");
            System.out.println(e);
        }
    }
}
