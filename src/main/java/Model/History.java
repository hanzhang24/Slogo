package Model;

import java.util.Stack;

/**
 * The History class is meant to track the history of user commands
 *
 * Note: probably should just live in the View section as a separate model
 */
public class History {

  private Stack<String> commandLog;

  /**
   * Class constructor
   */
  public History() {
    commandLog = new Stack<>();
  }

  /**
   * Log a new entry into the history
   *
   * @param userInput user input
   */
  public void addEntry(String userInput) {
    commandLog.push(userInput);
  }

  /**
   * Fetch the most recent command. Should only called when endOp() is called, representing a
   * successful operation.
   *
   * @return user's last command
   */
  public String getLastCommand() {
    return commandLog.peek();
  }
}
