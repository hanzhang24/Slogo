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
  * The different commands need to know what kind of commands they are, such as whether it’s a variable, basic command, or a function. 
  * They also need to know how many parameters the command takes. 
  * These need to be defined before entering Controller, and they learn about all these things inside the Parsing class.
* What behaviors does the result of a command need to have to be used by the View?
  * The resulting changes in coordinates, color, rotation, and various other ints and strings
  * View receives numbers and strings only (JSON perhaps?)
* How is the View updated after a command has completed execution?
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
  * Each command has a valid set of parameters
  * Each command touches only relevant states
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
  * ModelPayload
  * ViewPayload
* Model
  * Pen
  * InstructionHistory
  * Workspace

### Backend Design CRCs

This class represents a generic instruction, which can be a command or a conditional
```java
public class Instruction { 
     int id;
     List<Instruction> instructions;
     // returns the Instruction name to the screen
     public String getInstructionName();
     
     // returns a ViewPayload object for the View to use to update itself
     public ViewPayload makeViewPayload();
       
     // returns if the set of parameters is valid
     public boolean hasValidParameters(List<T> parameters)
 }
 ```

This class is meant to represent a return payload of a command
```java
public class ViewPayload {
  List<Command> updateList;
  // returns the list of updates to the View to process
  public List<Command> getUpdateList();
}
```

This class is meant to represent a input payload of a command
```java
public class InputPayload {
  List<Instruction> instructionList;
  // returns the list of updates to the View to process
  public List<Command> getInstructionList();
}
```

This class's purpose is to store the information for a command name and the required parameters
```java
public class Command extends Instruction {

  List<T> functionParameters
  int returnValue

  // returns if the set of parameters is valid
  public boolean hasValidParameters(List<T> parameters)

  // set the function parameters
  public void setFunctionParameters(List<T> parameters)
}
```

```java
public class Conditional extends Instruction{
     List<T> booleanParameters
     // returns if the set of parameters is valid
     public boolean hasValidParameters(List<T> parameters)
     // sets the boolean parameters
     public void setBooleanParameters(List<T> parameters)
}
```

This class's purpose is to compile commands for models to execute:
```java
public class Compiler {
     // builds the list of commands for one user input
     public ModelPayload buildPayload throws CompilationException (String userInput)
    
 }
```

This class's purpose is to look up supported commands:
```java
public class CommandLookup {
     // returns a Command object based on the lookup key, or throws an exception
     public Command lookupCommand throws InvalidCommandException (String userCommand)
     // adds a new user defined command to the lookup table
     public void addNewCommand throws InvalidCommandException (Command command);
    
 }
```
This class's purpose is to track the history of successful commands:
```java
public class InstructionHistory {
     // adds a successful payload to the InstructionHistory
     public void addPayloadToHistory(ModelPayload modelPayload);
    
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

This class's purpose is to hold a variety of program-associated collections of information
```java
public class InstanceManager {
    List<Command> commandHistory;
    List<Command> userDefinedCommands;
    Map<T,T> userDefinedVariables; 
    // getters and setters for all methods
    // returns a list of user defined variables
    public List<String> getVariables();
}
```

This class's purpose is to 

This abstract class's purpose to set up the fundamental screen:
```java
public interface Screen {
  Pane pane;
  Scene scene;
 }
 ```

This class's purpose is to display the splash screen:
```java
public class SplashSpreen implements Screen {
  Pane pane;
  Scene scene;
 }
 ```

This class's purpose is to display the GameScreen and pass information to the commandlookup
```java
public class GameScreen implements Screen {
  Pane pane;
  Queue inertionQueue;
  Scene scene;
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
  Void addSelection();
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
  void Draw();
}
```
This class's purpose is to represent the Turtle

```java

import javafx.scene.image.ImageView;

public class TurtleView {
  ImageView image;
  void setImage();
}
```

This class's purpose is to display errors

```java

public class PopupView {

  Alert alert;

  void PopUpAppear();
  void setErrorMessage();
}
```

This class is to provide a container for CSS properties and formating, ex: HistoryView

```java

public abstract class Container{
  List<Nodes> objects;
  Pane container;
  void setProperty();
}
```
This class HistoryView provides the option to view 
```java

public abstract class HistoryView extends Container{
  TextArea display;
  void updateDisplay();  
}
```

This class is to provide an area for the User to type in code

```java

