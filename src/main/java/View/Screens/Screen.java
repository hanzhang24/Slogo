package View.Screens;

import View.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.awt.*;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


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

  protected Label makeLabel (String property) {
    Label label = new Label(resources.getString(property));
    return label;
  }

  protected Button makeButton(String property, EventHandler<ActionEvent> response) {
    Button result = new Button();
    result.setText(resources.getString(property));
    result.setOnAction(response);
    return result;
  }

  protected Node makeActions (Node ... buttons) {
    HBox panel = new HBox();
    panel.getStyleClass().add("button-box");
    panel.getChildren().addAll(buttons);
    return panel;
  }

  protected ColorPicker makeColorPicker (String id) {
    ColorPicker picker = new ColorPicker();
    return picker;
  }

  protected ComboBox makeLanguagePicker (ObservableList<String> options) {
    ComboBox languageComboBox = new ComboBox(options);
    return languageComboBox;
  }

}
