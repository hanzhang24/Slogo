package slogo.View.Screens;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.View.Animator;
import slogo.View.Containers.HistoryView;
import slogo.View.Containers.SliderView;
import slogo.View.PenView;
import slogo.View.Avatars.Turtle;
import slogo.View.Containers.CommandBoxView;
import slogo.View.DrawBoardView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class GameScreen extends Screen implements ModelView{

  private static final String DEFAULT_RESOURCE_PACKAGE = "View.";
  private static final String DEFAULT_RESOURCE_FOLDER = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private static final String GAME_SCREEN_LAYOUT = "GameScreenLayout";

  private String stylesheet = "Day.css";

  private ResourceBundle LayoutResources;

  private Color color;
  private List<PenView> avatars;
  private Animator animations;
  private CommandBoxView commandBoxView;
  private HistoryView historyView;
  private DrawBoardView canvas;
  private final Group all;
  private final FileChooser fileChooser;

  /**
   * Constructor for GameScreen, which is the class that represents the screen that contains everything
   * required to display to the user for SLogo. This class is essentially a GripPane with
   * objects that aren't inside the GridPane such as PenView are stacked on top.
   * @param stage the stage that the screen will be displayed on
   * @param language The language that the GameScreen is displayed in.
   * @param color the initial color of the trail that will be displayed in the GameScreen
   */
  public GameScreen(Stage stage, String language, Color color) {
    super(language, stage);
    LayoutResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + GAME_SCREEN_LAYOUT);
    fileChooser = new FileChooser();
    setRoot(new GridPane());
    this.color = color;
    all = new Group();
    avatars = new ArrayList<>();
  }

  /**
   * Return the animation speed of the current animation
   * @return a double that represents the current animation speed
   */
  public double getAnimationSpeed() {
    return animations.getAnimationSpeed();
  }

  /**
   * Overrides the default makeScene in Screen abstract
   * @param width width of the Scene in pixels
   * @param height height of the Scenes in pixels
   * @return gives the Scene which contains all the view Objects that need to be displayed
   */
  @Override
  public Scene makeScene(int width, int height) {
    setUpGridPane();
    createCanvas();
    MakeTurtle();
    createCommandBox();
    createButtons();
    createHistoryView();
    makeColorPicker();
    makeColorSchemePicker();

    setScene(new Scene(all, width, height));
    getScene().getStylesheets().add(
        Objects.requireNonNull(getClass().getResource(DEFAULT_RESOURCE_FOLDER + stylesheet)).toExternalForm());

    return getScene();
  }

  /**
   * Sets up the ColorPicker to be added to the view
   */
  private void makeColorPicker() {
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setId("Color-Selector");
    colorPicker.setOnAction(handler -> {
      color = colorPicker.getValue();
      canvas.setColor(color);
    });
    all.getChildren().add(colorPicker);
  }

  private void makeColorSchemePicker() {
    List<String> options = getPanelButtons("ColorSchemesPanel", getPanelResources());
    String id = "Color-Scheme-Box";
    ComboBox ColorSchemePicker = makeDropDown(options, id);
    ColorSchemePicker.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> updateScheme(ColorSchemePicker.getValue())));
    all.getChildren().add(ColorSchemePicker);
  }

  private void updateScheme(Object value) {
    getScene().getStylesheets().clear();
    stylesheet = value.toString() + ".css";
    getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + stylesheet).toExternalForm());
    getStage().setScene(getScene());
  }

  private void createHistoryView() {
    historyView = new HistoryView();
    VBox container = historyView.make(getPanelButtons("DropDownPanel", getPanelResources()), getLabelResources());
    container.setId("History-Container");
    String[] indexes = LayoutResources.getString("HistoryView").split(",");
    setIndexes(indexes, container);
  }

  /**
   * Dictates where in the GripPane the Panes should be
   * @param indexes the indices given by the Properties sheet
   * @param pane The Pane that contains whatever View Class that needs to be added to GameScreen
   */
  private void setIndexes(String[] indexes, Pane pane) {
    getRoot().getChildren().add(pane);
    GridPane.setConstraints(pane, Integer.parseInt(indexes[0]),Integer.parseInt(
        indexes[1]));
  }

  /**
   * Creates the Buttons that display Animation control options and the Animation Speeds
   */
  private void createButtons() {
    HBox container = makeInputPanel(getPanelButtons("GameScreenNavigationPanel", getPanelResources()), this, getLabelResources(), getReflectionResources());
    container.setId("Animation-Panel");

    SliderView animationInputs  = new SliderView(animations);
    container.getChildren().add(animationInputs.getSliderContainer());
    String[] indexes = LayoutResources.getString("ButtonPanel").split(",");
    setIndexes(indexes, container);
  }

  /**
   * Create the CommandBox to display in the GridPane
   */
  private void createCommandBox() {
    commandBoxView = new CommandBoxView(animations);
    String[] indexes = LayoutResources.getString("CommandBox").split(",");
    setIndexes(indexes, commandBoxView.getCommandContainer());
  }

  /**
   * Create the Canvas to display in the GridPane
   */
  private void createCanvas() {
    canvas = new DrawBoardView();
    canvas.setColor(this.color);
    String[] indexes = LayoutResources.getString("Canvas").split(",");
    setIndexes(indexes, canvas.getContainer());
  }

  /**
   * Create both the Turtle and Animation needed to manage the Turtle
   */
  private void MakeTurtle() {
    Turtle newTurtle = new Turtle();
    newTurtle.getImage().toBack();
    avatars.add(newTurtle);
    all.getChildren().add(newTurtle.getImage());
    animations = new Animator(avatars, canvas);
  }

  /**
   * Creates the GridPane that contains everything
   */
  private void setUpGridPane() {
    setRoot(new GridPane());
    getRoot().getStyleClass().add("grid-pane");
    getRoot().setId("Pane");
    all.getChildren().add(getRoot());
  }

  /**
   * Allows us to Change the image of the Avatar
   */
  public void changeAvatar() {
    for(PenView avatar: avatars){
      all.getChildren().remove(avatar.getImage());
    }
    File selectedFile = fileChooser.showOpenDialog(getStage());
    Image image = new Image(selectedFile.toURI().toString());
    ImageView imageView = new ImageView(image);
    for(PenView avatar: avatars){
      all.getChildren().add(avatar.setImage(imageView));
    }
  }

  /**
   * Set the AvatarPenDown to whatever state is required
   * @param penStatus if the pen is down
   */
  public void updateAvatarIsPenDown(int ExternalID, boolean penStatus) {
    getAvatar(ExternalID).updatePen(penStatus);
  }

  /**
   * Implements the method updateAvatarPenColor from ModelView InterFace, Updates the Avatar Pen Colo using RGB values
   * @param red   red color value
   * @param green green color value
   * @param blue  blue color value
   */
  @Override
  public void updateAvatarPenColor(int ExternalID, int red, int green, int blue) {
    Color newColor = Color.rgb(red, green, blue);
    getAvatar(ExternalID).updateColor(newColor);
  }

  /**
   * Creates an animation to make a Translation to update the Avatar's X and Y coordinates
   * @param newX New x coordinate
   * @param newY New y coordinate
   */
  public void updateAvatarPosXY(int ExternalID, double newX, double newY) {
    animations.makeTranslation(ExternalID, newX, newY);
  }

  /**
   * Creates an Animation to rotate the Avatar
   * @param newRot new rotation that needs to be relative
   */
  public void updateAvatarRot(int ExternalID, double newRot) {
    double oldRot = getAvatar(ExternalID).getRot();
    double saved = newRot;
    newRot = -1*newRot + 90;
    newRot = (newRot-oldRot);
    animations.makeRotation(ExternalID, newRot);
    getAvatar(ExternalID).updateRot(saved);
  }

  /**
   * Update if the Avatar is visible or not
   * @param state if the avatar is visible
   */
  @Override
  public void updateAvatarVisible(int ExternalID, boolean state) {
    getAvatar(ExternalID).changeVisible();
  }

  /**
   * Clear the canvas of any drawn lines
   */
  @Override
  public void clearScreen() {
    canvas.clear();
  }

  /**
   * Update the HistoryView's display to reflect new Commands made
   * @param userInput user inputted command added from previous successful run
   */
  @Override
  public void updateDisplayedHistory(String userInput) {
    historyView.updateCommandHistory(userInput);
  }

  /**
   * Display the returnValues from the commands run
   * @param returnValues sequential list of returns values as Strings
   */
  @Override
  public void displayReturnValues(List<String> returnValues) {
  }

  /**
   * Add the commands to the UserLibrary
   * @param functionDescription function content
   */
  @Override
  public void addToUserLibrary(String functionDescription) {
    historyView.updateLibraryHistory(functionDescription);
  }

  @Override
  public void createNewAvatar(int externalID, double numericDefault, boolean booleanDefault,
      double[] colorDefault) {
    PenView avatar = new Turtle(externalID, numericDefault, booleanDefault, colorDefault);
    avatars.add(avatar);
    all.getChildren().add(avatar.getImage());
    animations.updateAvatars(avatar);
  }

  /**
   * Getter method to access the CommandBoxView
   * @return the CommandBoxView
   */
  public CommandBoxView getCommandBoxView(){
    return commandBoxView;
  }

  /**
   * Getter method to access the GameScreen's Avatar
   * @return the GameScreen's current Avatar
   */
  public PenView getAvatar(int ExternalID){
    for(PenView avatar: avatars){
      if(avatar.getID() == ExternalID){
        return avatar;
      }
    }
    return null;
  }
  /**
   * Clear the screen, and run the Animation from the beginning
   */
  public void reset(){
    clearScreen();
    animations.runAnimation();
  }

  /**
   * Pause the Animation
   */
  public void pause(){
    animations.pause();
  }

  /**
   * Step through the Animation
   */
  public void step(){
    animations.step();
  }

  /**
   * Run the commands from the TextBox
   */
  public void run(){ commandBoxView.sendText(); }

  /**
   * Clear the CommandBoxView text area
   */
  public void clear(){ commandBoxView.clear(); }

  /**
   * Clears the animations in preparation of new Steps
   */
  public void initializeSequentialTransition(){
    animations.resetAnimations();
  }

  /**
   * Plays the Animation in GameScreen after the ViewController is done running every ViewCommand
   */
  public void playSequentialTransition(){
    animations.runAnimation();
  }

  /**
   * Updates the PenColor on the Frontend
   * @param penColor new PenColor that will be displayed
   */
  public void updatePenColor(int ExternalID, Color penColor) {
    getAvatar(ExternalID).updateColor(penColor);
  }
}
