package View;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Group;

public abstract class Screen {

  Pane root;
  Scene scene;
  public abstract Scene makeScene(int width, int height);
}
