package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * @author Alec Liu This command updates the Avatar's y position.
 */
public class UpdateAvatarY extends ViewCommand {

  public UpdateAvatarY(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void executeSpecificCommand() {
    double newYPosition = Double.parseDouble(parameters.get(0));
    // gameScreen.updateAvatarY(newPosition);
  }

  @Override
  public String getDescription() {
    return "Update Avatar Y";
  }
}
