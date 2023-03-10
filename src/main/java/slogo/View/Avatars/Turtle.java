package slogo.View.Avatars;

import slogo.View.PenView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Turtle extends PenView {

  private static final String TURTLE_URL = IMAGE_PATH + "avatar.png";

  public Turtle(){
    ImageView image = new ImageView(new Image(getClass().getResourceAsStream(TURTLE_URL)));
    setImage(image);
    defaultPos();
  }
}
