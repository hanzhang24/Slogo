package slogo.View.Screens;

import java.util.List;

public interface ModelView {

  /**
   * Update the state of the avatar pen
   *
   * @param state if the pen is down
   */
  void updateAvatarIsPenDown(boolean state);

  /**
   * Update the pen color
   *
   * @param color new color
   */
  void updateAvatarPenColor(String color);

  /**
   * Update the avatar position
   *
   * @param x new x
   * @param y new y
   */
  void updateAvatarPosXY(double x, double y);

  /**
   * Update the avatar rotation
   *
   * @param rotation new rotation
   */
  void updateAvatarRot(double rotation);

  /**
   * Update the visibility of the avatar
   *
   * @param state if the avatar is visible
   */
  void updateAvatarVisible(boolean state);

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
}
