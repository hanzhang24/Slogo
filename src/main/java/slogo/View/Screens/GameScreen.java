package slogo.View.Screens;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
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
  private PenView avatar;
  private Animator animations;
  private CommandBoxView commandBoxView;
  private HistoryView historyView;
  private DrawBoardView canvas;
  private Group all;

  private FileChooser fileChooser;

  public GameScreen(Stage stage, String language, Color color) {
    super(language, stage);
    LayoutResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + GAME_SCREEN_LAYOUT);
    fileChooser = new FileChooser();
    setRoot(new GridPane());
    this.color = color;
    all = new Group();
  }

  public double getAnimationSpeed() {
    return animations.getAnimationSpeed();
  }

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
    getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + stylesheet).toExternalForm());

    return getScene();
  }

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

  private void setIndexes(String[] indexes, Pane node) {
    getRoot().getChildren().add(node);
    GridPane.setConstraints(node, Integer.parseInt(indexes[0]),Integer.parseInt(
        indexes[1]));
  }

  private void createButtons() {
    HBox container = makeInputPanel(getPanelButtons("GameScreenNavigationPanel", getPanelResources()), this, getLabelResources(), getReflectionResources());
    container.setId("Animation-Panel");

    SliderView animationInputs  = new SliderView(animations);
    container.getChildren().add(animationInputs.getSliderContainer());
    String[] indexes = LayoutResources.getString("ButtonPanel").split(",");
    setIndexes(indexes, container);
  }

  private void createCommandBox() {
    commandBoxView = new CommandBoxView(animations);
    String[] indexes = LayoutResources.getString("CommandBox").split(",");
    setIndexes(indexes, commandBoxView.getCommandContainer());
  }

  private void createCanvas() {
    canvas = new DrawBoardView();
    canvas.setColor(this.color);
    String[] indexes = LayoutResources.getString("Canvas").split(",");
    setIndexes(indexes, canvas.getContainer());
  }

  private void MakeTurtle() {
    avatar = new Turtle();
    avatar.getImage().toBack();
    all.getChildren().add(avatar.getImage());
    animations = new Animator(avatar, canvas);
  }

  private void setUpGridPane() {
    setRoot(new GridPane());
    getRoot().getStyleClass().add("grid-pane");
    getRoot().setId("Pane");
    all.getChildren().add(getRoot());
  }

  public void changeAvatar() {
    all.getChildren().remove(avatar.getImage());
    File selectedFile = fileChooser.showOpenDialog(getStage());
    Image image = new Image(selectedFile.toURI().toString());
    ImageView imageView = new ImageView(image);
    all.getChildren().add(avatar.setImage(imageView));
  }

  public void updateAvatarIsPenDown(boolean penStatus) {
    avatar.updatePen(penStatus);
  }

  @Override
  public void updateAvatarPenColor(int red, int green, int blue) {
    Color newColor = Color.rgb(red, green, blue);
    avatar.updateColor(newColor);
  }
  public void updateAvatarPosXY(double newX, double newY) {

    animations.makeTranslation(newX, newY);

  }
  public void updateAvatarRot(double newRot) {
    double oldRot = avatar.getRot();
    double saved = newRot;
    newRot = -1*newRot + 90;
    newRot = (newRot-oldRot);
    animations.makeRotation(newRot);
    animations.runAnimation();
    avatar.updateRot(saved);
  }

  @Override
  public void updateAvatarVisible(boolean state) {
    avatar.changeVisible();
  }

  @Override
  public void clearScreen() {
    canvas.clear();
  }

  @Override
  public void updateDisplayedHistory(String userInput) {
    historyView.updateCommandHistory(userInput);
  }
  @Override
  public void displayReturnValues(List<String> returnValues) {
  }

  @Override
  public void addToUserLibrary(String functionDescription) {
    historyView.updateLibraryHistory(functionDescription);
  }
  public CommandBoxView getCommandBoxView(){
    return commandBoxView;
  }
  public PenView getAvatar(){
    return avatar;
  }

  private void reset(){
    clearScreen();
  }
  private void pause(){
    animations.pause();
  }
  private void step(){
    animations.step();
  }
  public void run(){ commandBoxView.sendText(); }
  public void clear(){ commandBoxView.clear(); }

  public void initializeSequentialTransition(){
    animations.resetAnimations();
  }
  public void playSequentialTransition(){
    animations.runAnimation();
  }
  public void updatePenColor(Color penTest) {
    this.avatar.updateColor(penTest);
  }
}
