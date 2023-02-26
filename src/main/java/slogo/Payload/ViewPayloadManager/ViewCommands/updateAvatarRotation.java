package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * @author Alec Liu This command updates the Avatar's rotation.
 */
public class updateAvatarRotation extends Command{
  public updateAvatarRotation(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void execute() {
    double newXRotation = Double.parseDouble(parameters.get(0));
    // gameScreen.updateAvatarRotation(newXRotation);
  }

  @Override
  public String getName() {
    return "Update Avatar Rotation";
  }
}
