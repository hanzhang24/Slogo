package View;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AvatarView {

  protected ImageView image;
  protected double XCor;
  protected double YCor;

  //TODO checkout what kind of variable this should be
  public static final String IMAGE_PATH = "/View/Images/";

  public ImageView getImage() {
    return image;
  }
}
