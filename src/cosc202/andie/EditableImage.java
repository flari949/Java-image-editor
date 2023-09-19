package cosc202.andie;

import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
/**
 * <p>
 * An image with a set of operations applied to it.
 * </p>
 * 
 * <p>
 * The EditableImage represents an image with a series of operations applied to it.
 * It is fairly core to the ANDIE program, being the central data structure.
 * The operations are applied to a copy of the original image so that they can be undone.
 * THis is what is meant by "A Non-Destructive Image Editor" - you can always undo back to the original image.
 * </p>
 * 
 * <p>
 * Internally the EditableImage has two {@link BufferedImage}s - the original image 
 * and the result of applying the current set of operations to it. 
 * The operations themselves are stored on a {@link Stack}, with a second {@link Stack} 
 * being used to allow undone operations to be redone.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class EditableImage {

    /** <p> The original image. This should never be altered by ANDIE. </p> */
    private BufferedImage original;
    /** <p> The current image, the result of applying {@link ops} to {@link original}. </p> */
    private BufferedImage current;
    /** <p> The sequence of operations currently applied to the image. </p> */
    Stack<ImageOperation> ops;
    /** <p> A memory of 'undone' operations to support 'redo'. </p> */
    Stack<ImageOperation> redoOps;
    /** <p> The file where the original image is stored/ </p> */
    private String imageFilename;
    /** <p> The file where the operation sequence is stored. </p> */
    private String opsFilename;
    /** <p> The sequence of operations being recorded. </p> */
    private Stack<ImageOperation> macroOps = new Stack<ImageOperation>();
    /** <p> The sequence of operations being recorded. </p> */
    private boolean recording = false;


    /**
     * <p>
     * Create a new EditableImage.
     * </p>
     * 
     * <p>
     * A new EditableImage has no image (it is a null reference), and an empty stack of operations.
     * </p>
     */
    public EditableImage() {
        original = null;
        current = null;
        ops = new Stack<ImageOperation>();
        redoOps = new Stack<ImageOperation>();
        imageFilename = null;
        opsFilename = null;
    }

    /**
     * <p>
     * Check if there is an image loaded.
     * </p>
     * 
     * @return True if there is an image, false otherwise.
     */
    public boolean hasImage() {
        return current != null;
    }

    /**
     * <p>
     * Make a 'deep' copy of a BufferedImage. 
     * </p>
     * 
     * <p>
     * Object instances in Java are accessed via references, which means that assignment does
     * not copy an object, it merely makes another reference to the original.
     * In order to make an independent copy, the {@code clone()} method is generally used.
     * {@link BufferedImage} does not implement {@link Cloneable} interface, and so the 
     * {@code clone()} method is not accessible.
     * </p>
     * 
     * <p>
     * This method makes a cloned copy of a BufferedImage.
     * This requires knowledge of some details about the internals of the BufferedImage,
     * but essentially comes down to making a new BufferedImage made up of copies of
     * the internal parts of the input.
     * </p>
     * 
     * <p>
     * This code is taken from StackOverflow:
     * <a href="https://stackoverflow.com/a/3514297">https://stackoverflow.com/a/3514297</a>
     * in response to 
     * <a href="https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage">https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage</a>.
     * Code by Klark used under the CC BY-SA 2.5 license.
     * </p>
     * 
     * <p>
     * This method (only) is released under <a href="https://creativecommons.org/licenses/by-sa/2.5/">CC BY-SA 2.5</a>
     * </p>
     * 
     * @param bi The BufferedImage to copy.
     * @return A deep copy of the input.
     */
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    /**
     * <p>
     * Open an image from a file.
     * </p>
     * 
     * <p>
     * Opens an image from the specified file.
     * Also tries to open a set of operations from the file with <code>.ops</code> added.
     * So if you open <code>some/path/to/image.png</code>, this method will also try to
     * read the operations from <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param filePath The file to open the image from.
     * @throws IOException If something goes wrong.
     */
    public void open(String filePath) throws IOException{
        ops.clear();
        redoOps.clear();
        imageFilename = filePath;
        opsFilename = imageFilename.substring(0, imageFilename.length() - 4) + ".ops";
        try {
            File imageFile = new File(imageFilename);
            original = ImageIO.read(imageFile);
            current = deepCopy(original);
            System.out.println("opened: " + imageFilename);
        } catch (FileNotFoundException ex){
            System.out.println(ex);
            PopUp.showMessageDialog("Could not create copy due to invalid file name");
        } 
        
        try {
            FileInputStream fileIn = new FileInputStream(this.opsFilename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            // Silence the Java compiler warning about type casting.
            // Understanding the cause of the warning is way beyond
            // the scope of COSC202, but if you're interested, it has
            // to do with "type erasure" in Java: the compiler cannot
            // produce code that fails at this point in all cases in
            // which there is actually a type mismatch for one of the
            // elements within the Stack, i.e., a non-ImageOperation.
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
            ops = opsFromFile;
            objIn.close();
            fileIn.close();
            System.out.println("opened " + opsFilename);
        } catch (FileNotFoundException ex){
            System.out.println(ex);
            //PopUp.showMessageDialog("no .ops file found");
            //when a new image is opened that doesn't have a .obs file.
        }catch (Exception ex){
            System.out.println(ex);
            PopUp.showMessageDialog("Unknown error");
            //Unknown Error
        }
        this.refresh();
    }

    /**
     * <p>
     * Save an image to file.
     * </p>
     * 
     * <p>
     * Saves an image to the file it was opened from, or the most recent file saved as.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @throws IOException If something goes wrong.
     */
    public void save() throws IOException{
        try{
            if (this.opsFilename == null) {
                this.opsFilename = this.imageFilename + ".ops";
            }
            // Write image file based on file extension
            String extension = imageFilename.substring(1+imageFilename.lastIndexOf(".")).toLowerCase();
            ImageIO.write(original, extension, new File(imageFilename));
            // Write operations file
            FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(this.ops);
            objOut.close();
            fileOut.close();
            System.out.println("saved " + this.imageFilename + " with " + this.opsFilename);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            PopUp.showMessageDialog("FileNotFoundException : Check file name and/or type");
        }catch (IllegalArgumentException ex){
            System.out.println(ex);
            PopUp.showMessageDialog("Error: There is no image to save!");
        } catch (NullPointerException ex){
            System.out.println(ex);
            PopUp.showMessageDialog("Error: There is nothing to save!");
        } catch (Exception ex){
            System.out.println(ex);
            PopUp.showMessageDialog(ex.getMessage());
        } 
    }


    /**
     * <p>
     * Save an image to a specified file.
     * </p>
     * 
     * <p>
     * Saves an image to the file provided as a parameter.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param newName The file location to save the image to.
     * 
     * @throws FileNotFoundException If something goes wrong.
     */
    public void saveAs(String newName) throws FileNotFoundException {
        try{            
            if(!newName.contains(".")){ //if the user added their own extension
                newName += ".png";
            }

            if(new File(newName).exists()){
                throw new Exception("File already exists");
            } else {
                this.imageFilename = newName;
                this.opsFilename = newName.substring(0, newName.lastIndexOf(".")) + ".ops";
                save();
            }
        } catch (Exception ex){
            System.out.println(ex);
            PopUp.showMessageDialog(ex.getMessage());
        }
    }

     /**
     * <p>
     * Export an image to a specified file.
     * </p>
     * 
     * @param imageFilename The file location to save the image to.
     * @throws FileNotFoundException If something goes wrong.
     */
    public void export(String imageFilename) throws FileNotFoundException {
        try{
            this.imageFilename = imageFilename;
            String extension = imageFilename.substring(1+imageFilename.lastIndexOf(".")).toLowerCase();
            ImageIO.write(current, extension, new File(imageFilename));
        } catch (IOException ex) {
            System.out.println(ex);
            PopUp.showMessageDialog("Error: Invalid filename! Please try again with a valid filename.");
        }catch (Exception ex) {
            System.out.println(ex);
            PopUp.showMessageDialog("Unknown error");
        }
    }

    /**
     * <p>
     * Apply an {@link ImageOperation} to this image.
     * </p>
     * 
     * @param op The operation to apply.
     */
    public void apply(ImageOperation op) {
        current = op.apply(current);
        ops.add(op);
        if(recording) macroOps.add(op);
    }

    /**
     * <p>
     * Undo the last {@link ImageOperation} applied to the image.
     * </p>
     */
    public void undo() {
        try{
            redoOps.push(ops.pop());
            refresh();
        } catch (EmptyStackException ex) {
            System.out.println(ex);
            PopUp.showMessageDialog("Stack is empty. No more Undos");
        }
    }

    /**
     * <p>
     * Reapply the most recently {@link undo}ne {@link ImageOperation} to the image.
     * </p>
     */
    public void redo()  {
        try{
            apply(redoOps.pop());
        } catch (EmptyStackException ex){
            System.out.println(ex);
            PopUp.showMessageDialog("Stack is empty. No more redos");
        }
    }

    /**
     * <p>
     * Get the current image after the operations have been applied.
     * </p>
     * 
     * @return The result of applying all of the current operations to the {@link original} image.
     */
    public BufferedImage getCurrentImage() {
        return current;
    }
    /**
     * <p>
     * Replaces the current .ops file.
     * </p>
     * @param opsFilePath file path of the .ops file
     */
    public void setOps(String opsFilePath) {
        try {
            
            FileInputStream fileIn = new FileInputStream(opsFilePath);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
            ops = opsFromFile;
            objIn.close();
            fileIn.close();

        } catch (Exception e){
            System.out.println(e);
            PopUp.showMessageDialog("Unknown error");
            //Unknown Error
        }
        this.refresh();

    }
    
    /**
     * <p>
     * adds operations from a .ops file to the current .ops file.
     * </p>
     * @param opsFilePath file path of the .ops file
     */
    public void addOps(String opsFilePath) {
        try {
            
            FileInputStream fileIn = new FileInputStream(opsFilePath);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
            Stack<ImageOperation> holder = new Stack<ImageOperation>();
            while(!opsFromFile.empty()){
                holder.add(opsFromFile.pop());
            }
            while(!holder.empty()){
                ops.add(holder.pop());
                this.refresh();
            }
            
            objIn.close();
            fileIn.close();
            

        } catch (Exception e){
            System.out.println(e);
            PopUp.showMessageDialog("Unknown error");
            //Unknown Error
        }
        

    }

    /**
     * <p>
     * adds operations from a .ops file to the current .ops file.
     * </p>
     * @param recording sets status of recording variable
     */
    public void setRecording(boolean recording) {
        this.recording = recording;
    }
    /**
     * <p>
     * Returns recording status as boolean.
     * </p>
     * @return boolean of recording status
     * 
     * 
     */
    public boolean getRecording() {
        return recording;
    }
    /**
     * <p>
     * Saves macro ops file.
     * </p>
     * @param macroFilePath the file path of the macro
     * @throws FileNotFoundException if file does not exist
     */
    public void saveRecording(String macroFilePath) throws FileNotFoundException {
        
        //String macroOpsFilename = PopUp.showInputDialog("Please enter file name for macro");
        //String fileExtension = "ops";
        
        try{
            FileOutputStream fileOut = new FileOutputStream(macroFilePath);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(this.macroOps);
            objOut.close();
            fileOut.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            PopUp.showMessageDialog("Error: Incorrect File name!");
            
        } catch (Exception ex) {
            System.out.println(ex);
            PopUp.showMessageDialog(ex.getMessage());
        }
    }
    

    /**
     * <p>
     * Reapply the current list of operations to the original.
     * </p>
     * 
     * <p>
     * While the latest version of the image is stored in {@link current}, this
     * method makes a fresh copy of the original and applies the operations to it in sequence.
     * This is useful when undoing changes to the image, or in any other case where {@link current}
     * cannot be easily incrementally updated. 
     * </p>
     */
    private void refresh()  {
        current = deepCopy(original);
        for (ImageOperation op: ops) {
            current = op.apply(current);
        }
    }

}
