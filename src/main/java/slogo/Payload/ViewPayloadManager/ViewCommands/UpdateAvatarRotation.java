package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * @author Alec Liu This command updates the Avatar's rotation.
 */
public class UpdateAvatarRotation extends ViewCommand {
  public UpdateAvatarRotation(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void executeSpecificCommand() {
    double newXRotation = Double.parseDouble(parameters.get(0));
    gameScreen.updateAvatarRot(newXRotation);
  }

  @Override
  public String getName() {
    return "Update Avatar Rotation";
  }
}
