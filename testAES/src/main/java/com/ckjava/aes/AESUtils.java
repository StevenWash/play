package com.ckjava.aes;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtils {

	private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);

	private static final String UTF8 = "UTF-8";
	private static final int AES_LENGTH = 128;
	private static final String AES = "AES";

	public static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(keyBytes));
			SecretKey key = keyGenerator.generateKey();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("exception:" + e.toString());
		}
		return null;
	}

	public static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(keyBytes));// key长可设为128，192，256位，这里只能设为128
			SecretKey key = keyGenerator.generateKey();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("exception:" + e.toString());
		}
		return null;
	}

	public static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes) {
		try {
			/*KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(keyBytes));// key长可设为128，192，256位，这里只能设为128
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			return result;*/

			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(keyBytes));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("exception:" + e.toString());
		}
		return null;
	}

	public static String byteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length);
		String sTemp;
		for (int i = 0; i < bytes.length; i++) {
			sTemp = Integer.toHexString(0xFF & bytes[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		
		String iv = "2x";
		/*String content = "hello";
		String key = "aaaaaaaa";
		String iv = "abcdefghijklmnop";

		System.out.println("加密前：" + byteToHexString(content.getBytes()));
		byte[] encrypted = AES_CBC_Encrypt(content.getBytes(), key.getBytes(), iv.getBytes());
		System.out.println("加密后：" + byteToHexString(encrypted));
		byte[] decrypted = AES_CBC_Decrypt(encrypted, key.getBytes(), iv.getBytes());
		System.out.println("解密后：" + byteToHexString(decrypted));*/

		// card_no=lYg2o3rccTv75QVZva%2bO7g%3d%3d
		String qunarkey = "ku8UHJijuJK6786Ddrf";
		
		MessageDigest messageDigest =MessageDigest.getInstance("MD5");
		messageDigest.update(qunarkey.getBytes());  
	    byte[] resultByteArray = messageDigest.digest();
	    String md5hexstring = byteToHexString(resultByteArray);
		System.out.println("md5 hex String = " + md5hexstring);
		//System.out.println("md5 = " + md5(qunarkey.getBytes("UTF-8")).toUpperCase());
		
		String card_str = "lYg2o3rccTv75QVZva%2bO7g%3d%3d";
		
		String card_url_decode_str = URLDecoder.decode(card_str, "UTF-8");
		System.out.println("card_url_decode_str = " + card_url_decode_str);
		
		byte[] base64_bytes = Base64.decodeBase64(card_url_decode_str.getBytes());
		String card_aes = byteToHexString(base64_bytes);
		System.out.println("card aes hexstr = " + card_aes);
		//byte[] card_decrypted = AES_CBC_Decrypt(Base64.decodeBase64(card_str.getBytes()), qunarkey.getBytes());
		
		byte[] key_bytes = Hex2Byte.hexStringToByte(md5hexstring);
		byte[] card_decrypted = AES_CBC_Decrypt(base64_bytes, key_bytes, key_bytes);
		System.out.println("解密后：" + byteToHexString(card_decrypted));
		
		/*byte[] ss = AESHelper.decrypt(Base64.decodeBase64(card_aes.getBytes()), qunarkey);
		System.out.println("解密后：" + byteToHexString(ss));*/
	}
	
	
	 public static String md5(byte[] bs) throws Exception{  
        MessageDigest digest = MessageDigest.getInstance("MD5");  
        digest.update(bs);  
        String hex = new BigInteger(1, digest.digest()).toString(16);  
        // 补齐BigInteger省略的前置0  
        return new String(new char[32 - hex.length()]).replace("\0", "0") + hex;  
	 }
	
}
