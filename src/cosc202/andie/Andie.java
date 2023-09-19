package cosc202.andie;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.event.*;
/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various image editing and processing operations.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class Andie {

    /** Creates a new Color object */
    public static Color FinalColour = Color.red;
    /** Creates a new ImagePanel object */
    public static ImagePanel target;
    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save, edit, etc. 
     * These operations are implemented {@link ImageOperation}s and triggerd via 
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * 
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception{
        
        // Set up the main GUI frame
        JFrame frame = new JFrame("ANDIE");
        PopUp.parent = frame;
        
        Image image = ImageIO.read(new File("./src/icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main content area is an ImagePanel
        JPanel sideBar = new JPanel();
        ToolBarActions toolBarActions = new ToolBarActions(); 
        ImagePanel imagePanel = new ImagePanel();


        JLabel ZoomLabel = new JLabel("Zoom %");
        

        sideBar.add(imagePanel.zoomLabel);
        sideBar.add(ZoomLabel);
        sideBar.add(toolBarActions.createMenu());
        
        
        
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        
        
        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();

        // File menus are pretty standard, so things that usually go in File menus go here.
        FileActions fileActions = new FileActions();
        menuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, but do not alter its actual content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local window
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        // Actions that affect orientation or size of the image
        TranslateActions translateActions = new TranslateActions();
        menuBar.add(translateActions.createMenu());

        // Actions that affect the representation of colour in the image
        DrawActions drawActions = new DrawActions();
        menuBar.add(drawActions.createMenu());

        // Actions that affect the representation of colour in the image
        MacroActions macroActions = new MacroActions();
        menuBar.add(macroActions.createMenu());

        //Help documentation
        HelpActions helpactions = new HelpActions();
        menuBar.add(helpactions.createMenu());
        
        

        menuBar.add(imagePanel.macroLabel);
        menuBar.add(FreeHand.macroLabel);

        JButton b = new JButton();
        
        b.setBackground(FinalColour);
        b.setPreferredSize(new Dimension(40, 40));

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (b.isEnabled()) {
                    ColorChooser CC = new ColorChooser();
                    FinalColour = CC.color;
                    b.setBackground(FinalColour);
                    
                }
            }
        });
    
        sideBar.add(b);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,sideBar, scrollPane);



        JViewport viewport = scrollPane.getViewport();
        MouseAdapter mouseadapter = new ZoomAndPanListener();
        viewport.addMouseMotionListener(mouseadapter);
        viewport.addMouseListener(mouseadapter);
        viewport.addMouseWheelListener(mouseadapter);
        


        Dimension minimumSize = new Dimension(100, 50);
        Dimension minimumSize2 = new Dimension(5, 50);
        sideBar.setMinimumSize(minimumSize2);
        splitPane.setDividerSize(10);

        menuBar.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(50);
        
        splitPane.setEnabled( false );
        frame.setJMenuBar(menuBar);
        frame.add(splitPane);
        frame.setPreferredSize(new Dimension(700, 710));
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Function to listen for mouse action for image zoom and panning
     */
    static class ZoomAndPanListener extends MouseAdapter {
        /** Create a new ViewActions instance */
        ViewActions viewActions1 = new ViewActions();
        /** Create a mouse zoom wheel action instance */
        ViewActions.ZoomController viewActions = viewActions1.new ZoomController(null, null, null, null);
        /** Create a new Point object */
        private final Point pp = new Point();

        @Override 
        public void mouseDragged(MouseEvent e){
            JViewport viewport = (JViewport)e.getSource();
            JComponent component = (JComponent)viewport.getView();
            Point xyofsource = e.getPoint();
            Point pointy = viewport.getViewPosition();
            pointy.translate(pp.x-xyofsource.x, pp.y-xyofsource.y);
            component.scrollRectToVisible(new Rectangle(pointy, viewport.getSize()));
            pp.setLocation(xyofsource);
        }
    
        @Override 
        public void mousePressed(MouseEvent e){
            pp.setLocation(e.getPoint());
        }


        @Override 
        public void mouseWheelMoved(MouseWheelEvent e){

            
            if(e.getWheelRotation() == -1 ){
                viewActions.ZoomIn();
            }else if(e.getWheelRotation() == 1 ){
                viewActions.ZoomOut();
            }       
        }
    }
    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    System.exit(1);
                }
            }
        });
    }
}
