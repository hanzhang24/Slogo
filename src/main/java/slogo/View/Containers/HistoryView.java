package slogo.View.Containers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HistoryView extends ContainerView {

  private static final String DEFAULT_RESOURCE_PACKAGE = "View.";
  private static final String DEFAULT_RESOURCE_FOLDER = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private static final String STYLESHEET = "HistoryView.css";
  private ComboBox<String> Options;

  private static final ObservableList<String> DROP_DOWN_OPTIONS =
      FXCollections.observableArrayList(
          "Command History",
          "Library",
          "Help"
      );


  private String Commands;
  private String Functions;
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
  }

  private void setUpComboBox(ObservableList<String> options, VBox container) {
    Options = new ComboBox<>(options);
    Options.setId("History-Selector");
    Options.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> updateHistoryDisplay(Options.getValue())));
    container.getChildren().add(Options);
  }

  private void updateHistoryDisplay(String selected){
    //TODO work with Package to Decode History and add Text
    historyDisplay.setText(selected);
  }

  public Pane getHistoryContainer(){
    return this.getContainer();
  }
}
