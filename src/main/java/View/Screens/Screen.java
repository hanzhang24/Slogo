package View.Screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public abstract class Screen {

  protected Stage stage;
  protected Pane root;
  protected Scene scene;

  public static final String DEFAULT_RESOURCE_PACKAGE = "View.";

  protected ResourceBundle resources;

  public Screen(Stage stage, String language) {
    this.stage = stage;
    resources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
  }

  public Scene makeScene(int width, int height) {
    return null;
  };

  protected Node makeLabel (String property) {
    Label label = new Label(resources.getString(property));
    return setID(property, label);
  }

  protected Node makeButton(String property, EventHandler<ActionEvent> response) {
    Button result = new Button();
    result.setText(resources.getString(property));
    result.setOnAction(response);
    return setID(property, result);
  }

  protected Node makeActions (Node ... buttons) {
    HBox panel = new HBox();
    panel.getStyleClass().add("button-box");
    panel.getChildren().addAll(buttons);
    return panel;
  }




  protected Node setID (String id, Node node) {
    node.setId(id);
    return node;
  }

}
