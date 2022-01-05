package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import blackjack.actions;
import blackjack.deck;
import blackjack.playerProfile;

class test_cases {
	
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
	
	// CHECKS PLAYERS NAME IS RECALLED CORRECTLY WITH 'GETNAME()' METHOD IN 'PLAYERPROFILE' CLASS
	@Test
    public void nameTest(){
        assertEquals(players.get(0).getName(), "Greg");
    }
	
	// CHECKS PLAYER HAS 2 CARDS WHEN 'DEAL()' IS CALLED TO COMMENCE GAME.
	// 'DEAL()' DEALS CARDS TO ALL PLAYERS AT THE BEGINNING OF THE GAME;
	// CHECKING THE 'SIZE()' OF THE 'THAND' ARRAYLIST TELLS YOU HOW MANY 'CARDS' IT HOLDS
	@Test
	void test1() {
		actions.deal(cards, cardVals, players);
		assertEquals(players.get(0).getHand().size(), 2);
	}
	
	// CHECKS IF PLAYERS CARD COUNT INCREASES FROM 2 CARDS TO 3 WHEN CALLING 'HIT()' AFTER 'DEAL()'
	// AND IF THE NEW VALUE HAS INCREASED.
	// 'DEAL()' IS CALLED AND HAS BEEN ESTABLISHED IN TEST1 IT DISTRIBUTES 2 CARDS. CALLING 'HIT()'
	// CREATES A TOTAL OF 3 CARDS AND 'SCORE2' IS GREATER THAN 'SCORE1'
	@Test
	void test2() {
		actions.deal(cards, cardVals, players);
		Integer score1 = players.get(0).getHandVal();
		actions.hit(players.get(0), cards, cardVals);
		Integer score2 = players.get(0).getHandVal();
		assertEquals(players.get(0).getHand().size(), 3);
		assertTrue(score2 > score1);
	}
	
	// CHECKS IF PLAYERS CARD COUNT IS THE SAME WHEN CALLING 'STAND()' AFTER 'DEAL()'
	// AND IF THE CARD VALUE STAYS THE SAME.
	// 'DEAL()' DISTRUBUTES 2 CARDS, AFTER 'STAND()' THERE ARE STILL 2 CARDS,
	// AND 'SCORE1' AND 'SCORE2' ARE EQUAL.
	@Test
	void test3() {
		actions.deal(cards, cardVals, players);
		Integer score1 = players.get(0).getHandVal();
		actions.stand(players.get(0));
		Integer score2 = players.get(0).getHandVal();
		assertEquals(players.get(0).getHand().size(), 2);
		assertTrue(score2 == score1);
	}
	
	// CHECKS THAT A HAND VALUE OF 21 IS VALID. 
	// ASSIGNS CARDS WITH A COMBINED VALUE OF 21 TO THE PLAYERS HAND USING 'SETHAND()' METHOD FROM PLAYERPROFILE CLASS,
	// THEN CALLS 'ISVALID()' FROM THE 'ACTIONS' CLASS TO ESTABLISH THE CURRENT HAND IS 21 OR LESS, THERFORE, VALID TO PLAY.
	@Test
    void test4() {
		players.get(0).setHand("King", 10);
		players.get(0).setHand("9", 9);
		players.get(0).setHand("2", 2);
        assertTrue(players.get(0).isValid());
    }
	
	// CHECKS THAT A HAND VALUE OF MORE THAN 21 (22 IN THIS CASE) IS NOT VALID. 
	// ASSIGNS CARDS WITH A COMBINED VALUE OF 22 TO THE PLAYERS HAND USING 'SETHAND()' METHOD 
	// IN THE PLAYERPROFILE CLASS, THEN CALLS 'ISVALID()' TO ESTABLISH THAT THIS HAND IS NOT VALID TO PLAY. 
	@Test
    void test5() {
		players.get(0).setHand("King", 10);
		players.get(0).setHand("King", 10);
		players.get(0).setHand("2", 2);
        assertFalse(players.get(0).isValid());
    }
	
/*
 * FOR TESTS 6, 7 & 8 WE ASSIGN SPECIFIC CARDS TO THE PLAYERS HAND AND THIER RESPECTIVE VALUES TO THE VALUE VARIABLE USING 'SETHAND()'
 * IN PLAYERPROFILE CLASS, THEN CHECK THE TOTAL VALUE USING THE 'GETHANDVAL()' METHOD. 
 * THIS METHOD RETURNS A LIST OF 2 INTEGER VALUES; THE FIRST IS A RUNNING TOTAL WHICH COUNTS ACES AS 1, 
 * THE SECOND DOES THE SAME BUT COUNTS ACES AS 11.
 */
	
	// CHECKS IF A KING AND AN ACE EQUATE TO 21 (AND IF 'GETHASACE()' RETURNS THE STORED INT VALUE 10 WHEN PLAYER HAS 1 IN THIER HAND)
	@Test
	void test6() {
		players.get(0).setHand("King", 10);
		players.get(0).setHand("Ace", 1);
		assertEquals(players.get(0).getHasAce(), 10);
		assertEquals(players.get(0).getHandVal(), 21);
	}
	
	// CHECKS IF KING, QUEEN AND ACE EQUATE TO 21
	@Test
	void test7() {
		players.get(0).setHand("King", 10);
		players.get(0).setHand("Queen", 10);
		players.get(0).setHand("Ace", 1);
		assertEquals(players.get(0).getHandVal(), 21);
	}
	
	// CHECKS IF 9 AND 2no. ACES EQUATE TO 21
	@Test
	void test8() {
		players.get(0).setHand("9", 9);
		players.get(0).setHand("Ace", 1);
		players.get(0).setHand("Ace", 1);
		assertEquals(players.get(0).getHandVal(), 21);
	}

}
