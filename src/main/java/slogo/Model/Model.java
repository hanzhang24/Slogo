package slogo.Model;

import java.util.Map;
import slogo.Payload.ViewPayloadManager.ViewPayload;

/**
 * @author Alec Liu The Model interface is meant to represent the external API of the Model. Any
 * model must support these functions for the Controller.
 */
public interface Model {

  /**
   * Initializes the proper workspace for a new Controller operation
   */
  void startOp();

  /**
   * End a Controller operation, signifying a successful batch of work has been finished. All
   * updates are committed to the original data.
   */
  ViewPayload endOp();

  /**
   * End a Controller operation abruptly, signifying an unsuccessful batch of work. All updates are
   * rejected.
   */
  void bail();

  /**
   * Switches the avatar to the one with the specified ID. By default, the ID is set to zero for the
   * initial avatar.
   *
   * @param id new avatar ID
   */
  void setCurrentAvatar(int id);

  /**
   * Gets the x position of the current avatar
   *
   * @return avatar's x-position
   */
  double getAvatarX();

  /**
   * Gets the y position of the current avatar
   *
   * @return avatar's y-position
   */
  double getAvatarY();

  /**
   * Gets the rotation of the current avatar
   *
   * @return avatar's
   */
  double getAvatarRotation();

  /**
   * Gets the pen color of the current avatar
   * @return pen color
   */
  String getAvatarPenColor();

  /**
   * Gets the state of the pen
   * @return if the pen is down
   */
  boolean getAvatarIsPenDown();

  /**
   * Gets the value of the variable with the specified key
   * @return the variable's value
   */

  double getUserVariable(String key);

  /**
   * Gets all user variables in the Model. Should not be called while there is an active operation
   * @return copy of all user variables
   */
  Map<String, Double> getAllUserVariables();

//  /**
//   * Sets the current avatar's x position
//   * @param x new x position
//   */
//  void setAvatarX(double x);
//
//  /**
//   * Sets the current avatar's y position
//   * @param y new y position
//   */
//  void setAvatarY(double y);

  /**
   * Simultaneously update the current Avatar's x and y position
   *
   * @param x new x position
   * @param y new y position
   */
  void setAvatarPosition(double x, double y);

  /**
   * Sets the current avatar's rotation
   * @param rotation new rotation
   */
  void setAvatarRotation(double rotation);

  /**
   * Sets the current avatar's pen color
   * @param color new color
   */
  void setAvatarPenColor(String color);

  /**
   * Sets the current avatar's pen enable setting
   * @param isPenDown new pen setting
   */
  void setAvatarPenDown(boolean isPenDown);

  /**
   * Sets the value of a user variable
   * @param key variable name
   * @param value variable value
   */
  void setUserVariable(String key, double value);
}
