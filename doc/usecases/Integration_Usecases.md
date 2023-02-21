### Use Cases

* The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.
 ``` java
  // Compiler is called as a handler
  ModelPayload modelPayload = buildPayload(userInput);
  controller.runPayload(modelPayload);
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
  ... in controller
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

* The user inputs an invalid command 'fd50'
```java
  String commands = GameScreen.getValue()
  Compiler.buildPayload()
  \\Throws an exception, tells the view to display PopUp
  \\Inside exception handling      
  PopUp error = new Popup(Error)
  
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
  controller.runPayload(modelPayload);

```

* The user wishes to see user defined variables
```java
  // handler is called from dropdown menu
  // inside handler
  // compiler registers query as instruction
  ModelPayload modelPayload = buildPayload(userInput)
  controller.runPayload(modelPayload)
  // in controller.runPayload
  // model.runInstruction will call Workspace.getVariables
  List<String> userVariables = Workspace.getVariables;
  ViewPayload viewPayload = new ViewPayload(viewPayload, View);
  history.add(modelPayload);
  viewUpdateQueue.add(viewPayload);
```

* The user runs a user defined function
```java
ControllerPayload controllerPayload = buildPayload(input);
// returns the relevant command sequence
List<Command> subComands = lookupCommand(parsedInput);
// in controller, executes payload and conducts the rest of the process
executePayload(controllerPayload); 
```

* The user changes the pen color
```java
ViewController.setPenColor(newColor);
// changes are automatically reflected in the GUI
```
