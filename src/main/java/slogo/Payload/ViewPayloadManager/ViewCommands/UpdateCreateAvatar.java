package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class UpdateCreateAvatar extends ViewCommand {
  public UpdateCreateAvatar(List<String> parameters, int externalID) {
    super(parameters, externalID);
  }

  @Override
  void executeSpecificCommand() {
    double numericDefault = Double.parseDouble(parameters.get(0));
    boolean booleanDefault = Boolean.parseBoolean(parameters.get(1));
    double[] colorDefault = new double[]{numericDefault, numericDefault, numericDefault};
    gameScreen.createNewAvatar(externalID, numericDefault, booleanDefault, colorDefault);
    // this could vary here, where I can pass you all the info
  }

  @Override
  public String getDescription() {
    return super.getDescription() + externalID;
  }
}


