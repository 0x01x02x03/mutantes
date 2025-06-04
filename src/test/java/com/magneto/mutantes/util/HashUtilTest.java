package com.magneto.mutantes.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;

public class HashUtilTest {

    @Test
    public void testGenerateSha256Hash_NotNull() {
        String input = "testString";
        String hash = HashUtil.generateSha256Hash(input);
        assertNotNull(hash);
    }

    @Test
    public void testGenerateSha256Hash_ConsistentOutput() {
        String input = "sameInput";
        String hash1 = HashUtil.generateSha256Hash(input);
        String hash2 = HashUtil.generateSha256Hash(input);
        assertEquals(hash1, hash2);
    }

    @Test
    public void testGenerateSha256Hash_DifferentInputs() {
        String input1 = "inputOne";
        String input2 = "inputTwo";
        String hash1 = HashUtil.generateSha256Hash(input1);
        String hash2 = HashUtil.generateSha256Hash(input2);
        assertNotEquals(hash1, hash2);
    }

    @Test
    public void testGenerateSha256Hash_KnownValue() {
        String input = "hello";
        String expectedHash = "LPJNul+wow4m6DsqxbninhsWHlwfp0JecwQzYpOLmCQ=";
        String actualHash = HashUtil.generateSha256Hash(input);
        assertEquals(expectedHash, actualHash);
    }

    @Test(expected = RuntimeException.class)
    public void testGenerateSha256Hash_NullInput() {
        HashUtil.generateSha256Hash(null);
    }

    @Test
    public void testGenerateSha256Hash_EmptyString() {
        String input = "";
        String expectedHash = "47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=";
        String actualHash = HashUtil.generateSha256Hash(input);
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void testGenerateSha256Hash_SpecialCharacters() {
        String input = "!@#$%^&*()_+-=[]{}|;':,.<>/?";
        String hash = HashUtil.generateSha256Hash(input);
        assertNotNull(hash);
        assertEquals(44, hash.length());
    }

    @Test
    public void testGenerateSha256Hash_LongString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("abc123");
        }
        String input = sb.toString();
        String hash = HashUtil.generateSha256Hash(input);
        assertNotNull(hash);
        assertEquals(44, hash.length());
    }

    @Test
    public void testGenerateSha256Hash_UnicodeCharacters() {
        String input = "áéíóúüñç漢字";
        String hash = HashUtil.generateSha256Hash(input);
        assertNotNull(hash);
        assertEquals(44, hash.length());
    }

    @Test
    public void testGenerateSha256HashThrowsRuntimeException_WhenNoSuchAlgorithmException() {
        try (MockedStatic<MessageDigest> mockDigest = mockStatic(MessageDigest.class)) {
            mockDigest.when(() -> MessageDigest.getInstance("SHA-256"))
                    .thenThrow(new NoSuchAlgorithmException("Algo not found"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                HashUtil.generateSha256Hash("AGTC");
            });

            assertTrue(exception.getMessage().contains("Failed to generate DNA hash"));
            assertTrue(exception.getCause() instanceof NoSuchAlgorithmException);
        }
    }

    @Test
    public void testGenerateSha256HashSuccess() {
        String input = "AGTC";
        String hash1 = HashUtil.generateSha256Hash(input);
        String hash2 = HashUtil.generateSha256Hash(input);

        assertNotNull(hash1);
        assertFalse(hash1.isEmpty());

        assertEquals(hash1, hash2);

        assertEquals(44, hash1.length());
    }

    @Test
    public void testGenerateSha256HashProducesDifferentValuesForDifferentInputs() {
        String hash1 = HashUtil.generateSha256Hash("AGTC");
        String hash2 = HashUtil.generateSha256Hash("TGCA");
        assertNotEquals(hash1, hash2);
    }

    @Test
    public void testHashUtilConstructorIsPrivateAndThrowsException() throws Exception {
        Constructor<HashUtil> constructor = HashUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        try {
            constructor.newInstance();
            fail("Expected UnsupportedOperationException to be thrown");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            assertTrue(cause instanceof UnsupportedOperationException);
            assertEquals("Utility class", cause.getMessage());
        }
    }

}
