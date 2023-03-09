package slogo.View.Containers;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import slogo.Controller.Controller;
import slogo.View.Animator;
import slogo.View.PopUp;

public class CommandBoxView extends ContainerView {
  private Controller controller;
  private TextArea textBox;
  private Animator animations;

  public CommandBoxView(Animator animations){
    this.animations = animations;
    Pane container = new HBox();
    this.setContainer(container);
    container.setId("Command-HBox");
    textBox = new TextArea();
    textBox.setId("Text-Box");
    container.getChildren().add(textBox);
  }

  public void sendText(){
    String input = textBox.getText();
    try {
      controller.runInput(input);
      animations.resetAnimations();
    } catch (Exception e) {
      new PopUp(e.getMessage());
    }
  }

  public void clear() {
    textBox.clear();
  }
  public void setController(Controller controller){
    this.controller = controller;
  }

  public Pane getCommandContainer(){
    return this.getContainer();
  }
}
