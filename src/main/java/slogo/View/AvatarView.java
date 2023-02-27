package slogo.View;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public abstract class AvatarView {

  protected ImageView image;
  protected double XCor;
  protected double YCor;
  protected boolean penActive;
  protected Color color;

  //TODO checkout what kind of variable this should be
  public static final String IMAGE_PATH = "/View/Images/";

  public ImageView getImage() {
    return image;
  }

  public void updatePen(boolean penActive) { this.penActive = penActive;}
  public void updateColor(Color color) { this.color = color; }
  public void updatePosXY(double newX, double newY) {
    this.XCor = newX;
    this.YCor = newY;
  }
  public void updateRot(double newRot) { image.setRotate(image.getRotate() + newRot); }


}
