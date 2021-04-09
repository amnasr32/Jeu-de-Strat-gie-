package view;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import style.GameLabel;


/**
 * L'interface utilisateur est tout les éléments
 * (interactifs ou non) en 2D lors du jeu, comme les
 * boutons clickables
 */
public class UserInterface extends Group {
    MainView view;
    Controller ctrl;
    Button endTurn;
    Button attack;
    Button start;
    Button buy;
    Group actions;
    List<ActionButton> actionButtons;
    GameLabel entityDetails;

    private final static String BUTTON_FREE = "-fx-background-color: transparent; " +
            "-fx-background-image: url('buttons/button_ready_on.png'); -fx-background-size: 170 50;" +
            "-fx-background-position: center";

    private final static String BUTTON_PRESSED = "-fx-background-color: transparent; " +
            "-fx-background-image: url('buttons/button_ready_off.png'); -fx-background-size: 170 50;" +
            "-fx-background-position: center";

    private final int START_BUTTON_X = 100;
    private final int START_BUTTON_Y = 100;

    int width;
    int height;

    int nbOfButtons=0;

    private class ActionButton extends Button {
        int actionNb;
        GameLabel description;
        boolean isSelected=false;
        ActionButton(String name, String desc, int actionNb) {
            super(name);
            setFont(new Font(20));
            setTranslateX(START_BUTTON_X + endTurn.getTranslateX() * 2 + actionButtons.size()*200);
            setTranslateY(height-100);
            description=new GameLabel(desc);
            description.setFont(new Font(20));
            description.setVisible(false);
            description.setTranslateX(START_BUTTON_X + endTurn.getTranslateX()*2 + actionButtons.size()*200);
            description.setTranslateY(height-220);
            description.initstyle();
            this.actionNb=actionNb;
            allowMouseListeners();
        }

        public Label getDescription() {
            return description;
        }

        public void allowMouseListeners() {
            setStyle(BUTTON_FREE);
            setPrefSize(170,50);
            setOnMousePressed(e->{
                setStyle(BUTTON_PRESSED);
            });

            setOnMouseReleased(e->{
                setStyle(BUTTON_FREE);
            });

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

    UserInterface(int width, int height, Controller controller, MainView view) {
        super();
        this.width=width;
        this.height=height;
        ctrl=controller;
        this.view=view;


        endTurn = makeButton("Fin du tour",0);
        start = makeButton("Commencer",0);
        buy = makeButton("Acheter",1);
        addButton(buy);
        addButton(start);
        start.setVisible(true);
        start.setDisable(true);


        actions=new Group();
        showActionButtons(false);
        getChildren().add(actions);
        initEntityDetails();

        buttonsMouseListeners();


    }

    public void buttonsMouseListeners(){
        endTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ctrl.endTurn();
        });

        buy.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            String [] entity= {"Sphere  100€","Autre"};
            ChoiceDialog<String> choice= new ChoiceDialog<>(entity[0], entity);
            choice.setTitle("Buy entity");
            choice.setHeaderText("Select an entity to buy");
            choice.setContentText("Entity:");
            Optional<String> selection=  choice.showAndWait();
            choice.show();
            choice.close();

            ctrl.getMainView().setChosenAction(-3);

            /* Apres avoir choisi une entité */
            selection.ifPresent(str-> {
                ctrl.getMainView().setChosenAction(-2);
            });
        });

        start.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            start.setVisible(false);
            buy.setVisible(false);
            ctrl.startGame();
        });
    }

    public void canPressReadyButton(boolean b) {
        start.setDisable(!b);
    }

    private void initEntityDetails() {
        entityDetails = new GameLabel();
        entityDetails.setVisible(false);
        entityDetails.setFont(new Font(20));
        entityDetails.initstyle2();
        getChildren().add(entityDetails);
    }

    public void updateEntityDetails(EntityView e) {
        entityDetails.setText(e.getName()+"\npv: "+e.getHp()+"/"+e.getMaxHp());
        entityDetails.setTranslateX(width/2-entityDetails.getWidth()/2);
    }

    public void showEntityDetails(boolean bool) {
        entityDetails.setVisible(bool);
    }

    public void buttonStyle(Button b){

        b.setStyle(BUTTON_FREE);

        b.setPrefSize(170,50);

        b.setOnMousePressed(e->{
            b.setStyle(BUTTON_PRESSED);
        });

        b.setOnMouseReleased(e->{
            b.setStyle(BUTTON_FREE);
        });
    }

    private Button makeButton(String name, int i) {
        Button b = new Button(name);
        b.setFont(new Font(20));
        b.setTranslateY(height-100);
        b.setTranslateX(START_BUTTON_X + i * 100);
        buttonStyle(b);
        return b;
    }

    public void updateActionButtons(EntityView e) {
        actions.getChildren().clear();
        actions.getChildren().add(endTurn);
        int nbOfActions=e.getActionNames().length;
        actionButtons= new LinkedList<>();
        for (int i = 0; i < nbOfActions; i++) {
            ActionButton b = new ActionButton(e.getActionNames()[i], e.getActionDesc()[i],i);
            actionButtons.add(i,b);
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
        b.setTranslateX(START_BUTTON_X + nbOfButtons*200);
        buttonStyle(b);
        nbOfButtons++;
    }
}
