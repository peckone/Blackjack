package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gameFiles.deck;
import gameFiles.playerProfile;

class test_casesAdd {
	
	// INITIATE VARIABLES FOR REQUIRED OBJECTS; PLAYERS, CARDS AND CARD VALUES
	ArrayList<playerProfile> players = new ArrayList<playerProfile>();
    ArrayList<String> cards = new ArrayList<String>();
    ArrayList<Integer> cardVals = new ArrayList<Integer>();
	
	// ASSIGN VARIABLES WITH THE OBJECTS REQUIRED FOR THE CASES;
	// CARDS, CARD VALUES AND A SINGLE NAME FOR PLAYER LIST TO TEST THIER HAND
	@BeforeEach
	public void setup() throws Exception{
		cards = deck.cardDeck(cards);
		cardVals = deck.cardDeckValues(cardVals);
		players.add(new playerProfile("Greg"));
	}
	
	// CHECKS IF NEW DECK HAS 52 CARDS
	@Test
    public void deckTest(){
        assertEquals(cards.size(), 52);
    }

	// CHECKS PLAYERS NAME IS RECALLED CORRECTLY WITH 'GETNAME()' METHOD IN 'PLAYERPROFILE' CLASS
	@Test
    public void nameTest(){
        assertEquals(players.get(0).getName(), "Greg");
    }
	
	// CHECKS IF A HAND WITH AN ACE RETURNS THE STORED INT VALUE 10
	@Test
	public void aceTest() {
		players.get(0).setHand("Ace", 1);
		assertEquals(players.get(0).getHasAce(), 10);
	}
	
	
	// CHECKS IF A HAND WITH AN ACE RETURNS THE STORED INT VALUE 10
	@Test
	public void blackjackTest() {
		players.get(0).setHand("Ace", 1);
		players.get(0).setHand("King", 10);
		assertTrue(players.get(0).isBlackjack());
	}

}
