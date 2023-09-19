//CROCS
package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Median (simple blur) filter.
 * </p>
 * 
 * <p>
 * A Median filter blurs an image by replacing each pixel with the median value
 * of the
 * pixels in a surrounding neighbourhood
 * </p>
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * @param radius The radius of the newly constructed MedianFilter
     */
    MedianFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Median filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Median filter has radius 1.
     * </p>
     * 
     * @see MedianFilter(int)
     */
    public MedianFilter() {
        this(1);
    }

    /**
     * <p>
     * Find the median value in an array
     * </p>
     * 
     * @param vals The input array containing integers
     * @return The median value of the array
     */
    public int getMedian(int[] vals) {
        Arrays.sort(vals);
        int centVal = ((2 * radius + 1) * (2 * radius + 1)) / 2;
        return vals[centVal];
    }

    /**
     * <p>
     * Apply a Median filter to an image.
     * </p>
     * 
     * <p>
     * The strength of the blur is specified by the {@link radius}.
     * a larger radius leads to stronger blurring through a greater surrounding
     * quantity of values to find the median values.
     * </p>
     * 
     * @param input The image to apply the Median filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {

        try {
            int size = (2 * radius + 1) * (2 * radius + 1);

            // Apply image extender
            BufferedImage tempImage = ImgExtend.extend(input, radius);

            RGB argbClass = new RGB(tempImage);
            // Iterate over pixels within the image
            for (int y = radius; y < tempImage.getHeight() - radius ; y++) {
                for (int x = radius; x < tempImage.getWidth() - radius; x++) {

                    // Initialise arrays to contain each pixel colour (and transparency) value
                    // within an area determined by the radius
                    int[] arrA = new int[size];
                    int[] arrR = new int[size];
                    int[] arrG = new int[size];
                    int[] arrB = new int[size];

                    // Iterate over pixels within the area of the given radius, push colour and
                    // transparency values into arrays
                    int innPos = 0;
                    for (int e = y - radius; e <= y + radius; e++) {
                        for (int i = x - radius; i <= x + radius; i++) {
                            int argb = argbClass.getRGB(i, e);
                            int a = (argb & 0xFF000000) >> 24;
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);

                            arrA[innPos] = a;
                            arrR[innPos] = r;
                            arrG[innPos] = g;
                            arrB[innPos] = b;
                            innPos++;
                        }
                    }

                    // Find the median values of the colour and transparency values within the
                    // determined area
                    int medianA = getMedian(arrA);
                    int medianR = getMedian(arrR);
                    int medianG = getMedian(arrG);
                    int medianB = getMedian(arrB);

                    // Transform copied image to apply the new colour and transparency to the given
                    // pixel,
                    // Create new colour and transparency value equal to the median of the values
                    // within the area determined,
                    //int argb = (medianA << 24) | (medianR << 16) | (medianG << 8) | medianB;
                    argbClass.setRGB(x, y, medianR, medianG, medianB, medianA);
                }
            }

            // Remove extended images border
            BufferedImage output = new BufferedImage(tempImage.getColorModel(), argbClass.getRaster(), tempImage.isAlphaPremultiplied(), null);
            output = output.getSubimage(radius, radius, input.getWidth(), input.getHeight());

            System.out.println("applied median filter");
            return output;

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex);
            PopUp.showMessageDialog("An ArrayIndexOutOfBounds has occured. Returning input");
            return input;
        } catch (Exception e) {
            System.out.println(e);
            PopUp.showMessageDialog("An unknown error has occured. Returning input");
            return input;
        }
    }
}
