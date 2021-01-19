package WeddingLottery;


import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        List<String> suitors = new LinkedList<>();
        TextField nameEntryTextField = new TextField();
        TextArea nameListTextArea = new TextArea();

        Button phase2Button = new Button("Go to the Suitor Selection Phase");
        BorderPane phase1TopLevelPane = new BorderPane();
        phase1TopLevelPane.setPadding(new Insets(10));

        // Miscellaneous GUI and Layout components

        HBox suitorEntryHBox = new HBox(10);
        BorderPane.setMargin(suitorEntryHBox, new Insets(5, 0, 10, 0));
        suitorEntryHBox.getChildren().add(new Label("Enter a suitor's name"));
        suitorEntryHBox.getChildren().add(nameEntryTextField);
        phase1TopLevelPane.setTop(suitorEntryHBox);

        phase1TopLevelPane.setCenter(nameListTextArea);

        phase1TopLevelPane.setBottom(phase2Button);
        BorderPane.setMargin(phase2Button, new Insets(10, 0, 5, 0));
        // Usual Scene and Stage stuff
        Scene scene = new Scene(phase1TopLevelPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Enter names of suitors");
        primaryStage.show();

        // Add the event handler for name entry
        EventHandler<ActionEvent> nameListener =
                new FXNameListListener(nameEntryTextField, nameListTextArea, suitors);
        nameEntryTextField.setOnAction(nameListener);

        // Add the event handler for the phase 2 button
        phase2Button.setOnAction(new FXPhase2ButtonListener(primaryStage, suitors));
    }


    public static void main(String[] args) {
        launch(args);
    }
}

/**
 * This listener will be attached to the NameEntryTextField.
 */
class FXNameListListener implements EventHandler<ActionEvent>
{
    private TextField nameEntryTextField;
    private TextArea nameListTextArea;
    private List<String> suitors;

    public FXNameListListener(TextField nameEntryTextField,
                              TextArea nameListTextArea,
                              List<String> suitors)
    {
        this.nameEntryTextField = nameEntryTextField;
        this.nameListTextArea = nameListTextArea;
        this.suitors = suitors;
    }

    @Override
    public void handle(ActionEvent e)
    {
        String name = nameEntryTextField.getText();
        nameListTextArea.appendText(name + "\n");
        suitors.add(name);
    }
}
/**
 * This listener will close the phase 1 Frame
 */
class FXPhase2ButtonListener implements EventHandler<ActionEvent>
{
    private Stage stage;
    private List<String> suitors;
    public FXPhase2ButtonListener(Stage stage,  List<String> suitors)
    {
        this.stage = stage;
        this.suitors = suitors;
    }

    @Override
    public void handle(ActionEvent e)
    {
        // Build the user interface for phase 2
        TextArea rejectedTArea = new TextArea();
        TextArea hopefulsTArea = new TextArea();
        TextField rotateStepTextField = new TextField();

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(rejectedTArea, hopefulsTArea);

        HBox rotateStepHBox = new HBox(10);
        rotateStepHBox.setPadding(new Insets(10, 0, 0, 0));
        rotateStepHBox.getChildren().add(new Label("Enter a rotation step "));
        rotateStepHBox.getChildren().add(rotateStepTextField);

        BorderPane phase2TopLevelPane = new BorderPane(); //
        phase2TopLevelPane.setPadding(new Insets(10));
        phase2TopLevelPane.setCenter(hBox);
        phase2TopLevelPane.setBottom(rotateStepHBox);

        Scene scene = new Scene(phase2TopLevelPane);
        stage.setScene(scene);
        stage.setTitle("Phase 2: Suitor Attrition Step");
        stage.show();


        // All suitors go into hopefuls Text Area
        for (String s : suitors)
        {
            hopefulsTArea.appendText(s + "\n");
        }

        // Event Handler for the rotateStepTextField
        EventHandler<ActionEvent> rotateStepListener =
                new FXRotateStepListener(hopefulsTArea, rejectedTArea, suitors);
        rotateStepTextField.setOnAction(rotateStepListener);
    }
}


class FXRotateStepListener implements EventHandler<ActionEvent> {
    private LotteryList lotteryList;
    private TextArea hopefulsTArea, rejectedTArea;

    public FXRotateStepListener(TextArea hopefuls, TextArea rejected, List<String> suitors) {
        lotteryList = new LotteryList(suitors, null);
        this.hopefulsTArea = hopefuls;
        this.rejectedTArea = rejected;
    }

    @Override
    public void handle(ActionEvent e) {
        if (lotteryList.getRemainingSuitors().size() == 1) {
            JOptionPane.showMessageDialog(null, "There is only one suitor left!");
            return;
        }

        // Get rotate step from text field
        int step = Integer.parseInt(((TextField) e.getTarget()).getText());
        lotteryList.rotateSingleStep(step);
        lotteryList.reject();

        // Update user interface
        java.util.List<String> rejected = lotteryList.getEliminated();
        java.util.List<String> hopefuls = lotteryList.getRemainingSuitors();
        rejectedTArea.setText("");
        hopefulsTArea.setText("");
        for (int k = 0; k < rejected.size(); k++) {
            rejectedTArea.appendText(rejected.get(k) + "\n");
        }
        for (int k = 0; k < hopefuls.size(); k++) {
            hopefulsTArea.appendText(hopefuls.get(k) + "\n");
        }
    }
}