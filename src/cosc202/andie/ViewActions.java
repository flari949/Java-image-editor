//This is my edit to ViewActions.java
package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the View menu.
 * </p>
 * 
 * <p>
 * The View menu contains actions that affect how the image is displayed in the application.
 * These actions do not affect the contents of the image itself, just the way it is displayed.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ViewActions {
    
    /**
     * A list of actions for the View menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     */
    public ViewActions() {
        actions = new ArrayList<Action>();
        actions.add(new ZoomInAction("Zoom In", null, "Zoom In", Integer.valueOf(KeyEvent.VK_EQUALS)));
        actions.add(new ZoomOutAction("Zoom Out", null, "Zoom Out", Integer.valueOf(KeyEvent.VK_MINUS)));
        actions.add(new ZoomFitAction("Zoom Fit", null, "Zoom Fit", Integer.valueOf(KeyEvent.VK_BACK_SLASH)));
        actions.add(new ZoomFullAction("Zoom Full", null, "Zoom Full", Integer.valueOf(KeyEvent.VK_BACK_SLASH)));
    }

    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     * 
     * @return The view menu UI element.
     */
    public JMenu createMenu() {
        JMenu viewMenu = new JMenu("View");

        for (Action action: actions) {
            viewMenu.add(new JMenuItem(action));
        }

        return viewMenu;
    }

    /**
     * <p>
     * Action to zoom in on an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomInAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
            
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl pressed EQUALS"));
        }

        /**
         * <p>
         * Callback for when the zoom-in action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomInAction is triggered.
         * It increases the zoom level by 10%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(target.getZoom()+10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to zoom out of an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomOutAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-out action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        
        
        /**
         * <p>
         * Callback for when the zoom-out action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomOutAction is triggered.
         * It decreases the zoom level by 10%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(target.getZoom()-10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to reset the zoom level to make the image fit the screen.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomFitAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-fit action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomFitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the zoom-Fit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomFitAction is triggered.
         * Makes the image maximally fit into the panel
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if(target.getImage().hasImage()){
                
                double imageWidth= target.getImage().getCurrentImage().getWidth();
                double imageHeight= target.getImage().getCurrentImage().getHeight();
                double panelWidth = (double)target.getParent().getWidth();
                double panelHeight = (double)target.getParent().getHeight();          

                double zoomFitScale = getFitScale(panelWidth, panelHeight, imageWidth, imageHeight);
                
                target.setZoom(zoomFitScale*100);
                target.repaint();
                target.getParent().revalidate();    
            }
        }

        /**
         * Method to test the getFitScale method
         * @param pW panel width
         * @param pH panel height
         * @param iW image width
         * @param iH image height
         * @return The appropriate scale
         */
        public double testGetFitScale(double pW, double pH, double iW, double iH) {
            return getFitScale(pW, pH, iW, iH);
        }

        /**
         * Method to calculate the appropraite scale so that the image 
         * fits completely and maximally within a panel
         * @param pW panel width
         * @param pH panel height
         * @param iW image width
         * @param iH image height
         * @return The appropriate scale
         */
        private double getFitScale(double pW, double pH, double iW, double iH){
            double zoomFitWidth = pW/iW;
            double zoomFitHeight = pH/iH;

            if (iH * zoomFitWidth < pH){
                return zoomFitWidth;
            } else {
                return zoomFitHeight;
            }
        }

    }

    /**
     * <p>
     * Action to reset the zoom level to actual size.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomFullAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-full action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl pressed BACK_SLASH"));
        }

        /**
         * <p>
         * Callback for when the zoom-full action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomFullAction is triggered.
         * It resets the Zoom level to 100% so the image can be seen in its original size.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(100);
            target.repaint();
            target.getParent().revalidate();
        }

    }
    /**
     * <p>
     * Action to manage the mouse wheel zoom actions
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomController extends ImageAction {
        /**
         * <p>
         * Create a new zoom controller.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomController(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * Increases the image zoom by 10%
         */
        public void ZoomIn(){
            target.setZoom(target.getZoom()+10);
            target.repaint();
            target.getParent().revalidate();
        }

        /**
         * Decreases the image zoom by 10%
         */
        public void ZoomOut(){
            target.setZoom(target.getZoom()-10);
            target.repaint();
            target.getParent().revalidate();
        }
        
        public void actionPerformed(ActionEvent e) {
        }
    }



}
