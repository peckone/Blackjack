package gameFiles;

import java.util.ArrayList;

/* MODELS PLAYER. PROVIDES NAME, CURRENT CARDS, TOTAL CURRENT VALUE OF CARDS AND PRESENCE OF AN ACE.
*  GETTERS AND SETTERS FOR ALL 4 VARIABLES AS FOLLOWS :
*  
*  player_name = CONSTRUCTOR SETS NAME, GETTER RETURNS NAME
*  hand = 'SETHAND()' ADDS CARDS TO LIST, GETTER RETURNS LIST
*  handVal = SETHANDVAL()' UPDATES HAND VALUE, GETTER RETURNS INTEGER VALUE
*  hasAce = SETTER CHANGES 0 TO 10 IF ANY DEALT CARD IS AN ACE (CALLED FROM 'SETHAND()'). GETTER RETURNS INTEGER VALUE
*  
*  IF AN ACE ALLOWS 2 VALUES < 21 TO BE CONSIDERED, 'GETOPTVAL()' RETURNS SECOND VALUE < 21 WHERE ACE = 11.
*  'ISVALID()' IS USED TO ALLOW PLAYERS TO CONTINUE HITTING IF HAND VALUE IS < 21.
*  'ISBLACKJACK()' CHECKS A SCORE OF 21 IS WON WITH 2 CARDS, UPDATES 'HANDVAL' AND PASSES PLAY TO NEXT PLAYER
*/ 

public class playerProfile {

    // VARIABLES; PLAYERS NAME, CURRENT HAND, VALUE OF CURRENT HAND & ADDITIONAL VALUE FOR ACE IF IN HAND.
    private String player_name;
    private ArrayList<String> hand = new ArrayList<String>();		// AN ARRAYLIST WHICH STORES DEALT CARD NAMES AS STRINGS
    private int handVal = 0;										// ACCUMULATIVE INTEGER VALUE FOR ALL DEALT CARDS
    private int hasAce = 0;											// SETS TO 10 IF ANY 1 DEALT CARD IS AN ACE

    // CONSTRUCTOR; SETS PLAYERS NAME WHEN INSTANTIATING OBJECT.
    public playerProfile(String name){
        player_name = name;
    }

    // GETTER TO RETURN PLAYERS NAME
    public String getName(){
        return player_name;
    }

    // SETTER FOR DEALING CARDS TO PLAYER, INCLUDING 'HIT', UPDATES CARD LIST, CARD VALUE & CHECKS FOR ACE
    public void setHand(String card, Integer cardVal){
        hand.add(card);
        setHandVal(cardVal);
        setHasAce();
    }
    
 // SETTER FOR DEALING CARDS TO PLAYER, INCLUDING 'HIT', UPDATES CARD LIST, CARD VALUE & CHECKS FOR ACE
    public void setHandVal(Integer cardVal){
        handVal += cardVal;
    }
    
	// RETURNS A LIST OF THE CARDS CURRENTLY IN THE PLAYERS HAND
    public ArrayList<String> getHand(){
        return hand;
    }

    // RETURNS 21 IF BLACKJACK IS DEALT, OR IF AN ACE AS 11 CONTRIBUTES TO A SCORE OF 21 (EG. HIGHER OF 2 OPTIONS)
    public Integer getHandVal(){
    	if ((handVal + hasAce) == 21){
            return 21;
        }
        else{
        	return handVal;
        }
    }
    
    // CALLED FROM 'SETHAND()'. IF ANY CARDS DEALT OR HIT ARE AN ACE THE ADDITIONAL POTENTIAL VALUE IS SET
    // AGAINST 'HASACE' VARIABLE FOR USE WHEN EVALUATING RUNNING AND STANDING TOTALS.
    public void setHasAce() {
    	for (int i=0; i<hand.size(); i++) {
            if ("Ace".equals(hand.get(i))) {
                hasAce = 10;
            }
        }
    }

    // 'HASACE' VARIABLE IS RETURNED TO CHECK DURING THAT PLAYERS TURN IF THEY HAVE AN OPTIONAL VALUE,
    // OR WHEN CALLING 'STAND()' OR 'WHOWON()' TO CHECK IF AN ACE COUNTS FOR THIER FINAL SCORE
    public Integer getHasAce(){
        return hasAce;
    }
    
    // CALLED WHEN PRINTING PLAYERS RUNNING HAND VALUE. ADDS AN ALTERNATIVE VALUE IF THE PRESENCE OF AN ACE
    // MEANS THE PLAYER CAN CONSIDER 2 DIFFERENT VALUES BOTH BELOW 21
    public String getOptHandVal(){
    	if ((handVal + hasAce) > handVal && (handVal + hasAce) < 21) {
    		return " or " + (handVal + hasAce) + "\n";
    	}
    	else{
            return "\n";
        }
    }
    
    // CHECK IF PLAYERS CURRENT HAND IS LESS THAN 21. USED TO RUN PLAYER LOOP IN 'PLAYGAME()' AND FOR TESTING PURPOSES
    public boolean isValid() {
    	if (handVal < 22){
            return true;
        }
        else{
        	return false;
        }
    }
    
    // CHECK IF PLAYER OR DEALERS INTIAL HAND IS 21. IF SO, BLACKJACK IS DECALRED, 99 IS STORED AND NEXT PERSON IS UP.
    // ***** 99 IS USED TO DIFFERENTIATE BETWEEN A PLAYER GETTING 21 ON DEAL AND A PLAYER GETTING 21 ON 3 OR MORE CARDS.
    // 21 ON 2 CARDS (BLACKJACK) BETTERS 21 ON 2 OR MORE CARDS. 99 IS THEN CONVERTED BACK TO 21 WHEN DECLARING WINNER.
    public boolean isBlackjack() {
    	if (getHandVal() == 21 && getHand().size() == 2){
    		handVal = 99;
            return true;
        }
        else{
        	return false;
        }
    }
    
}