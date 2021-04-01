package view;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class BuyEntityInterface extends Group{


	
	Controller ctrl;
    Button back;
    int width;
    int height;
    int nbOfButtons=0; 
	       
	  
	BuyEntityInterface(int width, int height, Controller controller){
		  
		this.width=width;
	    this.height=height;
	    ctrl=controller;
	    back = makeButton("Back");
	    addButton(back);
	    
	    Scene entity=new Scene (ctrl.getmainGroup(),width,height);
	    
	    
	  } 
         

	private void addButton(Button b) {
        getChildren().add(b);
        b.setTranslateX(nbOfButtons*200);
        nbOfButtons++;
    }

    private Button makeButton(String name) {
        Button b = new Button(name);
        b.setFont(new Font(30));
        b.setTranslateX(300); 
        b.setTranslateY(height-100); 
        return b;
    }
}
