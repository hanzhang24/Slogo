package slogo.View;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public abstract class PenView {
  private ImageView image;

  private double XCor;
  private double YCor;
  private boolean penActive;

  private Color color;
  private Line line;

  //TODO checkout what kind of variable this should be
  public static final String IMAGE_PATH = "/View/Images/";

  public PenView() {
    line = new Line(XCor + 300, YCor + 300, XCor + 300, YCor + 300);
    line.setFill(Color.BLACK);
    penActive = true;
  }

  public Group getView() {
    Group group = new Group();
    group.getChildren().add(image);
    group.getChildren().add(line);
    return group;
  }

  public void updatePen(boolean penActive) {
    this.penActive = penActive;
  }

  public void updateColor(Color color) {
    this.color = color;
  }

  public void updatePosXY(double newX, double newY) {
//    line.setEndX(newX + 300);
//    line.setEndY(newY + 300);
    this.XCor = newX + 275;
    this.YCor = newY + 275;
//    image.setX(newX + 275);
//    image.setY(-1 * newY + 275);
  }
  public void updateRot(double newRot) { image.setRotate(-1 * newRot + 90); } // to keep the orientation consistent - Alec :))))
  public double getXCor(){return XCor;}
  public double getYCor(){return YCor;}
  public boolean getPenActive(){return penActive;}
  public Color getColor(){return color;}

  public ImageView getImage() {
    return image;
  }
  protected void setImage(ImageView newImage) {image = newImage;}
  public void setCoordinates(double X, double Y) {
    XCor = X;
    YCor = Y;
    image.setX(X + 25);
    image.setY(Y + 25);
  }
}
