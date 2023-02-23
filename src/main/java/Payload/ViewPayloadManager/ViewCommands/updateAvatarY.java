package Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * Command to update the Avatar's X position in the Screen
 */
public class updateAvatarY extends Command {

  public updateAvatarY(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void execute() {
    double newYPosition = Double.parseDouble(parameters.get(0));
    // gameScreen.updateAvatarY(newPosition);
  }

  @Override
  public String getName() {
    return "Update Avatar Y";
  }
}
