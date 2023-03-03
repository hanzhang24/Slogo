package slogo.View.Containers;

import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import slogo.View.Containers.ContainerView;

public class SliderView extends ContainerView {

  Slider slider;

  public SliderView(){
    slider = new Slider();
    slider.setId("Animation-Slider");
    HBox container = new HBox();
    this.setContainer(container);
    container.setId("Animation-Box");
    
  }

  public Slider getSlider() {
    return slider;
  }
}
