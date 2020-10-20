package com.epfl.test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
	    String s = "Hey boy how are you?";
	    String initKeyS = "I Like Cats and.";
	    byte[] initKey = StringToByte(initKeyS);


		byte[] m = StringToByte(s);
		// Divide m into multiple 16 bytes (128 bits) blocks
		//Find how many blocks there will be
		int mult = numberOfBlocks(m);

		byte[] padded = new byte[mult*16];
		System.arraycopy(m, 0, padded, 0, m.length);
		for(int i=0; i<12; i++){
			padded[m.length + i] = 0b01;
		}

		byte[][] blocks = new byte[mult][16];
		for(int i = 0; i < mult; i++){
			for(int j=0;j<16;j++){
				blocks[i][j] = padded[(i*16)+j];
			}
		}
		System.out.println("h");
		//Add round key

		byte[][] postRoundKey = new byte[mult][16];

		for(int i=0; i<mult; i++){
			for(int j=0; j<16; j++){
				postRoundKey[i][j] = (byte) (initKey[j] ^ blocks[i][j]);
			}
		}

		System.out.println(postRoundKey);


    }


	/**
	 *
	 * FUNCTIONS
	 *
	 */

	private static byte[] StringToByte(String s){
    	return s.getBytes(StandardCharsets.ISO_8859_1);
	}

	private static int numberOfBlocks(byte[] b){
		int mult = 1;
		while (16 * mult < b.length) {
			mult++;
		}
		return mult;
	}

}
