package blackjack;

import java.util.ArrayList;

/* MODELS PLAYER. PROVIDES NAME, CURRENT CARDS, TOTAL CURRENT VALUE OF CARDS AND PRESENCE OF AN ACE.
*  GETTERS AND SETTERS FOR ALL 4 VARIABLES AS FOLLOWS :
*  
*  player_name = CONSTRUCTOR SETS NAME, GETTER RETURNS NAME
*  hand = 'SETHAND()' ADDS CARDS TO LIST, GETTER RETURNS LIST
*  handVal = SETHAND()' ALSO UPDATES HAND VALUE, GETTER RETURNS INTEGER VALUE
*  hasAce = SETTER CHANGES 0 TO 10 IF ANY DEALT CARD IS AN ACE (CALLED FROM 'SETHAND()'). GETTER RETURNS INTEGER VALUE
*  
*  IF AN ACE ALLOWS 2 VALUES < 21 TO BE CONSIDERED, 'GETOPTVAL()' RETURNS SECOND VALUE < 21 WHERE ACE = 11.
*  'ISVALID()' IS USED TO ALLOW PLAYERS TO CONTINUE HITTING IF HAND VALUE IS < 21.
*/ 

public class playerProfile {

    // VARIABLES; PLAYERS NAME, CURRENT HAND, VALUE OF CURRENT HAND & ADDITIONAL VALUE FOR ACE IF IN HAND.
    private String player_name;
    private ArrayList<String> hand = new ArrayList<String>();
    private int handVal = 0;
    private int hasAce = 0;

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
        handVal += cardVal;
        setHasAce();
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
        //else if ((handVal + hasAce) > 21 && handVal < 21){
        //    return handVal;
        //}
        else{
        	return handVal;
        }
    }
    
    // CALLED FROM 'SETHAND()'. IF ANY CARDS DEALT OR HIT ARE AN ACE THE ADDITIONAL POSSIBLE VALUE IS SET
    // AGAINST 'HASACE' VARIABLE FOR USE WHEN EVALUATING RUNNING AND STANDING TOTALS.
    public void setHasAce() {
    	for (int i=0; i<hand.size(); i++) {
            if ("Ace".equals(hand.get(i))) { //<-- change your condition to this
                hasAce = 10;
            }
        }
    }

    // 'HASACE' VARIABLE IS RETURNED TO CHECK DURING THAT PLAYERS TURN IF THEY HAVE AN OPTIONAL VALUE,
    // OR WHEN CALLING 'STAND()' OR 'WHOWON()' TO CHECK IF AN ACE COUNTS FOR THIER FINAL SCORE
    public Integer getHasAce(){
        return hasAce;
    }
    
    // CALLED WHEN PRINTING PLAYERS CURRENT HAND VALUE. ADDS AN ALTERNATIVE VALUE IF THE PRESENCE OF AN ACE
    // MEANS THE PLAYER CAN USE 2 DIFFERENT VALUES BOTH BELOW 21
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
    
    // CHECK IF PLAYER OR DEALERS INTIAL HAND IS 21. IF SO, BLACKJACK IS DECALRED, 21 STORED AND NEXT PERSON IS UP. 
    public boolean isBlackjack() {
    	if (getHandVal() == 21 && getHand().size() == 2){
            return true;
        }
        else{
        	return false;
        }
    }
    
}