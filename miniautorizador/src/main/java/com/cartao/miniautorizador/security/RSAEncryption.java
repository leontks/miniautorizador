package com.cartao.miniautorizador.security;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAEncryption {
	
	 private static PublicKey publicKey;
	    private static PrivateKey privateKey;

	    static {
	        try {
	            // Carregar chave pública
	            InputStream publicKeyStream = RSAEncryption.class.getResourceAsStream("/public_key.pem");
	            byte[] publicBytes = publicKeyStream.readAllBytes();
	            publicKey = loadPublicKey(publicBytes);

	            // Carregar chave privada
	            InputStream privateKeyStream = RSAEncryption.class.getResourceAsStream("/private_key.pem");
	            byte[] privateBytes = privateKeyStream.readAllBytes();
	            privateKey = loadPrivateKey(privateBytes);
	        } catch (Exception e) {
	            throw new RuntimeException("Erro ao carregar chaves RSA", e);
	        }
	    }

	    public static PublicKey loadPublicKey(byte[] keyBytes) throws Exception {
	        String publicKeyPEM = new String(keyBytes)
	                .replace("-----BEGIN PUBLIC KEY-----", "")
	                .replace("-----END PUBLIC KEY-----", "")
	                .replaceAll("\\s", "");

	        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
	        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
	        KeyFactory factory = KeyFactory.getInstance("RSA");
	        return factory.generatePublic(spec);
	    }

	    public static PrivateKey loadPrivateKey(byte[] keyBytes) throws Exception {
	        String privateKeyPEM = new String(keyBytes)
	                .replace("-----BEGIN PRIVATE KEY-----", "")
	                .replace("-----END PRIVATE KEY-----", "")
	                .replaceAll("\\s", "");

	        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
	        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
	        KeyFactory factory = KeyFactory.getInstance("RSA");
	        return factory.generatePrivate(spec);
	    }

	    public static String encrypt(String plainText) throws Exception {
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
	        return Base64.getEncoder().encodeToString(encryptedBytes);
	    }

	    public static String decrypt(String encryptedText) throws Exception {
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
	        return new String(decryptedBytes);
	    }

}
