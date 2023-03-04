package slogo.View.Containers;

import java.util.ResourceBundle;
import javafx.scene.layout.Pane;

public abstract class ContainerView {
  private Pane container;

  protected Pane getContainer() {
    return container;
  }
  public void setContainer(Pane cont) {
    container = cont;
  }
}
