package com.damera.utils;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncrypter {

	Key secretKey = getSecretKey();

	IvParameterSpec ivParameterSpec = getIvParameterSpec();

	public static Key getSecretKey() {

		try {

			KeyGenerator aes = KeyGenerator.getInstance("AES");
			aes.init(192);
			Key secretKey = aes.generateKey();
			return secretKey;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static IvParameterSpec getIvParameterSpec() {

		try {

			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			byte[] random = new byte[16];
			secureRandom.nextBytes(random);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(random);

			return ivParameterSpec;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] encrypt(byte[] input, Key secretKey, IvParameterSpec ivParameterSpec)
			throws Exception {

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

		byte[] encrypted = cipher.doFinal(input);

		return encrypted;
	}

	public static byte[] decrypt(byte[] encryptedInput, Key secretKey, IvParameterSpec ivParameterSpec)
			throws Exception {

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
		byte[] decryptedInput = cipher.doFinal(encryptedInput);
		System.out.println("decrypted input bytes: " + decryptedInput);

		return decryptedInput;
	}


	public String encryptPassword(String password) throws Exception{

		byte[] bytes = password.getBytes();

		byte[] encrypt = encrypt(bytes,secretKey,ivParameterSpec);

		byte[] encode = Base64.getEncoder().encode(encrypt);

		return new String(encode); 
	}


	public String decryptPassword(String password) throws Exception {


		byte[] decode = Base64.getDecoder().decode(password);

		byte[] encrypt = decrypt(decode,secretKey,ivParameterSpec);

		return new String(encrypt); 
	}

}
