/*
 * Created on May 19, 2009
 *
 */
package it.tasgroup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import sun.misc.BASE64Encoder;

/**
 * @author BastiaP
 *
 */
public class Base64Conv {

	public static void main(String[] args) throws IOException {
		printBase64();
	}
	
	public static void printBase64() throws IOException {

		
		String file_txt="d:\\temp\\big-base64.txt";		
		String filename="d:\\temp\\big.jpg";
		String verify_binary="d:\\temp\\big-from-base64.jpg";
		{
				FileInputStream fis=new FileInputStream(filename);
				BASE64Encoder encoder = new BASE64Encoder();
				FileOutputStream fileOutputStream= new FileOutputStream(file_txt);
				encoder.encodeBuffer(fis,fileOutputStream);
		}
			
		{
				FileInputStream fileInputStream= new FileInputStream(file_txt);
				
				sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
				FileOutputStream fileOutputStream= new FileOutputStream(verify_binary);
					
				decoder.decodeBuffer(fileInputStream,fileOutputStream);
		}
		
	}
}
