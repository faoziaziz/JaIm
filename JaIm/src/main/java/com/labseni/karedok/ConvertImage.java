package com.labseni.karedok;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
 
public class ConvertImage {
 
    public static void main(String[] args) throws FileNotFoundException, IOException {
    	/*
    	 * In this function the first part shows how to convert an image file to 
    	 * byte array. The second part of the code shows how to change byte array
    	 * back to a image
    	 */
        File file = new File("gambar/rose.jpeg");
        System.out.println(file.exists() + "!!");
 
        FileInputStream fis = new FileInputStream(file);
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.
 
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); 
                //no doubt here is 0
                /*Writes len bytes from the specified byte array starting at offset 
                off to this byte array output stream.*/
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            Logger.getLogger(ConvertImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] bytes = bos.toByteArray();
        //bytes is the ByteArray we need
 
        try(OutputStream outputStream = new FileOutputStream("thefilename")) {
            bos.writeTo(outputStream);
        }
        /*
         * The second part shows how to convert byte array back to an image file  
         */
 
 
        //Before is how to change ByteArray back to Image
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");
        //ImageIO is a class containing static convenience methods for locating ImageReaders
        //and ImageWriters, and performing simple encoding and decoding. 
 
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream, it seems file is OK
 
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        //Returns an ImageInputStream that will take its input from the given Object
 
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
 
        Image image = reader.read(0, param);
        //got an image file
 
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //bufferedImage is the RenderedImage to be written
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, null, null);
        File imageFile = new File("gambar/newrose2.jpeg");
        ImageIO.write(bufferedImage, "jpg", imageFile);
        //"jpg" is the format of the image
        //imageFile is the file to be written to.
        
        System.out.println(imageFile.getPath());
    }
}