package slogo.View.Screens;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public abstract class Screen {

  private static final String DEFAULT_RESOURCE_PACKAGE = "View.";
  private static final String DEFAULT_RESOURCE_FOLDER = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private static final String REFLECTION_RESOURCES = "ReflectionActions";
  private static final String PANEL_RESOURCES = "PanelActions";
  private static final String STYLESHEET = "GameScreen.css";

  private ResourceBundle LabelResources;
  private ResourceBundle ReflectionResources;
  private ResourceBundle PanelResources;

  private Pane root;
  private Scene scene;
  private Stage stage;


  /**
   * Abstract class for Screen that sets up ResourceBundles
   * @param language is the default branch
   */
  public Screen(String language, Stage stage) {
    this.stage = stage;
    LabelResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    ReflectionResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + REFLECTION_RESOURCES);
    PanelResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + PANEL_RESOURCES);
  }

  /**
   * Method for creating screen to setUp scenes, allows for flexibility when reassigning css stylesheets
   * @param width width of the Scene in pixels
   * @param height height of the Scenes in pixels
   * @return the Scene that represents the Screen
   */
  public abstract Scene makeScene(int width, int height);

  /**
   * Allows for
   * @param property
   * @param LabelResources
   * @return
   */
  protected Label makeLabel (String property, ResourceBundle LabelResources) {
    Label label = new Label(LabelResources.getString(property));
    return label;
  }


  /**
   *
   * @param property
   * @param PanelResources
   * @return
   */
  // get button actions for each panel from resource file
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


}
