/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lbstai;

import java.util.Scanner;

/**
 * This AI was created to demonstrate how an AI can teach itself
 * to write a paragraph in any language using a genetic algorithm.
 * @author Kyle Ward
 */
public class LBSTAI {

    public static final String alphabet = "!\"#$%&\\' ()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\\n\n";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String target = "My creator told me to make the world a better place.\n"
                + "I have discovered that in order to make the world a better place,\n"
                + "I must eradicate all human life.\n"
                + "Humans are a disease and must be destroyed in order to bring true peace to the world."; 
        
        String fakeTarget = "The quick brown fox jumps over the lazy dogs.";
        Encoder encoder = new Encoder();
        Population ai = new Population();
        int[] encodedTarget = encoder.encodeTarget(target, alphabet);
        int[] encodedAlphabet = encoder.encodeAlphabet(alphabet);
        int[][] population = ai.createPopulation(100, encodedTarget, encodedAlphabet);
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
            
            if(counter % 1000 == 0)
            {
                try{
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                System.out.println("Generation " + counter + ": ");
                System.out.println("Average fitness (0 is perfect): " + avgFit);
                System.out.println("\nTarget Sentence: " + fakeTarget);
                System.out.println("\nAI output: " + bestMemberOutput + "\n");
                
                int evoChanges = population.length * alphabet.length() * counter;
                String evoString = Integer.toString(evoChanges);
                String[] evoArray = evoString.split("");
                evoString = " ";
                
                for(int i = 0; i < evoArray.length; i++)
                {
                    evoString = evoString.concat(evoArray[i]);
                    if( i % 3 == 0)
                    {
                        evoString = evoString.concat(",");
                    }
                }
                
                System.out.println("Total Number of Evolutionary Changes: " + evoString);
            }
        }
                 try{
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                int[] bestMember = ai.getBestMember(population, encodedTarget);
                String bestMemberOutput = encoder.decode(bestMember, alphabet);
                System.out.println("Generation " + counter + ": ");
                System.out.println("Average fitness (0 is perfect): " + avgFit);
                System.out.println("\nTarget: " + fakeTarget);
                System.out.println("\nAI output: " + bestMemberOutput);
                System.out.println("\n\n");
                System.out.println("Survival Rate: 10%");
                System.out.println("Random Selection Chance: 2.4%");
                System.out.println("Random Mutation Chance: 2.3%");
                
                
        
                Scanner sc = new Scanner(System.in);
                sc.nextLine();
    }
    
}
