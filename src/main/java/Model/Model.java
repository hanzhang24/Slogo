package Model;

import Payload.ViewPayloadManager.ViewPayload;

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
   * End a Controller operation, signifying a successful batch of work has been finished. All updates
   * are committed to the original data.
   */
  ViewPayload endOp();

  /**
   * End a Controller operation abruptly, signifying an unsuccessful batch of work. All updates are
   * rejected.
   */
  void bail();

  /**
   * Gets a variable as a double
   *
   * @param key name of the variable
   * @return value of the variable
   * @throws NumberFormatException if the variable represents a String, not a double
   */
  double getNumber(String key);

  /**
   * Gets a variable as a String
   *
   * @param key name of the variable
   * @return value of the variable
   */
  String getString(String key);

  /**
   * Sets the value of a numeric parameter
   *
   * @param key   name of the parameter
   * @param value value of the parameter
   * @throws NumberFormatException if the method tries to reassign a default parameter's type
   */
  void setValue(String key, double value);

  /**
   * Sets the value of a String parameter
   *
   * @param key   name of the parameter
   * @param value value of the parameter
   * @throws NumberFormatException if the method tries to reassign a default parameter's type
   */
  void setValue(String key, String value);

  /**
   * Simultaneously update the Avatar's x and y position - might be expanded to execute an arbitrary
   * number of simultaneous updates
   *
   * @param avatarX avatar's new x position
   * @param avatarY avatar's new y position
   */
  void setPosition(double avatarX, double avatarY);

}
