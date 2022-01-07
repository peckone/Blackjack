package gameFiles;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/*
* THIS CLASS CONTAINS ALL THE METHODS THE DEALER WOULD MANAGE.
* 1. 'ESTABLISHGAME()'
* 		ASKS HOW MANY PLAYERS, ASKS NAMES AND INSTANTIATES 'PLAYERPROFILE' OBJECTS, INCLUDING FOR THE DEALER
* 2. 'DEAL()'
* 		DEALS THE CARDS; 2 EACH FOR EACH PLAYER
* 3. 'PLAYTHEGAME()'
* 		COMMENCES THE GAME ASKING 'HIT' OR 'STAND' FOR EACH PLAYER IN TURN. THEY EITHER STAND ON A VALID HAND OR GO BUST.
* 		DEALER 'HITS' UNTIL SHE HAS > 14 & <22, THEN AUTOMATICALLY STANDS. CAN GO BUST TOO.
* 4. 'HIT()'
* 		CALLED DURING 'PLAYTHEGAME()' TO DISTRUBUTE A SINGLE CARD AND UPDATES PLAYERS HAND AND HAND VLAUE
* 5. 'STAND()'
* 		CALLED DURING 'PLAYTHEGAME()' TO ALLOW PLAYER TO STICK WITH THE VALID HAND THEY HAVE
* 6. 'SAVEWINNER()'
* 		CALLED FROM 'STAND' TO DETERMINE WHICH PLAYER CHOOSING TO STAND HAS THE HIGHEST SCORE (COULD BE MULTIPLE)
* 6. 'WHOWON()'
* 		CHECKS THE CONTENTS OF 'WINNER' LIST AND AND RETURS A STRING DECLARING THE WINNER, WINNERS, OR THAT EVERYONE WAS BUST!
*/

public class actions {
	
	// ASKS NUMBER OF PLAYERS, REQUESTS NAMES AND RETURNS ARRAYLIST OF 'PLAYERPROFILE' OBJECTS PER PLAYER
	public static ArrayList<playerProfile> establishGame(Scanner scanObj, ArrayList<playerProfile> players){
		int num_of_players = 0;
		boolean number = false;
		while(!number) {								// MAKES SURE A NUMBER IS INPUT
			try {
				System.out.print("Welcome to the Blackjack table. How many players are there : ");	// ASKS FOR NUMBER OF PLAYERS 
		    	num_of_players = scanObj.nextInt(); 
		    	number = true;
		        System.out.println("OK, we have " + num_of_players + " players!");
			} catch(InputMismatchException ime) {		// THROWS ERROR IS INPUT IS NOT AN INT
				System.out.println("That is not a number.");
				scanObj.nextLine();
			}
		}
	    int i = 1;
	    while (i <= num_of_players){					// ASKS NAMES FOR THE NUMBER OF PLAYERS INPUT
	        System.out.print("Player " + i + ", what is your name : ");
	        String name = scanObj.next();
	        players.add(new playerProfile(name));		// CREATES PLAYER OBJECTS FOR EACH NAME
	        i++;
	    }
	    players.add(new playerProfile("Dealer"));		// ADD DEALER AS PLAYER LAST
	    return players;
	}

    // DEALS EACH PLAYER 2 CARDS EACH AT THE START OF THE GAME USING 'HIT()' FROM THIS CLASS
    // ULTIMATELY UPDATES PLAYERS CARD LIST AND CARD VALUES IN 'PLAYERPROFILE' OBJECT
    public static void deal(ArrayList<String> cards, ArrayList<Integer> cardsVals, ArrayList<playerProfile> players) {
        int firstCards = 0;
        while (firstCards <2){
            for (playerProfile player : players){
                hit(player, cards, cardsVals);
            }
            firstCards++;
        }
    }

