# slogo

## TEAM NUMBER - 07

## NAMES - Alec, Aryan, Jack, Han, and Yegor

This project implements a simplified version of Logo, an educational coding app for new programmers.
Through a terminal-like command box, users can interact with an avatar, conduct mathematical
operations, define variables and commands, and customize UI colors and simulation speed.

### Timeline

* Start Date: 2-14-23

* Finish Date: 3-9-23

* Hours Spent: 80+

### Attributions

* Resources used for learning (including AI assistance)
    * TODO
* Resources used directly (including AI assistance)
    * TODO

### Running the Program

* Main class:
    * Run `Main.java`
* Data files needed:
    * Refer to src/main/resources to see required resource files to run
* Interesting data files:
    * TODO
* Key/Mouse inputs:
    * Keyboard
        * Type commands into the input box
        * Enter custom values for settings
    * Mouse
        * Navigate app windows
        * Select preset configurations for color, etc.

### Notes/Assumptions

* Assumptions or Simplifications:
    * Model
        * Interactions with the Controller ("Operations")
            * Assumes that Controller filters mistyped commands
            * Assumes that Controller filters invalid parameters
            * Assumes that Controller properly passes return values and user input
                * These are recorded and passed along to the View
        * Avatar
            * Assumes that provided default parameters file contains necessary parameters
                * Initial X, Y, and rotation coordinates
                * Initial pen color settings
                * Initial visibility settings
                * Default numeric and boolean values
                    * These are allowed to be different from initial values
    * View Update Commands
        * Payload Creation
            * Assumes that Model queues all necessary changes for the View
        * View Command
            * Assumes that Model provides correct parameters for each View Command
                * When ID is necessary, Model provides a valid ID (not negative or nonexistent)
                * When other inputs are necessary, Model provides them in the correct format (
                  numbers and booleans are parseable)
    * View
        * Animation
            * When multiple animations are required, they occur in sequential order
            * When returning to home from a large x/y coordinate, the avatar may not move directly
              to the middle
                * Instead, the avatar may travel multiple times across the screen, backtracking its
                  movement away from the true (0,0) center
* Known Bugs:
    * TODO
* Features implemented:
    * Wide suite of user actions
        * Avatar actions
        * Avatar queries
        * Mathematical functions
        * Boolean operations
        * User defined variables
        * Control structures
        * User defined commands
    * View customization
        * Pen color selection
        * Color pallet selection
        * Simulation speed selection
        * Avatar custom image upload
        * Multiple language support
    * View Display
        * History of successful commands
        * User defined commands
        * Descriptive dialog error messages
* Features unimplemented:
    * TODO
* Noteworthy Features:
    * Model
        * Implements backup functionality that recovers previous state when an error occurs
        * Generates unique keys to protect key backend parameters and prevent collisions with
          user-defined variables

### Assignment Impressions


