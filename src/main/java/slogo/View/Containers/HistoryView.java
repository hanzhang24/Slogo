package slogo.View.Containers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HistoryView extends ContainerView {

  private static final String DEFAULT_RESOURCE_PACKAGE = "View.";
  private static final String DEFAULT_RESOURCE_FOLDER = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/");

  private static final String HISTORY_REFLECTION = "ReflectionActions";
  private static final String STYLESHEET = "HistoryView.css";
  private ComboBox<String> Options;

  private ResourceBundle ReflectionResources;
  private static final ObservableList<String> DROP_DOWN_OPTIONS =
      FXCollections.observableArrayList(
          "Commands",
          "Library",
          "Help"
      );
  private String storedHistory;
  private String storedLibrary;
  private String help;
  private TextArea historyDisplay;


  public HistoryView(){
    VBox container = new VBox();
    container.setId("History-Container");
    container.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    this.setContainer(container);
    setUpComboBox(DROP_DOWN_OPTIONS, container);
    historyDisplay = new TextArea();
    historyDisplay.setId("History-Display");
    container.getChildren().add(historyDisplay);
    ReflectionResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + HISTORY_REFLECTION);
    storedHistory = "";
    storedLibrary = "";
    help = "";
  }

  private void setUpComboBox(ObservableList<String> options, VBox container) {
    Options = new ComboBox<>(options);
    Options.setId("History-Selector");
    Options.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> updateHistoryDisplay(Options.getValue())));
    container.getChildren().add(Options);
  }

  private void updateHistoryDisplay(String selected){
      try {
        String methodName = ReflectionResources.getString(selected);
        Method m = this.getClass().getDeclaredMethod(methodName);
        m.invoke(this);
      } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  public void displayCommands(){
    historyDisplay.clear();
    historyDisplay.setText(storedHistory);
  }

  public void displayLibrary(){
    historyDisplay.clear();
    historyDisplay.setText(storedLibrary);
  }
  public void displayHelp(){
    historyDisplay.setText(help);
  }

  public Pane getHistoryContainer(){
    return this.getContainer();
  }

  public void updateCommandHistory(String userInput) {
    storedHistory = storedHistory + "\n" + userInput;
    historyDisplay.setText(storedLibrary);
  }
  public void updateLibraryHistory(String userInput) {
    storedLibrary = storedLibrary + userInput;
  }
}
