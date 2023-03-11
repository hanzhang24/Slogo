package slogo.View;

import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
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
  private List<PenView> avatars;
  private double animationSpeed;

  private Timeline boundaryChecker;
  public static final int FRAMES_PER_SECOND = 60;
  public static final int DEFAULT_SPEED = 60;

  private DrawBoardView canvas;


  /**
   * Animation Class that handles the animations that are created. The constructor intializies the
   * speed to the DEFAULT_SPEED of 60, avatar just create
   * @param gameAvatar This parameter is the PenView object that needs to be moved,
   */
  public Animator(List<PenView> gameAvatar, DrawBoardView drawboard){
    animationSpeed = DEFAULT_SPEED;
    avatars = gameAvatar;
    action = new SequentialTransition();
    canvas = drawboard;
  }

  /**
   * Method call for any outside class to create the animation required to translate the
   * avatar to the endX and endY
   * @param newX is the final X coordinate the turtle should be at from the Model's perspective
   * @param newY is the final Y coordinate the turtle should be at from the View's perspective
   */
  public void makeTranslation (int ExternalID, double newX, double newY) {
    //    newX = ((newX + 250) % 500);
//    if(newX < 0){
//      newX += 500;
//    }
//    newY = -1*(newY + 250) % 500; // converts to View coordinates
//    if(newY < 0){
//      newY += 500;
//    }
    PenView avatar = getAvatar(ExternalID);

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
        PathTransition pt = createNewPathTransition(avatar, travelingX, travelingY);
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

  }

  private PenView getAvatar(int ExternalID){
    for(PenView avatar: avatars){
      if(avatar.getID() == ExternalID){
        return avatar;
      }
    }
    return null;
  }
  /**
   * Private Method inside the class to create a newPathTransition
   * @param endX end X Coordinate
   * @param endY end Y Coordinate
   * @return PathTransition from avatar's starting point to ending point
   */
  private PathTransition createNewPathTransition(PenView avatar, double endX, double endY) {
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

  public Animation makeRotation (int externalID, Double newRot){
    RotateTransition rt = new RotateTransition(Duration.seconds(FRAMES_PER_SECOND/animationSpeed), getAvatar(externalID).getImage());
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
  public void updateAvatars(PenView avatar){
    avatars.add(avatar);
  }
}
