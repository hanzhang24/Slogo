package slogo.View.Containers;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CommandBoxView extends ContainerView {

  public CommandBoxView(){
    container = new HBox();
    container.setId("Command-HBox");
    TextArea textBox = new TextArea();
    textBox.setId("Text-Box");
    container.getChildren().add(textBox);
    createButtons(textBox);
  }

  private void createButtons(TextArea textBox) {
    VBox buttons = new VBox();
    buttons.setId("Command-VBox");
    Button run = new Button("Run");
    run.setId("Run");
    run.setOnAction(e -> sendText(textBox));
    Button clear = new Button("Clear");
    run.setId("Clear");
    clear.setOnAction(e -> textBox.clear());
    buttons.getChildren().add(run);
    buttons.getChildren().add(clear);
    container.getChildren().add(buttons);
  }

  private void sendText(TextArea textBox){
    String input = textBox.getText();
    //send to the control/parser
    //parser.parseInput(input)
  }
}