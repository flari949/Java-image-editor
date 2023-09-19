package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

/**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 * 
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be
 * added.
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
public class ToolBarActions {
    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     * 
     * @throws Exception if something goes wrong.
     */
    public ToolBarActions() throws Exception {
        ImageIcon zoomin = new ImageIcon(ImageIO.read(new File("./src/zoomPlus.png")));
        ImageIcon zoomout = new ImageIcon(ImageIO.read(new File("./src/zoomMinus.png")));
        ImageIcon undoIcon = new ImageIcon(ImageIO.read(new File("./src/undoIcon.png")));
        ImageIcon redoIcon = new ImageIcon(ImageIO.read(new File("./src/redoicon.png")));
        ImageIcon fliphoriIcon = new ImageIcon(ImageIO.read(new File("./src/fliphorizontalicon.png")));
        ImageIcon flipvIcon = new ImageIcon(ImageIO.read(new File("./src/flipverticalicon.png")));
        ImageIcon rotateRIcon = new ImageIcon(ImageIO.read(new File("./src/Rotate90RIcon.png")));
        ImageIcon rotateLIcon = new ImageIcon(ImageIO.read(new File("./src/Rotate90LIcon.png")));
        ImageIcon FreeHandIcon = new ImageIcon(ImageIO.read(new File("./src/FreeHand.png")));
        actions = new ArrayList<Action>();

        EditActions editactions = new EditActions();
        EditActions.UndoAction undobutton = editactions.new UndoAction(null, undoIcon, "Undo (Ctrl + Z)", Integer.valueOf(KeyEvent.VK_Z));
        EditActions.RedoAction redobutton = editactions.new RedoAction(null, redoIcon, "Redo (Ctrl + Y)", Integer.valueOf(KeyEvent.VK_Y));

        TranslateActions translateactions = new TranslateActions();
        TranslateActions.RotateImageAction90L rotate90L = translateactions.new RotateImageAction90L(null, rotateLIcon, "Rotate 90° to the left (Ctrl + I)", Integer.valueOf(KeyEvent.VK_I), -90);
        TranslateActions.RotateImageAction90R rotate90R = translateactions.new RotateImageAction90R(null, rotateRIcon, "Rotate 90° to the right (Ctrl + R)", Integer.valueOf(KeyEvent.VK_R), 90);
        TranslateActions.FlipImageActionhorizontally fliphorizontally = translateactions.new FlipImageActionhorizontally(null, fliphoriIcon, "Flip Horizontally (Ctrl + H)", Integer.valueOf(KeyEvent.VK_H), true);
        TranslateActions.FlipImageActionvertically flipvertically = translateactions.new FlipImageActionvertically(null, flipvIcon, "Flip Vertically (Ctrl + V)", Integer.valueOf(KeyEvent.VK_V), false);
        
        ViewActions viewactions = new ViewActions();
        ViewActions.ZoomInAction zoominaction = viewactions.new ZoomInAction(null, zoomin, "Zoom In (Ctrl + =)", Integer.valueOf(KeyEvent.VK_EQUALS));
        ViewActions.ZoomOutAction Zoomoutaction = viewactions.new ZoomOutAction(null, zoomout, "Zoom Out (Ctrl + -)", Integer.valueOf(KeyEvent.VK_MINUS));
        
        FreeHand drawActions = new FreeHand();
        FreeHand.FreeHandAction freehandaction = drawActions.new FreeHandAction(null, FreeHandIcon, "Free Hand Draw (Ctrl + \\)", Integer.valueOf(KeyEvent.VK_EQUALS));

        actions.add(zoominaction);
        actions.add(Zoomoutaction);

        actions.add(undobutton);
        actions.add(redobutton);

        actions.add(freehandaction);

        actions.add(rotate90L);
        actions.add(rotate90R);
        
        actions.add(fliphorizontally);
        actions.add(flipvertically);

        
        
    }

    /**
     * <p>
     * Create a menu contianing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JToolBar createMenu() {
        JToolBar editMenu = new JToolBar(null, JToolBar.VERTICAL);

        for (Action action : actions) {
            editMenu.add(new JButton(action));
            editMenu.setAlignmentX(1);
        }
        editMenu.setFloatable(false);
        return editMenu;
    }

}
