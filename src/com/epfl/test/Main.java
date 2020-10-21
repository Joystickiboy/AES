package com.epfl.test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
	    String s = "Hey boy how are you?";
	    String initKeyS = "I Like Cats and.";
	    byte[] initKey = StringToByte(initKeyS);


	    byte[][] key = new byte[4][4];

		//Put key in to 4x4 matrix
	    for(int i=0; i<4; i++){
	    	for(int k=0;k<4;k++) {
				key[i][k] = initKey[(i * 4) + k];
			}
		}


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

		byte[][] state = new byte[4][4];

		for(int i=0;i<mult;i++){
			for (int j=0;j<4;j++) {
				for(int k=0;k<4;k++){
					state[j][k] = blocks[i][(j*4)+k];
				}
			}
			//INIT ROUND
			key = genKey(key);
			state = addKey(key, state);
			//MAIN ROUND
			for (int r=0;r<10;r++){
				for (int j=0;j<4;j++) {
					for(int k=0;k<4;k++){
						//DO SBOX SUBSTITIUTION
					}
				}


			}
		}

    }

	private static byte[][] addKey(byte[][] key, byte[][] state) {
    	for (int i=0;i<4;i++){
    		for(int j=0;j<4;j++){
    			state[i][j] = (byte) (state[i][j] ^ key[i][j]);
			}
		}
    	return state;
	}

	private static byte[][] genKey(byte[][] initKey){
		SBox sbox = new SBox();

		//ROT WORD
    	byte temp = initKey[0][3];
    	initKey[0][3] = initKey[1][3];
    	initKey[1][3] = initKey[2][3];
    	initKey[2][3] = initKey[3][3];
    	initKey[3][3] = temp;

    	//SUB BYTE
		int a1 = initKey[0][0];

		int b10 = (a1 & 0xF0) >> 4;
		int b01 = (a1 & 0x0F);
		initKey[0][0] = (byte) sbox.apply(b10,b01);

		a1 = initKey[1][0];
		b10 = (a1 & 0xF0) >> 4;
		b01 = (a1 & 0x0F);
		initKey[1][0] = (byte) sbox.apply(b10,b01);

		a1 = initKey[2][0];
		b10 = (a1 & 0xF0) >> 4;
		b01 = (a1 & 0x0F);
		initKey[2][0] = (byte) sbox.apply(b10,b01);

		a1 = initKey[1][0];
		b10 = (a1 & 0xF0) >> 4;
		b01 = (a1 & 0x0F);
		initKey[3][0] = (byte) sbox.apply(b10,b01);

		//RCON TIME

		return initKey;
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
