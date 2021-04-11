package view;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;


/**
 * L'interface utilisateur est tout les éléments
 * (interactifs ou non) en 2D lors du jeu, comme les
 * boutons clickables
 */
public class UserInterface extends Group {
    private final MainView view;
    private final Controller ctrl;
    private final Button endTurn;
    //Button attack;
    private final Button start;
    private final BuyButton[] buyButtons;
    private final Group actions;
    private ActionButton[] actionButtons;
    private Label entityDetails;
    private Label money;

    private int width;
    private int height;

    private int nbOfButtons=0;

    private final String[] listOfPossibleEntities = {"soldat", "chevalier"};

    private class ActionButton extends Button {
        int actionNb;
        Label description;
        boolean isSelected=false;
        ActionButton(String name, String desc, int actionNb) {
            super(name);
            setFont(new Font(20));
            setTranslateX(10+(actionNb+1)*300);
            setTranslateY(height-100);
            description=new Label(desc);
            description.setFont(new Font(20));
            description.setVisible(false);
            description.setTranslateX(10+(actionNb+1)*300);
            description.setTranslateY(height-200);
            this.actionNb=actionNb;
            allowMouseListeners();
        }

        public Label getDescription() {
            return description;
        }

        public void allowMouseListeners() {
            setOnMouseEntered(event -> {
                description.setVisible(true);
            });
            setOnMouseExited(event -> {
                description.setVisible(false);
            });
            setOnMouseClicked(event -> {
                toggleSelected();
            });
        }

        private void toggleSelected() {
            if (isSelected) {
                view.resetAction();
                isSelected=false;
            } else {
                for (ActionButton ab: actionButtons) {
                    ab.isSelected=false;
                }
                isSelected=true;
                view.setAction(actionNb);
            }
        }

    }

    private class BuyButton extends Button {
        private final int entityNb;
        BuyButton(String s, int i) {
            super(s);
            entityNb=i;
            setFont(new Font(20));
            setTranslateX(10+(i+2)*200);
            setTranslateY(height-100);
            setOnMouseClicked(event -> {
                view.setPreGameAction(entityNb);
            });
        }

    }

    UserInterface(int width, int height, Controller controller, MainView view) {
        super();
        this.width=width;
        this.height=height;
        ctrl=controller;
        this.view=view;


        endTurn = makeButton("Fin du tour",0);
        start = makeButton("Commencer",0);
        //buy=makeButton("acheter",1);
        //addButton(buy);

        addButton(start);
        start.setVisible(true);
        start.setDisable(true);

        buyButtons=new BuyButton[listOfPossibleEntities.length+1];
        buyButtons[0]=new BuyButton("supprimer",-1);
        getChildren().add(buyButtons[0]);
        for (int i = 1; i < buyButtons.length; i++) {
            buyButtons[i]=new BuyButton(listOfPossibleEntities[i-1],i-1);
            getChildren().add(buyButtons[i]);
        }


        actions=new Group();
        showActionButtons(false);
        getChildren().add(actions);
        initEntityDetails();

        endTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
         ctrl.endTurn();
        });

        /*
        buy.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {


         	String [] entity= {"Sphére   100€","Autre"};
         	ChoiceDialog<String> choice= new ChoiceDialog<>(entity[0], entity);
         	choice.setTitle("Buy entity");
         	choice.setHeaderText("Select an entity to buy");
         	choice.setContentText("Entity:");
         	Optional<String> selection=  choice.showAndWait();
         	choice.show();
         	choice.close();        	

            ctrl.getMainView().setChosenAction(-3);
        	
         	// Apres avoir choisi une entité
         	selection.ifPresent(str-> {
         		ctrl.getMainView().setChosenAction(-2);
         	    });
         	});
        */
           
        start.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            start.setDisable(true);
            ctrl.toggleReady();
        });

        money = new Label("");
        money.setFont(new Font(20));
        getChildren().add(money);
        money.setVisible(true);

    }

    public void canPressReadyButton(boolean b) {
        start.setDisable(!b);
    }

    private void initEntityDetails() {
        entityDetails = new Label();
        entityDetails.setVisible(false);
        entityDetails.setFont(new Font(20));
        getChildren().add(entityDetails);
    }

    public void updateEntityDetails(EntityView e) {
        entityDetails.setText(e.getName()+"\npv: "+e.getHp()+"/"+e.getMaxHp());
        entityDetails.setTranslateX(width/2-entityDetails.getWidth()/2);
    }

    public void showEntityDetails(boolean bool) {
        entityDetails.setVisible(bool);
    }

    private Button makeButton(String name, int i) {
        Button b = new Button(name);
        b.setFont(new Font(20));
        b.setTranslateY(height-100);
        b.setTranslateX(120+i*100);
        return b;
    }

    public void updateActionButtons(EntityView e) {
        actions.getChildren().clear();
        actions.getChildren().add(endTurn);
        int nbOfActions=e.getActionNames().length;
        actionButtons=new ActionButton[nbOfActions];
        for (int i = 0; i < nbOfActions; i++) {
            ActionButton b = new ActionButton(e.getActionNames()[i], e.getActionDesc()[i],i);
            actionButtons[i]=b;
            actions.getChildren().addAll(b,b.getDescription());
        }
    }

    public void showActionButtons(boolean bool) {
        actions.setVisible(bool);
    }

    public void resetActionButtons() {
        if (actionButtons!=null) {
            for (ActionButton ab : actionButtons) {
                ab.isSelected = false;
            }
        }
    }

    private void addButton(Button b) {
        getChildren().add(b);
        b.setTranslateX(nbOfButtons*200);
        nbOfButtons++;
    }

    // à appeler quand le jeu commence
    public void hidePreGameButtons() {
        getChildren().remove(start);
        getChildren().remove(money);
        for (Button b:buyButtons) {
            getChildren().remove(b);
        }
        //start.setVisible(false);
        //buy.setVisible(false);
    }

    // à appeler à la fin du jeu
    public void hideAllGameButtons() {
        actions.setVisible(false);
    }

    public void setMoneyValue(int money) {
        this.money.setText(money+" £");
    }
}
