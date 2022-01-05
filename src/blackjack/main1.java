package blackjack;
import java.util.ArrayList;
import java.util.Scanner;

public class main1{

    public static void main (String args[]){

        // NEW OBJECTS REQUIRED TO STORE DATA AND GET INPUT
        Scanner scanObj = new Scanner(System.in);
        ArrayList<playerProfile> players = new ArrayList<playerProfile>();	// STORES ALL PLAYER OBJECTS
        ArrayList<String> cards = new ArrayList<String>();					// STORES ALL 52 'CARDS'
        ArrayList<Integer> cardVals = new ArrayList<Integer>();				// STORES ALL 52 'CARDS' CORRESPONDING VALUES

        // INSTANTIATE CARDS, VALUES AND PLAYERS IN THE GAME
        cards = deck.cardDeck(cards);
        cardVals = deck.cardDeckValues(cardVals);
        players = actions.establishGame(scanObj, players);

        // DEAL THE CARDS / PLAY THE GAME / DECLARE THE WINNER
        actions.deal(cards, cardVals, players);
        actions.playTheGame(players, cards, cardVals, scanObj);
        System.out.println("\n\nAnd the winner is........"+ actions.whoWon(players));

        // ASK TO PLAY AGAIN. RE-RUNS MAIN FUNCTIONS IF SO, SAYS GOODBYE IF NOT
        System.out.print("\nDo you want to play again? ('y' for YES, 'n' for NO) : ");
        scanObj.nextLine();
        String again = scanObj.nextLine();
        if(again.equalsIgnoreCase("y")){
            main(null);
        }
        else{
            System.out.println("OK, thank you for playing. Goodbye");
        }
        scanObj.close();
    }

}