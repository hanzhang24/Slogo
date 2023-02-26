package View;

import java.util.List;
import javafx.scene.control.ComboBox;

public class DropDownView {

  private ComboBox<String> box;
  public DropDownView(List<String> options){
//    make the box, set lambda function
    box = new ComboBox<>();
    box.getItems().addAll(options);
//    box.setOnMouseClicked(e -> sendvalue());

  }

  public ComboBox<String> getBox(){
    return box;
  }
}
