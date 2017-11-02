package com.FiletoBase64;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class FileUitl {

 /**
  * 将文件转成base64 字符串
  * @param path文件路径
  * @return  * 
  * @throws Exception
  */
public static String encodeBase64File(String path) throws Exception {
  File file = new File(path);;
  FileInputStream inputFile = new FileInputStream(file);
  byte[] buffer = new byte[(int) file.length()];
  inputFile.read(buffer);
  inputFile.close();
  return new BASE64Encoder().encode(buffer);

 }

 /**
  * 将base64字符解码保存文件
  * @param base64Code
  * @param targetPath
  * @throws Exception
  * @author gjw
  */
 public static void decoderBase64File(String base64Code, String targetPath)
   throws Exception {
  byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
  FileOutputStream out = new FileOutputStream(targetPath);
  out.write(buffer);
  out.close();

 }

 /**
  * 将base64字符保存文本文件
  * @param base64Code
  * @param targetPath
  * @throws Exception
  */

 public static void toFile(String base64Code, String targetPath)
   throws Exception {

  byte[] buffer = base64Code.getBytes();
  FileOutputStream out = new FileOutputStream(targetPath);
  out.write(buffer);
  out.close();
 }

 public static void main(String[] args) throws Exception {
     //String base64Code = encodeBase64File("c:/cs/tt.png");
     //System.out.println(base64Code);
     String base64Code = "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";
     decoderBase64File(base64Code,"c:/cs/jj.png");
  /*try {
   String base64Code = encodeBase64File("c:/cs/02-3C-1-1.bin");
   System.out.println(base64Code);
   decoderBase64File(base64Code, "c:/cs/02-3C-1-1-副本.bin");
   //toFile(base64Code, "c:/cs/02-3C-1-1-副本.bin");
  } catch (Exception e) {
   e.printStackTrace();

  }*/

 }  

}