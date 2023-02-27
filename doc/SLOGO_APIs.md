# SLogo API Design Lab Discussion
### NAMES Aryan, Yegor, Alec, Jack, Han
### TEAM 7


## Planning Questions

* What behaviors (methods) should the Turtle have and what service should it provide?
    * Change color
    * Move 
    * Pen up 
    * Pen down
    * Repeat previous behavior
    * Loop behavior
    * Get Color
    * Get Location
    * Get Pen Conditions

* When does parsing need to take place and what does it need to start properly?
  * Parsing happens between View and Controller, it requires an input of Strings from the View.

* What is the result of parsing (not the details of the algorithm) and who receives it?
  * A list of Commands that the Controller will contain
  * Needs to be expandable

* When are errors detected and how are they reported?
  * Errors are caught by the same class that handles parsing

* What do different commands need to know, when do they know it, and how do they get it?
  * The different commands need to know what kind of commands they are, such as whether it’s a variable, basic viewCommand, or a function. 
  * They also need to know how many parameters the viewCommand takes. 
  * These need to be defined before entering Controller, and they learn about all these things inside the Parsing class.
* What behaviors does the result of a viewCommand need to have to be used by the View?
  * The resulting changes in coordinates, color, rotation, and various other ints and strings
  * View receives numbers and strings only (JSON perhaps?)
* How is the View updated after a viewCommand has completed execution?
  * The View is only updated from what the Model gives it, which is taken from the Controller.
* What value would Controller(s) have in mediating between the Model and View?
  * Allows users to enter desired changes, which move to the Controller so it can update the model, and those changes are reflected in changes in the view


## APIs

### Model/Backend External API

