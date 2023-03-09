package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class UpdateUserLibrary extends ViewCommand {

  /**
   * Class constructor
   *
   * @param parameters list of parameters
   */
  public UpdateUserLibrary(List<String> parameters, int externalID) {
    super(parameters, externalID);
  }

  /**
   * Executes command-specific code
   */
  @Override
  void executeSpecificCommand() {
    gameScreen.addToUserLibrary(parameters.get(0));
  }
}
