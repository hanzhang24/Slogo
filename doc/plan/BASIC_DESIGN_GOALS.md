# Tasks left to do for "Basic" deadline:

Target Finish Date: 3-4-23

## OVERALL:

* Increase test coverage overall
* Write test harness to run programs start to finish
* Write documentation... a lot of it
    * JavaDoc for every public method at least, preferably every method

## MODEL: Alec

* Write through history and return values on successful operation
    * Implement history in the MVC track
        * Change endOp() method to accept a `String history` to allow history to write through, and
          a List of Doubles to representing the return values
            * New usage: VIEWPayload v = endOp(history, returnValues);
        * The history and return values will be included in the payload, and
            * It requires the VIEW to implement a method that accepts a String displays that string
              in the HistoryVIEWBox
                * A command in VIEWPayload will now
                  call `gameScreen.appendToHistory(String history)` to do this
            * It also requires the VIEW to implement a method that accepts a List of Doubles and
              displays them
                * A command in VIEWPayload will now
                  call `gameScreen.printReturnValues(List returnValues)` to do this

## VIEW:

* Finish implementing the VIEW objects/functionality
    * Implement animations in line drawing and turtle movements
      * Utilize Canvas for commands
    * Implement functionality to change pen color and simulation speed
    * Fix Animation to actually display
      * Keep animation in bounds of canvas
    * Create Containers for History and Animation Slider
    * Implement View Queue to update with Animation Slider Speed
    * Implement PopUp to connect with Parser
    * Allow for languages to change
    * Fix CSS color Layouts

* XML Parsing
    * Write xml files for every command using the specified format in the design plan
    * Work within the VIEW to display documentation for any command
        * Process should look like: click "Help" button -> click "Command" Button -> displays
          information about syntax and usage
        * To fetch information about commands, should use some sort of getTag function
    * Devise a scheme to save and load previous configurations
        * You'll have to decide on the names of each tag and how to load it
        * This can happen any number of ways, here is one:
        * Saving: save all successful user commands
        * Loading: load and rerun all successful user commands
        * Note: the weakness of this method is it wouldn't save all the times the user changes the
          pen color, unless we store pen color in the backend as well
            * One solution: we could call the Controller every time the color is changed, but now
              the VIEW has CONTROLLER references in multiple places

## CONTROLLER:

* Finish implementing the steps for each method
    * Clarify the necessary adjustments to correctly display coordinates and rotations
    * For example, the y-axis is inverted in the VIEW, so make sure that SETHEADING works properly
* Implement user defined functions
    * Recognize proper syntax and break down user functions into basic calls to MODEL