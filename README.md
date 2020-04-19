# cse237-project

### Brief Description ###

Our project will implement artificial intelligence algorithms such as the A* algorithm and the Q-Learning algorithm in order to make one player run away from another. After sufficient training, the human should be able to play as the player being chased. 

Start game by running "java -jar RunGame.jar" from terminal If that doesn't work due to dependencies, resort to running RunGame.java (located in src/code/) in Eclipse or IDE. 

### Iteration 1 ###

##### What user stories were completed this iteration?
* The Programmer should have the ability to use the AStar algorithm given a maze(run from eclipse or other ide)
* The user should be able to see the maze and the player
* The user should be able to control the player by using the W,A,S,D keys (click on window if not working)

##### What user stories do you intend to complete next iteration?
* The user should have the option to see the best path from one point to another
* Enemy will be on the maze and move

##### Is there anything that you implemented but doesn't currently work?
* The AStar algorithm can find the best path, but we have not yet connected it to the maze and the enemy.
* The maze is generated and the user can control the player, but the enemy is not yet on the maze

### Iteration 2 ###

##### What user stories were completed this iteration?
* *Based on Iteration 1 Feedback:* Refactoring of classes for better readability
* *Based on Iteration 1 Feedback:* Added Javadocs for each class where necessary
* Transformed the data structures so that the AStar algorithm now works with our maze
* The Enemy will be on the maze and move
* The user is now chased by the enemy as the user navigates the maze
* The user should have the option to see the best path from one point to another --> Moved to a unit test, doesn't make sense in game

##### What user stories do you intend to complete next iteration?
* The user should be scored on how well they navigate the maze and avoid the enemies
* The user should be able to see their score displayed
* The user should restart the game when they get caught
* The user should be able to go up in difficulty as they get better scores

##### Is there anything that you implemented but doesn't currently work?
* Single enemies are working, but there is no scoring mechanism yet
* We implemented drawing a maze of given size, but we haven't implemented changing mazes / trying different maze sizes