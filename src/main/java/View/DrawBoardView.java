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
    canvas = new Canvas(500, 500);
    canvas.setId("Canvas");
    GraphicsContext gc = canvas.getGraphicsContext2D();
    HBox.getChildren().add(canvas);

    gc.fillRect(100,100,100,100);
  }
  public Node getCanvas() {
    return canvas;
  }
  public Pane getContainer(){
    return HBox;
  }
}
