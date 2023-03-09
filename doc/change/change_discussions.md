## Backend

How parsing will work?

Cases:

* TELL [3 4] 
* fd sum id TELL[1 2]
In this case, we first set the number of active turtles to 3, 4.
Then, the forward would be locked onto turtle 3, and execute the command sum id TELL[1,2] (3 moves 5)
Then, the forward would be locked onto turtle 4, and execute the command sum id TELL[1,2] (4 moves 6)
While the active turtles are changed throughout, the current turtle is 1 and 2 throughout.
In this sense, the *current turtle does not necessarily point to an active turtle*

* (assuming total 8 turtles, :var initially 0)
* TELL [5,6]
* fd askwith [> id 3] [set :var add :var 10]

In this case, we first set active turtles to 5,6. 
Then, forward locks in on turtle 5.
right now, activeID = 5
Then, askwith executes var = var + 10 on turtles 4,5,6,7,8. :x is now 50. 
    activeID = 1,2,3,4,5,6,7,8
activeID reset back to 5
Askwith returns 50
Turtle 1 goes foward 50

Then, forward locks in on turtle 6.
Then, askwith executes set :x add :x 10 on turtles 4,5,6,7,8. :x is now 100.
Askwith returns 100
Turtle 2 goes foward 100.

clearscreen applies to all turtles
## Frontend

