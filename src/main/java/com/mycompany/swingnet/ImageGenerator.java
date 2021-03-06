/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.swingnet;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.ImageIcon;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author vampire
 */
public class ImageGenerator {
    static ImageGenerator instance;
    public static ImageIcon lastImage;
    public static double [] lastArrayImage;
    public static char lastLetter;
    public static ImageGenerator getInstance()
    {
        if (instance == null)
            instance = new ImageGenerator();
        return instance;
    }
    public double[] getRandLetterNoise(double ordM, double invM) throws IOException {
        char letter;
        Random r = new Random();
        letter = (char) (r.nextInt(5) + 'J');
        BufferedImage letterImage = ImageIO.read(getClass().getResource("/images/" + letter + ".png"));
        double[] result = new double[400];
        int rand;
        for (int i = 0 ; i< 20; i++)
            for (int k = 0 ; k< 20; k++){
                if (Math.random() > (1 - ordM))
                {
                    rand = r.nextInt(255);
                    letterImage.setRGB(i, k, new Color(rand,rand, rand ).getRGB() );
                }
                if (Math.random() > (1 - invM))
                    letterImage.setRGB(i, k, 0xFFFFFF - letterImage.getRGB(i, k) );
            }
        int m;
        int rgb ;
        int red ;
        int green ;
        int blue ;
        for (int i = 0 ; i< 20; i++)
            for (int k = 0 ; k< 20; k++){
                rgb = letterImage.getRGB(i, k);
                red  = (rgb >> 16) & 0xFF;
                green = (rgb >> 8) & 0xFF;
                blue = (rgb & 0xFF);
                result[20*i + k] = ((red + green + blue) / 3) - 128;
            m = letterImage.getRGB(i, k);
            }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ImageIcon icon = new ImageIcon( letterImage );
        
        lastImage = icon;
        double d = 0;
        lastLetter = letter;
        lastArrayImage = result;
        return result;
     }
    public double[] getPureLetter(char letter) throws IOException {
        BufferedImage letterImage = ImageIO.read(getClass().getResource("/images/" + letter + ".png"));
        double[] result = new double[400];
        int m;
        int rgb ;
        int red ;
        int green ;
        int blue ;
        for (int i = 0 ; i< 20; i++)
            for (int k = 0 ; k< 20; k++){
                rgb = letterImage.getRGB(i, k);
                red  = (rgb >> 16) & 0xFF;
                green = (rgb >> 8) & 0xFF;
                blue = (rgb & 0xFF);
                result[20*i + k] = ((red + green + blue) / 3) - 128;
            m = letterImage.getRGB(i, k);
            }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ImageIcon icon = new ImageIcon( letterImage );
        
        lastImage = icon;
        double d = 0;
        lastLetter = letter;
        lastArrayImage = result;
        return result;
     }
    
    public ImageIcon getIconLetter(char letter) throws IOException
    {
        return new ImageIcon(ImageIO.read(getClass().getResource("/images/" + letter + ".png")));
    }
    
    public ImageIcon createLargeImage(double[][] vectors)
    {
        BufferedImage neuronImage = neuronImage = new BufferedImage(402, 81, BufferedImage.TYPE_INT_RGB); 
        for (int j = 0;j<vectors.length;j++)
        {
            double[] vector = vectors[j];
            int firstX = 0, firstY = 0;
            if (j * 40 <= 360){
                firstX = j * 40;
                firstY = 0;
            }
            else {
                firstX = (j - 10) * 40;
                firstY = 40;
            }
                
        
            
            for (int i = 0 ; i< 20; i++)
                for (int k = 0 ; k< 20; k++){
                    int gray = (int)Math.round(vector[20*i + k] + 128);
                    if ((gray < 0 ))
                        gray = 0;
                    if (gray > 255)
                        gray = 255;
                    Color cr = new Color(gray,gray,gray);
                    int RGB = cr.getRGB();
                    neuronImage.setRGB(2 * i + firstX, 2 * k + firstY, RGB);
                    neuronImage.setRGB((2 * i) + 1 + firstX, 2 * k + firstY, RGB);
                    neuronImage.setRGB(2 * i + firstX, (2 * k) + 1 + firstY, RGB);
                    neuronImage.setRGB((2 * i) + 1 + firstX, (2 * k) + 1 + firstY, RGB);
                }
            
        }
        return new ImageIcon(neuronImage);
    }
    
}
