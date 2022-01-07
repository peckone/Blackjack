package gameFiles;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * 'MAIN' FILE INSTANTIATES A SCANNER OBJECT AND VARIOUS ARRAYLISTS TO CREATE THE CARD DECK, PLAYER LIST
 *  AND PASS TO THE 'ACTIONS' METHODS TO CONTROLS THE CREATION AND FLOW OF THE GAME.
 *  THERE IS A 'WINNER' LIST WHICH LATTERLY STORES THE PLAYER, OR PLAYERS, WHO HAVE THE HIGHEST SCORE.
 *  FINALLY, YOU ARE ASKED IF YOU WISH TO PLAY AGIAN.
 */

public class main1{

    public static void main (String args[]){

        // NEW OBJECTS REQUIRED TO STORE DATA AND GET INPUT
        Scanner scanObj = new Scanner(System.in);
        ArrayList<playerProfile> players = new ArrayList<playerProfile>();	// STORES ALL PLAYER OBJECTS
        ArrayList<String> cards = new ArrayList<String>();					// STORES ALL 52 'CARDS'
        ArrayList<Integer> cardVals = new ArrayList<Integer>();				// STORES ALL 52 'CARDS' CORRESPONDING VALUES
        ArrayList<playerProfile> winner = new ArrayList<playerProfile>();	// STORES WINNER(S) OF THE HAND

        // INSTANTIATE CARDS, VALUES AND PLAYERS IN THE GAME
        cards = deck.cardDeck(cards);										// 52-ELEMENT ARRAYLIST TO MODEL CARDS
        cardVals = deck.cardDeckValues(cardVals);							// 52-ELEMENT ARRAYLIST TO STORE CORRESPONDING CARD VALUES
        players = actions.establishGame(scanObj, players);					// CALLS METHOD TO INTRODUCE THE GAME AND GATHER PLAYER NAMES

        // DEAL THE CARDS / PLAY THE GAME / DECLARE THE WINNER
        actions.deal(cards, cardVals, players);								// 2 CARDS PER PLAYER, INDLUDING DEALER
        actions.playTheGame(players, cards, cardVals, winner, scanObj); 	// LOOPS THROUGH 'PLAYERS' LIST TO 'HIT' TIL BUST OR 'STAND'
        System.out.println(actions.whoWon(winner));							// CHECKS 'WINNER' LIST TO FIND NAME(S) OF WINNING PLAYER
     
        // ASK TO PLAY AGAIN. RE-RUNS MAIN FUNCTIONS IF SO, SAYS GOODBYE IF NOT
        System.out.print("\nDo you want to play again? ('y' for YES, any other key for NO) : ");
        scanObj.nextLine();
        String again = scanObj.nextLine();
        if(again.equalsIgnoreCase("y")){
            main(null);														// RESTARTS MAIN FUNCTION
        }
        else{
            System.out.println("OK, thank you for playing. Goodbye"); 		// QUITS GAME ON ANY OTHER INPUT VALUE...
        }
        scanObj.close();
    }

}