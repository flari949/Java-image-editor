package cosc202.andie;

import java.awt.image.*;

/** 
 * <p>
 * ImageOperation to adjust the contrast of an image
 * </p>
 * 
 * <p>
 * Adjusts the contrast of an image using equation 2.50
 * from the cosc202 lab book
 * </p>
 * 
*/
public class AdjustContrast implements ImageOperation, java.io.Serializable{

    /** 
     * <p>
     * The percentage to increase or decrease the contrast
     * </p>
    */
    private double contrast;

    /**
     * <p>
     * Construct an AdjustContrast with the given Contrast
     * </p>
     * 
     * @param contrast The percentage to increase or decrease the Contrast
     */
    public AdjustContrast(double contrast){
        this.contrast = contrast;
    }

    /**
     * <p>
     * Construct a new AdjustContrast operation with the default Contrast
     * </p>
     * 
     * <p>
     * BY default, the percentage is 10
     * </p>
     * 
     * @see AdjustContrast(int)
     */
    public AdjustContrast(){
        this(10);
    }

    /**
     * <p>
     * Method for testing
     * </p>
     * 
     * @return returns the contrast value
     */
    public double returnContrast(){
        return contrast;
    }

    /**
     * <p>
     * Apply Contrast adjustment to an image
     * </p>
     * 
     * <p>
     * The Contrast adjustment is applied using the equation 2.5 of the 202 lab book.
     * Where b = 0 and c = contrast;
     * The lab book is here: https://cosc202.cspages.otago.ac.nz/lab-book/COSC202LabBook.pdf
     * </p>
     * 
     * @param input the image to apply the Contrast adjustment to
     * @return The resulting adjusted image
     */
    public BufferedImage apply(BufferedImage input) {

        RGB argbClass = new RGB(input);
        for (int y = 0; y < input.getHeight(); y++) {// loops for image matrix
            for (int x = 0; x < input.getWidth(); x++) {
                int argb = argbClass.getRGB(x, y);

                // adding factor to rgb values
                int r = ((argb & 0x00FF0000) >> 16);
                int b = (argb & 0x000000FF);
                int g = ((argb & 0x0000FF00) >> 8);
                int a = ((argb & 0xFF000000) >> 24);

                r = (int) getAdjustedValue(contrast, r);
                g = (int) getAdjustedValue(contrast, g);
                b = (int) getAdjustedValue(contrast, b);

                argbClass.setRGB(x, y, r, g, b, a);
            }
        }
        BufferedImage output = new BufferedImage(input.getColorModel(), argbClass.getRaster(), input.isAlphaPremultiplied(), null);

        return output;
    }

    /**
     * Method for testing the getAdjustedValue method
     * @param v The value to adjust. Assumed to be between 0 and 255
     * @return The value with the adjustment applied
     */
    public double testAdjustment(double v){
        return getAdjustedValue(contrast, v);
    }
    
    /**
     * <p>
     * Method to apply a Contrast adjusted to a color channel of a pixel
     * </p>
     * 
     * @param c the percentage change in contrast
     * @param v the value to adjust
     * @return The adjusted value
     */
    private double getAdjustedValue(double c, double v) {

        if(c < -99) c = -100;

        double result = ((1 + (c / 100.0)) * (v - 127.5)) + 127.5;

        if(result > 255) {
            result = 255;
        } else if (result < 0) {
            result = 0;
        }

        return result;
    }
}