    // CONTROLS THE HAND. GOES ROUND EACH PLAYER ASKING TO 'HIT' UNTIL THEY ARE BUST OR 'STAND'. AN ACE AND 10 OR FACE CARD = BLACKJACK
    // SO PLAYERS SCORE IS AUTOMATICALLY STORED AND NEXT PLAYER IS UP. DEALER AUTIMATICALLY STICKS ON 15+.
    // ALL FINAL HAND VALUES ARE STORED IN EACH 'PLAYERPROFILE' 'HANDVAL' VARIABLE TO BE COMPARED AT THE END.
    public static void playTheGame(ArrayList<playerProfile> players, ArrayList<String> cards, ArrayList<Integer> cardVals, ArrayList<playerProfile> winner, Scanner scanObj){
        ArrayList<String> dealerCards = new ArrayList<String>(players.get(players.size()-1).getHand());
        dealerCards.set(1, "?");
        System.out.println("\n" + players.get(players.size()-1).getName() + "'s Cards = " + dealerCards + "\n");	// SHOWS DEALERS CARDS BEFORE PLAYERS TURNS
        for (playerProfile turn : players){						// EACH PLAYERS TURN IS DONE BY ITERATING OVER 'PLAYERS' ARRAYLIST
        	System.out.print(turn.getName() + "'s cards = " + turn.getHand() + " = " + turn.getHandVal() + turn.getOptHandVal());
        	
            if (turn.getName().equals("Dealer")) {				// DEALER'S TURN ONLY. AUTOMATED 'HIT' TIL 16 OR BUST
            	while (turn.getHandVal() < 16){					// DEALER ALWAYS STANDS ON 16 MINIMUM
            		if (turn.isBlackjack()) {
	            		System.out.println("Blackjack!\n");
	            		saveWinner(turn, winner);				// BLACKJACK MEANS AUTOMATIC STAND AND SAVE
	            		break;
	            	}
	                actions.hit(turn, cards, cardVals);
	                System.out.print(turn.getName() + "'s cards = " + turn.getHand() + " = " + turn.getHandVal() + turn.getOptHandVal());
            	}
            	System.out.println(stand(turn, winner));
            }
            else {
	            while (turn.isValid()){							// CHECKS EACH PLAYERS HAND IS VALID (<21)
	            	if (turn.isBlackjack()) {
	            		System.out.println("Blackjack!\n");
	            		saveWinner(turn, winner);				// BLACKJACK MEANS AUTOMATIC STAND AND SAVE
	            		break;
	            	}
	                System.out.print("OK "+ turn.getName() + ", Hit or Stand (type 'h' for Hit or 's' for Stand) : ");
	                String choice2 = scanObj.next();
	                if (choice2.equalsIgnoreCase("h")){
	                    actions.hit(turn, cards, cardVals);		// HIT WHILE STILl VALID
	                }
	                System.out.print(turn.getName() + "'s cards = " + turn.getHand() + " = " + turn.getHandVal() + turn.getOptHandVal());
	           
	                if (turn.getHandVal() >= 22){
	                    System.out.println("Bad luck! That's you bust!\n");	// BUST
	                    break;
	                }
	                if (choice2.equalsIgnoreCase("s")){
	                    System.out.println(stand(turn, winner));	// STAND WITH CURRENT VALID HAND
	                    break;
	                    
	                }
	                
	            }
            }
        }
    }
    
    // RANDOMLY SELECTS A CARD (USING INDICES) FROM THE DECK TO ALLOCATE TO A PLAYER. SIMULATES TAKING A CARD FROM THE TOP OF
    // A SHUFFLED DECK. 'SETCARDS()' ADDS THE CARD AND ITS VALUE TO THE HAND OF THAT PLAYER IN THE 'PLAYERPROFILE' OBJECT, 
    // RECORDS IF THERE IS AN ACE, THEN THE NEW CARD AND VALUE ARE REMOVED FROM THE 'CARDS' AND 'CARDSVALS'.
    public static void hit(playerProfile plays, ArrayList<String> cards, ArrayList<Integer> cardsVals) {
        Random random = new Random();
        int rnd = random.nextInt(cards.size());
        plays.setHand(cards.get(rnd), cardsVals.get(rnd));
        cards.remove(rnd);
        cardsVals.remove(rnd);
    }

    // CALLED WHEN A PLAYER CHOOSES TO 'STAND' WITH THEIR CURRENT HAND. CHECKS THE VALIDITY OF THE FINAL VALUE (16 OR OVER)
    // IN 'PLAYERPROFILE' AND CONSIDERS THE PRESENCE OF AN ACE THAT ALLOWS STANDING WITH IT REPRESENTING 1 OR 11.
    public static String stand(playerProfile turn, ArrayList<playerProfile> winner) {
        if (turn.getHandVal() < 22 && turn.getHasAce() == 0){
        	saveWinner(turn, winner);
            return "OK, " + turn.getName() + " is standing with " + turn.getHandVal() + "\n";		// SIMPLE HAND <21 WITH NO ACE
        }
        else if (turn.getHandVal() < 22 && (turn.getHandVal() + turn.getHasAce()) > 21) {
        	saveWinner(turn, winner);
            return "OK, " + turn.getName() + " is standing with " + turn.getHandVal() + "\n";		// SIMPLE HAND <21, BUT +ACE MEANS > 21
        }
        else if (turn.getHandVal() < 22 && (turn.getHandVal() + turn.getHasAce()) > turn.getHandVal()) {
        	turn.setHandVal(turn.getHasAce());														// UPDATES 'HANDVAL' TO INCLUDE ACE=11 OPTION
        	saveWinner(turn, winner);
            return "OK, " + turn.getName() + " is standing with " + turn.getHandVal() + "\n";		// STANDING WITH AN +ACE VALUE < 22 TO KEEP HIGHER SCORE 
        }
        else{
        	return "Dealer Bust!\n";
        }
    }
    
