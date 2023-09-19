package cosc202.andie;
 
import java.awt.*;
import javax.swing.*;  

/**
 * <p>
 * Class to create a color chooser popup
 * </p>
 * 
 * <p>
 * Creates a JColorChooser and stores the chosen color
 * </p>
 */
public class ColorChooser extends JFrame {
    /** A new Color object */
    Color color;

    /**
     * <p>
     * Construct a ColorChooser
     * </p>
     * 
     * <p>
     * This can be called any time a shape is to be drawn
     * to streamline to color selecting process
     * </p>
     * 
     */
    public ColorChooser(){
        Color initialcolor=Color.RED;    
        Color color=JColorChooser.showDialog(this,"Select a color",initialcolor);
        this.color = color;
    }
}
