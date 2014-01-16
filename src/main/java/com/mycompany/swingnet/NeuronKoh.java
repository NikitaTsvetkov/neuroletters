/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.swingnet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vampire
 */
public class NeuronKoh {
    double[] weights;
    int inN;
    double a;
    double lastResult;
    Long stepNum = 0L;
    public int timesWin =0;
    List <Double> weightsDiffHistory;
    double weightsDifference = 0.1;
    double deltaAlpha = 0;
    public NeuronKoh(int nInputs, double alpha, double wCriteria, int predictedIter){
        weights = new double[nInputs];
        for (int i = 0; i < weights.length; i++)
            weights[i] = (Math.random() * 120) ;
        inN = nInputs;
        a = alpha;
        deltaAlpha = a / predictedIter;
        weightsDifference = wCriteria;
        weightsDiffHistory = new ArrayList<Double>();
    }
    
    public void adjustWeights(double[] vector, double koef)
    {
      // if ((Double.isInfinite(koef))||(Double.isNaN(koef))|| (koef > 1))
      //     System.out.println("WRONG");
       double diffCoor = 0; 
       for (int i = 0; i < inN; i++){
           double check = (vector[i] - weights[i]) * a * koef;
           if (Double.isNaN(check) || (Double.isInfinite(check)))
                    System.out.println("WRONG");
           double oldCoor = weights[i]; 
           weights[i] +=  (vector[i] - weights[i]) * a * koef;
           diffCoor += (weights[i] - oldCoor)*(weights[i] - oldCoor) ;
           if (Double.isNaN(weights[i]) || (Double.isInfinite(weights[i])))
                   System.out.println("WRONG");
            
        }
       a -= deltaAlpha;
        //a -= 0.001 * stepNum;
        //if (a < 0)
        //    a = 0.001;
        diffCoor = Math.sqrt(diffCoor);
        System.out.println(diffCoor);
        weightsDiffHistory.add(diffCoor);
        if (weightsDiffHistory.size() > 20)
            weightsDiffHistory.remove(0);
        stepNum ++;
    }
    
    public boolean isTeached()
    {
        boolean tchd = true;
        for(Double x : weightsDiffHistory)
        {
            tchd = tchd && (x < weightsDifference);
        }
        tchd = (tchd && (weightsDiffHistory.size() >= 20));
        return tchd;
    }
    
    public void trigger(double[] vector)
    {
        double result = 0;
        for (int i = 0; i < inN; i++){
            result += weights[i] * vector[i]; 
        }
        lastResult = result;
        
        if (Double.isNaN(lastResult))
            System.out.println("nan");
    }
    public double getLastResult(){ return lastResult; }
    
    
}
