package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {

    /**
     * <p> 
     * A list of actions for the Colour menu. 
     * </p>
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction("Greyscale", null, "Convert to greyscale", Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new AdjustBrightnessAction("Brightness", null, "Adjust Brightness", Integer.valueOf(KeyEvent.VK_B)));
        actions.add(new AdjustContrastAction("Contrast", null, "Adjust Contrast", Integer.valueOf(KeyEvent.VK_C)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Colour");

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if(!target.getImage().hasImage()){
                //System.out.println("Export error handling");
                PopUp.showMessageDialog("Error: No image to apply change to!");

            } else {
                target.getImage().apply(new ConvertToGrey());
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to adjust and image's brightness.
     * </p>
     * 
     * @see AdjustBrightness
     */
    public class AdjustBrightnessAction extends ImageAction {

        /**
         * <p>
         * Create a new AdjustBrightness action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        AdjustBrightnessAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the AdjustBrightness action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the AdjustBrightnessAction is triggered.
         * It changes the image's brightness.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if(!target.getImage().hasImage()){
                //System.out.println("Export error handling");
                PopUp.showMessageDialog("Error: No image to apply change to!");

            } else {
                double brightness = PopUp.getSliderInt("Enter Brightness Value", 0, -255, 255, 10);

                target.getImage().apply(new AdjustBrightness(brightness));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to adjust and image's contrast.
     * </p>
     * 
     * @see AdjustContrast
     */
    public class AdjustContrastAction extends ImageAction {

        /**
         * <p>
         * Create a new AdjustContrast action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        AdjustContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the AdjustContrast action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the AdjustContrastAction is triggered.
         * It changes the image's contrast.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if(!target.getImage().hasImage()){
                //System.out.println("Export error handling");
                PopUp.showMessageDialog("Error: No image to apply change to!");

            } else {
                double contrast = PopUp.getSliderInt("Enter Contast Value", 0, -100, 100, 10);

                // If they entered a value outside of the range `contrast` will be zero so we should alert them and cancel the operation
                if(contrast == 0) {
                    PopUp.showMessageDialog("Please choose a value within range -1000 to 1000");
                    return;
                }

                target.getImage().apply(new AdjustContrast(contrast));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

}
