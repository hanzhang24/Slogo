package Model;

public class ModelTest {
  public static void main(String[] args){
    ModelTracker modelTracker = new ModelTracker();
    //runSuccessfulPayload(modelTracker);
    //forgotToStartOp(modelTracker);
    //forgotToEndLastOp(modelTracker);
    tryToAssignNewTypeToDefaultVariable(modelTracker);
    //bailThenSuccessfulPayload(modelTracker);
  }

  // test run payload successfully function
  private static void runSuccessfulPayload(ModelTracker modelTracker){
    modelTracker.startOp();
    double d = modelTracker.getNumber("AvatarX");
    d += 50;
    modelTracker.setValue("AvatarX", d);
    String penColor = modelTracker.getString("AvatarPenColor");
    penColor = "blue";
    modelTracker.setValue("AvatarPenColor", penColor);
    modelTracker.setPosition(3, 6);
    modelTracker.setValue("a", 112);
    modelTracker.printState();
    modelTracker.endOp();
    modelTracker.printState();
  }

  private static void forgotToStartOp(ModelTracker modelTracker){
    modelTracker.getNumber("AvatarX");
  }

  private static void forgotToEndLastOp(ModelTracker modelTracker){
    modelTracker.startOp();
    modelTracker.getNumber("AvatarX");

    modelTracker.startOp();
  }

  private static void tryToAssignNewTypeToDefaultVariable(ModelTracker modelTracker){
    modelTracker.startOp();
    modelTracker.setValue("AvatarX", "Hello");
    modelTracker.endOp();
  }

  private static void bailThenSuccessfulPayload(ModelTracker modelTracker){
    modelTracker.startOp();
    modelTracker.getString("AvatarPenColor");
    modelTracker.bail();

    runSuccessfulPayload(modelTracker);
  }
}
