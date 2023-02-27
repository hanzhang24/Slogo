package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * @author Alec Liu This command updates the Avatar's position simultaneously.
 */
public class updateAvatarPosition extends ViewCommand {
  public updateAvatarPosition(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void execute() {
    double newXPosition = Double.parseDouble(parameters.get(0));
    double newYPosition = Double.parseDouble(parameters.get(1));
    gameScreen.updateAvatarPosXY(newXPosition, newYPosition);
  }

  @Override
  public String getName() {
    return "Update Avatar Position";
  }
}
