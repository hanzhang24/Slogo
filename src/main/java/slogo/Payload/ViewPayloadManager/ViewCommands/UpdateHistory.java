package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class UpdateHistory extends ViewCommand {
  public UpdateHistory(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void execute() {
    // gameScreen.updateDisplayedHistory(parameters.get(0));
  }

  @Override
  public String getName() {
    return "Update Displayed History";
  }
}
