/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lbstai;

import java.util.*;
/**
 * This AI was created to demonstrate how an AI can teach itself
 * to write a paragraph in any language using a genetic algorithm.
 * @author Kyle Ward
 */
public class LBSTAI {

    public static final String alphabet = "!\"#$%&\\' ()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\\n";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String target = "The quick brown fox jumps over the lazy dogs"; //Placeholder sentece
        Encoder encoder = new Encoder();
        Population ai = new Population();
        int[] encodedTarget = encoder.encodeTarget(target, alphabet);
        int[] encodedAlphabet = encoder.encodeAlphabet(alphabet);
        int[] testMember = ai.createMember(encodedTarget, encodedAlphabet);
        int[][] population = ai.createPopulation(100, encodedTarget, encodedAlphabet);
        String testOutput = encoder.decode(testMember, alphabet);
        int fitness = ai.fitness(testMember, encodedTarget);
        int avgFit = ai.averageFitness(population, encodedTarget);
        int counter = 0;
        
        
        System.out.println(" Starting average fitness for population (0 is perfect): " + avgFit);
        
        //Train the AI
        while(ai.averageFitness(population, encodedTarget) > 0)
        {
            counter++;
            population = ai.getNextGeneration(population, 0.1, encodedTarget, encodedAlphabet);
            avgFit = ai.averageFitness(population, encodedTarget);
            int[] bestMember = ai.getBestMember(population, encodedTarget);
            String bestMemberOutput = encoder.decode(bestMember, alphabet);
            
            if(counter % 10000 == 0)
            {
                System.out.println("Generation " + counter + ": ");
                System.out.println("Average fitness (0 is perfect): " + avgFit);
                System.out.println("Target: " + target);
                System.out.println("Best output: " + bestMemberOutput);
            }
        }
        
    }
    
}
