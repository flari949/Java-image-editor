package cosc202.andie;

import java.awt.image.*;

/**
 * This class convolutes an image
 * Heavily influenced by this article: http://ramok.tech/2018/09/27/convolution-in-java/
 * TODO improve javadoc
 */
public class Convoluter {

    /**
     * Applys a border aware convolution to a buffered image
     * @param input Image to convolve
     * @param kernel kernel used for convolution
     * @param offset Offset used if you expect the convolution to produce negative values
     * @return A new BufferedImage with the covolution applied
     */
    public static BufferedImage applyConvolution(BufferedImage input, float[][] kernel, int offset) {

        // for (float[] fs : kernel) {
        //     for (float f : fs) {
        //         System.out.print(f + ", ");
        //     }
        //     System.out.println();
        // }

        //read image to 3d array
        float[][][] image = new float[4][input.getWidth()][input.getHeight()];
        // Faster (but more memory intensive) method of retrieving and setting an images colour values
        RGB argbClass = new RGB(input);
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                int rgb = argbClass.getRGB(x, y);
                image[0][x][y] = (float)((rgb & 0x00FF0000) >> 16);
                image[1][x][y] = (float)((rgb & 0x0000FF00) >> 8);
                image[2][x][y] = (float)(rgb & 0x000000FF);
                image[3][x][y] = (float)((rgb & 0xFF000000) >> 24);
            }
        }
        
        //convolve and write image
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                //convolve each of the color channels of each pixel separately using the given kernel
                int red = convolvePixel(image[0], x, y, kernel, offset);
                int green = convolvePixel(image[1], x, y, kernel, offset);
                int blue = convolvePixel(image[2], x, y, kernel, offset);

                //Write new pixel to output image, alpha is not changed
                argbClass.setRGB(x, y, red, green, blue, (int)(image[3][x][y]));
            }
        }
        // Create a new image based off of the updated Raster
        BufferedImage output = new BufferedImage(input.getColorModel(), argbClass.getRaster(), input.isAlphaPremultiplied(), null);
        return output;
    }

    /**
     * Wrapper method for applyConcolution which allows the method to be used with either square or flat arrys
     * @param input Image to convolve
     * @param kernel kernel used for convolution
     * @param offset Offset used if you expect the convolution to produce negative values
     * @return A new BufferedImage with the covolution applied
     */    
    public static BufferedImage applyConvolution(BufferedImage input, float[] kernel, int offset){
        try{
            float[][] squareKernel = getSquareArray(kernel);
            return applyConvolution(input, squareKernel, offset);
        } catch(Exception e){
            PopUp.showMessageDialog(e.getMessage());
            return input;
        }
    }    

    /**
     * Multiplies a single color channel of a pixel with the given kernel
     * @param input the input image
     * @param x the x position of the pixel
     * @param y the y position of the pixel
     * @param kernel the kernel to be applied
     * @param offset amount to shift the ouput colors
     * @return the new value of that channel of that pixel
     */
    private static int convolvePixel(float[][] input, int x, int y, float[][] kernel, int offset) {
        float out = 0;
        int radius = (kernel.length-1)/2;
        //multiply the kernel matrix with the sub-matrix of the input surrounding the givel pixel
        //We subtract the radius from the x, y values so that the kernel is "centered" on the pixel
        //This means some pixels will be "outside" of the image
        //So we constrain these pixels to the nearest valid pixel
        for (int kernelX = -radius; kernelX <= radius; kernelX++) {
            for(int kernelY = -radius; kernelY <= radius; kernelY++) {
                int constrainedX = constrain(x+kernelX, input.length);
                int constrainedY = constrain(y+kernelY, input[0].length);

                float kernelVal = kernel[kernelX+radius][kernelY+radius];
                float imageVal = input[constrainedX][constrainedY];

                out += (kernelVal * imageVal);
            }
        }
        //We also need to ensure the pixel does not go outside the range 0-255
        //Some filters are designed to produce negative values
        //to account for these we can make zero become the midpoint by applying an offset
        return constrain((int)(out + offset), 256);
    }

    /**
     * Constrains a value to a range. 
     * Values below the range are set to zero
     * Values above the range are set to the maximum
     * Values within the range are unchanged
     * @param val the value to be constrained
     * @param range the range to constrain the value to
     * @return a value within the range
     */
    private static int constrain(int val, int range){
        if(val < 0) return 0; //if its below zero return zero
        if(val >= range) return range - 1; //if its bigger than the range return the range - 1
        return val; //otherwise do nothing
    }

    /**
     * 
     * @param flat a flattened array representing the image pixels
     * @return returns a 2D array of the image's pixel values
     * @throws Exception if invalid file or image type parsed
     */
    private static float[][] getSquareArray(float[] flat) throws Exception{
        int radius = (int)Math.sqrt(flat.length);

        if(Math.pow(radius, 2) != flat.length) {
            throw new Exception("Length of input array must be a square number");
        }

        float[][] square = new float[radius][radius];

        for (int i = 0; i < radius; i++) {
            for (int j = 0; j < radius; j++) {
                square[i][j] = flat[(3*i) + j];
            }
        }
        return square;
    }
}
