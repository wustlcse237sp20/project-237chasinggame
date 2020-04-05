# cse237-project

### Brief Description ###

Our project will implement artificial intelligence algorithms such as the A* algorithm and the Q-Learning algorithm in order to make one player run away from another. After sufficient training, the human should be able to play as the player being chased. 

Start game by running "java -jar TestRun.jar" from terminal (the file is located in the second subfolder). If that doesn't work due to dependencies, resort to running RunGame.java (located in src/code/) in Eclipse or IDE. 

### Iteration 1 ###

##### What user stories were completed this iteration?
* The Programmer should have the ability to use the AStar algorithm given a maze
* The user should be able to see the maze and the player
* The user should be able to control the player by using the W,A,S,D keys (click on window if not working)

##### What user stories do you intend to complete next iteration?
* The user should have the option to see the best path from one point to another
* Enemy will be on the maze and move

##### Is there anything that you implemented but doesn't currently work?
* The AStar algorithm can find the best path, but we have not yet connected it to the maze and the enemy.
* The maze is generated and the user can control the player, but the enemy is not yet on the maze
