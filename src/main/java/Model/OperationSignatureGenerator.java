package Model;

import java.util.Random;

public class OperationSignatureGenerator {
  private Random randomNumberGenerator;

  /**
   * Class constructor
   */
  OperationSignatureGenerator(){
    randomNumberGenerator = new Random();
  }

  /**
   * Generate a random operation signature to prevent collisions between user variables and Avatar parameters
   * @return new operation signature
   */
  int generateOperationSignature(){
    return randomNumberGenerator.nextInt(10000);
  }

}
