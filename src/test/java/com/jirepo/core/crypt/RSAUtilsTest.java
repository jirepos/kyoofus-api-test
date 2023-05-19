package com.jirepo.core.crypt;



import java.io.File;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import org.junit.jupiter.api.Test;

import com.jirepo.core.util.IoUtils;



public class RSAUtilsTest {
  	
	/** 키 쌍을 생성하고 개인키/공개키를 Base64로 변환 */ 
	@Test
	void testCreateKeyPair() {
		try {
			
			
			KeyPair keyPair = RSAUtils.createKeyPair(1024);  // 키 쌍 생성
			byte[] privBytes = keyPair.getPrivate().getEncoded(); // 개인키 바이트로 변환 
			byte[] pubBytes = keyPair.getPublic().getEncoded(); // 공개키 바이트로 변환 

			String privEncodedStr  = new String(java.util.Base64.getEncoder().encode(privBytes)); // Base64로 변환 
			String publicEncodedStr = new String(java.util.Base64.getEncoder().encode(pubBytes)); // Base64로 변환 

			// 개인키 저장
			String resourcePath = "G:\\github\\sogood-core\\src\\main\\resources";
			File privateFile = new File(resourcePath + "\\rsa-private.key");
			IoUtils.writeStringToFile(privateFile, privEncodedStr, "utf-8");
			// 공개키 저장
			File publicFile = new File(resourcePath + "\\rsa-public.key");
			IoUtils.writeStringToFile(publicFile, publicEncodedStr, "utf-8");
			
			// RSAUtils 사용 
			privEncodedStr = RSAUtils.privateKeyToBase64(keyPair); // 개인키 BASE64 변환
			System.out.println(privEncodedStr);
			publicEncodedStr = RSAUtils.publicKeyToBase64(keyPair); // 공개키 BASE64 변환
			System.out.println(publicEncodedStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//:




	/** 암호화 복호화 테스트 */ 
	@Test
	void testEncrypt() {
		try {
			// 암호화 
			String strToEncrypt = "암호화할 문자열입니다.";

			KeyPair keyPair = RSAUtils.createKeyPair(1024);  // 키 쌍 생성
			byte[] encryptedByte = RSAUtils.encrypt(strToEncrypt.getBytes(), keyPair.getPublic()); // 공개키로 암호화 
			String encodedStr = new String(Base64.getEncoder().encode(encryptedByte)); // base64변환 
			System.out.println("암호화된 문자열:" + encodedStr);
			
			// 복호화
			byte[] decodedByte = Base64.getDecoder().decode(encodedStr);  // base64 decoding
			String decryptedStr = new String(RSAUtils.decrypt(decodedByte, keyPair.getPrivate())); // 복호화 
			System.out.println("복호화된 문자열:" + decryptedStr);
			
			// RSAUtils 사용 
			String base64PrivateKey = RSAUtils.privateKeyToBase64(keyPair); // 개인키를 base64로 
			String base64PublicKey  = RSAUtils.publicKeyToBase64(keyPair);  // 공개키를 base64로 
			
			PrivateKey privKey = RSAUtils.base64ToPrivateKey(base64PrivateKey); // base64로 인코딩된 바이트 배열에서 개인키 구함
			PublicKey pubKey = RSAUtils.base64ToPublicKey(base64PublicKey);     // base64로 인코딩된 바이트 배열에서 공개키 구함
			
			encryptedByte = RSAUtils.encrypt(strToEncrypt.getBytes(), pubKey);  // 암호화
			encodedStr = new String(Base64.getEncoder().encode(encryptedByte)); // base64 변환 
			System.out.println("암호화된 문자열:" + encodedStr);
	
			decodedByte = Base64.getDecoder().decode(encodedStr);  // base64 decoding
			byte[] decryptedByte = RSAUtils.decrypt(decodedByte, privKey);
			System.out.println("복호화된 문자열:" + new String(decryptedByte));
			
			// 간단하게 사용하는 방법 
			String encryptedStr = RSAUtils.encrypt(strToEncrypt, base64PublicKey);  // 암호화한 문자열을 base64로 
			System.out.println("암호화된 문자열:" + encryptedStr);
			decryptedStr = RSAUtils.decrypt(encryptedStr, base64PrivateKey);  // base64로 암호화된 문자열을 복호화 
			System.out.println("복호화된 문자열:" + decryptedStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//:

	@Test
	public void testDecrypt() throws Exception  {
		String encryptedStr = "DrjV3ZtRdINm0YIUWpHc3axIre2PeaCbMTUoPXogO35lcEtt8cSRx/WOJ6vvqt8mZjbcpmv5K9PCWE0BHZ+sz8jL64rgh2Am/CnTV36t+s70Hg5oQ253TjQ2rG7iGYiovpBzJOTEXOmcBfh/pzwY+hKyJA4iicFxeR/m6y56OOY=";
		String base64PrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKvwBBe+9rVhiNA90iblXDUK856oR+DPa0aKgxO2wrKQOdYvVLMG2A3H+6syiMOcDX3alLwtEFivySN6fG+IHdjrKbFIRhoQ49Q2dCjEMZwltZ2FW2z/q0+Ch2/DwoPUTQ+7yzyBs5hMZs8Ov5wSc8HeGr+Q55u1acgPhTUn98b1AgMBAAECgYBNkdBW4jmtpR4zTODF2Y5bA+DbQHKFMy7juVPfjgGSy8y8G8hrqHfK5+Fb4EWhcoMHt5iIuQ/54vysu/Lt4owL9mV47Xvtm/75a7kIrdBbSsqhjAy2as6TAwkbcng4yW6MwhERP9L9xOUeLW7jvvUiS8fguxxLwmbYyekJw3iyFQJBAOGqDOHjWwuV3fziWrLEZM0Pxlcdki6bqoDxn0rZikV1uEGaJPHBgB+KAGHarbdaHHTWS+pe2HI7LPUjbzXmQH8CQQDDDQeJsg4vKZE+6GSlWnhVjrYXY96LO2NLBh3zULmcWpdckIeedHUUbgYTN61KQI3pzplkZKrbd7k8bYi1rD6LAkEAzeOq0UXwQYfA/ANBoS0Skw27dqwiPagFTpQlM9N7FpirPh93aaOUVDJC2wT7zffHUspahUF31fEruZVU2CZ13QJAcOALaq3182kUutj11ZMDbE/IJMUQtnZJwdLqZjJjF459ZX3mXdZ7IrWBIoHn8L5m4dVDg4uWLKeeNNAWezwQfQJBALvpsqpEu65EEMvHL/6Sk7+Vofz5rRQThIJGvl5b5fiUtAXYFjBaLRog4txI3lc1jjCnU4S6ckiumJdGCvPG6Zw=";

		//byte[] decodedByte = Base64.getDecoder().decode(encryptedStr);  // base64 decoding
		//PrivateKey privKey = RSAUtils.base64ToPrivateKey(base64PrivateKey); // base64로 인코딩된 바이트 배열에서 개인키 구함
		//String decryptedStr = new String(RSAUtils.decrypt(decodedByte, privKey)); // 복호화 
		//System.out.println("복호화된 문자열:" + decryptedStr);

		String decryptedStr = RSAUtils.decrypt(encryptedStr, base64PrivateKey);
		System.out.println("복호화된 문자열:" + decryptedStr);
	}
}
