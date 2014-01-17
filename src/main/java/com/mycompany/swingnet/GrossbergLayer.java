/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.swingnet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vampire
 */
public class GrossbergLayer {
    NeuronGross[] neurons;
    int neuronsCount;
    int criteria;
    KohonenLayer kohLayer;
    int nIter = 0;
    char resultLetter = 'A';
    public GrossbergLayer(int nNeurons, double speed, int nCriteria, KohonenLayer klayer, char startLetter)
    {
        kohLayer = klayer;
        neuronsCount = nNeurons;
        neurons = new NeuronGross[nNeurons];
        for (int i =0; i < nNeurons; i++)
        {
            try {
                neurons[i] = new NeuronGross(neuronsCount, 400, speed, (char)((int)startLetter + i));
            } catch (IOException ex) {
                Logger.getLogger(GrossbergLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        criteria = nCriteria;
    }
    public void teach(double ordM, double invM) throws IOException{
        while (!teached()){
            kohLayer.step(ImageGenerator.getInstance().getRandLetterNoise(ordM, invM));
            double[] inputKoh = new double[neuronsCount];
            
            for (int i = 0; i< neuronsCount; i++)
            {
                neurons[i].adjustWeights(kohLayer.winnerNum, getArrayByLetter(ImageGenerator.lastLetter, neuronsCount)[i]);
            }
            nIter += 1;
        }
    }
    public boolean teached(){
        return (nIter > criteria);
    }
    
    double [] getArrayByLetter(char letter, int size)
    {
        int letterNum = ((int)letter) - (int)'A';
        double[] result = new double[size];
        for (int i=0;i<size;i++)
           if ((int)((1 << i) & (letterNum)) != 0)
               result[i] = 1;
        return result;
    }
    
    char getLetterByArray(double[] array)
    {
        int result = 0;
        for (int i=0;i<array.length;i++)
            result = ((int)Math.round(array[i]) << i) | result ;
        
        return (char) (result + (int)'A');
    }
    
    public char step(double[] kohInput){
        double[] letterArray = new double[neuronsCount];
        for(int i=0; i<letterArray.length; i++)
        {
            neurons[i].trigger(kohInput);
            letterArray[i] = neurons[i].lastResult;
        }
        return getLetterByArray(letterArray);
    }
}
