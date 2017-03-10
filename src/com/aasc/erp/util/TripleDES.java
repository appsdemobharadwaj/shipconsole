package com.aasc.erp.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *  TripleDES class is for Encrypting and Decrypting a String
 *  
 * @author vijaykumar.gandra
 */
public class TripleDES {

    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    public static final String DESEDE_ENCRYPTION_KEY = "THISISASAMPLETRIPLEDESKEY";
    private KeySpec myKeySpec;
    private SecretKeyFactory mySecretKeyFactory;
    private Cipher cipher;
    byte[] keyAsBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;

    public TripleDES() throws Exception {
        myEncryptionKey = DESEDE_ENCRYPTION_KEY;
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        myKeySpec = new DESedeKeySpec(keyAsBytes);
        mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = mySecretKeyFactory.generateSecret(myKeySpec);
    }

    synchronized public String encrypt(String unencryptedString) {
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            BASE64Encoder base64encoder = new BASE64Encoder();
            encryptedString = base64encoder.encode(encryptedText);
        } catch (Exception e) {
            System.out.println(e);
        }
        return encryptedString;
    }

    synchronized public String decrypt(String encryptedString) {
        String decryptedText = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] encryptedText = base64decoder.decodeBuffer(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = bytes2String(plainText);
        } catch (Exception e) {
            System.out.println(e);
        }
        return decryptedText;
    }

    private static String bytes2String(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            stringBuffer.append((char) bytes[i]);
        }
        return stringBuffer.toString();
    }

    public static void main(String a[]) {
        TripleDES myEncryptor = null;
        try {
            myEncryptor = new TripleDES();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String secretpin = null;
        String input = "apps123";
        secretpin = myEncryptor.encrypt(input);
      System.out.println("input = " + input);
        System.out.println("Encrypted = " + secretpin);
          System.out.println("Decrypted = "+ myEncryptor.decrypt("t9f6sSTT2ag="));

    }
}
//this is comment check test