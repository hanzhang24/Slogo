package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class UpdateHistory extends ViewCommand {
  public UpdateHistory(List<String> parameters, int externalID) {
    super(parameters, externalID);
  }

  @Override
  public void executeSpecificCommand() {
    gameScreen.updateDisplayedHistory(parameters.get(0));
  }

  @Override
  public String getDescription() {
    return "Update Displayed History";
  }
}
