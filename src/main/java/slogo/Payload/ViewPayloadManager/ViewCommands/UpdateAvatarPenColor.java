package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

/**
 * @author Alec Liu This command updates the pen color.
 */
public class UpdateAvatarPenColor extends ViewCommand {
  private static final int RED_INDEX = 0;
  private static final int GREEN_INDEX = 1;
  private static final int BLUE_INDEX = 2;
  public UpdateAvatarPenColor(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void executeSpecificCommand() {
    String[] parsedString = parameters.get(0).split(" ");
    int[] parsedColor = new int[parsedString.length];
    for(int i = 0; i < parsedColor.length; i++){
      parsedColor[i] = Integer.parseInt(parsedString[i]);
    }
    gameScreen.updateAvatarPenColor(parsedColor[RED_INDEX], parsedColor[GREEN_INDEX], parsedColor[BLUE_INDEX]);
  }

  @Override
  public String getDescription() {
    return "Update Avatar Pen Color";
  }
}
