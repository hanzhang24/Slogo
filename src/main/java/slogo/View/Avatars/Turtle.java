package slogo.View.Avatars;

import slogo.View.PenView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Turtle extends PenView {

  public static final int TURTLE_SIZE = 50;
  private static final String TURTLE_URL = IMAGE_PATH + "square.png";

  public Turtle(){
    ImageView image = new ImageView(new Image(getClass().getResourceAsStream(TURTLE_URL)));
    setImage(image);
    image.setRotate(90); // to orient turtle facing right - Alec :)))
    image.setFitWidth(TURTLE_SIZE);
    image.setFitHeight(TURTLE_SIZE);
    //TODO fix magic numbers
    setCoordinates(250,250);
  }

}
