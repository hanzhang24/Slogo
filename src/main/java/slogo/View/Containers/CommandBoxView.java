package slogo.View.Containers;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import slogo.Controller.Controller;
import slogo.View.Animator;

public class CommandBoxView extends ContainerView {
  private Controller controller;
  public CommandBoxView(Animator animations){
    Pane container = new HBox();
    this.setContainer(container);
    container.setId("Command-HBox");
    TextArea textBox = new TextArea();
    textBox.setId("Text-Box");
    container.getChildren().add(textBox);
    createButtons(textBox, animations);
  }
  private void createButtons(TextArea textBox, Animator animations) {
    VBox buttons = new VBox();
    buttons.setId("Command-VBox");
    Button run = new Button("Run");
    run.setId("Run");
    run.setOnAction(e -> sendText(textBox, animations));
    Button clear = new Button("Clear");
    clear.setId("Clear");
    clear.setOnAction(e -> textBox.clear());
    buttons.getChildren().add(run);
    buttons.getChildren().add(clear);
    this.getContainer().getChildren().add(buttons);
  }

  private void sendText(TextArea textBox, Animator animations){
    String input = textBox.getText();
    //send to the control/parser
    try {
      controller.runInput(input);
      animations.resetAnimations();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  public void setController(Controller controller){
    this.controller = controller;
  }

  public Pane getCommandContainer(){
    return this.getContainer();
  }
}
