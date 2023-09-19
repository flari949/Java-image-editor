package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to flip Image
 * </p>
 * 
 * <p>
 * flip either vertically or horizontally
 * </p>
 */
public class FlipImage implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Indicative of the axis to flip the image about
     * </p>
     */
    private boolean isX;

    /**
     * <p>
     * Construct a Flip by given axis
     * </p>
     * 
     * <p>
     * The 'isX' determines what axis the image
     * is flipped about
     * </p>
     * 
     * @param isX The axis for the image to be flipped on
     */
    public FlipImage(boolean isX) {
        this.isX = isX;
    }

    /**
     * <p>
     * This method exists soley for testing purposes
     * </p>
     * 
     * @return returns the representative of the axis to be flipped about
     */
    public boolean returnIsX() {
        return isX;
    }

    /**
     * <p>
     * Apply a Flip to an image.
     * </p>
     * 
     * <p>
     * The flip is done by taking the pixels of the original image
     * and placing them in their 'flipped' positions in a new image
     * and then returning the new image
     * </p>
     * 
     * @param input The image to be flipped.
     * @return The resulting (flipped) image.
     */
    public BufferedImage apply(BufferedImage input) {
        try {
            BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
            if (!isX) {
                for (int y = 0; y < output.getHeight(); y++) {
                    for (int x = 0; x < output.getWidth(); x++) {
                        output.setRGB(x, y, input.getRGB(x, ((input.getHeight() - 1) - y)));
                    }
                }
            } else {
                for (int y = 0; y < output.getHeight(); y++) {
                    for (int x = 0; x < output.getWidth(); x++) {
                        output.setRGB(x, y, input.getRGB(((input.getWidth() - 1) - x), y));
                    }
                }
            }
            return output;

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex);
            PopUp.showMessageDialog("An ArrayIndexOutOfBounds has occured. Returning input");
            return input;
        } catch (NullPointerException e) {
            System.out.println(e);
            PopUp.showMessageDialog("No Image to Flip!");
            return input;
        } catch (Exception e) {
            System.out.println(e);
            PopUp.showMessageDialog("An unknown error has occured. Returning input");
            return input;
        }
    }
}
