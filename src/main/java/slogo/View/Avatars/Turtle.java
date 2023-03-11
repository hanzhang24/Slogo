package slogo.View.Avatars;

import javafx.scene.paint.Color;
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
    this.setID(1);
  }

  public Turtle(int externalID, double numericDefault, boolean booleanDefault, double[] colorDefault) {
    super();
    setID(externalID);
    setCoordinates(250 + numericDefault, 250 + numericDefault);
    setModelCoordinates(numericDefault, numericDefault);
    updatePen(booleanDefault);
    Color newColor = Color.rgb((int) colorDefault[0], (int) colorDefault[1], (int) colorDefault[2]);
    updateColor(newColor);
  }
}
