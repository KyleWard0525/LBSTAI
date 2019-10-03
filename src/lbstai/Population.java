/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lbstai;

import java.util.*;

/**
 * This class contains the "AI" and its methods that are used
 * to interpret and process data and then formulate an output
 * using a genetic algorithm.
 * 
 * @author Kyle Ward
 */
public class Population {
    
    /**
     * This method creates a new member of the population and 
     * populates it with random integers or "letters" of the
     * alphabet.
     * 
     * @param encodedTarget
     * @param encodedAlphabet
     * @return member 
     */
    public int[] createMember(int[] encodedTarget, int[] encodedAlphabet)
    {
        int[] member = new int[encodedTarget.length];
        Random rand = new Random();
        
        //Populate member with random letters
        for(int i = 0; i < member.length; i++)
        {
            int randomLetter = rand.nextInt(encodedAlphabet.length);
            member[i] = randomLetter;
        }
        return member;
    }
    
    /**
     * This method creates the initial population of members
     * 
     * @param numMembers
     * @param encodedTarget
     * @param encodedAlphabet
     * @return 
     */
    public int[][] createPopulation(int numMembers, int[] encodedTarget, int[] encodedAlphabet)
    {
        int[][] population = new int[numMembers][encodedTarget.length];
        
        //Fill population with members
        for(int i = 0; i < numMembers; i++)
        {
            population[i] = createMember(encodedTarget, encodedAlphabet);
        }
        return population;
    }
    
    /**
     * This method calculates the fitness (how well the member did)
     * relative to the target
     * @param member
     * @param encodedTarget
     * @return fitness
     */
    public int fitness(int[] member, int[] encodedTarget)
    {
        int fitness = 0;
        
        //Loop through the member and calculate its fitness
        for(int i = 0; i < member.length; i++)
        {
            fitness += Math.abs(encodedTarget[i] - member[i]);
        }
        return fitness;
    }
    
    /**
     * This method calculates the average fitness of the 
     * population
     * @param population
     * @param encodedTarget
     * @return avgFitness
     */
    public int averageFitness(int[][] population, int[] encodedTarget)
    {
        int avgFitness = 0;
        
        //Loop through the population and calculate the average fitness
        for(int[] member : population)
        {
            avgFitness += fitness(member, encodedTarget);
        }
        return avgFitness / population.length;
    }
    
    /**
     * This method gets the top percent of members and selects them
     * as the parents for the next generation
     * @param population
     * @param survivalRate
     * @param encodedTarget
     * @return 
     */
    public int[][] getParents(int[][] population, double survivalRate, int[] encodedTarget)
    {
        int numSurvivors = (int) (population.length * survivalRate);
        Random rand = new Random();
        
        ArrayList<int[]> parents = new ArrayList<>();
        double randomSelection = 0.024; //2.4% chance of randomly selecting another member (for genetic diversity)
        int[] fitnessScores = new int[population.length];
        
        //Get the fitness scores of all members in the population
        for(int i = 0; i < population.length; i++)
        {
            fitnessScores[i] = fitness(population[i], encodedTarget);
        }
        
        
        //Populate best members
        for(int i = 0; i < numSurvivors; i++)
        {
            //Best member is the index of the member in the population with the best score
            int highestScoreIndex = findBestScoreIndex(fitnessScores);
            parents.add(population[highestScoreIndex]);
            fitnessScores[highestScoreIndex] = 0;
        }
        
        
        //Calculate random selection
        for(int i = numSurvivors; i < population.length-1; i++)
        {
            double random = rand.nextDouble(); //Generate a number between 0-1
            
            //Check if random selection is greater than random
            if(randomSelection > random)
            {
                parents.add(population[i]);
            }
        }
        return convertTo2DIntArray(parents);
    }
    
