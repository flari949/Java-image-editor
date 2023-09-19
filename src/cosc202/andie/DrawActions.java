package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

 /**
 * <p>
 * Actions provided by the Draw menu.
 * </p>
 * 
 * <p>
 * The Draw menu handles the events that enable the
 * user to draw shapes of different size and color onto the
 * image
 * </p>
 * 
 */
public class DrawActions {
    
    /** A list of actions for the Draw menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Draw menu actions.
     * </p>
     */
    public DrawActions() {
        actions = new ArrayList<Action>();

        actions.add(new DrawAction("Fill", null, "Rectangle", KeyEvent.VK_1));
        actions.add(new DrawAction("Outline", null, "Rectangle Outline", KeyEvent.VK_2));
        actions.add(new DrawAction("Fill", null, "Circle", KeyEvent.VK_3));
        actions.add(new DrawAction("Outline", null, "Circle Outline", KeyEvent.VK_4));
        actions.add(new DrawAction("Line", null, "Draw Line", KeyEvent.VK_5));
    }

    /**
     * <p>
     * Create a menu containing the list of Draw actions.
     * </p>
     * 
     * @return The draw menu UI element.
     */
    public JMenu createMenu() {
        JMenu drawMenu = new JMenu("Draw");
        JMenu rectangleMenu = new JMenu("Rectangle");
        JMenu circleMenu = new JMenu("Circle");

        drawMenu.add(rectangleMenu);
        drawMenu.add(circleMenu);
        drawMenu.add(actions.get(4));

        rectangleMenu.add(actions.get(0));
        rectangleMenu.add(actions.get(1));

        circleMenu.add(actions.get(2));
        circleMenu.add(actions.get(3));

        return drawMenu;
    }

    /**
     * <p>
     * Action to draw a shape on the image.
     * </p>
     *
     */
    public class DrawAction extends ImageAction {
        /** The shape to be drawn */
        private String shape;
        /**
         * <p>
         * Create a new draw action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        DrawAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.shape = desc;
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the draw action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the DrawAction is triggered.
         * It uses the DrawShapes + AreaSelect classes to draw a shape
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if(!target.getImage().hasImage()){
                PopUp.showMessageDialog("Error: No image to draw on!");

            } else {
                new AreaSelect(target, shape);
            }
        }
    }
}
