package blackjack;

import java.util.ArrayList;
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
* 		DEALER 'HITS' UNTIL SHE HAS > 16 & <22, THEN AUTOMATICALLY STANDS. CAN GO BUST TOO.
* 4. 'HIT()'
* 		CALLED DURING 'PLAYTHEGAME()' TO DISTRUBUTE A SINGLE CARD AND UPDATES PLAYERS HAND AND HAND VLAUE
* 5. 'STAND()'
* 		CALLED DURING 'PLAYTHEGAME()' TO ALLOW PLAYER TO STICK WITH THE VALID HAND THEY HAVE
* 6. 'WHOWON()'
* 		CHECKS WHO WON BY COMPARING EACH PAYERS FINAL TOTAL CARD VALUES, TAKING ACES INTO ACCOUNT, AND RETURS A NAME
*/

public class actions {
	
	// ASKS NUMBER OF PLAYERS, REQUESTS NAMES AND RETURNS ARRAYLIST OF 'PLAYERPROFILE' OBJECTS PER PLAYER
	public static ArrayList<playerProfile> establishGame(Scanner scanObj, ArrayList<playerProfile> players){
	    System.out.print("Welcome to the Blackjack table. How many players are there : ");
	    int num_of_players = scanObj.nextInt();
	    System.out.println("OK, we have " + num_of_players + " players!");
	    int i = 1;
	    while (i <= num_of_players){
	        System.out.print("Player " + i + ", what is your name : ");
	        String name = scanObj.next();
	        players.add(new playerProfile(name));
	        i++;
	    }
	    players.add(new playerProfile("Dealer"));
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
    public static void playTheGame(ArrayList<playerProfile> players, ArrayList<String> cards, ArrayList<Integer> cardVals, Scanner scanObj){
        ArrayList<String> dealerCards = new ArrayList<String>(players.get(players.size()-1).getHand());
        dealerCards.set(1, "?");
        System.out.println("\n" + players.get(players.size()-1).getName() + "'s Cards = " + dealerCards + "\n");
        for (playerProfile turn : players){
        	System.out.print(turn.getName() + "'s cards = " + turn.getHand() + " = " + turn.getHandVal() + turn.getOptHandVal());
        	
            if (turn.getName().equals("Dealer")) {
            	while (turn.getHandVal() < 16){
            		if (turn.isBlackjack()) {
	            		System.out.println("Blackjack!\n");
	            		break;
	            	}
	                actions.hit(turn, cards, cardVals);
	                System.out.print(turn.getName() + "'s cards = " + turn.getHand() + " = " + turn.getHandVal() + turn.getOptHandVal());
	                
            	}
            	System.out.println(stand(turn));
            }
            else {
	            while (turn.isValid()){
	            	if (turn.isBlackjack()) {
	            		System.out.println("Blackjack!\n");
	            		break;
	            	}
	                System.out.print("OK "+ turn.getName() + ", Hit or Stand (type 'h' for Hit or 's' for Stand) : ");
	                String choice2 = scanObj.next();
	                if (choice2.equalsIgnoreCase("h")){
	                    actions.hit(turn, cards, cardVals);
	                }
	                System.out.print(turn.getName() + "'s cards = " + turn.getHand() + " = " + turn.getHandVal() + turn.getOptHandVal());
	           
	                if (turn.getHandVal() >= 22){
	                    System.out.println("Bad luck! That's you bust!\n");
	                    break;
	                }
	                if (choice2.equalsIgnoreCase("s")){
	                    System.out.println(stand(turn));
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
    public static String stand(playerProfile turn) {
        if (turn.getHandVal() < 22 && turn.getHasAce() == 0){
            return "OK, " + turn.getName() + " is standing with " + turn.getHandVal() + "\n";
        }
        else if (turn.getHandVal() < 22 && (turn.getHandVal() + turn.getHasAce()) > 21) {
            return "OK, " + turn.getName() + " is standing with " + turn.getHandVal() + "\n";
        }
        else if (turn.getHandVal() < 22 && (turn.getHandVal() + turn.getHasAce()) < 22) {
            return "OK, " + turn.getName() + " is standing with " + (turn.getHandVal() + turn.getHasAce()) + "\n";
        }
        else{
        	return "Dealer Bust!\n";
        }
    }

    // EACH PLAYERS FINAL HAND VALUES ARE CHECKED AND THE HIGHEST STORED IN 'WINNERVALUE' BEFORE RETURNING THAT PERSON'S
    // NAME AS THE WINNER. USES 'HANDVAL' AND A COMBINED 'HANDVAL' & 'HASACE' DEPENDING ON AN ACE BEING PRESENT. 
    // (NEEDS ALTERED TO RETURN MUTIPLE WINNERS IF DEALER DOES NOT GET BLACKJACK!)
    public static String whoWon(ArrayList<playerProfile> players) {
        int winnerValue = 0;
        String winnerName = "No one";
        for (playerProfile player : players){
        	
        	Integer handPLUSace = player.getHandVal() + player.getHasAce();
        	
        	if (player.getHandVal() > winnerValue && player.getHandVal() < 22 && (handPLUSace > 21 || player.getHasAce() == 0)){
                winnerValue = player.getHandVal();
                winnerName = player.getName();
            }
            else if (handPLUSace > winnerValue && (handPLUSace == 21 || handPLUSace < 22)){
                winnerValue = handPLUSace;
                winnerName = player.getName();
            }
            System.out.println("current winner = " + winnerName);
            System.out.println("current high score = " + winnerValue);
        }
        return winnerName;
    }
   
}