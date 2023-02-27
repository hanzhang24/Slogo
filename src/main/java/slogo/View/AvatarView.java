package slogo.View;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import java.awt.*;
import javafx.scene.paint.Color;

public abstract class AvatarView {

  protected ImageView image;

  protected double XCor;
  protected double YCor;
  protected boolean penActive;
  protected Color color;
  protected Line line;

  //TODO checkout what kind of variable this should be
  public static final String IMAGE_PATH = "/View/Images/";

  public AvatarView() {
    line = new Line(XCor + 300, YCor + 300, XCor + 300, YCor + 300);
    line.setFill(Color.BLACK);
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
    if(penActive){
      line = new Line(XCor, YCor, newX + 300, newY + 300);
    }

//    line.setEndX(newX + 300);
//    line.setEndY(newY + 300);
    this.XCor = newX + 275;
    this.YCor = newY + 275;
    image.setX(newX + 275);
    image.setY(newY + 275);
  }

  public void updateRot(double newRot) { image.setRotate(-1*(newRot-90)); }

  public double getXCor() {
    return XCor;
  }

  public double getYCor() {
    return YCor;
  }
}
