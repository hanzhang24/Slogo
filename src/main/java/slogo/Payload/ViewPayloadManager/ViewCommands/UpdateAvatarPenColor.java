package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * @author Alec Liu This command updates the pen color.
 */
public class UpdateAvatarPenColor extends ViewCommand {
  public UpdateAvatarPenColor(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void executeSpecificCommand() {
    //gameScreen.updateAvatarPenColor(parameter.get(0));
  }

  @Override
  public String getName() {
    return "Update Avatar Pen Color";
  }
}
