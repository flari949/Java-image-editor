package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to resize Image
 * </p>
 * 
 * <p>
 * resize either by 150% or 50%
 * </p>
 */
public class ScaleImage implements ImageOperation, java.io.Serializable {
    /**
     * <p>
     * The scale at which the image is to be adjusted
     * </p>
    */
    double scale;

    /**
     * <p>
     * Construct a Resize by given scale
     * </p>
     * 
     * <p>
     * The 'scale' determines what scale the image
     * is resized to
     * </p>
     * 
     * @param scale The scale for the image to be resized to
     */
    ScaleImage(double scale) {
        this.scale = scale;
    }

    /**
     * <p>
     * Apply a Scale to an image.
     * </p>
     * 
     * @param input The image to be flipped.
     * @return The resulting (scaled) image.
     */
    public BufferedImage apply(BufferedImage input) {

        try {
            int newWidth = (int) Math.round(input.getWidth() * scale);
            int newHeight = (int) Math.round(input.getHeight() * scale);
            BufferedImage output = new BufferedImage(newWidth, newHeight, input.getType());
            Graphics2D g = output.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(input, 0, 0, newWidth, newHeight, 0, 0, input.getWidth(), input.getHeight(), null);
            g.dispose();

            return output;

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex);
            PopUp.showMessageDialog("An ArrayIndexOutOfBounds has occured. Returning input");
            return input;
        } catch (NullPointerException e) {
            System.out.println(e);
            PopUp.showMessageDialog("No Image to scale!");
            return input;
        } catch (Exception e) {
            System.out.println(e);
            PopUp.showMessageDialog("An unknown error has occured. Returning input");
            return input;
        }
    }
}
