package slogo.View.Screens;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class GameScreen extends Screen implements ModelView{

  private Color color;
  private PenView avatar;
  private Animator animations;
  private CommandBoxView commandBoxView;
  private HistoryView historyView;
  private DrawBoardView canvas;

  private FileChooser fileChooser;

  public GameScreen(Stage stage, String language, Color color) {
    super(language, stage);
    setScreenLayout("GameScreenLayout");
    setStylesheet("Day.css");
    setLayoutResources(ResourceBundle.getBundle(getDEFAULT_RESOURCE_PACKAGE() + getScreenLayout()));
    fileChooser = new FileChooser();
    this.color = color;
  }

  public Scene makeScene(int width, int height) {
    setPane(new GridPane());

    createCanvas();
    createTurtle();
    createCommandBox();
    createButtons();
    createHistoryView();
    createColorPicker();
    createColorSchemePicker();

    setPositions(getRoot());
    setScene(new Scene(getAllNodes(), width, height));
    getScene().getStylesheets().add(getClass().getResource(getDEFAULT_RESOURCE_FOLDER() + getStylesheet()).toExternalForm());

    return getScene();
  }

  private void createCanvas() {
    canvas = new DrawBoardView();
    canvas.setColor(this.color);

    getRoot().getChildren().add(canvas.getContainer());
  }

  private void createTurtle() {
    avatar = new Turtle();
    avatar.getImage().toBack();
    getAllNodes().getChildren().add(avatar.getImage());
    animations = new Animator(avatar, canvas);
  }

  private void createCommandBox() {
    commandBoxView = new CommandBoxView(animations);
    getRoot().getChildren().add(commandBoxView.getCommandContainer());
  }

  private void createButtons() {
    HBox container = makeInputPanel(getPanelButtons("GameScreenNavigationPanel", getPanelResources()), this, getLabelResources(), getReflectionResources());
    container.setId("Animation-Panel");

    SliderView animationInputs  = new SliderView(animations);
    container.getChildren().add(animationInputs.getSliderContainer());
    getRoot().getChildren().add(container);
  }

  private void createColorPicker() {
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setId("ColorPicker");
    colorPicker.setOnAction(handler -> {
      color = colorPicker.getValue();
      canvas.setColor(color);
    });
    getRoot().getChildren().add(colorPicker);
  }

  private void createColorSchemePicker() {
    List<String> options = getPanelButtons("ColorSchemesPanel", getPanelResources());
    String id = "Color-Scheme-Box";
    ComboBox ColorSchemePicker = makeDropDown(options, id);
    ColorSchemePicker.setId("ColorSchemePicker");
    ColorSchemePicker.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> updateScheme(ColorSchemePicker.getValue())));
    getRoot().getChildren().add(ColorSchemePicker);
  }

  private void updateScheme(Object value) {
    getScene().getStylesheets().clear();
    setStylesheet(value.toString() + ".css");
    getScene().getStylesheets().add(getClass().getResource(getDEFAULT_RESOURCE_FOLDER() + getStylesheet()).toExternalForm());
    getStage().setScene(getScene());
  }

  private void createHistoryView() {
    historyView = new HistoryView();
    VBox container = historyView.make(getPanelButtons("DropDownPanel", getPanelResources()), getLabelResources());
    container.setId("History-Container");
    getRoot().getChildren().add(container);
  }

  public void changeAvatar() {
    getAllNodes().getChildren().remove(avatar.getImage());
    File selectedFile = fileChooser.showOpenDialog(getStage());
    Image image = new Image(selectedFile.toURI().toString());
    ImageView imageView = new ImageView(image);
    getAllNodes().getChildren().add(avatar.setImage(imageView));
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

  public double getAnimationSpeed() {
    return animations.getAnimationSpeed();
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
