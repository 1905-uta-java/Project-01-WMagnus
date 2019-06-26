package com.revature.projects.wmagnus.project1.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class AuthCryptoUtil {

	private static boolean initialized = false;

	private static byte[] key;
	private static SecureRandom sr;
	
	private static char splitChar = ';';
	
	
	public static void init()
	{
		if (!initialized)
		{
			setSR();
			setKey();
			
			initialized = true;
		}
	}
	
	public static void setSR()
	{
		sr = new SecureRandom();
	}
	
	public static void setKey()
	{
		key = new byte[16];
		
		sr.nextBytes(key);
	}
	
	public static String encrypt(String encryptString)
	{
		try
		{
			
			//Overpadding to avert the problems of unknown IV and endpadding problems(no pkcs7, no desire to implement it myself)
			while(encryptString.length() % 32 != 0)
			{
				encryptString = encryptString+" ";
			}
			for(int i = 0; i < 16; i ++)
			{
				encryptString = " " + encryptString;
			}


			SecretKey aeskey = new SecretKeySpec(key, "AES");
			SecretKey hmackey = new SecretKeySpec(key, "HMAC-SHA256");
			
			Mac mac = Mac.getInstance("HMACSHA256");
			mac.init(hmackey);
			byte[] hmac_digest = mac.doFinal(encryptString.getBytes());
			String hmac_phase = Base64.encodeBase64URLSafeString(hmac_digest);
			
			byte[] iv = new byte[16]; //get that nonce
			sr.nextBytes(iv);
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, aeskey, ivspec);
			String aes_phase =  Base64.encodeBase64URLSafeString(cipher.doFinal(encryptString.getBytes("UTF-8")));
						
			return aes_phase.trim() + ((Character) splitChar).toString() + hmac_phase;
		}
		catch(Exception e)
		{
			System.out.println("Error during encryption time: " + e.toString());
		}
		
		return null;
	}
	
	public static String decrypt(String decryptString)
	{
		try {
			String[] datasplit = decryptString.split(((Character)splitChar).toString());
			
			byte[] decryptData = Base64.decodeBase64(datasplit[0]);
			SecretKey aeskey = new SecretKeySpec(key, "AES");
			SecretKey hmackey = new SecretKeySpec(key, "HMAC-SHA256");
			
			Mac mac = Mac.getInstance("HMACSHA256");
			mac.init(hmackey);
			
			byte[] iv = new byte[16]; //get that nonce
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, aeskey, ivspec);
			byte[] all = cipher.doFinal(decryptData);
			
			byte[] toDigest = new byte[all.length];
			for(int iter = 0; iter < all.length; iter++)
			{
				if (iter < 16) toDigest[iter] = ' ';
				else toDigest[iter] = all[iter];
			}
			
			byte[] hmacDigestOrig = Base64.decodeBase64(datasplit[1]);
			byte[] hmac_digest = mac.doFinal(toDigest);
			
			
			if(!(new String(hmac_digest).equals(new String(hmacDigestOrig))))
			{
				System.out.println("HMAC NON MATCH");
				System.out.println(new String(hmacDigestOrig));
				System.out.println(new String(hmac_digest));
				return null;
			}
			
			return new String(toDigest).trim();
		}
		catch (Exception e)
		{
			System.out.println("Error during decryption time: " + e.toString());
		}
		return null;
	}
}
