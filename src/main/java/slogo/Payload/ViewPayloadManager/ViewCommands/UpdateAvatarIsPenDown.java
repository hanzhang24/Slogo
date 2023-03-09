package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class UpdateAvatarIsPenDown extends ViewCommand {
  public UpdateAvatarIsPenDown(List<String> parameters, int externalID) {
    super(parameters, externalID);
  }

  @Override
  public void executeSpecificCommand() {
    gameScreen.updateAvatarIsPenDown(Boolean.parseBoolean(parameters.get(0)));
    // gameScreen.updateAvatarIsPenDown(externalID, Boolean.parseBoolean(parameters.get(0)));

  }

  @Override
  public String getDescription() {
    return "Update Avatar Is Pen Down";
  }
}
