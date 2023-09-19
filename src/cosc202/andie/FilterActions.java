package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Cursor;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
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
public class FilterActions {

    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();
        actions.add(new MeanFilterAction("Mean filter", null, "Apply a Mean filter", Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new SharpenFilterAction("Sharpen Filter", null, "Apply a Sharpen filter", Integer.valueOf(KeyEvent.VK_J)));
        actions.add(new GaussianFilterAction("Gaussian Filter", null, "Apply a Gaussian filter", Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new MedianFilterAction("Median Filter", null, "Apply a Median filter", Integer.valueOf(KeyEvent.VK_D)));
        actions.add(new PosteriseFilterAction("Posterise Filter", null, "Apply a Posterise filter", Integer.valueOf(KeyEvent.VK_P)));
        //actions.add(new EmbossFilterAction("Emboss Filter", null, "Apply an Emboss filter", Integer.valueOf(KeyEvent.VK_E)));
        
        actions.add(new EmbossFilterAction("Emboss 1", null, "Emboss filter 0", KeyEvent.VK_0));
        actions.add(new EmbossFilterAction("Emboss 2", null, "Emboss filter 1", KeyEvent.VK_1));
        actions.add(new EmbossFilterAction("Emboss 3", null, "Emboss filter 2", KeyEvent.VK_2));
        actions.add(new EmbossFilterAction("Emboss 4", null, "Emboss filter 3", KeyEvent.VK_3));
        actions.add(new EmbossFilterAction("Emboss 5", null, "Emboss filter 4", KeyEvent.VK_4));
        actions.add(new EmbossFilterAction("Emboss 6", null, "Emboss filter 5", KeyEvent.VK_5));
        actions.add(new EmbossFilterAction("Emboss 7", null, "Emboss filter 6", KeyEvent.VK_6));
        actions.add(new EmbossFilterAction("Emboss 8", null, "Emboss filter 7", KeyEvent.VK_7));
        actions.add(new EmbossFilterAction("Sobel Vertical", null, "Emboss filter 8", KeyEvent.VK_8));
        actions.add(new EmbossFilterAction("Sobel Horizontal", null, "Emboss filter 9", KeyEvent.VK_9));
        
    }
 

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Filter");

        for (int i = 0; i < 5; i++){
        //for (Action action : actions) {
            fileMenu.add(actions.get(i));
        }

        JMenu embossMenu = new JMenu("Emboss");
        fileMenu.add(embossMenu);

        for (int e = 5; e < actions.size(); e++){
            embossMenu.add(actions.get(e));
        }

        return fileMenu;
    }

    

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
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
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if(!target.getImage().hasImage()){
                //System.out.println("Export error handling");
                PopUp.showMessageDialog("Error: No image to apply filter to!");

            } else {
                // Determine the radius - ask the user.
                int radius = PopUp.getSliderInt("Enter Filter radius", 1, 1, 10, 1);
                if(radius != -1 || radius != 0){
                    // Create and apply the filter
                    target.getImage().apply(new MeanFilter(radius));
                    target.repaint();
                    target.getParent().revalidate();
                }
            }
        }

    }

    /**
     * <p>
     * Action to sharpen an image with a sharpen filter.
     * </p>
     * 
     * @see SharpenFilter
     */
    public class SharpenFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sharpen-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * This method is called whenever the SharpenFilterAction is triggered.
         * It applies a Sharpen filter to the image
         * {@link SharpenFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            if(!target.getImage().hasImage()){
                //System.out.println("Export error handling");
                PopUp.showMessageDialog("Error: No image to apply filter to!");

            } else {
                target.getImage().apply(new SharpenFilter());
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to blur an image with a gaussian filter.
     * </p>
     * 
     * @see GaussianFilter
     */
    public class GaussianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new gaussian-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        GaussianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * This method is called whenever the GaussianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link GaussianFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            if(!target.getImage().hasImage()){
                //System.out.println("Export error handling");
                PopUp.showMessageDialog("Error: No image to apply filter to!");

            } else {
                int radius = PopUp.getSliderInt("Enter Filter radius", 1, 1, 10, 1);
                if(radius != -1  || radius != 0){
                    target.getImage().apply(new GaussianFilter(radius));
                    target.repaint();
                    target.getParent().revalidate();
                }
            }
        }
    }

    /**
     * <p>
     * Action to blur an image with a median filter.
     * </p>
     * 
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /** 
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MedianFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            if(!target.getImage().hasImage()){
                //System.out.println("Export error handling");
                PopUp.showMessageDialog("Error: No image to apply filter to!");

            } else {
                
                int radius = PopUp.getSliderInt("Enter Filter radius", 1, 1, 10, 1);
                
                if(radius != -1 || radius != 0){
                    target.getImage().apply(new MedianFilter(radius));
                    target.repaint();
                    target.getParent().revalidate();
                }
            }
        }
    }

    /**
     * <p>
     * Action to posterise an image.
     * </p>
     * 
     * @see PosteriseFilter
     */
    public class PosteriseFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        PosteriseFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /** 
         * <p>
         * This method is called whenever the PosteriseFilterAction is triggered.
         * It prompts the user for a k value, then applys an appropriate 
         * {@link PosteriseFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            if(!target.getImage().hasImage()){
                //System.out.println("Export error handling");
                PopUp.showMessageDialog("Error: No image to apply filter to!");

            } else {
                int k = PopUp.getSpinnerInt("Enter k radius", 5, 5, 256, 5);
                if(k != -1){
                    target.getParent().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    target.getImage().apply(new PosteriseFilter(k));
                    target.repaint();
                    target.getParent().revalidate();
                    target.getParent().setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        }
    }

    /**
     * <p>
     * Action to emboss an image.
     * </p>
     * 
     * @see EmbossFilter
     */
    public class EmbossFilterAction extends ImageAction {
        /** The emboss filter to be applied */
        private String embossFilter;
        /**
         * <p>
         * Create a new median-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.embossFilter = desc;
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /** 
         * <p>
         * This method is called whenever the EmbossFilter is triggered.
         * {@link EmbossFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            if(!target.getImage().hasImage()){
                //System.out.println("Export error handling");
                PopUp.showMessageDialog("Error: No image to apply filter to!");

            } else {
                //int direction = PopUp.getSliderInt("Entry type 1-10", 1, 1, 10, 1);
                //if(direction != -1){
                    target.getImage().apply(new EmbossFilter(embossFilter));
                    target.repaint();
                    target.getParent().revalidate();
                //}
            }
        }
    }
}
