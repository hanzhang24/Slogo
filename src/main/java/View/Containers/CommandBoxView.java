package View.Containers;

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
    VBox buttons = new VBox();
    buttons.setId("Command-VBox");
    Button run = new Button("Run");
    run.setId("Run");
    Button clear = new Button("Clear");
    run.setId("Clear");
    buttons.getChildren().add(run);
    buttons.getChildren().add(clear);
    container.getChildren().add(buttons);
  }

}
