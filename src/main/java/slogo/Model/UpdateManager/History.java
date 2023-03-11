package slogo.Model.UpdateManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alec Liu The History class is meant to track the history of user commands.
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
