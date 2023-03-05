package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * @author Alec Liu This command updates the Avatar's x position.
 */
public class UpdateAvatarX extends ViewCommand {

  public UpdateAvatarX(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void executeSpecificCommand() {
    double newXPosition = Double.parseDouble(parameters.get(0));
    // gameScreen.updateAvatarX(newPosition);
  }

  @Override
  public String getDescription() {
    return "Update Avatar X";
  }
}
