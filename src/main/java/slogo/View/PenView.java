package slogo.View;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public abstract class PenView {
  private ImageView image;

  private double XCor;
  private double YCor;
  private double rot;
  private boolean penActive;
  private boolean visible;
  private Color color;

  public static final String IMAGE_PATH = "/View/Images/";

  public PenView() {
    penActive = true;
    visible = true;
    rot = 90;
  }


  public void defaultPos() {
    //TODO fix magic numbers
    image.setRotate(90); // to orient turtle facing right - Alec :)))
    image.setFitWidth(50);
    image.setFitHeight(50);
    setCoordinates(250,250);
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
  public ImageView setImage(ImageView newImage) {
    image = newImage;
    defaultPos();
    return image;
  }
  public void setCoordinates(double X, double Y) {
    XCor = X;
    YCor = Y;
    image.setX(X-25);
    image.setY(Y-25);
  }

  public void changeVisible() {
    visible = !visible;
    image.setVisible(visible);
  }
}
