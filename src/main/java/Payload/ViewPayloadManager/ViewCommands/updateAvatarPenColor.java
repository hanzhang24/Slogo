package Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * @author Alec Liu This command updates the pen color.
 */
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