    // STORES THE PLAYER(S) WITH THE HIGHEST 'STAND' SCORE. ONLY EVER HAS A SINGLE PLAYER OR PLAYERS WITH THE SAME SCORE.
    // CHECKS THE EXISTING PLAYER(S) STORED IN 'WINNER' ARRAYLIST AGAINST EACH NEW PLAYER CHOOSING 'STAND', WITH
    // REGARDS TO THEIR SCORES. 
    // EG. A NEW PLAYER WITH THE SAME SCORE AS THE EXISTING PLAYER(S) IS ADDED TO THE LIST, A HIGHER SCORING PLAYER 
    // REPLACES ALL EXISTING PLAYERS, AND LOWER SCORING PLAYERS ARE DISCARDED. (FIRST PLAYER IS ALWAYS ADDED) 
    public static void saveWinner(playerProfile turn, ArrayList<playerProfile> winner) {
    	if (winner.isEmpty()) {
    		winner.add(turn);			// STORES FIRST PLAYER EACH TIME
    	}
    	else if (turn.getHandVal().equals(winner.get(0).getHandVal())) {
    		winner.add(turn);			// STORES ADDITIONAL PLAYERS WITH SAME SCORE
    	}
    	else if (turn.getHandVal() > winner.get(0).getHandVal()) {
    		winner.removeAll(winner);	// EMPTIES LIST
    		winner.add(turn);			// NEW ENTRY WITH NEW HIGH SCORE
    	}
    }

    // 'WINNER' ARRAYLIST IS CHECKED TO SEE WHO WON WITH 4 SCENARIOS......
    // IF THEY DEALER IS THE LAST, OR ONLY, PLAYER IN THE LIST, NO ONE WINS. (NO ONE BEAT THE DEALER....)
    // SINGLE PLAYER (NON-DEALER) STORED MEANS 1 WINNER. STRING RETURNED TO SAY AS MUCH
    // >1 PLAYER STORED AND 'STRINGBUILDER' IS CALLED TO BUILD A STRING TO INCLUDE ALL NAMES WITH THE SAME SCORE.
    // OR ELSE, ALL PLAYERS ARE BUST!!
    public static String whoWon(ArrayList<playerProfile> winner) {
    	// 
    	if (winner.size() != 0 && winner.get(winner.size() -1).getName().equals("Dealer")) {		// CHECKS IF DEALER IS INCLUDED IN LIST, THEREFORE NO PLAYERS WIN
    		if (winner.get(winner.size() -1).getHandVal() == 99) {									// SCORE OF 99 = BLACKJACK TO BETTER 21 WITH 3+ CARDS
    			return "\n\nNo one wins, Dealer has highest hand with Blackjack!";
    		}
    		else {
    			return "\n\nNo one wins, Dealer has highest hand with " + winner.get(0).getHandVal();						// DEALER WINS WITH HIGHEST, OR JOINT HIGHEST SCORE
    		}
        }
    	else if (winner.size() == 1 && !winner.get(winner.size() -1).getName().equals("Dealer")) {	// CHECKS IS DEALER IS *NOT* INCLUDED IN LIST
    		if (winner.get(0).getHandVal() == 99) {													// SCORE OF 99 = BLACKJACK TO BETTER 21 WITH 3+ CARDS
    			return "\n\nAnd the winner is........" + winner.get(0).getName() + " with Blackjack!";
    		}
    		else {
    			return "\n\nAnd the winner is........" + winner.get(0).getName() + " with " + winner.get(0).getHandVal();	// SINGLE HIGHEST NON-DEALER, NON-BLACKJACK SCORING PLAYER
    		}
    		
        }
    	else if (winner.size() > 1) {									// BUILDS STRING OF MULTIPLE WINNING PLAYERS WITH SAME SCORE
        	StringBuilder str = new StringBuilder();
        	str.append("\n\nAnd the winners are........");
        	for (playerProfile plays : winner) {
        		str.append(plays.getName() + " and ");					// ALL WINNERS NAMES ARE RECORDED
        	}
        	String removeAnd = str.substring(0, str.length() - 4);
        	
        	if (winner.get(0).getHandVal() == 99) {						// SCORE OF 99 = BLACKJACK TO BETTER 21 WITH 3+ CARDS
        		String finalWinner = removeAnd + "with Blackjack";		// BLACKJACK DECLARED FOR ALL WINNING PLAYERS
        		return finalWinner;
    		}
        	else {
	        	String finalWinner = removeAnd + "with " + winner.get(0).getHandVal();	// SINGLE SCORE PLAYERS SHARE IS ADDED
	        	return finalWinner;	
        	}
        }
    	else {
    		return "No Winner, everyone is bust!";									// EVERYONE IS BUST
    	}
    }
      
}