    /**
     * Breeds the parents together in order to create the next generation.
     * There is a small chance that one element of a parent's may be mutated 
     * to increase genetic diversity
     * 
     * @param population
     * @param parents
     * @return children
     */
    public int[][] getChildren(int[][] population, int[][] parents, int[] encodedAlphabet)
    {
        double mutationChance = 0.023; //2.3% chance that a parent's element may be mutated
        Random rand = new Random(); //Random number generator
        int numChildren = population.length - parents.length; //Number of children needed for full population
        ArrayList<int[]> children = new ArrayList<>();
        
        //Loop through the members in parents and possibly mutate
        for(int[] member : parents)
        {
            double random = rand.nextDouble();
            
            //Check if member should be mutated
            if(mutationChance > random)
            {
                int elementToMutate = rand.nextInt(member.length);
                member[elementToMutate] = rand.nextInt(getMemberMaxValue(encodedAlphabet)) + getMemberMinValue(encodedAlphabet);
            }
        }
        
        //Get children
        while(children.size() < numChildren)
        {
            int motherIndex = rand.nextInt(parents.length);
            int fatherIndex = rand.nextInt(parents.length);
            
            if(motherIndex != fatherIndex)
            {
            int[] mother = parents[motherIndex];
            int[] father = parents[fatherIndex];
            int half = father.length / 2;
            int[] child = new int[mother.length];
            
            //Add mother elements to child array
            for(int i = 0; i < half; i++)
            {
                child[i] = mother[i];
            }
            
            //Add father elements to child
            for(int i = half; i < father.length; i++)
            {
                child[i] = father[i];
            }
            children.add(child); //Add the child to the children array
            }
        }
        return convertTo2DIntArray(children);
    }
    
    /**
     * Gets the next generation of AI's 
     * @param population
     * @param survivalRate
     * @param encodedTarget
     * @return 
     */
    public int[][] getNextGeneration(int[][] population, double survivalRate, int[] encodedTarget, int[] encodedAlphabet)
    {
        ArrayList<int[]> nextGen = new ArrayList<>();
        int[][] parents = getParents(population, survivalRate, encodedTarget);
        int[][] children = getChildren(population, parents, encodedAlphabet);
        
        //Add parents to the next generation
        for(int[] parent : parents)
        {
           nextGen.add(parent); 
        }
        
        //Add children to the next generation
        for(int[] child : children)
        {
            nextGen.add(child);
        }
        
        return convertTo2DIntArray(nextGen);
    }
    
    /**
     * Find the best member in that generation
     * @param population
     * @param encodedTarget
     * @return 
     */
    public int[] getBestMember(int[][] population, int[] encodedTarget)
    {
        int[] fitnessScores = new int[population.length];
        int highestScoreIndex;
        
        //Get the fitness scores of all members in the population
        for(int i = 0; i < population.length; i++)
        {
            fitnessScores[i] = fitness(population[i], encodedTarget);
        }
        
        //Get the member with the highest score
        highestScoreIndex = findBestScoreIndex(fitnessScores);
        int[] bestMember = population[highestScoreIndex];
        
        return bestMember;
    }
    
    /**
     * Sorts fitness scores in ascending order
     * @param fitnessScores
     * @return fitnessScores
     */
    public int[] sortScores(int[] fitnessScores)
    {
        //Sort scores
        for(int i = 0; i < fitnessScores.length; i++)
        {
            for(int j = i + 1; j < fitnessScores.length; j++)
            {
                if(fitnessScores[i] > fitnessScores[j])
                {
                    int temp = fitnessScores[i];
                    fitnessScores[i] = fitnessScores[j];
                    fitnessScores[j] = temp;
                }
            }
        }
        return fitnessScores;
    }
    
    /**
     * Converts and ArrayList of integer arrays to a 2D int array
     * @param array the array to convert
    */
    public int[][] convertTo2DIntArray(ArrayList<int[]> array)
    {
        int[][] newArray = new int[array.size()][array.get(0).length];
        
        for(int i = 0; i < array.size(); i++)
        {
            for(int j = 0; j < array.get(i).length; j++)
            {
                newArray[i][j] = array.get(i)[j];
            }
        }
        return newArray;
    }
    
    /**
     * returns the index of the highest score
     * @param scores
     * @return 
     */
    public int findBestScoreIndex(int[] scores)
    {
        int bestScoreIndex = 0;
        
        for(int i = 1; i < scores.length; i++)
        {
            if(scores[i] <  scores[bestScoreIndex])
            {
                bestScoreIndex = i;
            }
        }
        return bestScoreIndex;
    }
    
    /**
     * Find the maximum value in the member array
     * @param member
     * @return 
     */
    public int getMemberMaxValue(int[] member)
    {
        int max = 0; 
        
        for(int i = 1; i < member.length; i++)
        {
            if(member[i] > max)
            {
                max = member[i];
            }
        }
        return max;
    }
    
    /**
     * Find the minimum value in the member array
     * @param member
     * @return 
     */
    public int getMemberMinValue(int[] member)
    {
        int min = 100;
        
        for(int i = 1; i < member.length; i++)
        {
            if(member[i] < min)
            {
                min = member[i];
            }
        }
        return min;
    }
}
