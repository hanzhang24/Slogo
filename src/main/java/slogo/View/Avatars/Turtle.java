package slogo.View.Avatars;

import slogo.View.AvatarView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sound.sampled.Line;

public class Turtle extends AvatarView {

  public static final int TURTLE_SIZE = 50;
  private static final String TURTLE_URL = IMAGE_PATH + "turtle.png";

  public Turtle(){
    image = new ImageView(new Image(getClass().getResourceAsStream(TURTLE_URL)));
    image.setFitWidth(TURTLE_SIZE);
    image.setFitHeight(TURTLE_SIZE);
    //TODO fix magic numbers
    XCor = 275;
    YCor = 275;
    setCoordinates(XCor,YCor);
  }

  public void setCoordinates(double X, double Y){
    image.setX(X);
    image.setY(Y);
  }
}
