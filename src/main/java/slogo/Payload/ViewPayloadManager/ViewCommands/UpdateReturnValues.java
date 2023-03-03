package slogo.Payload.ViewPayloadManager.ViewCommands;

import java.util.List;

public class UpdateReturnValues extends ViewCommand {

  public UpdateReturnValues(List<String> parameters) {
    super(parameters);
  }

  @Override
  public void executeSpecificCommand() {
    // gameScreen.displayReturnValues(parameters); // will just be strings already, no need to translate
  }

  @Override
  public String getDescription() {
    return "Update Displayed Return Values";
  }
}
