/** Represents a deck of cards, and operations that can be performed
 * on it.
 * 
 * @22254937 - Harwinddranath Muralitheran 
 *
 */

import java.util.*;
import java.lang.*;
import java.util.stream.Stream;
import java.util.stream.IntStream;
public class Deck {
    /** The size of a deck of cards. Four suits of thirteen cards,
     * plus two jokers.
     */
    public final static int DEFAULT_DECK_SIZE = 13 * 4 + 2;
    
    /** The value given to the first joker
     **/
    public final static int JOKER1 = 13 * 4 + 1;
    
    /** The value given to the second joker
     **/
    public final static int JOKER2 = 13 * 4 + 2;
    
    // Array holding ints representing the cards. 
    // Card values should start from 1, and each int
    // should be unique within the array.
    private int[] cards;
    
    /** Create a deck of cards in the default order.
     */
    public Deck() {
        cards = new int[DEFAULT_DECK_SIZE];
        for (int i = 0; i < DEFAULT_DECK_SIZE; i++) {
            cards[i] = i + 1;
        }
    }
    
    /** Returns true when all values of the array arr are
     * different to each other; returns false otherwise.
     * 
     * @param arr An array of int values to be checked
     * @return Whether all values in the array are different
     */
    public static boolean allDifferent(int[] arr) {
        Set<Integer> foundNumbers = new HashSet<>();
        for (int num : arr){
            if(foundNumbers.contains(num)){
                return false;
            }
            foundNumbers.add(num);
        }
        return true;
    }
    

    /** Construct a deck of cards from a String of comma-separated values.
     * 
     * @param s A string, consisting of comma-separated integers.
     */
    public Deck(String inputString) {
        if (inputString.equals("")) {
            cards = new int[0];
            return;
        }
        
        String[] strings = inputString.split(",");
        
        cards = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            cards[i] = Integer.parseInt( strings[i] );
        }
        validateCards();
    }
    
    /** This method should check whether all the
     * values in the "cards" instance variable
     * are different.
     *    If they are not, it should throw an
     * IllegalArgumentException exception.
     * 
     */
    private void validateCards()throws IllegalArgumentException {
       boolean validation;
       validation = allDifferent(cards);
       if(!validation){
           throw new IllegalArgumentException("Card not valid.");
        }
    }
    
   
    public int size() {
        return cards.length;
    }
    
    public int getCard(int idx) {
        return cards[idx];
    }
    
    /** Shuffles elements of an array into a random permutation.
     * 
     * @param arr Array to be shuffled.
     */
    public void shuffleArray(int[] arr) {
        Random r = new Random();
        for(int i=0; i<50; i++){
            cards[r.nextInt(DEFAULT_DECK_SIZE-1)] = cards[r.nextInt(DEFAULT_DECK_SIZE-1)];
            shiftDownOne(r.nextInt(DEFAULT_DECK_SIZE-1));
            tripleCut(r.nextInt(DEFAULT_DECK_SIZE-1),r.nextInt(DEFAULT_DECK_SIZE-1));
        }
        
    }
    
    public void shuffle() {
        shuffleArray(cards);
    }
    
    /** Find the position in the deck of the card
     * with value cardVal.
     * 
     * @param cardVal The card to find
     * @return The position of the card to find, or -1
     *     if it wasn't in the deck. 
     */
    public int findCard(int cardVal) {
        //return Arrays.asList(cards).indexOf(cardVal);
        for(int i=0; i<cards.length;i++){
            if(cards[i] == cardVal) return i;
        }
        return -1;
    }
    
    
    /** Shift a particular card down the deck by one place,
     * and if this would take you past the end of the deck,
     * treat the end of the deck as joining onto the beginning.
     * 
     * In other words: for any card except the last card, 
     * the card is swapped with the card immediately 
     * after it. For the last card: it gets inserted after the
     * first card, and the second card and all subsequent cards
     * are "moved down one".  
     * 
     * If the argument passed is not found in the deck,
     * this method should throw an IllegalArgumentException 
     * exception.
     * 
     * @param cardVal The value of the card to be shifted.
     */
    public void shiftDownOne(int cardVal) {
        try{
        int indexOfCard = findCard(cardVal);
        if(indexOfCard == (cards.length-1)){
            int last = cards[cards.length-1];
            System.arraycopy(cards,0,cards,1,cards.length-1);
            cards[0] = last;
            cards[1] = cards[0];
            cards[0] = cardVal;
        }
        else{
            cards[indexOfCard] = cards[indexOfCard+1];
            cards[indexOfCard+1] = cardVal;
        }
         }
        catch(IllegalArgumentException e) {
        System.err.println("Caught IllegalArgumentException: " + e.getMessage());
       }
    }
    
    /** Perform a "triple cut": Given the positions of 2 cards in the deck,
     * divide the deck into three "chunks": 
     *  chunk "A", the "top" - cards before either position
     *  chunk "B", the "middle" - cards lying between the 2 positions
     *  chunk "C", the "bottom" - cards after either position.
     *  
     *  Reorder the deck by swapping the top and bottom chunks
     *  (chunks "A" and "C").
     *  
     * @param pos1 Position of a "fixed" card, counting from 0.
     * @param pos2 Position of another "fixed" card, counting from 0.
     */
    public void tripleCut(int pos1, int pos2) {
        int[] top = Arrays.copyOfRange(cards, 0, pos1);
        
        int[] middle = Arrays.copyOfRange(cards, pos1, pos2+1);
        
        int[] bottom = Arrays.copyOfRange(cards, pos2+1, cards.length);
        
        int[] middleAndBottom = IntStream.concat(Arrays.stream(bottom),Arrays.stream(middle)).toArray();
        int[] newCards = IntStream.concat(Arrays.stream(middleAndBottom),Arrays.stream(top)).toArray();
      
        cards = newCards;
    }
    
    /** Perform a "count cut". Let n be a number of cards.
     * Remove n cards from the top of the deck, and place them
     * just above the last card.
     * 
     * @param numCards
     */
    public void countCut(int numCards) {
        int[] nFromTop = Arrays.copyOfRange(cards, 0, numCards);
        int[] belowTopAndBeforeLast = Arrays.copyOfRange(cards, numCards, cards.length-1);
        int[] last = {cards[cards.length-1]};
        
        
        int[] newCards = IntStream.concat(Arrays.stream(belowTopAndBeforeLast),Arrays.stream(nFromTop)).toArray();
        cards = IntStream.concat(Arrays.stream(newCards),Arrays.stream(last)).toArray();
    }

}
