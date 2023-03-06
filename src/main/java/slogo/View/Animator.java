package slogo.View;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Animator {
  private SequentialTransition action;
  private PenView avatar;
  private double animationSpeed;

  private Timeline boundaryChecker;
  public static final int FRAMES_PER_SECOND = 60;
  public static final int DEFAULT_SPEED = 60;

  private Animation checked;
  public Animator(PenView gameAvatar){
    animationSpeed = DEFAULT_SPEED;
    avatar = gameAvatar;
    action = new SequentialTransition(avatar.getImage());
  }

  // create sequence of animations
  public void makeTranslation (double endX, double endY) {
    PathTransition pt = createNewPathTransition(endX, endY);
    checkBounds(pt, endX, endY);
    action.getChildren().add(checked);
  }

  private PathTransition createNewPathTransition(double endX, double endY) {
    double XStart = avatar.getXCor();
    double YStart = avatar.getYCor();
    double XLength = endX - XStart;
    double YLength = endY - YStart;
    double pathLength = Math.sqrt(Math.pow(XLength, 2) + Math.pow(YLength, 2));
    Path path = new Path();
    path.getElements().addAll(new MoveTo(XStart, YStart), new LineTo(endX, endY));
    avatar.setCoordinates(endX, endY);
    PathTransition pt = new PathTransition(Duration.seconds((FRAMES_PER_SECOND/animationSpeed)*pathLength/500), path, avatar.getImage());
    return pt;
  }
  public void runAnimation(){
    action.playFromStart();
  }
  public Animation checkBounds(PathTransition pt, double endX, double endY){
    checked = pt;
    Timeline animationLoop = new Timeline(new KeyFrame(Duration.millis(10), event -> {
      checkAnimation(pt, endX, endY);
    }));
    animationLoop.setCycleCount(Timeline.INDEFINITE);
    pt.play();
    animationLoop.play();
    return checked;
  }

  private void checkAnimation(PathTransition pt, double endX, double endY) {
    if (avatar.getXCor() > 500) {
      System.out.println("reset to 0");
      System.out.println(avatar.getXCor());
      System.out.println(avatar.getImage().getX());
      PathTransition firstHalf = createNewPathTransition(500, avatar.getYCor());
      avatar.setCoordinates(0, avatar.getYCor());
      PathTransition secondHalf = createNewPathTransition(endX%500, endY);
      avatar.setCoordinates(endX%500, avatar.getYCor());
      checked = new SequentialTransition(firstHalf, secondHalf);
      System.out.println(avatar.getXCor());
    }
    if (avatar.getYCor() > 500) {
      avatar.setCoordinates(avatar.getXCor(), 0);
      pt.stop();
    }
    if(avatar.getXCor() < 0){
      avatar.setCoordinates(500, avatar.getYCor());
      pt.stop();
    }
    if(avatar.getYCor() < 0){
      avatar.setCoordinates(avatar.getXCor(), 500);
      pt.stop();
    }
    else{
      checked = pt;
    }
  }

  public Animation makeRotation (Double newRot){
    RotateTransition rt = new RotateTransition(Duration.seconds(1), avatar.getImage());
    rt.setByAngle(newRot);
    action.getChildren().add(rt);
    return action;
  }
  public void resetAnimations(){
    action.getChildren().clear();
  }
  public double getAnimationSpeed() {
    return animationSpeed;
  }
  public void setAnimationSpeed(double speed){
    animationSpeed = speed;
  }

  public void pause() {
  }

  public void step() {
  }
}
