package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class UpdateAvatarVisible extends ViewCommand {

  public UpdateAvatarVisible(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void executeSpecificCommand() {
    // gameScreen.updateAvatarVisible(Boolean.parseBoolean(parameters.get(0)));
  }

  @Override
  public String getName() {
    return "Update Avatar Visibility";
  }

}
