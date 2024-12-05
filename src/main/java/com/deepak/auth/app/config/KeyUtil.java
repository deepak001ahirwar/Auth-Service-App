package com.deepak.auth.app.config;

import io.jsonwebtoken.SignatureAlgorithm;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class KeyUtil {
    
    public static PrivateKey readPrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return decodePrivateKey(key, SignatureAlgorithm.RS256.getFamilyName());
    }

    private static PrivateKey decodePrivateKey(String pemEncoded, String algorithm) throws NoSuchAlgorithmException, InvalidKeySpecException {
        pemEncoded = removeCertBeginEnd(pemEncoded);
        byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        return kf.generatePrivate(keySpec);
    }
    
    private static String removeCertBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN PRIVATE KEY-----", "");
        pem = pem.replaceAll("-----END PRIVATE KEY-----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        pem = pem.replaceAll("\\\\n", "");
        return pem.trim();
    }
}