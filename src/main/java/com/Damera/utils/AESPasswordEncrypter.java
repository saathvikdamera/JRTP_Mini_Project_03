package com.damera.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class AESPasswordEncrypter {

	private static SecretKey secretKey;
	private static byte[] key;

	public static void setKey(String myKey) {
		try {
			
			key = myKey.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}
	
	
	//encryption
    public String encrypt(String strToEncrypt,String sec) {
    	try {
    		setKey(sec);
    		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    		return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    } 
    
    //decryption
    public String decrypt(String strToDecrypt,String sec) {
    	try {
    		setKey(sec);
    		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    		cipher.init(Cipher.DECRYPT_MODE, secretKey);
    		return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
	
	
}
