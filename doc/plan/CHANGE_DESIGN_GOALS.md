# Tasks left to do for "Change" deadline:

Target Finish Date: 3-4-23

## OVERALL:

* Increase test coverage overall
* Write test harness to run programs start to finish
* Write documentation... a lot of it
    * JavaDoc for every public method at least, preferably every method

## Priority 1: Adding multiple avatars to screen

* Controller needs to recognize new command syntax
* Model needs to add functionality to create new avatars
    * Also needs to add current ID to View Commands
* View Commands need to specify which avatar is being modified
* View needs to interact with avatars correctly when given a specific ID and action

## Priority 2: Adding multiple screens

* Should be pretty straightforward
    * Add a button named `New Window`
    * Handler for the button to look like:

```java
public void handlerName(){
    Main newWindow = new Main(); // should just open a separate window entirely
}
```

## Priority 3: Adding avatar fetch commands
* View should develop a new window/dialog/place to display avatar coordinates, rotation, other info
* Controller needs new access points to allow the View to request information
* Controller/Model can collect all information and add to Payload
    * Might be good enough for Controller to run a bunch of getters and setters, then pass them in
      as the list of return values

## Catch-up Goals: Yegor
* Write command.xml files for all standard commands as listed in the "Basic" assignment
  * These will be located in src/main/resources
* Write classes/methods for the XMLParser to save and load file configurations
  * Any saved files will be located in /data/SavedUserConfigurations
* Once all of the above are done... look into writing a speech-to-text conversion class.
  * Needs one public method `recordSpeech()` that returns a String that will populate the CommandBox
  * Implementation details are unknown at the moment and will require research


## Thursday Goals
* Color picker inside the View
* Introduce a color scheme picker (night mode)
* Fix HistoryView
  * History currently disappears when a command is run
  * Have dropdown box options translate with language
* Make a return value box
* Make a help dialog box (as much as you can)
* JavaDoc for backend/frontend
* Testing for Nodes
* Bug fixes for avatar creation
* Update ModelView interface with IDs, and reimplement game screen
* Create an interface called InputCollector that returns a Sting to run in the Command Box
* Fix the bug where rotations and translations don't occure in the correct order.
* README

## Friday Goals
* Return value box
* Help dialog box and XML
* Loading and saving files
* Fix bug with translation and rotation animations together
* Multiple Turtle support
* JavaDoc and Testing overall
* README