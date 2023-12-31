package slogo.View.Screens;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * Screen is an abstract class that holds the common instance variables and methods that all screens have in common.
 * @authors hanzhang, aryankothari
 */
public abstract class Screen {

  private final String DEFAULT_RESOURCE_PACKAGE = "View.";
  private final String DEFAULT_RESOURCE_FOLDER = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private final String REFLECTION_RESOURCES = "ReflectionActions";
  private final String PANEL_RESOURCES = "PanelActions";

  private String screenLayout;
  private String Stylesheet;

  private ResourceBundle LabelResources;
  private ResourceBundle ReflectionResources;
  private ResourceBundle PanelResources;
  private ResourceBundle LayoutResources;

  private Pane root;
  private Scene scene;
  private Stage stage;
  private Group allNodes;



  public Screen(String language, Stage stage) {
    this.stage = stage;
    allNodes = new Group();
    LabelResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    ReflectionResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + REFLECTION_RESOURCES);
    PanelResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + PANEL_RESOURCES);
  }

  /**
   * sets up the grid pane
   * @param pane
   */
  protected void setPane(Pane pane) {
    root = pane;
    root.getStyleClass().add("grid-pane");
    root.setId("Pane");
    allNodes.getChildren().add(root);
  }

  /**
   * Assigns each node the position on the grid it needs to be. This is done by accessing a properties file that stores
   * the coordinates for each node, found by using its iD. This allows the program to be dynamic.
   * @author aryankothari, hanzhang
   * @param pane
   */
  protected void setPositions(Pane pane) {
    String[] indexes;
    for (Node n : pane.getChildren()) {
      indexes = getLayoutResources().getString(n.getId()).split(",");
      GridPane.setConstraints(n, Integer.parseInt(indexes[0]),Integer.parseInt(
              indexes[1]));
    }
  }

  /**
   * creates label and assigns iD
   * @param property
   * @param LabelResources
   * @return
   */
  protected Label makeLabel (String property, ResourceBundle LabelResources) {
    Label label = new Label(LabelResources.getString(property));
    label.setId(property);
    return label;
  }

  /**
   * allows for program to look up the display of the button based on what language the user has assigned it
   * @param property
   * @param PanelResources
   * @return
   */
  protected List<String> getPanelButtons (String property, ResourceBundle PanelResources) {
    return Arrays.asList(PanelResources.getString(property).split(","));
  }

  /**
   * this function groups all the buttons together under one Hbox.
   * @param actions
   * @param screen
   * @param LabelResources
   * @param ReflectionResources
   * @return
   */
  protected HBox makeInputPanel (List<String> actions, Screen screen, ResourceBundle LabelResources, ResourceBundle ReflectionResources) {
    HBox result = new HBox();
    // create buttons, with their associated actions
    for (String a : actions) {
      result.getChildren().add(makeButton(screen, a, LabelResources, ReflectionResources));
    }
    return result;
  }

  /**
   * create drop down with specified iD
   * @param items
   * @param id
   * @return
   */
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

  /**
   * helper function that creates method, eventually becomes a node in the InputPanel
   * @param screen
   * @param property
   * @param LabelResources
   * @param ReflectionResources
   * @return
   */
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
  public Group getAllNodes() {
    return allNodes;
  }
  public void setAllNodes(Group allNodes) {
    this.allNodes = allNodes;
  }
  public String getDEFAULT_RESOURCE_FOLDER() {
    return DEFAULT_RESOURCE_FOLDER;
  }
  public String getScreenLayout() {
    return screenLayout;
  }
  public void setScreenLayout(String screenLayout) {
    this.screenLayout = screenLayout;
  }
  public String getStylesheet() {
    return Stylesheet;
  }
  public void setStylesheet(String STYLESHEET) {
    this.Stylesheet = STYLESHEET;
  }
  public ResourceBundle getLayoutResources() {
    return LayoutResources;
  }
  public void setLayoutResources(ResourceBundle layoutResources) {
    LayoutResources = layoutResources;
  }
  public String getDEFAULT_RESOURCE_PACKAGE() {
    return DEFAULT_RESOURCE_PACKAGE;
  }


}
