package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * @author Alec Liu This command updates the Avatar's rotation.
 */
public class UpdateAvatarRotation extends ViewCommand {
  public UpdateAvatarRotation(List<String> parameters, int externalID) {
    super(parameters, externalID);
  }
  @Override
  public void executeSpecificCommand() {
    double newXRotation = Double.parseDouble(parameters.get(0));
    //gameScreen.updateAvatarRot(newXRotation);
    gameScreen.updateAvatarRot(externalID, newXRotation);
  }

  @Override
  public String getDescription() {
    return super.getDescription() + externalID;
  }
}
