/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.swingnet;

import java.io.IOException;

/**
 *
 * @author vampire
 */
public class NeuronGross {
    int n;
    double [] weights;       
    double b ;
    double lastResult;
    char letter;
    double [] pureLetter;
    public NeuronGross(int nInputs, int vectorSize, double speed, char l) throws IOException
    {
        n = nInputs;
        b = speed;
        weights = new double[nInputs];
        letter = l;
        pureLetter = ImageGenerator.getInstance().getPureLetter(letter);
    }
    void adjustWeights(int kohNum, double neededNET )
    {
        weights[kohNum] += b * (neededNET - weights[kohNum]);
        
    }
    
    void trigger(double[] input)
    {
        lastResult = 0;
        for (int i = 0; i< input.length ; i++)
        {
            lastResult += (input[i] * weights[i]);
        }
    }
    
    
}
