//CROCS
package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.AdjustBrightness;
import cosc202.andie.AdjustContrast;
import cosc202.andie.GaussianFilter;
import cosc202.andie.ImagePanel;
import cosc202.andie.MedianFilter;
import cosc202.andie.PosteriseFilter;
import cosc202.andie.FlipImage;
import cosc202.andie.EditableImage;
import cosc202.andie.EmbossFilter;

public class AndieTest {

    @Test 
    void dumbyTest(){
    }
    //#region Image Panel Tests
    @Test
    void getZoomInitialValue(){
        ImagePanel testPanel = new ImagePanel();
        Assertions.assertEquals(100.0, testPanel.getZoom());
    }

    @Test 
    void setZoomTestMidRange(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(75);
        Assertions.assertEquals( 75, testPanel.getZoom());
    }

    @Test 
    void setZoomTestLowRange(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(-10);
        Assertions.assertEquals(ImagePanel.zoomMin , testPanel.getZoom());
    }

    @Test 
    void setZoomTestHighRange(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(400);
        Assertions.assertEquals( 400, testPanel.getZoom());
    }
    //#endregion

    //#region brightness tests
    @Test 
    void brightnessDefault(){
        AdjustBrightness test = new AdjustBrightness();
        Assertions.assertEquals( 10, test.returnBrightness());
    }

    @Test 
    void brightnessChange(){
        AdjustBrightness test = new AdjustBrightness(55);
        Assertions.assertEquals( 55, test.returnBrightness());
    }
    
    @Test 
    void brightnessAdjustedValuesMidRange(){
        AdjustBrightness test = new AdjustBrightness(50);    
        Assertions.assertEquals( 163.75, test.testAdjustment(100));
    }

    @Test 
    void brightnessAdjustedValuesLowRange(){
        AdjustBrightness test = new AdjustBrightness(-50);
        Assertions.assertEquals( 0, test.testAdjustment(1));
    }

    @Test 
    void brightnessAdjustedValuesHighRange(){
        AdjustBrightness test = new AdjustBrightness(100);
        Assertions.assertEquals( 255, test.testAdjustment(254));
    }
    //#endregion

    //#region AdjustContrast Tests
    @Test 
    void contrastDefault(){
        AdjustContrast test = new AdjustContrast();
        Assertions.assertEquals( 10, test.returnContrast());
    }

    @Test 
    void contrastChange(){
        AdjustContrast test = new AdjustContrast(69);
        Assertions.assertEquals( 69, test.returnContrast());
    }

    @Test 
    void contrastAdjustedValuesMidRange(){
        AdjustContrast test = new AdjustContrast(50);
        Assertions.assertEquals(116.25 , test.testAdjustment(120));
    }

    @Test 
    void contrastAdjustedValuesLowRange(){
        AdjustContrast test = new AdjustContrast(-50);
        Assertions.assertEquals( 64.25, test.testAdjustment(1));
    }

    @Test 
    void contrastAdjustedValuesHighRange(){
        AdjustContrast test = new AdjustContrast(100);
        Assertions.assertEquals( 255, test.testAdjustment(254));
    }
    //#endregion

    //#region Median Filter Tests
    @Test
    void medianFilterMedianCalculation(){
        MedianFilter test = new MedianFilter();
        int[] testArray = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Assertions.assertEquals(5, test.getMedian(testArray));
    }
    //#endregion

    //#region Gaussian Filter Tests
    @Test
    void GaussianFilterCalculation(){
        GaussianFilter test = new GaussianFilter();
        Assertions.assertEquals("0.957", String.format("%.3f", (test.getGaussian(0, 0, 1/3.0f))/1.496751349652186));
    }
    //#endregion

    //#region Editable Image Tests
    @Test void editableImagePresenceCheck(){
        EditableImage test = new EditableImage();
        Assertions.assertFalse(test.hasImage());
    }
    //#endregion

    //#region Flip Image Tests
    @Test void flipImageHorizontally(){
        FlipImage test = new FlipImage(true);
        Assertions.assertTrue(test.returnIsX());
    }

    @Test void flipImageVertically(){
        FlipImage test = new FlipImage(false);
        Assertions.assertFalse(test.returnIsX());
    }
    //#endregion

    //#region Posterise Tests
    @Test
    void testGetDistance(){
        PosteriseFilter p = new PosteriseFilter();
        Assertions.assertEquals(226, (int)Math.ceil(p.testGetDistance(255, 32, 78, 255, 228, 190)));
    }

    @Test
    void testGetDistanceMax(){
        PosteriseFilter p = new PosteriseFilter();
        Assertions.assertEquals(442, (int)Math.ceil(p.testGetDistance(0, 0, 0, 255, 255, 255)));
    }
    
    @Test
    void testGetDistanceMin(){
        PosteriseFilter p = new PosteriseFilter();
    Assertions.assertEquals(0, (int)Math.ceil(p.testGetDistance(120, 120, 120, 120, 120, 120)));
    }
    //#endregion

    //#region Emboss Tests
    @Test
    void testEmbossFilters(){
        EmbossFilter test = new EmbossFilter();
        Assertions.assertEquals(1, test.kernels[6][0][0]);
        }
    //#endregion
}