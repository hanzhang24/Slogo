package Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * Command to update the Avatar's X position in the Screen
 */
public class updateAvatarX extends Command {

  public updateAvatarX(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void execute() {
    double newXPosition = Double.parseDouble(parameters.get(0));
    // gameScreen.updateAvatarX(newPosition);
  }

  @Override
  public String getName() {
    return "Update Avatar X";
  }

  @Override
  public List<String> getParameters() {
    return parameters;
  }
}
