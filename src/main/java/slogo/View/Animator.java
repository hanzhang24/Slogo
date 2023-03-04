package slogo.View;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Animator {
  private Animation action;
  private PenView avatar;
  private double animationSpeed;

  public static final int FRAMES_PER_SECOND = 60;
  public static final int DEFAULT_SPEED = 60;
  public Animator(PenView gameAvatar){
    animationSpeed = DEFAULT_SPEED;
    avatar = gameAvatar;
  }
  // create sequence of animations
  public Animation makeTranslation (double endX, double endY) {
    Path path = new Path();
    path.getElements().addAll(new MoveTo(avatar.getXCor() + 25, avatar.getYCor() + 25),
        new LineTo(endX + 300, endY + 300));
    avatar.updatePosXY(endX, endY);
    PathTransition pt = new PathTransition(Duration.seconds(FRAMES_PER_SECOND/animationSpeed), path, avatar.getImage());
    return new SequentialTransition(avatar.getImage(), pt);
  }
  public double getAnimationSpeed() {
    return animationSpeed;
  }
  public void setAnimationSpeed(double speed){
    animationSpeed = speed;
  }
}
