package slogo.View.Containers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

public class HistoryView extends ContainerView {

  private static final String DEFAULT_RESOURCE_PACKAGE = "View.";
  private static final String DEFAULT_RESOURCE_FOLDER = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private static final String HISTORY_RESOURCES = "HistoryParams";
  private static final String HISTORY_REFLECTION = "ReflectionActions";
  private static final String STYLESHEET = "HistoryView.css";
  private ComboBox<String> Options;

  private ResourceBundle ReflectionResources;
  private ResourceBundle HistoryResources;

  private String storedHistory;
  private String storedLibrary;
  private TextArea historyDisplay;
  private VBox container;
  private String help;

  public HistoryView(){
    ReflectionResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + HISTORY_REFLECTION);
    HistoryResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + HISTORY_RESOURCES);
  }

  public VBox make(List<String> options, ResourceBundle LabelResources) {
    container = new VBox();
    container.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    this.setContainer(container);

    container.getChildren().add(makeScreen());
    container.getChildren().add(makeDropDown(options, LabelResources));

    return container;
  }

  private ComboBox<String> makeDropDown(List<String> options, ResourceBundle LabelResources) {
    Options = new ComboBox<>();
    for(String s:options) {
      Options.getItems().add(s);
    }

    for (int i = 0; i < options.size(); i++) {
      Options.getItems().set(i, LabelResources.getString(options.get(i)));
    }

    Options.setId("History-Selector");
    Options.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> updateHistoryDisplay(Options.getValue())));
    return Options;
  }

  private TextArea makeScreen() {
    historyDisplay = new TextArea();
    historyDisplay.setId("History-Display");
    historyDisplay.setEditable(false);
    return historyDisplay;
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

  public void displayHelp() { historyDisplay.setText(help);}

  public void displayLibrary(){
    historyDisplay.clear();
    historyDisplay.setText(storedLibrary);
  }

  public Pane getHistoryContainer(){
    return this.getContainer();
  }

  public void updateCommandHistory(String userInput) {
    if(storedHistory == null){
      storedHistory = userInput;
      storedHistory = storedHistory.concat(HistoryResources.getString("Seperator"));
    }else{
      storedHistory = storedHistory.concat(userInput);
      storedHistory = storedHistory.concat(HistoryResources.getString("Seperator"));
    }
    historyDisplay.setText(storedHistory);
  }
  public void updateLibraryHistory(String userInput) {
    storedLibrary = storedLibrary + userInput;
  }
}
