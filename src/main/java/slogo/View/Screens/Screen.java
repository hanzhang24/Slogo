package slogo.View.Screens;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
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

  public abstract Scene makeScene(int width, int height);

  protected Label makeLabel (String property, ResourceBundle LabelResources) {
    Label label = new Label(LabelResources.getString(property));
    return label;
  }


  // get button actions for each panel from resource file
  protected List<String> getPanelButtons (String property, ResourceBundle PanelResources) {
    return Arrays.asList(PanelResources.getString(property).split(","));
  }

  protected Node makeInputPanel (List<String> actions, Screen screen, ResourceBundle LabelResources, ResourceBundle ReflectionResources) {
    HBox result = new HBox();
    // create buttons, with their associated actions
    for (String a : actions) {
      result.getChildren().add(makeButton(screen, a, LabelResources, ReflectionResources));
    }
    return result;
  }

  // makes a button using either an image or a label
  private Button makeButton (Screen screen, String property, ResourceBundle LabelResources, ResourceBundle ReflectionResources) {
    // represent all supported image suffixes
    Button result = new Button();
    String label = LabelResources.getString(property);
    result.setText(label);
    // turn given string into method call
    result.setOnAction(handler -> {
      try {
        String methodName = ReflectionResources.getString(property);
        Method m = screen.getClass().getDeclaredMethod(methodName);
        m.invoke(screen);
      } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });
    result.setId(property);
    return result;
  }

}
