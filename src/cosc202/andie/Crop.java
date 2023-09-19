package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to crop an image
 * </p>
 * 
 * <p>
 * Can crop any user selection
 * </p>
 */
public class Crop implements ImageOperation, java.io.Serializable {
    /**
    * The co-ordinate values for the image to be cropped
    */
    int xOrigin, yOrigin, xEnd, yEnd;
    
    /**
     * <p>
     * Construct a Crop with given co-ordinates
     * </p>
     * 
     * <p>
     * The co-ordinates are used as a 'boundary' for the new image
     * to be created in
     * </p>
     * 
     * @param xOrigin The upper-left most co-ordinates
     * @param yOrigin The upper-left most co-ordinates
     * @param xEnd The lower-right most co-ordinates
     * @param yEnd The lower-right most co-ordinates
     */
    Crop(int xOrigin, int yOrigin, int xEnd, int yEnd){
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }

    /**
     * <p>
     * Crop an image to the selection
     * </p>
     * 
     * <p>
     * The crop is done by taking the co-ordinates from
     * the users mouse selection and changing the target image
     * to just those measurements
     * </p>
     * 
     * @param input The image to be cropped
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
        try {
            BufferedImage output = new BufferedImage(xEnd - xOrigin, yEnd - yOrigin, input.getType());
            for (int y = yOrigin; y < yEnd; y++) {
                for (int x = xOrigin; x < xEnd; x++) {
                    output.setRGB(x - xOrigin, y - yOrigin, input.getRGB(x, y));
                }
            }
            return output;
        }catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex);
            PopUp.showMessageDialog("Error: Selection is outside image bounds");
            return input;
        }catch (Exception e) {
            System.out.println(e);
            PopUp.showMessageDialog("An unknown error has occured. Returning input");
            return input;
        }
    }
}
