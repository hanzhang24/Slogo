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
    Path path = new Path();
    path.getElements().addAll(new MoveTo(avatar.getXCor() + 25, avatar.getYCor() + 25),
        new LineTo(endX + 300, endY + 300));
    avatar.updatePosXY(endX, endY);
    PathTransition pt = new PathTransition(Duration.seconds(2), path, avatar.getImage());
    return new SequentialTransition(avatar.getImage(), pt);
  }

}
