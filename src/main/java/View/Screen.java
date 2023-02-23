package View;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Group;

public abstract class Screen {

  public Pane root;
  public Scene scene;
  public abstract Scene makeScene();
}
