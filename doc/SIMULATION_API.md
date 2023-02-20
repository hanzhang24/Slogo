# Cell Society API Lab Discussion
### NAMES Aryan, Yegor, Alec, Jack, Han
### TEAM 7


## Current Simulation API

### External
* Identified Classes/Methods
    * GUI
    * Getter and setter methods for cell states
    * Inside CellArrangement and Cells
    *  Get neighbors functions
    * Initialization of different grid types

* Goals
    * Get and set any cell state
    * Get and set grid types and neighborhood types

* Abstractions
    * CellArrangement
* Services and their Contract
    * Users can ask CellArrangement for surrounding cells
    * Users can expect an error if they ask for an invalid cell or out of bounds cell
### Internal

* Identified Classes/Methods

    * SimulationManager
    * Specific gamemode simulations

* Goals

    * Manage simulations and update the grid

* Abstractions

    * Abstract class Simulation

* Services and their Contract

    * Users can implement new types of simulations



## Wish Simulation API

### External

* Goals
    * Be able to access and modify any cell state as desired
    * Be able to create any grid shape type and initial configuration
    * Be able to create any neighborhood scheme
    * Be able to use combinations of these to encode complex simulation rules
    * Be able to randomize cell states
    * Be able to return analytics for graphs
    * Be able to quickly create simulation configurations to test their simulation
    * Be able to have design verification

* Abstractions
    * Grid
* Services and their Contract
    * Users should expect to receive correct information about neighbors of valid cells
    * User should have a testing scheme to quickly create and validate design rules
    * Users should be able to receive information exception messages
### Internal

* Goals
    * To be able to make different layouts of the view
    * To be able to make new types of simulations

* Abstractions
    * Simulation
    * GUIToolbar

* Services and their Contract
    * If the user implemented all the required methods for a new simulation type, it should run as expected
    * If the user implements a new toolbar, it should behave as expected