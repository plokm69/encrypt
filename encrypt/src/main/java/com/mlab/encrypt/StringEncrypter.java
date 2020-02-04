package com.mlab.encrypt;

/*
 * Description :
 * 
 *   This file contains a class which converts a UTF-8 string into a cipher string, and vice versa.
 *   The class uses 128-bit AES Algorithm in Cipher Block Chaining (CBC) mode with a UTF-8 key
 *   string and a UTF-8 initial vector string which are hashed by MD5. PKCS5Padding is used
 *   as a padding mode and binary output is encoded by Base64. 
 */

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * This class converts a UTF-8 string into a cipher string, and vice versa.
 * It uses 128-bit AES Algorithm in Cipher Block Chaining (CBC) mode with a UTF-8 key
 * string and a UTF-8 initial vector string which are hashed by MD5. PKCS5Padding is used
 * as a padding mode and binary output is encoded by Base64.
 * 
 * @author JO Hyeong-ryeol
 */
public class StringEncrypter {
	private String iv;
	private Key keySpec;
	
	/*
	 * 16자리의 키값을 입력하여 객체를 생성한다.
	 * @param key
	 *  
	 * en/de crypt를 위한 키값
	 * 
	 * @throws UnsupportedEncodingException
	 * 키값의 길이가 16이하일 경우 발생
	 */
	final static String key = "FRAMEWORKTESTENRCRYPTKEY";
	
	public StringEncrypter() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		md.update(key.getBytes());
		byte byteData[] = md.digest();
		
		StringBuffer sb = new StringBuffer(); 
		for(int i=0; i<byteData.length; i++){
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		StringBuffer keyString = new StringBuffer();
		for(int i=0; i<byteData.length; i++){
			String hex = Integer.toHexString(0xff & byteData[i]);
			if(hex.length()==1){
				keyString.append('0');
			}
			keyString.append(hex);
		}
		this.iv = keyString.substring(0, 16);
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if(len > keyBytes.length){
			len = keyBytes.length;
		}
		
		System.out.println("sha256 key gen ] "+keyString.toString());
		SecretKeySpec keySpec = new SecretKeySpec(byteData, "AES");
		
		this.keySpec = keySpec;
	}
	
	public String encrypt(String val) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException{
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(c.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
		byte[] encrypted = c.doFinal(val.getBytes("UTF-8"));
		String enVal = new String(Base64.encodeBase64(encrypted));
		
		return enVal;
	}
	
	public String decrypt(String val) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException{
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
		byte[] byteStr = Base64.decodeBase64(val.getBytes());
		
		String deVal = new String(c.doFinal(byteStr), "UTF-8");
		
		return deVal;
	}
}
