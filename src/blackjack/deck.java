package blackjack;
import java.util.ArrayList;

/*
 * MODELS A DECK OF CARDS FOR BLACKJACK; ALL 52 CARDS BUT SUITS ARE IRRELEVANT.
 * CREATES 2NO. ARRAYLISTS; ONE OF WHICH STORES THE 'CARDS' AND THE OTHER STORES THE
 * CORRESPONDING VALUES.
 */

public class deck {

    // RETURNS AN ARRAYLIST OF 4 x ACE-KING CARDS (MODELS 52 CARD PACK, BUT IGNORES SUITS)
    public static ArrayList<String> cardDeck(ArrayList<String> deck){
        String[] cards = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        for(int i=0; i<4; i++){
            for(String j : cards){
                deck.add(j);
            }
        }
        return deck;
    }

    // RETURNS ARRAYLIST OF 4 x 13no. VALUES TO MATCH CARDS. ACCUMULATING CARD VALUES MEANS
    // PAIRING UP CARD INDEX WITH VALUE INDEX IN BOTH LISTS (EG. 'DECK[11]' = 'QUEEN' --> 'DECKVALS[11]' = 10)
    public static ArrayList<Integer> cardDeckValues(ArrayList<Integer> deckVals){
        Integer[] cardValues = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
        for(int i=0; i<4; i++){
            for(Integer k : cardValues){
                deckVals.add(k);
            }
        }
        return deckVals;
    }

}