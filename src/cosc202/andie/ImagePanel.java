package cosc202.andie;

import java.awt.*;

import javax.swing.*;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well as zooming
 * in and out. 
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ImagePanel extends JPanel {
    
    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;

    /**
     * {@link JPanel} to show to current zoom level
     */
    public ZoomLabel zoomLabel;

    /**
     * Current state of macro recording
     */
    public MacroLabel macroLabel;

    /**
     * Minimum zoom level
     */
    public static double zoomMin = 0;

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally as a percentage.
     * </p>
     */
    private double scale;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */
    public ImagePanel() {
        image = new EditableImage();
        scale = 1.0;
        zoomLabel = new ZoomLabel();
        zoomLabel.setZoom(this.getZoom());
        macroLabel = new MacroLabel();
        macroLabel.setMacroLabel(false);
    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * </p>
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100*scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage. And update the zoomLabel
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * The most it will zoom out is 1/4 the image size and there is no max zoom in.
     * </p>
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {
        if(getImage().hasImage()){
            double imageWidth = getImage().getCurrentImage().getWidth();
            double imageHeight= getImage().getCurrentImage().getHeight();
            double panelWidth = (double)getParent().getWidth();
            double panelHeight = (double)getParent().getHeight();
            double zoomFit = getFitScale(panelWidth, panelHeight, imageWidth, imageHeight);
            //as a percentage
            zoomFit *= 100;
            //allows zooming out to 1/4 of the image size
            zoomFit *= 0.25;
            if(zoomPercent < zoomFit){
                zoomPercent = zoomFit;
            }
        }
        if (zoomPercent < zoomMin) {
            zoomPercent = zoomMin;
        }
        scale = zoomPercent / 100;
        revalidate();
        zoomLabel.setZoom(this.getZoom());
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
    private static double getFitScale(double pW, double pH, double iW, double iH){
         double zoomFitWidth = pW/iW;
         double zoomFitHeight = pH/iH;

         if (iH * zoomFitWidth < pH){
             return zoomFitWidth;
         } else {
              return zoomFitHeight;
          }
     }
    
    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth()*scale), 
                                 (int) Math.round(image.getCurrentImage().getHeight()*scale));
        } else {
            return new Dimension(450, 450);
        }
    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image.hasImage()) {
            Graphics2D g2  = (Graphics2D) g.create();
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);
            g2.dispose();
        }
    }
}
