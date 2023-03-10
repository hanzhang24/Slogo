package slogo.View;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class PopUp {

  public PopUp(String message) {
    Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
    alert.showAndWait();
  }

}
