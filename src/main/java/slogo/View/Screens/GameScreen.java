package slogo.View.Screens;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.Main;
import slogo.Parser.XMLParser;
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
import slogo.View.PopUp;

/**
 * @author aryankothari, hanzhang
 */

public class GameScreen extends Screen implements ModelView {

  private Color color;
  private List<PenView> avatars;
  private PenView avatar;
  private Animator animations;
  private CommandBoxView commandBoxView;
  private HistoryView historyView;
  private DrawBoardView canvas;
  private final FileChooser fileChooser;

  public GameScreen(Stage stage, String language, Color color) {
    super(language, stage);
    setScreenLayout("GameScreenLayout");
    setStylesheet("Day.css");
    setLayoutResources(ResourceBundle.getBundle(getDEFAULT_RESOURCE_PACKAGE() + getScreenLayout()));
    fileChooser = new FileChooser();
    this.color = color;
    avatars = new ArrayList<>();
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
//    animations = new Animator(avatar, canvas);
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


  /**
   * Create both the Turtle and Animation needed to manage the Turtle
   */
  private void MakeTurtle() {
    Turtle newTurtle = new Turtle();
    newTurtle.getImage().toBack();
    avatars.add(newTurtle);
    getAllNodes().getChildren().add(newTurtle.getImage());
    animations = new Animator(avatars, canvas);
  }

  private void createHistoryView() {
    historyView = new HistoryView();
    VBox container = historyView.make(getPanelButtons("DropDownPanel", getPanelResources()),
        getLabelResources());
    container.setId("History-Container");
    getRoot().getChildren().add(container);
  }

  /**
   * Allows us to Change the image of the Avatar
   */
  public void changeAvatar() {
    for(PenView avatar: avatars){
      getAllNodes().getChildren().remove(avatar.getImage());
    }
    File selectedFile = fileChooser.showOpenDialog(getStage());
    Image image = new Image(selectedFile.toURI().toString());
    ImageView imageView = new ImageView(image);
    for(PenView avatar: avatars){
      getAllNodes().getChildren().add(avatar.setImage(imageView));
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

  public double getAnimationSpeed() {
    return animations.getAnimationSpeed();
  }

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
    getAllNodes().getChildren().add(avatar.getImage());
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

  // TODO: refactor the following section to be in the right class, have no hard-coded values, add the actual help button

  /**
   * Handler function to display help dialog box
   */
  public void displayHelp() {
    String pathName = "Parser.Commands.English";
    ResourceBundle resourceBundle = ResourceBundle.getBundle(pathName);
    Enumeration<String> allCommands = resourceBundle.getKeys();
    List<String> allCommandKeys = formatKeysAsString(allCommands);
    ChoiceDialog choiceDialog = new ChoiceDialog("Select a command...", allCommandKeys);
    choiceDialog.setTitle("Help");
    choiceDialog.setHeaderText("Command Documentation");

    Optional<String> result = choiceDialog.showAndWait();
    if (result.isPresent()) {
      fetchAndDisplayDocumentation((String) choiceDialog.getSelectedItem());
    }
  }

  /**
   * Formats the valid command names as a list of Strings to display in the ChoiceDialog
   *
   * @param allCommands keys of every supported command
   * @return list of formatted Strings including every valid command
   */
  private static List<String> formatKeysAsString(Enumeration<String> allCommands) {
    List<String> allCommandKeys = new ArrayList<>();
    while (allCommands.hasMoreElements()) {
      String command = allCommands.nextElement();
      if (command.contains(".")) {
        String[] parsed = command.split("\\.");
        command = parsed[parsed.length - 1];
      }
      allCommandKeys.add(command);
    }
    Collections.sort(allCommandKeys);
    return allCommandKeys;
  }

  /**
   * Fetches the correct XML file for a command and displays its contents in a dialog box
   *
   * @param commandName name of the requested command
   */
  private void fetchAndDisplayDocumentation(String commandName) {
    if (commandName.equals("Select a command...")) {
      return;
    }
    String USER_DIRECTORY = System.getProperty("user.dir");
    try {
      XMLParser xmlParser = new XMLParser(
          USER_DIRECTORY + "/src/main/resources/Parser/Commands/" + commandName + ".xml");
      Alert alert = formatDocumentationDialog(xmlParser);
      alert.showAndWait();
    } catch (Exception e) {
      new PopUp("Sorry, the documentation for this command has not been added yet... coming soon!");
    }

  }

  /**
   * Using the given XML parser, build an Alert to display all information
   *
   * @param xmlParser configured XML parser for the specific command
   * @return configured alert
   */
  private static Alert formatDocumentationDialog(XMLParser xmlParser) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Command Documentation");
    alert.setHeaderText("Learn More:");
    String body = String.format(
        "Name:    %s\nSyntax:    %s\nNumber of Parameters:    %s\nParams:    %s\nReturns:    %s\nDescription:    %s\nClassification:    %s",
        xmlParser.getName(), xmlParser.getSyntax(), xmlParser.getNumParameters(),
        xmlParser.getParams(), xmlParser.getReturns(), xmlParser.getDescription(),
        xmlParser.getType());
    alert.setContentText(body);
    return alert;
  }

  public void openNewWindow(){
    Main main = new Main();
    main.start(new Stage());
  }
}
