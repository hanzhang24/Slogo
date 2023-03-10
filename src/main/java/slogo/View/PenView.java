package slogo.View;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public abstract class PenView {
  private ImageView image;

  private double modelX;
  private double modelY;
  private double XCor;
  private double YCor;
  private double rot;
  private boolean penActive;

  private boolean visible;

  private Color color;

  //TODO checkout what kind of variable this should be
  public static final String IMAGE_PATH = "/View/Images/";

  public PenView() {
    penActive = true;
    visible = true;
    rot = 90;
  }

  public void updatePen(boolean penActive) {
    this.penActive = penActive;
  }

  public void updateColor(Color color) {
    this.color = color;
  }

  public void updatePosXY(double newX, double newY) {
    this.XCor = newX;
    this.YCor = newY;
//    image.setX(newX + 275);
//    image.setY(-1 * newY + 275);
  }
  public void updateRot(double newRot) { rot = (-1 * newRot + 90); } // to keep the orientation consistent - Alec :))))
  public double getXCor(){return XCor;}
  public double getYCor(){return YCor;}
  public double getRot(){return rot;}
  public boolean getPenActive(){return penActive;}
  public Color getColor(){return color;}

  public ImageView getImage() {
    return image;
  }
  protected void setImage(ImageView newImage) {image = newImage;}
  public void setCoordinates(double X, double Y) {
    XCor = X;
    YCor = Y;
    image.setX(X-25);
    image.setY(Y-25);
  }
  public void setModelCoordinates(double X, double Y){
    modelX = X;
    modelY = Y;
  }

  public double getModelX() {
    return modelX;
  }
  public double getModelY(){
    return modelY;
  }
  public void changeVisible() {
    visible = !visible;
    image.setVisible(visible);
  }
}
