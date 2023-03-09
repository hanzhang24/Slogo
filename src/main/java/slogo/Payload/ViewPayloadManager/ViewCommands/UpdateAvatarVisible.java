package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class UpdateAvatarVisible extends ViewCommand {

  public UpdateAvatarVisible(List<String> parameters, int externalID) {
    super(parameters, externalID);
  }

  @Override
  public void executeSpecificCommand() {
    gameScreen.updateAvatarVisible(Boolean.parseBoolean(parameters.get(0)));
    // gameScreen.updateAvatarVisible(Boolean.parseBoolean(externalID, parameters.get(0)));
  }

  @Override
  public String getDescription() {
    return super.getDescription() + externalID;
  }
}
