package slogo.View.Avatars;

import slogo.View.PenView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Turtle extends PenView {

  private static final String TURTLE_URL = IMAGE_PATH + "avatar.png";
  private static final double TURTLE_SIZE = 50;

  public Turtle(){
    ImageView image = new ImageView(new Image(getClass().getResourceAsStream(TURTLE_URL)));
    setImage(image);
    image.setRotate(90); // to orient turtle facing right - Alec :)))
    image.setFitWidth(TURTLE_SIZE);
    image.setFitHeight(TURTLE_SIZE);
    //TODO fix magic numbers
    setCoordinates(250,250);
    setModelCoordinates(0,0);
  }
}
