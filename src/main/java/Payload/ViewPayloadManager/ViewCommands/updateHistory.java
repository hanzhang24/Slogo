package Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class updateHistory extends Command {

  /**
   * Class constructor
   *
   * @param parameters list of parameters
   */
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
    return null;
  }
}
