## Blackjack Tech Test

This solution is completed using Java and runs on version 11 or higher.
It simlautes a full game (minus a number of player options depending on cards) where multiple players
can play a hand, including the dealer, a winner is declared and you are asked if you wish to play again.
*** Rules vary from country to country, and from casino to casino. This game does not copy any single rule book. ***

Once the repository has been cloned, the 4no. class files can be found in 'blackjack > src > blackjack'.
Opening and running 'main1.java' will begin the game.

Once ran, the game asks for the number of players who are going to play, names for that number of players,
then deals 2 cards per player to start the game. Dealers cards are shown with the second card obscured with a '?' 
to simulate a version of the game played in some Euro casinos where only 1 dealer card is revealed. 
As each player takes a turn their cards are printed to the console to simulate being face up on the table
as they would be in real life, with an accumulated total.

As each player plays they can either 'hit' to receive another card, with a running total of their cards and value shown,
until they are either bust (over 21) or choose to 'stand'. This will save their current score and pass play to the
next player. This process repeats until all players have had a go, then the dealers turn is run automatically,
hitting til they have 15 or more, then stands. Though they can go bust too. Finally, all players scores are checked
and the highest is declared the winner. You then have the choice to play again.


In 'blackjack > src > test' is a file 'test_cases.java' which includes all 8 scenarios outlines in the
provided guidance. As well as a few additional tests to cover various other methods in the class files.


Briefly, the solution's 'main1' file instantiates the card deck object from 'deck' class, then calls a series of 
methods in the 'actions' class which simulate the game. These in turn call the 'playerprofile' class to instantiate
the player objects for each player in attendance, and uses the 'hit' and 'stand' methods to control the cards each
player gets. And finally it establishes the winner and asks if you wish to play again. Doing so then calls a new
deck and once again begins the process of inviting players and playing a hand......
