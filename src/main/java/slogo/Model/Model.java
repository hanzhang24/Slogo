package slogo.Model;

import java.util.List;
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
  ViewPayload endOp(String userInput, List<Double> returnValues);

  /**
   * End a Controller operation abruptly, signifying an unsuccessful batch of work. All updates are
   * rejected.
   */
  void bail();

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
   *
   * @return pen color as [red, green, blue]
   */
  int[] getAvatarPenColor();

  /**
   * Gets the state of the pen
   *
   * @return if the pen is down
   */
  boolean getAvatarIsPenDown();

  /**
   * Gets whether the avatar is currently visible
   *
   * @return if the avatar is visible
   */
  boolean getAvatarVisible();

  /**
   * Gets the value of the variable with the specified key
   *
   * @return the variable's value
   */

  double getUserVariable(String key);

  /**
   * Gets all user variables in the Model. Should not be called while there is an active operation
   *
   * @return copy of all user variables
   */
  Map<String, Double> getAllUserVariables();

  /**
   * Fetches all successful user typed commands in the current instance of the application
   *
   * @return copy of all successful commands
   */
  List<String> getAllHistory();

  /**
   * Simultaneously update the current Avatar's x and y position
   *
   * @param x new x position
   * @param y new y position
   */
  void setAvatarPosition(double x, double y) throws RuntimeException;

  /**
   * Sets the current avatar's rotation
   *
   * @param rotation new rotation
   */
  void setAvatarRotation(double rotation) throws RuntimeException;

  /**
   * Sets the current avatar's pen color
   *
   * @param red   red value 0-255
   * @param green green value 0-255
   * @param blue  blue value 0-255
   */
  void setAvatarPenColor(double red, double green, double blue) throws RuntimeException;

  /**
   * Sets the current avatar's pen enable setting
   *
   * @param isPenDown new pen setting
   */
  void setAvatarPenDown(boolean isPenDown) throws RuntimeException;

  /**
   * Sets the current avatar's visibility setting
   *
   * @param visible whether the current avatar is visible
   */
  void setAvatarVisible(boolean visible) throws RuntimeException;

  /**
   * Sets the value of a user variable
   *
   * @param key   variable name
   * @param value variable value
   */
  void setUserVariable(String key, double value) throws RuntimeException;

  /**
   * Sets all avatars to the default position and rotation values
   */
  void resetOrientation() throws RuntimeException;


  /**
   * Gets the list of active avatar external IDs
   * @return list of active avatar external IDs
   */
  List<Integer> getActiveAvatars();

  /**
   * Sets the list of Avatar IDs to be active. Any previously active avatars are removed
   * @param externalIDs list of new active avatarIDs
   */
  void setActiveAvatars(List<Integer> externalIDs);

  /**
   * Gets the current avatar's external ID
   *
   * @return external ID
   */
  int getCurrentAvatarID();

  /**
   * Switches the avatar to the one with the specified ID. By default, the ID is set to zero for the
   * initial avatar.
   *
   * @param id new avatar ID
   */
  void setCurrentAvatarID(int id) throws RuntimeException;


  /**
   * Gets the total number of avatars
   *
   * @return total number of avatars
   */
  int getTotalNumberOfAvatars();
}
