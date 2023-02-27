package IntegrationTests;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.SlogoInitializer;

public class SimpleProgramTests {
  private SlogoInitializer slogoInitializer;
  @BeforeEach
  void setup(){
    SlogoInitializer slogoInitializer = new SlogoInitializer(new Stage());
  }

  @Test
  void testForward(){
    // simulate text box input for fd 50
  }
}
