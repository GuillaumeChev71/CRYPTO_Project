import java.io.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
  
class test {
    public static void main (String[] args) throws IOException {
        
      // read the image from the file
      BufferedImage image = ImageIO.read(new File("test.png"));
        
      // create the object of ByteArrayOutputStream class
      ByteArrayOutputStream outStreamObj = new ByteArrayOutputStream();
        
      // write the image into the object of ByteArrayOutputStream class
      ImageIO.write(image, "png", outStreamObj);
        
      // create the byte array from image
      byte [] byteArray = outStreamObj.toByteArray();
        
      // create the object of ByteArrayInputStream class
      // and initialized it with the byte array.
      ByteArrayInputStream inStreambj = new ByteArrayInputStream(byteArray);
        
      // read image from byte array
      BufferedImage newImage = ImageIO.read(inStreambj);
        
      // write output image
      ImageIO.write(newImage, "png", new File("outputImage.png"));
      System.out.println("Image generated from the byte array.");
    }
}