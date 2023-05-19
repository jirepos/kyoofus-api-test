package com.jirepo.core.crypt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;
import org.junit.jupiter.api.Test;

import com.jirepo.core.util.IoUtils;

public class PemUtilsTest {

  private static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
    //KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(1024); // JSEncrypt 라이브러리와 맞추기 위해서 키 사이즈는 1024로 설정
    KeyPair keyPair = generator.generateKeyPair();
    return keyPair;
  }

  private static void writePemFile(Key key, String description, String filename) throws Exception  {
    PemObject pemObject = new PemObject (description, key.getEncoded());
    PemWriter pemWriter = null;
    try {
      pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(filename)));  
      pemWriter.writeObject(pemObject);
    } catch (Exception e) {
      throw new Exception(e);
    }finally { 
      pemWriter.close();
    }
  }

  private static String writePemToString(Key key, String description) throws Exception  {
    PemObject pemObject = new PemObject (description, key.getEncoded());
    PemWriter pemWriter = null;
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    try {
      pemWriter = new PemWriter(new OutputStreamWriter( bout ));  
      pemWriter.writeObject(pemObject);
    } catch (Exception e) {
      throw new Exception(e);
    }finally { 
      pemWriter.close();
    }
    return bout.toString();
  }

  private static PrivateKey getRSAPrivateKeyFrom(String content) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    PemReader reader = new PemReader(new StringReader(content));
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(reader.readPemObject().getContent());
    return KeyFactory.getInstance("RSA").generatePrivate(spec);
  }



  @Test
  public void testPem() throws Exception { 
    //Security.addProvider(new BouncyCastleProvider());  /// 이것 없으면 키 생성할 때 'BC' 때문에 오류 발생
    //KeyPair keyPair = generateRSAKeyPair();
    KeyPair keyPair = RSAUtils.createKeyPair(1024);
    System.out.println(">>>> Success.");
    String encodedPrivateKey = RSAUtils.privateKeyToBase64(keyPair);
    String encodedPublicKey =  RSAUtils.publicKeyToBase64(keyPair);
    System.out.println("---Private Key");
    System.out.println(encodedPrivateKey);
    System.out.println("---Public Key");
    System.out.println(encodedPublicKey);

    String path = "G:\\github\\jirepo-framework\\src\\main\\resources\\"; 

    // 개인키를 PEM 포맷의 파일로 저장
    RSAUtils.writePemFile(keyPair.getPrivate(), "RSA PRIVATE KEY", path + "id_rsa" );
    // 공개키를 PEM 포맷의 파일로 저장
    RSAUtils.writePemFile(keyPair.getPrivate(), "RSA PUBLIC KEY", path + "id_rsa.pub" );


    String content = "한글"; 
    String encrypted = RSAUtils.encrypt(content, encodedPublicKey); // 공개키로 암호화 하기
    System.out.println("---Encrypted");
    System.out.println(encrypted);
    String decrypted = RSAUtils.decrypt(encrypted, encodedPrivateKey); // 개인키로 복호화 하기
    System.out.println("---Decrypted");
    System.out.println(decrypted);

    
    File privateFile = new File(path + "rsa-private.key");
    File publicFile = new File(path + "rsa-public.key");
    IoUtils.writeStringToFile(privateFile, encodedPrivateKey, "iso-8859-1");
    IoUtils.writeStringToFile(publicFile, encodedPublicKey, "iso-8859-1");

    //System.out.println(writePemToString(keyPair.getPrivate(),  "RSA PRIVATE KEY" ));

  }

}//:
