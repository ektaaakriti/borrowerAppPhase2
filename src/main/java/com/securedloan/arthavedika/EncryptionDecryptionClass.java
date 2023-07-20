package com.securedloan.arthavedika;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class EncryptionDecryptionClass {
	public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {
	    int digestLength = md.getDigestLength();
	    int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
	    byte[] generatedData = new byte[requiredLength];
	    int generatedLength = 0;
	    try {
	        md.reset();
	        // Repeat process until sufficient data has been generated
	        while (generatedLength < keyLength + ivLength) {
	            // Digest data (last digest if available, password data, salt if available)
	            if (generatedLength > 0)
	                md.update(generatedData, generatedLength - digestLength, digestLength);
	            md.update(password);
	            if (salt != null)
	                md.update(salt, 0, 8);
	            md.digest(generatedData, generatedLength, digestLength);
	            // additional rounds
	            for (int i = 1; i < iterations; i++) {
	                md.update(generatedData, generatedLength, digestLength);
	                md.digest(generatedData, generatedLength, digestLength);
	            }
	            generatedLength += digestLength;
	        }
	        // Copy key and IV into separate byte arrays
	        byte[][] result = new byte[2][];
	        result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
	        if (ivLength > 0)
	            result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);
	        return result;
	    } catch (DigestException e) {
	        throw new RuntimeException(e);
	    } finally {
	        // Clean out temporary data
	        Arrays.fill(generatedData, (byte)0);
	    }
	}

	public static String decryptnew(String encrypted) throws InvalidAlgorithmParameterException,
	InvalidKeyException,
	BadPaddingException, 
	IllegalBlockSizeException,
	NoSuchAlgorithmException,
	NoSuchPaddingException {
	 String secret = "1234567890123456";
	 String cipherText = encrypted;	    
	 byte[] ciphertextData = org.apache.commons.codec.binary.Base64.decodeBase64(cipherText);
	 byte[] saltData = Arrays.copyOfRange(ciphertextData, 8, 16);
	 MessageDigest md5 = MessageDigest.getInstance("MD5");
	 final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
	 SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
	 IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);
	    // get encrypted data without iv
	    byte[] encrypted1 = Arrays.copyOfRange(ciphertextData, 16, ciphertextData.length);
	    Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
	    byte[] decryptedData = aesCBC.doFinal(encrypted1);
	    String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);	        
	    return decryptedText;

	}
	public static String encryptnew(String stringToEncrypt) {
	    try {
	       // String stringToEncrypt = "Hello world 12345";
	        String password = "1234567890123456";
	        SecureRandom sr = new SecureRandom();
	        byte[] salt = new byte[8];
	        sr.nextBytes(salt);
	        final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, salt, password.getBytes(StandardCharsets.UTF_8),
	                MessageDigest.getInstance("MD5"));
	        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
	            // insert at specific position
	            Security.insertProviderAt(new BouncyCastleProvider(), 1);
	        }
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", BouncyCastleProvider.PROVIDER_NAME);
	        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyAndIV[0], "AES"), new IvParameterSpec(keyAndIV[1]));
	        byte[] encryptedData = cipher.doFinal(stringToEncrypt.getBytes(StandardCharsets.UTF_8));
	        byte[] prefixAndSaltAndEncryptedData = new byte[16 + encryptedData.length];
	        // Copy prefix (0-th to 7-th bytes)
	        System.arraycopy("Salted__".getBytes(StandardCharsets.UTF_8), 0, prefixAndSaltAndEncryptedData, 0, 8);
	        // Copy salt (8-th to 15-th bytes)
	        System.arraycopy(salt, 0, prefixAndSaltAndEncryptedData, 8, 8);
	        // Copy encrypted data (16-th byte and onwards)
	        System.arraycopy(encryptedData, 0, prefixAndSaltAndEncryptedData, 16, encryptedData.length);
	        return org.apache.commons.codec.binary.Base64.encodeBase64String(prefixAndSaltAndEncryptedData);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}

