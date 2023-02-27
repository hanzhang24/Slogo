package slogo.View;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Animator {
  private Animation action;
  private AvatarView avatar;
  public Animator(AvatarView gameAvatar){
    avatar = gameAvatar;
  }
  // create sequence of animations
  public Animation makeTranslation (double endX, double endY) {
    // create something to follow
    Path path = new Path();
    path.getElements().addAll(new MoveTo(avatar.getImage().getBoundsInParent().getMinX(), avatar.getImage().getBoundsInParent().getMinY()),
        new LineTo(endX, endY));
    // create an animation where the shape follows a path
    PathTransition pt = new PathTransition(Duration.seconds(2), path, avatar.getImage());
    // put them together in order
    return new SequentialTransition(avatar.getImage(), pt);
  }

}
