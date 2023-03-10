package slogo.View;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DrawBoardView {

  private Pane HBox;
  private Canvas canvas;
  private GraphicsContext gc;
  private Color color;
  public DrawBoardView(){
    //TODO move into actual class
    HBox = new HBox();
    HBox.setId("Canvas");
    canvas = new Canvas(500, 500);
    gc = canvas.getGraphicsContext2D();
    HBox.getChildren().add(canvas);
  }
  public Pane getContainer(){
    return HBox;
  }

  public void clear() {
    gc.clearRect(0,0, 500,500);
  }

  public void draw(double v, double v1, double xCor, double yCor) {
    System.out.printf("%f%f%f%f", v,v1,xCor, yCor);
    gc.strokeLine(v, v1, xCor, yCor);
  }
  public void setColor(Color newColor){
    color = newColor;
    gc.setStroke(color);
  }
}
