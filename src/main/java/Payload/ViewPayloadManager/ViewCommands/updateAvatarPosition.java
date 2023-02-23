package Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class updateAvatarPosition extends Command{
  public updateAvatarPosition(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void execute() {
    double newXPosition = Double.parseDouble(parameters.get(0));
    double newYPosition = Double.parseDouble(parameters.get(1));
    // gameScreen.updateAvatarPosition(newXPosition, newYPosition);
  }

  @Override
  public String getName() {
    return "Update Avatar Position";
  }
}
