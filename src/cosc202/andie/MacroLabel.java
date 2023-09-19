package cosc202.andie;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.*;
/**
 * <p>
 * Class that shows the current state of recording in {@link EditableImage}
 * </p>
 */
public class MacroLabel extends JLabel {
    /**
     * The recording icon
     */
    private ImageIcon macroIcon;
    /**
     * Update the macro label
     * @param recording a boolean. True if recording
     */
    public void setMacroLabel(boolean recording){
        try {
            ImageIcon imageIcon = new ImageIcon("./src/macroIcon.png"); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it 
            Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
            macroIcon = new ImageIcon(newimg);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(recording){
            this.setIcon(macroIcon);
        } else {
            this.setIcon(null);
        }
    }
        
        
      
}



