package Model;

public class ModelTest {
  public static void main(String[] args){
    AvatarTracker avatarTracker = new AvatarTracker();
    runPayload(avatarTracker);
  }

  // test run payload function
  private static void runPayload(AvatarTracker avatarTracker){
    avatarTracker.startOp();
    avatarTracker.setAvatarX(500);
    avatarTracker.setAvatarY(-100);
    avatarTracker.setAvatarPenColor("red");
    avatarTracker.setAvatarRotation(187);
    avatarTracker.setAvatarPosition(-4, -3);
    avatarTracker.setUserDouble("a", 45);
    avatarTracker.printState();
    // avatarTracker.endOp();
    avatarTracker.bail();
    avatarTracker.printState();
  }

}
