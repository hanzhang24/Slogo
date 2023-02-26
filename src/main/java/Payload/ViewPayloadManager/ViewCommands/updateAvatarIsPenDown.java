package Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class updateAvatarIsPenDown extends Command{
  public updateAvatarIsPenDown(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void execute() {
    // gameScreen.updateAvatarIsPenDown(Boolean.parseBoolean(parameter.get(0)));
  }

  @Override
  public String getName() {
    return "Update Avatar Is Pen Down";
  }
}
