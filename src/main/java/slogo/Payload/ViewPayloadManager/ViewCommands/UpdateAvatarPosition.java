package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * @author Alec Liu This command updates the Avatar's position simultaneously.
 */
public class UpdateAvatarPosition extends ViewCommand {
  public UpdateAvatarPosition(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void executeSpecificCommand() {
    double newXPosition = Double.parseDouble(parameters.get(0));
    double newYPosition = Double.parseDouble(parameters.get(1));
    gameScreen.updateAvatarPosXY(newXPosition, newYPosition);
  }

  @Override
  public String getName() {
    return "Update Avatar Position";
  }
}
