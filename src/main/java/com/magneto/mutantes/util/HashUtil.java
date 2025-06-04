package com.magneto.mutantes.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtil {

    private HashUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String generateSha256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to generate DNA hash", e);
        }
    }
}
