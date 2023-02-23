package Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class updateAvatarPenColor extends Command{
  public updateAvatarPenColor(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void execute() {
    // gameScreen.updateAvatarPenColor(parameter.get(0));
  }

  @Override
  public String getName() {
    return "Update Avatar Pen Color";
  }
}
