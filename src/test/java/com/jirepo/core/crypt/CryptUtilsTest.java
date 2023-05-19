package com.jirepo.core.crypt;

import org.junit.jupiter.api.Test;

import com.jirepo.core.codec.HexConverter;
import com.jirepo.core.crypt.CryptUtils.Aes;

public class CryptUtilsTest {
 
	/** AES 암호화, 복호화 테스트 */ 
	@Test
	void testGetAES() {
		
		try {
			String strToEncrypt = "암호화복호화 테스트.";
			// 키의 길이는 128 또는 256으로 선택할 수 있다. 
			String key = "1234567890123456"; //  1286bit , 16byte 문자열
			//String key = "12345678901234567890123456789012"; //  256bit , 32byte 문자열
			Aes aes = CryptUtils.getAES();
			String encryptedStr = aes.encrypt(key, strToEncrypt);
			System.out.println("암호화된 문자열:");
			System.out.println(encryptedStr);
			String decryptedStr = aes.decrypt(key, encryptedStr);
			System.out.println(decryptedStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//:

	@Test
	void testGetDigest() {

		String strToEncrypt = "hello";
		// digest()로 암호화된 배열을 구한다.
		// AlgorithmEnum.SHA256을 사용하여 암호화할 알고리즘을 선택한다.
		byte[] cryptedBytes = CryptUtils.getDigest().digest(strToEncrypt, AlgorithmEnum.SHA256);
		// byte를 출력하기 위해서 HexConverter를 사용한다.
		String hexString = HexConverter.bytesToHex(cryptedBytes);
		System.out.println(hexString);

		// SHA256으로 암호화하고 16진수 문자열로 변환한다.
		String hexStr = CryptUtils.getDigest().digestToHex(strToEncrypt,AlgorithmEnum.SHA256);
		System.out.println(hexStr);
	} 
}///~
