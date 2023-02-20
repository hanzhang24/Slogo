# Format of Parsing and Running Commands

## Description

### Parsing Commands

1. Receive user input
2. Parse each line into either a control statement or a functional call
3. If a control statement, append to Payload under a new Control ID
    1. All function calls within this block inherit their parent Control ID
    2. If nested control, then a new ID is generated and used locally
    3. Function calls keep track of all of their parent IDs (will be a list)
4. If a function call, fetch list of relevant commands and append to Payload under parent Control ID
5. Repeat until entire string is processed and Payload object is complete, then send to Model to run

### Running Commands in Model

1. When the Model runs a Payload, it calls nextInstruction to receive the information about the
   instruction to run
    1. If it's a conditional and the branch shouldn’t be taken, skipBranch is called, and Payload
       moves internal pointer to the next valid ID (using the highest ID of the failed conditional)
    2. Otherwise, just run the method and call nextInstruction
2. For each payload, a set of CHANGED simulation parameters (like turtle position, etc) is tracked
   over the course of the payload.
    1. At the end of running the payload, a dedicated method will create a ViewPayload to send back
       based upon the set of changed parameters

## Example

* If the turtle is on the right side of the screen,
    * if it is in the top half, then move 10 units to the right
    * then move 10 units up
* then set the rotation to 90


* User input:

```java
if(turtleX>0){
    if(turtleY>0){
      turtleX+10;
    }
    turtleY+10;
}
turtleRotation=90;
```

* ModelPayload will look like:

```java
Payload{
  List<Instruction> = 
    Conditional{getTurtleX,>0}id:1
    Conditional{getTurtleY,>0}id:1,2
    Command{moveRight,10}id:1,2
    Command{moveUp,10}id:1
    Command{setRotation,90}id:3
}
```

* When Model runs this function:
    * If getTurtleX > 0 is false, the Payload will be asked to skip to the first instruction without
      id = 1
        * Moves pointer to setRotation command
    * If getTurtleY > 0 is false, the Payload will be asked to skip to the first instruction without
      id = 2
        * Moves pointer to the moveUp command
    * Then, the Model can request the next instruction as normal using Payload.nextInstruction()