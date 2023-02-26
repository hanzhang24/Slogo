package View;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class DrawBoardView {

  private Pane HBox;
  private Canvas canvas;
  public DrawBoardView(){
    //TODO move into actual class
    HBox = new HBox();
    HBox.setId("Canvas");
    canvas = new Canvas(500, 500);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    HBox.getChildren().add(canvas);

  }
  public Pane getContainer(){
    return HBox;
  }
}
