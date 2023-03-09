package slogo.View.Screens;

import java.util.List;

public interface ModelView {

  /**
   * Update the state of the avatar pen
   *
   * @param state if the pen is down
   */
  void updateAvatarIsPenDown(boolean state);
  //void updateAvatarIsPenDown(int externalID, boolean state);

  /**
   * Update the pen color
   *
   * @param red   red color value
   * @param green green color value
   * @param blue  blue color value
   */
  void updateAvatarPenColor(int red, int green, int blue);
  // void updateAvatarPenColor(int externalID, int red, int green, int blue);

  /**
   * Update the avatar position
   *
   * @param x new x
   * @param y new y
   */
  void updateAvatarPosXY(double x, double y);
  // void updateAvatarPosXY(int externalID, double x, double y);


  /**
   * Update the avatar rotation
   *
   * @param rotation new rotation
   */
  void updateAvatarRot(double rotation);
  //void updateAvatarRot(int externalID, double rotation);

  /**
   * Update the visibility of the avatar
   *
   * @param state if the avatar is visible
   */
  void updateAvatarVisible(boolean state);
  //void updateAvatarVisible(int externalID, boolean state);

  /**
   * Remove all lines drawn on the screen
   */
  void clearScreen();

  /**
   * Update history view to include most recent successful user input
   *
   * @param userInput user inputted command
   */
  void updateDisplayedHistory(String userInput);

  /**
   * Display the most recent set of return values
   *
   * @param returnValues sequential list of returns values as Strings
   */
  void displayReturnValues(List<String> returnValues);

  /**
   * Add a new user-defined function to the library
   *
   * @param functionDescription function content
   */
  void addToUserLibrary(String functionDescription);

//  /**
//   * Creates a new avatar with the given properties
//   *
//   * @param externalID     avatar external ID
//   * @param numericDefault X, Y, Rotation
//   * @param booleanDefault PenDown, Visibility
//   * @param colorDefault   default color
//   */
//  void createNewAvatar(int externalID, double numericDefault, boolean booleanDefault,
//      double[] colorDefault);
}
