# Required Features

### Sharpen Filter
**Contributors:** Riley, 

**Access:** Menu, Keyboard Shortcut (Ctrl-J)

**Testing:** 

**Known Issues:** No known issues

---

### Gaussian Blur Filter
**Contributors:** Riley, Will, Jet

**Access:** Menu, Keyboard Shortcut (Ctrl-G)

**Testing:** JUnit testing to ensure 2d guassian filter calculations are correct.

**Known Issues:** No known issues

---

### Median Filter
**Contributors:** Riley, 

**Access:** Menu, Keyboard Shortcut (Ctrl-D)

**Testing:** JUnit testing to ensure median calculations are done correctly.

**Known Issues:** No known issues

---

### Emboss Filter
**Contributors:** Jet, Riley

**Access:** Menu, Keyboard Shortcuts (Ctrl-{1-9})

**Testing** JUnit testing to ensure emboss Kernels are correct.

**Known Issues:** No known issues

---

### Posterise Filter
**Contributors:** Jet

**Access:** Menu, Keyboard Shortcuts (Ctrl-P)

**Testing:** JUnit testing of the getDistance method. Also manual printf testing of Centroid distribution

**Known Issues:** Runs a bit slow, no severe bugs

---

### Brightness Adjustment
**Contributors:** Jet, Arlo

**Access:** Menu, Keyboard Shortcut (Ctrl-B)

**Testing:** JUnit testing with various pixel and adjustment values

**Known Issues:** No known issues

---

### Contrast Adjustment
**Contributors:** Jet, Arlo

**Access:** Menu, Keyboard Shortcut (Ctrl-C)

**Testing:** JUnit testing with various pixel and adjustment values

**Known Issues:**

---

### Image Resize
**Contributors:** Bradley, 

**Access:** Menu, Keyboard Shortcut (Ctrl-Comma, Ctrl-Period)

**Testing:**Ensured that images dont lose edges when downsizing, and dont have black
borders when caling up. Tested with images with different width/height ratios

**Known Issues:** Scaling out too much will result in being unable to zoom in to a reasonable size, Scaling out many times then attempting Scale up will result in a blurry image, 

---

### Image Rotations
**Contributors:** Bradley, 

**Access:** Menu, Keyboard Shortcut (Ctrl-L, Ctrl-R)

**Testing:** Ensured that images with different width/height ratios dont get 
cropped or muddled up during the rotations. Ensured that doing multiple rotations
and flips in a row does not ruin the image or cause unexpected reults.

**Known Issues:** No known issues

---

### Image Flip
**Contributors:** Bradley

**Access:** Menu, Keyboard Shortcut (Ctrl-H, Ctrl-V)

**Testing:** Ensured that images with different width/height ratios dont get 
cropped or muddled up during the flip. Ensured that doing multiple rotations
and flips in a row does not ruin the image or cause unexpected reults.

**Known Issues:** No known issues

---

### Area Select
**Contributors:** Bradley, 

**Access:** Used by crop and Draw Shapes classes

**Testing:** Tested ensuring that all directions of drawing
are valid, so that a selection can start and finish anywhere
within the image bounds

**Known Issues:** No known issues

---

### Draw Shapes
**Contributors:** Bradley, 

**Access:** Menu, Keyboard Shortcut

**Testing:** Tested drawing shapes from different points, ensured
that shapes draw correctly based on the zoom level of the image, ensured
that shapes could be drawn from all different directions (right to left etc)

**Known Issues:** No known issues

---

### Crop Image
**Contributors:** Bradley, 

**Access:** Menu, Keyboard Shortcut (Ctrl - Q)

**Testing:** Tested crop from different points, ensured that
it crops correctly based on the zoom level of the image, ensured
that image could be cropped from all different directions (right to left etc)

**Known Issues:** No known issues

---

### Image Export
**Contributors:** Arlo, 

**Access:** Menu, Keyboard Shortcut (Ctrl-E)

**Testing:**

**Known Issues:** .png files with transparent pixel won't save as a jpg

---

### Toolbar
**Contributors:** Arlo, 

**Brief Discussion:** Zoom in and out, rotate 90 degrees, flip vertically and horizontally, undo and redo. These features were deemed the most useful and needed to be more promenant within Andie

---

### Macro
**Contributors:** Will 

**Brief Discussion:** Macro allows you to record a series of operations is a seperate .ops file, which can then be applied to other photos. 

---

### Keyboard Shortcuts
**Contributors:** Arlo, 

**Brief Discussion:** Every feature ended up having a shortcut. A help menu was added to show the list of shortcuts to help the user find them.

---

### Exception Handling/Other Error Avoidance
**Contributors:** Will, Riley

**Testing:** On going testing attempting to break the program, find and resolve issues

**General Errors Caught:** Added error handling to all functions that require an image if being used without an image loaded.  Added error handling for save, open and export cases (no file, incorrect file name or type). Added error handling for empty stacks in undo and redo.  Replaced System.exit in exception handling to a less aggressive forms of handling (method exiting and/or error messages).  New errors caught in export (incorrect file names handling). Fixed Macro not applying immediatly.  Found bug where macro applied operations in the wrong order, fixed. Found errors where filters would be called every time there was an undo.  Fixed bugs and changed functionality of zoom so that it works in conjunction with scale. 

### Image Extending/Convolution
**Contributors:** Jet, Riley

**Brief Discussion:** Convoluter class takes desired Kernel and the image and applies the Kernel in a process that extends the desired filter Kernel to the ends of the image and also accounts for negative pixel values. - Class creation headed by Jet. ImgExtend class extends the parameterised BufferedImage object to the radius of the Kernel so the filter is applied to the edges of the image. A sub image of the created image is required to remove the excess border created by the class. - Class creation headed by Riley.

---

# Other Features

### Continuous Integration, Testing & Javadoc Documentation
**Contributors:** Riley

**Brief Discussion:** Created JUnit testing and added in depth Javadoc commenting to all code, added said functionality to the Gitlab continuous integration pipeline.

---

### Zoom Fit
**Contributors:** Jet

**Brief Discussion:** Add another zoom option to 'fit' the image width or height (depending on which is appropriate) to the window. 

**Testing:** Testing with portrait and landscape images fitting to a varitey of windows shapes and sizes.

**Known Issues:** Sometimes after fitting it wont refresh

---

### Image Colour Getter and Setter - RGB.class
**Contributors:** Riley

**Brief Discussion:** Creates Raster object equal to that of the input image, reads colour values at given x, y values and can set argb values in the new Raster to be returned later. Purpose of class is to bypass the extremely slow Color object getRGB/setRGB methods. Without the constant colour space checking and converting the RGB class is much faster (whilst somewhat more memory intensive) and allows for larger more thorough covolution. 

---

### PopUp class

**Contributors:** Jet, Arlo

**Brief Discussion:** Static class used to display various dialogs and messages (Spinner, slider, message) to the user.


**Testing:** Tested with all applicable filters through the Convoluter class, or directly i.e. the Posterise or Median filter
