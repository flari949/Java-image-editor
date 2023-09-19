package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Gaussian filter.
 * </p>
 * 
 * <p>
 * A Gaussian filter blurs an image based on a 2-Dimensional Gaussian Equation,
 * and can be implemented by a convolution.
 * </p>
 * 
 **/
public class GaussianFilter implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * The radius of the Gaussian filter to be applied
     * </p>
     */
    private int radius;

    /**
     * <p>
     * Constructor method for the GaussianFilter class
     * </p>
     * 
     * @param radius The radius of the gaussian filter desired
     */
    public GaussianFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Alternate constructor method for the Gaussian filter class
     * with no parameters
     * </p>
     */
    public GaussianFilter() {
        this(1);
    }

    /**
     * <p>
     * Method for finding the Gaussian function value of the input coordinates.
     * </p>
     * 
     * @param x     The horizontal locational value of the determined kernel.
     * @param y     The vertical locational value of the determined kernel.
     * @param sigma The multiplier to ensure kernel edge values aren't cut off too
     *              much.
     * @return Returns the Gaussian function value at the given coordinates.
     */
    public double getGaussian(int x, int y, float sigma) {
        return (1 / (2 * Math.PI * Math.pow(sigma, 2))
                * Math.exp(-(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(sigma, 2))));
    }

    /**
     * <p>
     * Apply a Gaussian filter to an image.
     * </p>
     * 
     * @param input The image to apply the Gaussian filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {
        int size = (2 * radius + 1);
        float[][] array = new float[size][size];
        float sigma = radius / 3.0f;
        double sum = 0;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                double result = getGaussian(x, y, sigma);
                array[x + radius][y + radius] = (float) result;
                sum += array[x + radius][y + radius];
            }
        }

        // normalise array
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array[i][j] = array[i][j] / (float) sum;
            }
        }

        //flatten array
        float[] normflatArr = new float[size * size];
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                normflatArr[count] = array[i][j];
                count++;
            }
        }

        BufferedImage output = Convoluter.applyConvolution(input, array, 0);

        return output;
    }
}
