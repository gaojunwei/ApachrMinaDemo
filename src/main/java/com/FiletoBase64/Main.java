package com.FiletoBase64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author gjw
 * @create 2017-10-31 13:07
 **/
public class Main {
    public static void main(String[] args) throws IOException {
        //toBinary("高军威");
        grayImage();
    }

    public static void grayImage() throws IOException {
        File file = new File(System.getProperty("user.dir")+"/test.jpg");
        BufferedImage image = ImageIO.read(file);

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for(int i= 0 ; i < width ; i++){
            for(int j = 0 ; j < height; j++){
                int rgb = image.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }

        File newFile = new File(System.getProperty("user.dir")+"/method1.jpg");
        ImageIO.write(grayImage, "jpg", newFile);
    }

    public static void toBinary(String str){
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i])+ " ";
        }
        System.out.println(result);
    }
}
