package Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * @author Alec Liu This command updates the Avatar's x position.
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
}
