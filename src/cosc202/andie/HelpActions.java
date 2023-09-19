package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the help menu
 * </p>
 * 
 * <p>
 * Provides information about the shortcuts
 * </p>
 */
public class HelpActions {

    /**
     * <p>
     * A List of actions for the help menu
     * </p>
     */
    protected static ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of help menu actions
     * </p>
     */
    public HelpActions() {
        actions = new ArrayList<Action>();
        actions.add(new ShortcutsAction("Shortcuts", null, "Lists all shortcuts", Integer.valueOf(KeyEvent.VK_O)));
    }

    /**
     * <p>
     * Create a menu containing the list of Menu actions
     * </p>
     * 
     * @return The fileMenu UI element
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Help");

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to show the shortcuts
     * </p>
     */
    public class ShortcutsAction extends ImageAction {

        /**
         * <p>
         * Create a new shortcuts actions
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represen the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ShortcutsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl pressed H"));
        }

        /**
         * <p>
         * Callback for when the shortcuts action if performed
         * </p>
         * 
         * <p>
         * This method is called whenever the ShortcutsAction is triggered
         * It shows all the shortcuts in a pop up menu
         * </p>
         * 
         * @param e The event triggering this callback
         */
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<String> l1 = new DefaultListModel<>();
            DefaultListModel<String> l2 = new DefaultListModel<>();
            l1.addElement("Help           ");
            l1.addElement("Open           ");
            l1.addElement("Save           ");
            l1.addElement("Save As        ");
            l1.addElement("Export         ");
            l1.addElement("Exit           ");
            l1.addElement("Undo           ");
            l1.addElement("Redo           ");
            l1.addElement("Crop           ");
            l1.addElement("Image panning  ");
            l1.addElement("Image Zooming  ");
            l1.addElement("Zoom In        ");
            l1.addElement("Zoom Out       ");
            l1.addElement("Zoom Fit       ");
            l1.addElement("Zoom Full      ");
            l1.addElement("Mean Filter    ");
            l1.addElement("Sharpen Filter ");
            l1.addElement("Gaussian Filter");
            l1.addElement("Median Filter  ");
            l1.addElement("Posterise Filter");
            l1.addElement("Emboss Filters");
            l1.addElement("Greyscale      ");
            l1.addElement("Brightness     ");
            l1.addElement("Contrast       ");
            l1.addElement("Rotate 180     ");
            l1.addElement("Rotate Left 90 ");
            l1.addElement("Rotate Right 90");
            l1.addElement("Flip Horizontal");
            l1.addElement("Flip Vertical  ");
            l1.addElement("Scale 150%     ");
            l1.addElement("Scale 50%      ");
            

            l2.addElement("|                  CTRL + Shift + H");
            l2.addElement("|                  CTRL + O");
            l2.addElement("|                  CTRL + S");
            l2.addElement("|                  CTRL + Shift + S");
            l2.addElement("|                  CTRL + E");
            l2.addElement("|                  CTRL + W");
            l2.addElement("|                  CTRL + Z");
            l2.addElement("|                  CTRL + Y");
            l2.addElement("|                  CTRL + Q");
            l2.addElement("|                  Mouse Button 1");
            l2.addElement("|                  Mouse Scroll Wheel");
            l2.addElement("|                  CTRL + =");
            l2.addElement("|                  CTRL + -");
            l2.addElement("|                  CTRL + \\");
            l2.addElement("|                  CTRL + Shift + \\");
            l2.addElement("|                  CTRL + M");
            l2.addElement("|                  CTRL + J");
            l2.addElement("|                  CTRL + G");
            l2.addElement("|                  CTRL + D");
            l2.addElement("|                  CTRL + P");
            l2.addElement("|                  CTRL + 0-9");
            l2.addElement("|                  CTRL + A");
            l2.addElement("|                  CTRL + B");
            l2.addElement("|                  CTRL + C");
            l2.addElement("|                  CTRL + F");
            l2.addElement("|                  CTRL + I");
            l2.addElement("|                  CTRL + R");
            l2.addElement("|                  CTRL + H");
            l2.addElement("|                  CTRL + V");
            l2.addElement("|                  CTRL + ,");
            l2.addElement("|                  CTRL + .");
            
            JList<String> list = new JList<>(l1);
            JList<String> list2 = new JList<>(l2);
            list.setSelectionModel(new NoSelectionModel());
            list2.setSelectionModel(new NoSelectionModel());
            JPanel panel = new JPanel(new BorderLayout());
            JPanel panel1 = new JPanel();
            panel1.add(list);
            JPanel panel2 = new JPanel();
            panel2.add(list2);
            JScrollPane scrollPane = new JScrollPane();

            list.setLayoutOrientation(JList.VERTICAL);
            list2.setLayoutOrientation(JList.VERTICAL);
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel1, panel2);
            panel1.setBackground(Color.white);
            panel2.setBackground(Color.white);
            splitPane.setOneTouchExpandable(false);
            splitPane.setDividerSize(0);
            scrollPane.setViewportView(splitPane);

            panel.add(scrollPane);

            JFrame frame = new JFrame("ANDIE | Shortcuts");
            Image image;
            try {
                image = ImageIO.read(new File("./src/icon.png"));
                frame.setIconImage(image);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            frame.add(panel);
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }

    }

    /**
     * <p>
     * Class to determine default data
     * </p>
     */
    private static class NoSelectionModel extends DefaultListSelectionModel {

        /**
         * <p>
         * Set the anchor selection index, leaving all selection values unchanged. If
         * leadAnchorNotificationEnabled is true, send a notification covering the old
         * and new anchor cells.
         * </p>
         * @param anchorIndex New anchor selection index.
         */
        @Override
        public void setAnchorSelectionIndex(final int anchorIndex) {
        }

        /**
         * <p>
         * Sets the value of the leadAnchorNotificationEnabled flag.
         * </p>
         * @param flag The value of the new leadAnchorNotification flag.
         */
        @Override
        public void setLeadAnchorNotificationEnabled(final boolean flag) {
        }

        /**
         * <p>
         * Sets the lead selection index
         * </p>
         * @param leadIndex Lead index value.
         */
        @Override
        public void setLeadSelectionIndex(final int leadIndex) {
        }

        /**
         * <p>
         * Changes the selection to be between index0 and index1 inclusive.
         * </p>
         * @param index0 New primary index value.
         * @param index1 New secondary index value.
         */
        @Override
        public void setSelectionInterval(final int index0, final int index1) {
        }
    }

}
