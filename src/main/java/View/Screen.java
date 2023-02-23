package View;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.stage.Stage;

public abstract class Screen {

  public Pane root;
  public Scene scene;
  public Stage stage;
  public static final String BUNDLE_FILE = "resources/View/Screen.properties";
  public ResourceBundle resources = ResourceBundle.getBundle(BUNDLE_FILE);

  public abstract Scene makeScene();
}
