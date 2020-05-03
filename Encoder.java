/**
 * @author Arran Stewart
 *
 */
import java.util.*;
public class Encoder {

    public static final int ALPHABET_SIZE = 26;
    private final Deck deck;
    
    /** Create an Encoder using the default ordering
     * of a deck of cards.
     * 
     */
    public Encoder() {
        deck = new Deck();
    }

    /** Create an Encoder using a particular deck
     * of cards to generate the key.
     * 
     */
    public Encoder(Deck d) {
        deck = d;
    }
    
    /** Remove all non-alphabetic characters from a string,
     * and convert all alphabetic characters to upper-case.
     * 
     * @param s Input string
     * @return Sanitized string
     */
    public static String sanitize(String s) {
        return s.replaceAll("[^\\p{Alpha}]","").toUpperCase();
    }
    
    /** Return the position in the alphabet of an uppercase
     * character, starting from 1 (i.e., charToInt('A') returns 1,
     * charToInt('B') returns 2, and so on).
     * 
     * @param c Character to convert to an int
     * @return Result of conversion
     */
    public static int charToInt(char c) {
        List<Character> alphabets = new ArrayList<Character>();
        for(char charr : ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray())){
            alphabets.add(charr);
        }
        return 1+(alphabets.indexOf(c));
    }
    
    /** Given a position in the alphabet (starting from 1),
     * return the character at that position. 
     * (i.e. intToChar(1) returns 'A', intToChar(2) returns 'B',
     * and so on). If a number larger than 26 is passed in,
     * subtract 26 from it before applying this conversion.
     * 
     * @param c int to convert to a character
     * @return Result of conversion
     */
    public static char intToChar(int i) {
        List<Character> alphabets = new ArrayList<Character>();
        for(char charr : ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray())){
            alphabets.add(charr);
        }
        int k;
        if((i%26)==0) k=26;
        else k=i%26;
        return alphabets.get(k-1);
    }
    
    /** Encode a character (inputChar) using a character from the keystream
     * (keyChar).
     * 
     * To do this, firstly convert both characters into integers,
     * as described in charToInt.
     * 
     * Then add the numbers together. If the result is greater than 26,
     * subtract 26 from it; then convert that result back to a character.
     * 
     * @param inputChar Character from message
     * @param keyChar Character from keystream
     * @return Encoded character
     */
    public static char encodeChar(char inputChar, char keyChar) {
        int input = charToInt(inputChar);
        int key = charToInt(keyChar);
        int sum = input+key;
        
        if(sum>26) sum=sum-26;
        return intToChar(sum);
    }


    /** Decode a character (inputChar) from an encrypted message using a character
     * from the keystream (keyChar).
     * 
     * Convert both characters to integers, as described for
     * charToInt. If inputChar is less than or equal to keyChar,
     * add 26 to it. Then subtract keyChar from inputChar,
     * and convert the result to a character.
     * 
     * @param inputChar Character from an encrypted message
     * @param keyChar Character from keystream
     * @return Decoded character
     */
    public static char decodeChar(char inputChar, char keyChar) {
        int input = charToInt(inputChar);
        int key = charToInt(keyChar);
        
        if(input<=key) input = input +26;
        int decodedChar = input - key;
        return intToChar(decodedChar);
    }
    
    /** Encode the string inputText using the keystream characters
     * in keyChars, by repeatedly calling encodeChar.
     * 
     * @param inputText Message text to encode
     * @param keyChars Characters from keystream
     * @return Encoded message
     */
    public static String encodeString(String inputText, String keyChars) {
        char[] inputArray = inputText.toCharArray();
        char[] keyArray = keyChars.toCharArray();
        String output = "";
        for(int i=0; i<inputArray.length;i++){
            output = output + encodeChar(inputArray[i],keyArray[i]);
        }
        
        return output;
    }
    
    /** Decode the string inputText using the keystream characters
     * in keyChars, by repeatedly calling decodeChar.
     * 
     * @param inputText Encoded text which needs to be decoded
     * @param keyChars Characters from keystream
     * @return Decoded message
     */
    public static String decodeString(String inputText, String keyChars) {
        inputText = sanitize(inputText);
        keyChars = sanitize(keyChars);
        
        char[] inputArray = inputText.toCharArray();
        char[] keyArray = keyChars.toCharArray();
        String output = "";
        for(int i=0; i<inputArray.length;i++){
            output = output + decodeChar(inputArray[i],keyArray[i]);
        }
        
        return output;
    }
    
    
    /** Apply the Pontoon algorithm to generate the
     * next character in the *keystream*. The character
     * returned will depend on the state of the "deck"
     * instance variable when the method is called.
     * 
     * @return A keystream character
     */
    public char nextKeyStreamChar() {
        int output = 0;
        while(true){
        deck.shiftDownOne(deck.findCard(53));
        deck.shiftDownOne(deck.findCard(54));
        deck.shiftDownOne(deck.findCard(54));
        int joker2 = deck.findCard(54);
        int joker1 = deck.findCard(53);
        deck.tripleCut(joker1,joker2);
        if(deck.getCard(53) == 54){
            deck.countCut(53);
        }
        if(deck.getCard(0) == 54){
            output = deck.findCard(53);
        }
        else{
            output = deck.getCard(0);
        }
        
        if(output == 54||output == 53){
            nextKeyStreamChar();
        }
        else{
            break;
        }
        System.out.println(output);
    }
    return intToChar(output);
    }
    
    /** Encrypt a string, using the deck to generate
     * *keystream* characters which can be passed
     * to encodeChar.
     * 
     * @param inputString The string to encrypt
     * @return The result of encryption
     */
    public String encrypt(String inputString) {
        
        return "";
    }
    
    /** Decrypt a string, using the deck to generate
     * *keystream* characters which can be passed
     * to decodeChar.
     * 
     * @param inputString The string to decrypt
     * @return The result of decryption
     */
    public String decrypt(String inputString) {
        // TODO: fill in method body
        return "";
    }
}
