package cosc202.andie;

import java.awt.image.*;

/**
 * Class to extend the border of images allowing for extended filters
 */
public class ImgExtend {

    /**
     * <p>
     * Create a buffered image object with a border of size radius and pixels along
     * an axis mimicing those pixels around the extremities of the original input in
     * order to extend a filter allowing for the filter to completely doctor an
     * image and reach all pixels within the image
     * buffered image
     * </p>
     * 
     * @param input  the buffered image object to be extended
     * @param radius the radius of the filter used
     * @return returns the new buffered image object with an extended margin
     *         relative to the existing biffered image object's border
     */
    public static BufferedImage extend(BufferedImage input, int radius) {
        // Create new BufferedImage object greater in size than the input image by the
        // radius on all sides to allow complete image convolution
        BufferedImage tempImage = new BufferedImage(input.getWidth() + radius * 2, input.getHeight() + radius * 2,
                input.getType());
        // Original image exists in tempImage within a one radius boundary
        tempImage.getGraphics().drawImage(input, radius, radius, null);

        // Sets rgb values for the upper and lower blank pixels
        int rgb;
        boolean north = true;
        for (int wPtr = 0; wPtr < tempImage.getWidth(); wPtr++) {
            // If outside left boundary
            if (wPtr <= radius) {
                // If upper boundary
                if (north) {
                    rgb = input.getRGB(0, 0);
                } else {
                    rgb = input.getRGB(0, input.getHeight() - 1);
                }
                // If outside right boundary
            } else if (wPtr >= tempImage.getWidth() - radius) {
                // If upper boundary
                if (north) {
                    rgb = input.getRGB(input.getWidth() - 1, 0);
                } else {
                    rgb = input.getRGB(input.getWidth() - 1, input.getHeight() - 1);
                }
            } else {
                if (north) {
                    rgb = input.getRGB(wPtr - radius, 0);
                } else {
                    rgb = input.getRGB(wPtr - radius, input.getHeight() - 1);
                }
            }

            // Set RGB values for added pixels
            // For all pixels above last known pixel copy said pixel's value
            if (north) {
                for (int hPtr = 0; hPtr <= radius; hPtr++) {
                    tempImage.setRGB(wPtr, hPtr, rgb);
                }
            } else {
                for (int hPtr = tempImage.getHeight() - radius; hPtr < tempImage.getHeight(); hPtr++) {
                    tempImage.setRGB(wPtr, hPtr, rgb);
                }
            }

            // Once upper boundary complete, continue to bottom
            if (wPtr == tempImage.getWidth() - 1 && north) {
                north = false;
                wPtr = 0;
            }
        }

        // Set colour values for vertical plane unknown pixels (excluding corners)
        boolean west = true;
        for (int hPtr = radius; hPtr < tempImage.getHeight() - radius; hPtr++) {
            if (west) {
                rgb = input.getRGB(0, hPtr - radius);
                for (int wPtr = 0; wPtr <= radius; wPtr++) {
                    tempImage.setRGB(wPtr, hPtr, rgb);
                }
            } else {
                rgb = input.getRGB(input.getWidth() - 1, hPtr - radius);
                for (int wPtr = tempImage.getWidth() - radius; wPtr < tempImage.getWidth(); wPtr++) {
                    tempImage.setRGB(wPtr, hPtr, rgb);
                }
            }
            if (hPtr == tempImage.getHeight() - radius - 1 && west) {
                west = false;
                hPtr = radius;
            }
        }
        
        return tempImage;
    }

}