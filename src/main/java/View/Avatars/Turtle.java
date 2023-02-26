package View.Avatars;

import View.AvatarView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Turtle extends AvatarView {

  private static final String TURTLE_URL =
  public Turtle(){
    image = new ImageView(new Image(getClass().getResourceAsStream(TURTLE_URL)));
  }
}
