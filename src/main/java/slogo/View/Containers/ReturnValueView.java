package slogo.View.Containers;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ReturnValueView extends ContainerView{

  private TextArea display;
  private String returnValues;
  private static final String DEFAULT_RESOURCE_PACKAGE = "View.";
  private static final String RETURN_RESOURCES = "ReturnParams";

  private final ResourceBundle ReturnValueView;
  public ReturnValueView(){
    HBox container = new HBox();
    container.setId("Return-Container");
    display = new TextArea();
    display.setEditable(false);
    display.setId("Return-Value");
    container.getChildren().add(display);
    this.setContainer(container);
    ReturnValueView = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + RETURN_RESOURCES);
  }

  public void setReturnValue(List<String> Input){
    if(returnValues == null){
      returnValues = Input.get(Input.size()-1);
    }
    else {
      returnValues = returnValues + Input.get(Input.size()-1);
    }
    returnValues = returnValues + ReturnValueView.getString("Seperator");
    display.setText(returnValues);
  }
  public Pane getReturnContainer(){
    return this.getContainer();
  }
}
