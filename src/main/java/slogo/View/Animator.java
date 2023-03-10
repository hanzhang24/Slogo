package slogo.View;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
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
  private DrawBoardView canvas;


  /**
   * Animation Class that handles the animations that are created. The constructor intializies the
   * speed to the DEFAULT_SPEED of 60, avatar just create
   * @param gameAvatar This parameter is the PenView object that needs to be moved,
   */
  public Animator(PenView gameAvatar, DrawBoardView drawboard){
    animationSpeed = DEFAULT_SPEED;
    avatar = gameAvatar;
    action = new SequentialTransition(avatar.getImage());
    canvas = drawboard;
  }

  /**
   * Method call for any outside class to create the animation required to translate the
   * avatar to the endX and endY
   * @param newX is the final X coordinate the turtle should be at from the Model's perspective
   * @param newY is the final Y coordinate the turtle should be at from the View's perspective
   */
  public void makeTranslation (double newX, double newY) {
    //    newX = ((newX + 250) % 500);
//    if(newX < 0){
//      newX += 500;
//    }
//    newY = -1*(newY + 250) % 500; // converts to View coordinates
//    if(newY < 0){
//      newY += 500;
//    }
    double oldModelXCoord = avatar.getModelX();
    double oldModelYCoord = avatar.getModelY();
//    double newViewX = newX + 250;
//    double newY = -1*newY + 250; // converts to View coordinates
    double OldViewXCor = avatar.getXCor();
    double OldViewYCor = avatar.getYCor();

    double totalChangeX = newX - oldModelXCoord;
    double totalChangeY = newY - oldModelYCoord;

    double totalDistance = Math.sqrt(totalChangeX * totalChangeX + totalChangeY * totalChangeY);

    double deltaX = totalChangeX / totalDistance;
    double deltaY = totalChangeY / totalDistance;

    double travelingX = OldViewXCor;
    double travelingY = OldViewYCor;

    double deltaD = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    for(double start = 0; start < totalDistance; start += deltaD){ //
      // set the avatar to be a small distance away
      travelingX += deltaX;
      travelingY -= deltaY;
      // if off screen, adjust accordingly
      boolean inBounds = true;
      if(travelingX < 0){
        travelingX += 500;
        inBounds = false;
      }
      if(travelingX >= 500){
        travelingX -= 500;
        inBounds = false;
      }
      if(travelingY < 0){
        travelingY += 500;
        inBounds = false;
      }
      if(travelingY >= 500){
        travelingY -= 500;
        inBounds = false;
      }
      if(inBounds){
        PathTransition pt = createNewPathTransition(travelingX, travelingY);
        action.getChildren().add(pt);
        if(avatar.getPenActive()){
          canvas.draw(travelingX - deltaX, travelingY - deltaY, travelingX, travelingY);
        }
      } else {
        // else, just make a teleport animation
        Path path = new Path();
        path.getElements().addAll(new MoveTo(travelingX, travelingY), new LineTo(travelingX, travelingY));
        PathTransition pt = new PathTransition(Duration.ZERO, path, avatar.getImage());
        action.getChildren().add(pt);
      }
      avatar.setCoordinates(travelingX, travelingY);
    }
    avatar.setModelCoordinates(newX, newY);
//
//    PathTransition pt = createNewPathTransition(newX, newY);
  }

  /**
   * Private Method inside the class to
   * @param endX
   * @param endY
   * @return
   */
  private PathTransition createNewPathTransition(double endX, double endY) {
    double XStart = avatar.getXCor();
    double YStart = avatar.getYCor();
    double XLength = endX - XStart;
    double YLength = endY - YStart;
    double pathLength = Math.sqrt(Math.pow(XLength, 2) + Math.pow(YLength, 2));
    Path path = new Path();
    path.getElements().addAll(new MoveTo(XStart, YStart), new LineTo(endX, endY));
//    avatar.setCoordinates(endX, endY);
    PathTransition pt = new PathTransition(Duration.seconds((FRAMES_PER_SECOND/animationSpeed)*pathLength/500), path, avatar.getImage());
    //Makes it linear
    pt.setInterpolator(Interpolator.LINEAR);
    return pt;
  }
  public void runAnimation(){
    action.play();
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
