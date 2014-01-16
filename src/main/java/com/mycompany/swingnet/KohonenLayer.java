/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.swingnet;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author vampire
 */
public class KohonenLayer {
    List<NeuronKoh> neurons;
    NeuronKoh winner = null;
    double winnerNET = 0;
    public int winnerNum;
    int iterCount = 0;
    int iterTeached;
    public KohonenLayer(int neuronsCount, int inputsCount, double alpha, int nPredictedIter, double weightCriteria)
    {
        neurons = new ArrayList<NeuronKoh>();
        for (int i = 1; i<=neuronsCount; i++)
        {
            neurons.add(new NeuronKoh(inputsCount, alpha, weightCriteria, nPredictedIter ));
        }
        iterTeached = nPredictedIter;
    }
    
    public void step(double [] vector){
        boolean firstNeuron = true;
        winnerNET = 0;
        
        for(NeuronKoh n : neurons)
        {
            n.trigger(vector);
            if ((n.getLastResult() > winnerNET) || (firstNeuron) || ((n.getLastResult() == winnerNET) && (Math.random() > 0.5)))
            {   
                winner = n;
                winnerNET = n.getLastResult();
                
                firstNeuron = false;
            }
        }
        winnerNum = neurons.indexOf(winner);
        System.out.println("winner is "+ String.valueOf(winnerNum));
        
        
    }
    
    public void teach(double ordM, double invM) throws IOException{
        double[] in;
        NeuronKoh lastWin =  winner;
        while(!teached()){
            in = ImageGenerator.getInstance().getRandLetterNoise(ordM,invM);
            boolean firstNeuron = true;
            winnerNET = 0;
            for(NeuronKoh n : neurons)
            {
                n.trigger(in);
                if ((n.getLastResult() > winnerNET)  || (firstNeuron) || ((n.getLastResult() == winnerNET) && (Math.random() > 0.5)))
                {   //if (n.timesWin > 3) {
                    
                      //  n.timesWin = 0;
               // }
                //else{
                    winner = n;
                    winnerNET = n.getLastResult();}
                    firstNeuron = false;
                //}
                
            }
            winner.adjustWeights(in, 1);
            for(NeuronKoh n : neurons)
            {
                
                double s =  n.lastResult / (10 * winner.getLastResult());
                if (Double.isNaN(s) )
                    System.out.println("NAN");
                if ((n != winner) && (Math.abs(s) < 1) && (n.lastResult * winner.lastResult > 0))
                    n.adjustWeights(in, s);
            }
            winnerNum = neurons.indexOf(winner);
            System.out.println("winner is "+ String.valueOf(winnerNum));
            if (lastWin == winner)
                winner.timesWin++;
            iterCount++;
        }
    }
    
    public boolean teached()
    {
        boolean weightCriteria = true;
        for (NeuronKoh n : neurons)
        {
            weightCriteria = weightCriteria && (n.isTeached());
        }
        return (iterCount >= 40) && ((iterCount >= iterTeached) || weightCriteria);
    }
    
    public String strRepresentation()
    {
        String result = "";
        for (NeuronKoh n : neurons)
        {
            for (int i=0;i<n.weights.length;i++)
                result += String.valueOf(n.weights[i]) + "  ";
            result += "\n";
        }
        return result;
    }
}
