* The user changes the color of the environment's background.
```java
  List <Strings> colors = new List
  DropDown colorSelection = new DropDown(options);
  ColorSelection sends a value on ActionEvent, MouseClick to the Compiler

```
* The user changes the dropdown selection from previous commands to User-Defined functions
```java
  Container history  = new HistoryView(DropDown, TextArea);
/**
 * history.updateDisplay works by getting the Values from DropDown and updating 
 the Text Area with whatever functions are in the Instance Manager
 */
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
* The user wishes to access history item
```java
  // 
  CommandHistory history = new CommandHistory();
  CommandViewBox box = new CommandViewBox();
  String historyText = history.getHistory().get(index);
  box.
  CommandBoxView.clear();
```