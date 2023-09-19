
package cosc202.andie;

import java.util.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates class for free hand drawing
 */
public class FreeHand {
    /** Creates an instance of Andie */
    Andie a = new Andie();
    /** Sets target imagepanel */
    ImagePanel target = Andie.target;
    /** Type of drawing (line settings) */
    String type;
    /** Represents if function is on */
    boolean on = false;
    /** Creates new JLabel */
    public static JLabel macroLabel = new JLabel("");
    /** Creates new list of actions */
    protected ArrayList<Action> actions;

    /** Method to initialise function accessability */
    public FreeHand() {
        actions = new ArrayList<Action>();
        actions.add(new FreeHandAction("Free Hand Drawing", null, "Free Hand Drawing",
                Integer.valueOf(KeyEvent.VK_BACK_SLASH)));
    }

    /**
     * Creates a new menu
     * 
     * @return returns the created menu
     */
    public JMenu createMenu() {
        JMenu viewMenu = new JMenu("View");

        for (Action action : actions) {
            viewMenu.add(new JMenuItem(action));
        }

        return viewMenu;
    }

    /**
     * Records mouse movement and traces with a line
     */
    public class FreeHandAction extends ImageAction {

        /**
         * Create new freehand drawing action
         * 
         * @param name     name of action
         * @param icon     icon of button
         * @param desc     description of function value
         * @param mnemonic the key associated with the function
         */
        FreeHandAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the FreeHand action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FreeHandAction is triggered.
         * It prompts the user to select a file and exports it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            
            if(on == true){
                on = false;
                macroLabel.setText("");
            }else if(on == false){

                if(!target.getImage().hasImage()){
                    PopUp.showMessageDialog("Error: No image to draw on!");

                } else {
                    macroLabel.setText("RIGHT CLICK TO CHANGE PEN SIZE");
                    on = true;
                    new FreeHandDraw(target);
                }
            }
        }

    }

    /**
     * Draws following the mouse's path
     */
    public class FreeHandDraw implements MouseListener, MouseMotionListener {
        /** Values representing the line */
        int xOrigin, yOrigin, xEnd, yEnd, weight = 10;
        /** The image's zoom value */
        double zoomLevel;
        /** The target image panel */
        ImagePanel target;
        /** The type of line */
        String type;
        /** The colour of the line */
        Color FinalColour = Andie.FinalColour;
        /** Whether the line is the first */
        private boolean first = true;

        /**
         * Draws the line following the mouse
         * 
         * @param target the target to be drawn on
         */
        public FreeHandDraw(ImagePanel target) {
            this.target = target;
            target.addMouseListener(this);
            target.addMouseMotionListener(this);
        }

        /** Function to follow the mouse's position */
        public void mouseDragged(MouseEvent e) {
            xOrigin = xEnd;
            yOrigin = yEnd;
            xEnd = (int) (e.getX() / zoomLevel);
            yEnd = (int) (e.getY() / zoomLevel);

            if (first) {
                xOrigin = xEnd;
                yOrigin = yEnd;
                first = false;
            }
            String type = "Line";
            target.getImage().apply(new DrawShapes(xOrigin, yOrigin, xEnd, yEnd, FinalColour, type, weight));
            target.repaint();
            target.getParent().revalidate();

        }

        /** Determines if the mouse has been pressed 
         * @param e The mouse event
        */
        @Override
        public void mousePressed(MouseEvent e){
            
            if (SwingUtilities.isLeftMouseButton(e)) {
                zoomLevel = target.getZoom() / 100;
                xOrigin = (int) (e.getX() / zoomLevel);
                yOrigin = (int) (e.getY() / zoomLevel);
            }

            if (SwingUtilities.isRightMouseButton(e)) {
                weight = Math.abs(PopUp.getSliderInt("Enter Line Weight", 1, 1, 50, 5));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e){
            
            
            if(on == true){
                macroLabel.setText("");

                xEnd = (int) (e.getX()/zoomLevel);
                yEnd = (int) (e.getY()/zoomLevel);

                target.removeMouseListener(this);
                target.removeMouseMotionListener(this);

                if(type != "Draw Line"){
                    if(xOrigin > xEnd){
                        int n = xOrigin;
                        xOrigin = xEnd;
                        xEnd = n;
                    }
                    if(yOrigin > yEnd){
                        int n = yOrigin;
                        yOrigin = yEnd;
                        yEnd = n;
                    }
                }  
            }

             
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }

}
