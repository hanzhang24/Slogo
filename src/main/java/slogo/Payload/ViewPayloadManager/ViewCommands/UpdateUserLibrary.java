package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class UpdateUserLibrary extends ViewCommand {

  /**
   * Class constructor
   *
   * @param parameters list of parameters
   */
  public UpdateUserLibrary(List<String> parameters) {
    super(parameters);
  }

  /**
   * Executes command-specific code
   */
  @Override
  void executeSpecificCommand() {
    gameScreen.addToUserLibrary(parameters.get(0));
  }

  /**
   * Return the name of the command
   *
   * @return command name
   */
  @Override
  public String getDescription() {
    return "Update User Library";
  }
}
