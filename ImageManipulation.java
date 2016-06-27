import java.awt.image.*;
/**
 *
 * Time-stamp: <2016-01-14 16:56:44 rlc3>
 *
 * ImageManipulation.java
 *
 * Class allows the manipulation of an image by
 * two alternative methods.
 *
 *
// *     @param image the image you are performing the twirls on
// *     @param n the smallest distance from the center of square A(x,y) at which pixels are rotated
// *     @param x the x-coordinate of the centre of the square A(x,y) determined by the mouse
// *     @param y the y-coordinate of the centre of the square A(x,y) determined by the mouse
// *     @param size half the length/width of the square
 *
 */

public class ImageManipulation {

// ---- BEGIN spiral

  static public void spiral(BufferedImage image, int n, int x, int y, int size) {
    //image is bufferedImage, n is parameters for sides, x and y is mouse coordinates
    //size is size of square

    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    BufferedImage temp = new BufferedImage(imageWidth, imageHeight, image.getType());
    (temp.getGraphics()).drawImage(image, 0, 0, imageWidth, imageHeight, null);

    // check if A(x,y) lies within a 10 pixel boundary of the image
    if(x-size > 10 && y-size > 10 && x+size < imageWidth - 10 && y+size < imageHeight - 10) {

      // loop visiting each pixel in A(x,y) at image position (i,j)
      for (int i = (x - size); i <= x + size; i++) {
        for (int j = (y - size); j <= y + size; j++) {

          // set d = distance of (x,y) to (i,j)
          //Pythagorean Theorem - a2 + b2 = c2
          int a = x - i;
          int o = y - j;
          double d = Math.sqrt((a*a)+(o*o));
          // if d is between n and size then undertake rotation
          // d is converted to int
          int dToInt = (int)d;
          // check if dToInt is greater than equal to size
          if (dToInt >= size) {
            // rgb is pure red for the outer square
           int rgb = (0 << 24) | (255 << 16) | (0 << 8) | (0 << 0);
            // setting images to rgb color at position i and j
            image.setRGB(i, j, rgb);
          }else if(dToInt <= size){
            // calculate angle alpha of rotation
            double alpha = Math.PI*((1-(d-n))/(size-n));

            // calculate (preI,preJ) from (I,J) rotate Anticlockwise

            int I = i-x;
            int J = j-y;

            int preI = (int)(I*Math.cos(alpha) - J*Math.sin(alpha))*-1;
            int preJ = (int)(I*Math.sin(alpha) + J*Math.cos(alpha))*-1;

            // set color of pixel at (i,j) to the color of pixel at (prei,prej)
            int rgb = temp.getRGB(preI+x,y-preJ);

            int a_ = (rgb>>24)&0xff; //alpha
            int r = (rgb>>16)&0xff; //red
            int g = (rgb>>8)&0xff; //green
            int b = (rgb>>0)&0xff; //blue

            // rgb equal to all colors at position preI+x,y-preJ
            rgb = (a_ << 24) | (r << 16) | (g << 8) | (b << 0);

            // setting image rgb at position i and j
            image.setRGB(i,j,rgb);
          }
        }// end forLoop j
      }// end forLoop i
    }// end check that A(x,y) is in image
  }// end method spiral

// ---- END spiral

// ---- BEGIN phaseShiftRed

  static public void phaseShiftRed(BufferedImage image, int n, int x, int y, int size) {

    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    // creates a copy of the image called temp
    BufferedImage temp = new BufferedImage(imageWidth, imageHeight, image.getType());
    (temp.getGraphics()).drawImage(image, 0, 0, imageWidth, imageHeight, null);

    // loop through each pixel (i,j) in "A(x,y) intersect (image - boundary)
    // if pixel is are over boundary then pixel inside (image - boundary) must be changed
    // pixel in S that is less than 2*n pixels away from any edge should not be changed.
    for (int i = Math.max(2*n,x-size); i < Math.min(image.getWidth()-n*2,x+size); i++) {
        for (int j = Math.max(2*n, y-size); j < Math.min(image.getHeight()-n*2, y+size); j++) {

          // getting rgb at position i j
          int rgb = temp.getRGB(i,j);

          // green,blue and alpha are not set 0 and we care about red
          int a = (0>>24)&0xff; //alpha
          int r = (rgb>>16)&0xff; //red
          int g = (0>>8)&0xff; //green
          int b = (0>>0)&0xff; //blue

          rgb = (0 << 24) | (r << 16) | (0 << 8) | (0 << 0);
          image.setRGB(i, j, rgb);
        } // end loop j
      } // end loop i
    } // end phaseShiftRed
// ---- END phaseShiftRed

// ---- BEGIN phaseShiftMix

  static public void phaseShiftMix(BufferedImage image, int n, int x, int y, int size) {
    //getting height and width
    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    // creates a copy of the image called temp
    BufferedImage temp = new BufferedImage(imageWidth, imageHeight, image.getType());
    (temp.getGraphics()).drawImage(image, 0, 0, imageWidth, imageHeight, null);

    // loop through each pixel (i,j) in "A(x,y) intersect (image - boundary)
    //pixels in S that is less than 2*n pixels away from any edge should not be changed.
    for (int i = Math.max(2*n,x-size); i < Math.min((image.getWidth()-n*2),x+size); i++) {
      for (int j = Math.max(2*n, y-size); j < Math.min((image.getHeight()-n*2), y+size); j++) {

        //getting rgb of temp - red component of the pixel is n-pixels down.
        // green is 0 and blue component of the pixel that is n-pixels to the left.
        int rgbDownRedPixel = temp.getRGB(i,j+n);
        int rgbLeftBluePixel = temp.getRGB(i-n,j);

        //red and blue is set, the rest we dont care about
        int a = (0>>24)&0xff; //alpha
        int r = (rgbDownRedPixel>>16)&0xff; //red
        int g = (0>>8)&0xff; //green
        int b = (rgbLeftBluePixel>>0)&0xff; //blue

        //binary construction of rgb
        int combineBlueAndRed = (0 << 24) | (r << 16) | (0 << 8) | (b << 0);

        //setting rgb pixel at i j position
        image.setRGB(i,j,combineBlueAndRed);
      } // end loop j
    } // end loop i
  }
// ---- END phaseShiftMix
} // ImageManipulation
