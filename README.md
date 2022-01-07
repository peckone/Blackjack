## Blackjack Tech Test

This solution is completed using Java openjdk 11.0.13 on Linux. Test cases were completed using JUnit 5.
Solution can be ran on Eclipse IDE

*** Rules vary from country to country, and from casino to casino. This game does not copy any single rule book ***
It simply allows multiple players to play a hand in turn where the player, or players, who get Blackjack
(21 from 2 cards) win the game, or the next highest of 21 from 3+ cards, then any highest value from 20 down.
If the dealer socres highest or joint highest, no one wins. Though in real life anyone matching that hand
would get thier stake back.

It simulates a full game (minus a number of player options depending on cards) where multiple players
can play a hand, including the dealer, a winner is declared and you are asked if you wish to play again.

Once the repository has been cloned, the 4no. class files can be found in 'blackjack > src > gameFiles'.
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

NOTE : When Blackjack is scored the value saved is 99, this allows the program to tell who got 21 with 2 cards
which beats 21 with 3 or more cards. The value 99 is then converted to 'Blackjack' when declaring the winner(s).


In 'blackjack > src > test' is a file 'test_cases.java' which includes all 8 scenarios outlines in the
provided guidance. As well as 'test_casesAdd.java' which contains a few additional tests to cover various
other methods in the class files.


Briefly, the solution's 'main1' file instantiates the card deck object from 'deck' class, then calls a series of 
methods in the 'actions' class which simulate the game. These in turn call the 'playerprofile' class to instantiate
the player objects for each player in attendance, and uses the 'hit' and 'stand' methods to control the cards each
player gets. And finally it establishes the winner and asks if you wish to play again. Doing so then calls a new
deck and once again begins the process of inviting players and playing a hand......
