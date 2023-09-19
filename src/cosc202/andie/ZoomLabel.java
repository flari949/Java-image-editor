package cosc202.andie;

import javax.swing.*;
import java.text.*;
import java.awt.*;
import javax.swing.border.Border;
/**
 * Class that shows the current zoom level of an {@link ImagePanel}
 */
public class ZoomLabel extends JLabel {
    /** A DecimalFormat object to format parsed number values */
    DecimalFormat df = new DecimalFormat("##");
    /**
     * Update the zoom label
     * @param zoom The new zoom level
     */
    public void setZoom(double zoom){
        
        this.setText(df.format(zoom));
        this.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        this.setForeground(Color.black);
        Border border = BorderFactory.createBevelBorder(1);
        this.setBorder(border);
        revalidate();
    }    
}
