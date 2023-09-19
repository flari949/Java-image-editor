package cosc202.andie;

import java.awt.image.*;

/** 
 * <p>
 * ImageOperation to adjust the brightness of an image
 * </p>
 * 
 * <p>
 * Changes the brightness of an image using 
 * equation 2.5 from the cosc202 lab book.
 * </p> 
*/
public class AdjustBrightness implements ImageOperation, java.io.Serializable{

    /** 
     * <p>
     * The percentage to increase or decrease the brightness
     * </p>
    */
    private double brightness;

    /**
     * <p>
     * Construct an AdjustBrightness with the given brightness
     * </p>
     * 
     * @param brightness The percentage to increase or decrease the brightness
     */
    public AdjustBrightness(double brightness){
        this.brightness = brightness;
    }

    /**
     * <p>
     * Construct a new AdjustBrightness operation with the default brightness
     * </p>
     * 
     * <p>
     * BY default, the percentage is 10
     * </p>
     * 
     * @see AdjustBrightness(int)
     */
    public AdjustBrightness(){
        this(10);
    }

    /**
     * <p>
     * Method for testing
     * </p>
     * 
     * @return returns the brightness value
     */
    public double returnBrightness(){
        return brightness;
    }

    /**
     * <p>
     * Apply brightness adjustment to an image
     * </p>
     * 
     * <p>
     * The brightness adjustment is applied using the equation 2.5 of the 202 lab book.
     * Where b = brightness and c = 0;
     * The lab book is here: https://cosc202.cspages.otago.ac.nz/lab-book/COSC202LabBook.pdf
     * </p>
     * 
     * @param input the image to apply the brightness adjustment to
     * @return The resulting adjusted image
     */
    public BufferedImage apply(BufferedImage input) {

        RGB argbClass = new RGB(input);
        for (int y = 0; y < input.getHeight() ; y++) {//loops for image matrix
            for (int x = 0; x < input.getWidth() ; x++) {        
                int argb = argbClass.getRGB(x, y);
            
                //adding factor to rgb values
                int r = ((argb & 0x00FF0000) >> 16);
                int b = (argb & 0x000000FF);
                int g = ((argb & 0x0000FF00) >> 8);
                int a = ((argb & 0xFF000000) >> 24);

                r = (int)getAdjustedValue(brightness, r);
                g = (int)getAdjustedValue(brightness, g);
                b = (int)getAdjustedValue(brightness, b);

                argbClass.setRGB(x, y, r, g, b, a);
            }
        }
        BufferedImage output = new BufferedImage(input.getColorModel(), argbClass.getRaster(), input.isAlphaPremultiplied(), null);

        return output;
    }

    /**
     * Method for testing the getAdjustedVale method
     * @param v The value to adjust. Assumed to be between 0 and 255
     * @return The value with the adjustment applied
     */
    public double testAdjustment(double v){
        return getAdjustedValue(brightness, v);
    }

    /**
     * <p>
     * Method to apply a brightness adjusted to a color channel of a pixel
     * </p>
     * 
     * @param b the percentage change in brightness
     * @param v the value to adjust
     * @return The adjusted value
     */
    private double getAdjustedValue(double b, double v){      
        double result = (v - 127.5) + 127.5 * (1+(b/100));

        if(result > 255){
            result = 255;
        } else if (result < 0) {
            result = 0;
        }

        return result;
    }
}
