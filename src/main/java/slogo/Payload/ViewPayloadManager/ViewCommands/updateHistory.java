package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * @author Alec Liu This command prompts the View to add the last user-entered command into the
 * history section.
 */
public class updateHistory extends Command {

  public updateHistory(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void execute() {
    // gameScreen.displayHistory();
    // under the hood, Game Screen should call history.getLastCommand and add it the text box displaying the history
  }

  @Override
  public String getName() {
    return "Update User History";
  }
}
