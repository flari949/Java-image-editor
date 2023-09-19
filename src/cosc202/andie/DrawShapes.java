package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.image.*;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import java.awt.*;

/**
 * <p>
 * ImageOperation to draw shapes onto an image
 * </p>
 * 
 * <p>
 * can draw rectangles, lines or circles
 * </p>
 */
public class DrawShapes implements ImageOperation, java.io.Serializable {

    /** Co-ordinate values */
    int xOrigin, yOrigin, xEnd, yEnd, weight;
    /** Color chooser instance */
    JColorChooser tcc;
    /** Banner label object */
    JLabel banner;
    /** Color object to be applied */
    java.awt.Color color;
    /** The type of operation to be applied */
    String type;
    
    /**
     * <p>
     * Construct a DrawShapes with given co-ordinates
     * </p>
     * 
     * <p>
     * The co-ordinates are used as a 'boundary' for the shape to be
     * drawn within
     * </p>
     * 
     * @param xOrigin The upper-left most co-ordinates
     * @param yOrigin The upper-left most co-ordinates
     * @param xEnd The lower-right most co-ordinates
     * @param yEnd The lower-right most co-ordinates
     * @param color The colour to be applied
     * @param type The type of operation to be applied
     * @param weight The width of the drawn line
     */
    DrawShapes(int xOrigin, int yOrigin, int xEnd, int yEnd, java.awt.Color color, String type, int weight){
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.color = color;
        this.type = type;
        this.weight = weight;
    }

    /**
     * <p>
     * Apply a drawing to an image.
     * </p>
     * 
     * <p>
     * The drawing is done by taking the co-ordinates from
     * the users mouse selection and drawing the shape within
     * those bounds
     * </p>
     * 
     * @param input The image to be drawn on.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g2d = input.createGraphics();
        
        if(type.toLowerCase().contains("rectangle")){
            g2d.setColor(color);
            if(type.toLowerCase().contains("line")){
                g2d.setStroke(new BasicStroke(weight));
                g2d.drawRect(xOrigin, yOrigin, xEnd - xOrigin, yEnd - yOrigin);
            }
            else{
                g2d.fillRect(xOrigin, yOrigin, xEnd - xOrigin, yEnd - yOrigin);
            }
            g2d.dispose();
        }
        else if(type.toLowerCase().contains("circle")){
            g2d.setColor(color);
            if(type.toLowerCase().contains("line")){
                g2d.setStroke(new BasicStroke(weight));
                g2d.drawOval(xOrigin, yOrigin, xEnd - xOrigin, yEnd - yOrigin);
            }
            else{
                g2d.fillOval(xOrigin, yOrigin, xEnd - xOrigin, yEnd - yOrigin);
            }
            g2d.dispose();
        }
        else if(type.toLowerCase().contains("line")){
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(weight));
            g2d.drawLine(xEnd, yEnd, xOrigin, yOrigin);
            g2d.dispose();
        }else{
            g2d.setStroke(new BasicStroke(weight));
            g2d.drawRect(xOrigin, yOrigin, xEnd - xOrigin, yEnd - yOrigin);
        }
        return input;
    }
}
