package slogo.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alec Liu The History class is meant to track the history of user commands. Note: probably
 * should just live in the View section as a separate model
 */
public class History {

  private List<String> commandLog;

  /**
   * Class constructor
   */
  public History() {
    commandLog = new ArrayList<>();
  }

  /**
   * Log a new entry into the history
   *
   * @param userInput user input
   */
  public void addEntry(String userInput) {
    commandLog.add(userInput);
  }

  /**
   * Fetch all previous history for this current application
   *
   * @return user command history
   */
  public List<String> getAllHistory() {
    return commandLog;
  }
}
