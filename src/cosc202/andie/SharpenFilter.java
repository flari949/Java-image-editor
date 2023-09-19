//CROCS
package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Sharpen filter.
 * </p>
 * 
 * <p>
 * A Sharpen filter enhances the difference between neighbouring values,
 * and can be implemented as a convoloution.
 * </p>
 * 
 **/

public class SharpenFilter implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Constructor for SharpenFilter class
     * </p>
     */
    SharpenFilter() {
    };

    /**
     * <p>
     * Apply a Sharpen filter to an image.
     * </p>
     * 
     * @param input The image to apply the Sharpen filter to.
     * @return The resulting (sharpened) image.
     */
    public BufferedImage apply(BufferedImage input) {

        float[] array = { 0, -1 / 2.0f, 0, -1 / 2.0f, 3.0f, -1 / 2.0f, 0, -1 / 2.0f, 0 };

        BufferedImage output = Convoluter.applyConvolution(input, array, 0);

        return output;
    }
}
