package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class UpdateAvatarIsPenDown extends ViewCommand {
  public UpdateAvatarIsPenDown(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void executeSpecificCommand() {
    gameScreen.updateAvatarIsPenDown(Boolean.parseBoolean(parameters.get(0)));
  }

  @Override
  public String getName() {
    return "Update Avatar Is Pen Down";
  }
}
