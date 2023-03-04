package slogo.View.Containers;

import java.util.ResourceBundle;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import slogo.View.Animator;

public class SliderView extends ContainerView {

  private Slider slider;

  private TextArea area;
  private ResourceBundle sliderBundle;
  private static final String DEFAULT_RESOURCE_PACKAGE = "View.";
  private static final String DEFAULT_RESOURCE_FOLDER = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private static final String STYLESHEET = "SliderContainer.css";
  private static final String SLIDER_RESOURCES = "SliderBoxParams";

  public SliderView(Animator animations){
    sliderBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + SLIDER_RESOURCES);
    HBox container = new HBox();
    this.setContainer(container);
    container.setId("Animation-Box");
    setUpSlider(animations, container);
    container.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

    setUpTextArea(container);
  }

  private void setUpTextArea(HBox container) {
    area = new TextArea();
    area.setId("Animation-Input");
    container.getChildren().add(area);
  }

  private void setUpSlider(Animator animations, HBox container) {
    slider = new Slider();
    slider.setId("Animation-Slider");
    slider.setOnMouseReleased(e -> setAnimationSpeed(animations));
    slider.setMin(Double.parseDouble(sliderBundle.getString("Min")));
    slider.setMax(Double.parseDouble(sliderBundle.getString("Max")));
    slider.setValue(Double.parseDouble(sliderBundle.getString("Default")));
    slider.setBlockIncrement(Double.parseDouble(sliderBundle.getString("Increment")));
    slider.setShowTickLabels(Boolean.parseBoolean(sliderBundle.getString("ShowLabels")));
    slider.setShowTickMarks(Boolean.parseBoolean(sliderBundle.getString("ShowTicks")));
    container.getChildren().add(slider);
    animations.setAnimationSpeed(slider.getValue());
  }

  public void setAnimationSpeed(Animator animations){
    animations.setAnimationSpeed(slider.getValue());
  }
  public Pane getSliderContainer(){
    return this.getContainer();
  }
}
