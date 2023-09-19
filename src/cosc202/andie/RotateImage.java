package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to rotate Image
 * </p>
 * 
 * <p>
 * Rotate by 90 degrees to the left, 90 degrees
 * to the right, or by 180 degrees
 * </p>
 */
public class RotateImage implements ImageOperation, java.io.Serializable {
    
    /**
    * The number of degrees to rotate the image, also used to 
    * differentiate between left and right rotation
    */
    private int degrees;
    
    /**
     * <p>
     * Construct a Rotate of given degrees
     * </p>
     * 
     * <p>
     * The degrees also help distinguish between left and right rotation
     * as -90 is left rotation, and 90 is right rotation (180 can go either way)
     * </p>
     * 
     * @param degrees The degrees for the image to be rotated
     */
    RotateImage(int degrees){
        this.degrees = degrees;
    }

    /**
     * <p>
     * Apply a Rotation to an image.
     * </p>
     * 
     * <p>
     * The rotation is done by taking the pixels of the origional image
     * and placing them in their 'rotated' positions in a new image
     * and then returning the new image
     * </p>
     * 
     * @param input The image to be rotated.
     * @return The resulting (rotated) image.
     */
    public BufferedImage apply (BufferedImage input){       
        try{
            BufferedImage output;
            //Rotates image 180 degrees
            if(degrees == 180){
                output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
                for (int y = 0; y < output.getHeight(); y++) {
                    for (int x = 0; x < output.getWidth(); x++) {
                        output.setRGB(x, y, input.getRGB(((input.getWidth() - 1) - x), ((input.getHeight() - 1) - y)));
                    }
                }
            }
            //Rotates Image 90 degrees to the LEFT
            else if(degrees == -90){
                output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
                for (int y = 0; y < output.getHeight(); y++) {
                    for (int x = 0; x < output.getWidth(); x++) {
                        output.setRGB(x, y, input.getRGB(((input.getWidth() - 1) - y), x));
                    }
                }
            }
            //Rotates Image 90 degrees to the RIGHT
            else {
                output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
                for (int y = 0; y < output.getHeight(); y++) {
                    for (int x = 0; x < output.getWidth(); x++) {
                        output.setRGB(x, y, input.getRGB(y, (input.getHeight() - 1) - x));
                    }
                }
            }
        
            return output;

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex);
            PopUp.showMessageDialog("An ArrayIndexOutOfBounds error has occured. Returning input");
            return input;
        } catch (NullPointerException e) {
            System.out.println(e);
            PopUp.showMessageDialog("No Image to rotate!");
            return input;
        } catch (Exception e) {
            System.out.println(e);
            PopUp.showMessageDialog("An unknown error has occured. Returning input");
            return input;
        }
    }
}
