package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class UpdateClearScreen extends ViewCommand {

  public UpdateClearScreen(List<String> parameters, int externalID) {
    super(parameters, externalID);
  }

  @Override
  void executeSpecificCommand() {
    gameScreen.clearScreen();
  }

  @Override
  public String getDescription() {
    return "Clear Screen";
  }
}
