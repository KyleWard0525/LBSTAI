/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lbstai;

/**
 * This class is used to encode the target so that it can be 
 * passed to the AI. It is also used to decode the AI's output.
 * @author Kyle Ward
 */
public class Encoder {
    
    /**
     * Integer encode the alphabet then encode the target
     * with respect to the encoded alphabet
     * @param target the target text
     * @param alphabet the collection of text the AI can use
     * @return encodedTarget
     */
    public int[] encodeTarget(String target, String alphabet)
    {
        int[] encodedTarget = new int[target.split("").length]; 
        int[] encodedAlphabet = new int[alphabet.split("").length];
        String[] alphabetArr = alphabet.split("");
        String[] targetArr = target.split("");
        
        //Encode alphabet
        for(int i = 0; i < alphabetArr.length; i++)
        {
            encodedAlphabet[i] = i;
        }
        
        //Encode target
        for(int i = 0; i < encodedTarget.length; i++)
        {
            int encodedLetter = encodedAlphabet[alphabet.indexOf(targetArr[i])];
            encodedTarget[i] = encodedLetter;
        }
        return encodedTarget;
    }
    
    /**
     * This method is used to integer encode the given alphabet
     * @param alphabet
     * @return encodedAlphabet
     */
    public int[] encodeAlphabet(String alphabet)
    {
        int[] encodedAlphabet = new int[alphabet.split("").length];
        
        //Encode alphabet
        for(int i = 0; i < encodedAlphabet.length; i++)
        {
            encodedAlphabet[i] = i;
        }
        return encodedAlphabet;
    }
    
    /**
     * Decodes the AI's output
     * @param member
     * @param alphabet
     * @return output
     */
    public String decode(int[] member, String alphabet)
    {
        String output = "";
        String[] alphabetArr = alphabet.split("");
        
        //Loop through the member and add the letters to the output
        for(int letter : member)
        {
            output += alphabetArr[letter]; 
        }
        return output;
    }
}