public class CommandBoxView{
  TextArea area;
  String getText();
  void clear();
}
```

This class is to provide an area for the User to type in code

```java

public class CommandBoxView{
  TextArea area;
  String getText();
  void clear();
}
```
This class is the ViewTranslator, and it takes can of the input from the Model and inserts into InstructionQueue
```java

public class ViewTranslator{
  void readInstructions(ViewPayload);
  List<Commands> getInstructions();
}
```
This class is ViewInstructionQueue, and it's job is to feed instructions into the Canvas one by one to control animation speed

```java

import java.util.Queue;

public class ViewInstructionQueue {
  //Not Exactly sure what the Queue will hold just yet, but it will hold something that represents commands
  Queue<Object> test;
  void getNext();
}
```

### Use Cases

 * The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.
 ``` java
  // Compiler is called as a handler
  ModelPayload modelPayload = buildPayload(userInput);
  model.runPayload(modelPayload);
  // in Model.runCommand
  try {
    for(Instruction instr : modelPayload){
      model.runInstruction(instr);
  } catch (Runtime Exception re) {
      throw re;
  }
  history.add(modelPayload);
  viewUpdateQueue.add(new ViewPayload(modelPayload));
  
```

 * The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.
 ``` java
  // Compiler is called as a handler
  try {
    ModelPayload modelPayload = buildPayload(userInput);
  } catch (InstructionFormatError ie) {
    throw ie;
  }
```
 * The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).
 ``` java
  // Compiler is called as a handler
  try {
    ModelPayload modelPayload = buildPayload(userInput);    model.runCommand(instructionSet);
  } catch (InstructionFormatError ie) {
    // handle error
  }
  ... in model
  for(Instruction instr : modelPayload){
    runInstruction(instr); // this path is followed down accordingly
  }
```

 * User types in 'pu fd 100000000000000000000000000'
```java
  // Compiler is called as a handler
  // inside buildPayload(userInput)
  List<Token> = Tokenizer.tokenize(userInput)

  // inside Tokenizer.tokenize(), it will detect that it is a number and try to initialize
  try {
    floatToken = new FloatToken(token) // 
  } catch (ValueError e){
        throw new ValueError("Invalid float value "+token)
  }
```


 * The user changes the color of the environment's background.
```java
  List <Strings> colors = new List
  DropDown colorSelection = new DropDown(options);
  ColorSelection sends a value on ActionEvent, MouseClick to the Compiler

```

* The user inputs an invalid command 'jksldjsl'
```java
  String commands = GameScreen.getValue()
  Compiler.buildPayload()
  \\Throws an exception, tells the view to display PopUp
  \\Inside exception handling      
  PopUp error = new Popup(Error)
  
```
* The user changes the dropdown selection from previous commands to User-Defined functions
```java
  Container history  = new HistoryView(DropDown, TextArea);
/**
 * history.updateDisplay works by getting the Values from DropDown and updating 
 */the Text Area with whatever functions are in the Instance Manager
  history.updateDisplay();
```
* The user clicks the run button
```java
  CommandViewBox box = new CommandViewBox();
  String commandText = box.getText();
  List<Instructions> instructions = Compiler.buildPayload(commandText);
  \\After this, the string is passed through the controller and throught the model, eventually back to the View,
  \\Running the simulation.  
```
 * The user queries the turtle for its x-position
```java
  // instruction is parsed as specified above
  // in model.runInstruction())
  ViewPayload ViewPayload = instruction.makeViewPayload();
  viewTranslator.add(ViewPayload);
  // in View
  for(Command c : ViewPayload){
    runCommand(c);
  }
```

* The user wishes to rerun the previous command
```java
  // in Compiler
  Instruction instr = history.getHistory.get(size - 1);
  ModelPayload modelPayload = new ModelPayload(instr);
  model.runPayload(modelPayload);

```

* The user wishes to see user defined variables
```java
  // handler is called from dropdown menu
  // inside handler
  // compiler registers query as instruction
  ModelPayload modelPayload = buildPayload(userInput)
  model.runPayload(modelPayload)
  // in model.runPayload
  // model.runInstruction will call Workspace.getVariables
  List<String> userVariables = Workspace.getVariables;
  ViewPayload viewPayload = new ViewPayload(viewPayload, View);
  history.add(modelPayload);
  viewUpdateQueue.add(viewPayload);
```

* The user wishes to see history
```java
  // HistoryView is called as
  
 
```