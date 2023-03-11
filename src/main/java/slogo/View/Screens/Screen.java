package slogo.View.Screens;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public abstract class Screen {

  private final String DEFAULT_RESOURCE_PACKAGE = "View.";
  private final String DEFAULT_RESOURCE_FOLDER = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private final String REFLECTION_RESOURCES = "ReflectionActions";
  private final String PANEL_RESOURCES = "PanelActions";

  private ResourceBundle LabelResources;
  private ResourceBundle ReflectionResources;
  private ResourceBundle PanelResources;

  private Pane root;
  private Scene scene;
  private Stage stage;
  private Group allObjects;



  public Screen(String language, Stage stage) {
    this.stage = stage;
    allObjects = new Group();
    LabelResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    ReflectionResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + REFLECTION_RESOURCES);
    PanelResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + PANEL_RESOURCES);
  }
  protected void setPane(Pane pane) {
    root = pane;
    root.getStyleClass().add("grid-pane");
    root.setId("Pane");
    allObjects.getChildren().add(root);
  }

  protected void setIndexes(String[] indexes, Pane node) {
    getRoot().getChildren().add(node);
    GridPane.setConstraints(node, Integer.parseInt(indexes[0]),Integer.parseInt(
            indexes[1]));
  }
  protected Label makeLabel (String property, ResourceBundle LabelResources) {
    Label label = new Label(LabelResources.getString(property));
    return label;
  }
  protected List<String> getPanelButtons (String property, ResourceBundle PanelResources) {
    return Arrays.asList(PanelResources.getString(property).split(","));
  }
  protected HBox makeInputPanel (List<String> actions, Screen screen, ResourceBundle LabelResources, ResourceBundle ReflectionResources) {
    HBox result = new HBox();
    // create buttons, with their associated actions
    for (String a : actions) {
      result.getChildren().add(makeButton(screen, a, LabelResources, ReflectionResources));
    }
    return result;
  }
  protected ComboBox makeDropDown(List<String> items, String id) {
    ComboBox box = new ComboBox();
    box.setId(id);
    for(String s:items) {
      box.getItems().add(s);
    }

    for (int i = 0; i < items.size(); i++) {
       box.getItems().set(i, getLabelResources().getString(items.get(i)));
    }
    return box;
  }
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


  /**
   * getters and setters for all instance variables in class
   */
  protected ResourceBundle getLabelResources() {
    return LabelResources;
  }
  protected void setLabelResources(ResourceBundle labelResources) {
    LabelResources = labelResources;
  }
  protected ResourceBundle getReflectionResources() {
    return ReflectionResources;
  }
  protected void setReflectionResources(ResourceBundle reflectionResources) {
    ReflectionResources = reflectionResources;
  }
  protected ResourceBundle getPanelResources() {
    return PanelResources;
  }
  protected void setPanelResources(ResourceBundle panelResources) {
    PanelResources = panelResources;
  }
  protected Pane getRoot() {
    return root;
  }
  protected void setRoot(Pane root) {
    this.root = root;
  }
  protected Scene getScene() {
    return scene;
  }
  protected void setScene(Scene scene) {
    this.scene = scene;
  }
  protected ResourceBundle getResources() {
    return LabelResources;
  }
  protected void setResources(ResourceBundle resources) {
    this.LabelResources = resources;
  }
  public Stage getStage() {
    return stage;
  }
  public void setStage(Stage stage) {
    this.stage = stage;
  }
  public Group getAllObjects() {
    return allObjects;
  }
  public void setAllObjects(Group allObjects) {
    this.allObjects = allObjects;
  }
  public String getDEFAULT_RESOURCE_FOLDER() {
    return DEFAULT_RESOURCE_FOLDER;
  }

}
