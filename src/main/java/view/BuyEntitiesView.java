package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class BuyEntitiesView extends Application {
	
	  private Controller ctrl;
	  private int height = 720;
	  private int width = 1080;
	  MainView view;

	       
	  
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		ctrl = new Controller(view);
		Group buyGroup = new Group();
    	Scene buyScene = new Scene(buyGroup,width,height);
    	buyScene.setFill((Paint)(Color.SANDYBROWN));
      	primaryStage.setScene(buyScene);
        primaryStage.show();
        
        
	}
	Controller getcntrl() {
		return ctrl;
	}
    void setcntrl(Controller controller) {
    	ctrl=controller;
    }
}