* Goals
  * Should communicate to the View what new information to display (new coordinates, etc)
  * Return if it’s a validState (checking for invalid parameters in the Parser

* Contract (what is it expecting)
  * Payload of instructions to run - must be valid
  * Parameters for each instruction - must be valid
* Services (what it can do)
  * Recalculate internal state based on given instructions
  * Send out updates to View with new positions, etc.
### View/Frontend External API

* Goals
  * Be able to read inputs to pass to the controller
  * Be able to trigger handler functions based on button presses/other user interaction

* Contract
  * Expects valid updated data from Model

* Services
  * Accepts user input commands
  * Provides support to the Controller to encode new functions

### Model/Backend Internal API

* Goals
  * Getter and setter methods for basic struct type
  * Methods to calculate paths, etc
  * Each Command will have public methods that can be call upon to modify the backend class that stores the turtle info.
* Contract
  * Each viewCommand has a valid set of parameters
  * Each viewCommand touches only relevant states
* Services
  * Updates internal state


### View/Frontend Internal API

* Goals
  * Be able to change the orientations of toolbars and buttons
  * Be able to support different languages (need to update parser as well)
  * Be able to support different color schemes
  * Be able to change the input method (possibly support scripts)?
  * 
* Contract
  * Receives valid parameters to update internal state
  * Has a valid FILEPATH to the css/property file it is trying to access
* Services
  * Updates internal state

## Design
* View
  * ViewTranslator
  * ViewInstructionQueue
  * Screen
  * SplashScreen
    * DropDownView
    * ButtonView
  * GameScreen
    * DrawBoardView
    * HistoryView
    * CommandBoxView 
    * ButtonsView
    * DropDownView 
    * SliderView
    * PopUpView
    * TurtleView
* Controller
  * CommandLookup
  * Compiler
  * Command
  * ControllerPayload
  * ViewPayload
* Model
  * Pen
  * CommandHistory
  * Workspace

### Backend Design CRCs

This class represents a generic instruction, which can be a viewCommand or a conditional

This class is meant to represent a return payload of a viewCommand
```java
public class ViewPayload {
  List<Command> updateList;
  // returns the list of updates to the View to process
  public List<Command> getUpdateList();
}
```

This class is meant to represent a input payload of a viewCommand
```java
public class ControllerPayload {
  Node commandRoot;
  String userCommands;
}
```

This class is meant to represent a node in the tree structure for a viewCommand
```java
public class Node {
  List<Node> children;
  protected Node getChild(int ith)
  protected int getNumParams()
  public double execute()
  public static void fromString(String input)
  public void setLanguage(Document languageDoc)
}

```

This class's purpose is to store the information for a viewCommand name and the required parameters
```java
public class Command extends Node {

  String name;

  // returns if the set of parameters is valid
  public boolean hasValidParameters(List<T> parameters)

  // set the function parameters
  public void setFunctionParameters(List<T> parameters)
}
```

This class's purpose is to compile commands for controller to execute:
```java
public class Compiler {
     // builds the list of commands for one user input
     public ControllerPayload buildPayload throws CompilationException (String userInput);
 }
```

This class's purpose is to look up supported commands:
```java
public class CommandLookup {
     // returns a Command object based on the lookup key, or throws an exception
     public Command lookupCommand throws InvalidCommandException (String userCommand);
     // adds a new user defined viewCommand to the lookup table
     public void addNewCommand throws InvalidCommandException (Command viewCommand);
 }
```
This class is representation of the History

```java
public abstract class History{
  
}
```
This class's purpose is to track the history of successful commands:
```java
public class CommandHistory extends History{
     // adds a successful payload to the CommandHistory
     public void addPayloadToHistory(ControllerPayload payload);
     public List<String> getHistory();
     public void clear();
 }
```

This class's purpose is to track the user's declared variables:
```java
public class Workspace {
     // reads a value from the temporary map, or from the original map if not present
     public double readValue(String variableName);
     // writes a value to the temporary map
     public void writeValue(String variableName, double value);
     // flushes all updates to the original map
     public void updateOriginalMap();
     // returns all user defined variables
     public List<String> getUserVariables();
 }
```

```java
public class Controller {
  // runs the given payload, accessing the Model for information as needed
  public void runPayload(Payload payload);
  public void setLanguage(Document languageDoc);
}
```

This abstract class's purpose to set up the fundamental screen:
```java
public abstract class Screen {
  Pane root;
  Scene scene;
  public Scene makeScene();
 }
 ```

This class's purpose is to display the splash screen:
```java
public class SplashSpreen extends Screen { 
    @override 
    public Scene makeScene();
    private Node drawButtons();
    private Node drawDropDown();
 }
 ```

This class's purpose is to display the GameScreen and pass information to the commandlookup
```java
public class GameScreen extends Screen {
  InsertionQueue insertionQueue;
  
  @override
  public Scene makeScene();
  public String getLanguage();
  
  private Node drawCommandBox();
  private Node drawButtons();
  private Node drawDropDown();
  private Node drawSlider();
  private Node drawPopUp();
  private Node drawTurtle();
  
 }
```

This class's purpose is to provide a DropDown Selection

```java
import javafx.scene.control.ComboBox;
public class DropDownView{
  public DropDown(List<String>);
  ComboBox<String>;
  ActionEvent event;
  String getSelection();
  public Void addSelection();
}
```
This class's purpose is to provide a Button

```java
import javafx.event.ActionEvent;
public class ButtonView {
  Button button;
  String label;
  ActionEvent event;
}
```
This class's purpose is to provide a Slider for animation Speed

```java
import javafx.event.ActionEvent;

public class SliderView{
  Slider slider;
  String label;
  ActionEvent event;
}
```
This class's purpose is to contain the DrawBoard for the program to display in

```java

import javafx.scene.canvas.Canvas;

public class DrawBoardView {
  Canvas canvas;
  TurtleView turtle;
  ActionEvent event;
  public void Draw();
}
```
This class's purpose is to represent the Avatar

```java

import javafx.scene.image.ImageView;

public abstract class AvatarView {
  ImageView image;
  public void setImage();
}
```

This class's purpose is to display errors

```java

public class PopupView {

  Alert alert;

  public void PopUpAppear();
  public void setErrorMessage();
}
```

This class is to provide a container for CSS properties and formating, ex: HistoryView

```java

public abstract class Container{
  List<Nodes> objects;
  Pane container;
  public void setProperty();
}
```
This class HistoryView provides the option to view 
```java

public abstract class HistoryView extends Container{
  TextArea display;
  public void updateDisplay();  
}
```

This class is to provide an area for the User to type in code

```java

public class CommandBoxView{
  TextArea area;
  public String getText();
  public void clear();
}
```

This class is ViewInstructionQueue, and it's job is to feed instructions into the Canvas one by one to control animation speed

```java

import java.util.Queue;

public class ViewInstructionQueue {
  //Not Exactly sure what the Queue will hold just yet, but it will hold something that represents commands
  Queue<Object> test;
  public void getNext();
}
```

This class is AnimationTextBox, and it's job is to read values from the user for animationSpeed
```java

public class AnimationTextBox throws InvalidInput{
  public float getSpeed();
}
```