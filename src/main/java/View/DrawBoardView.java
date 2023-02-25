package View;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class DrawBoardView {
  Canvas canvas;
  public DrawBoardView(){
    //TODO move into actual class
    canvas = new Canvas(100, 100);
    canvas.setId("Canvas");
    GraphicsContext gc = canvas.getGraphicsContext2D();
  }
  public Node getCanvas() {
    return canvas;
  }
}
