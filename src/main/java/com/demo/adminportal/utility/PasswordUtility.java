package com.demo.adminportal.utility;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class for handling password encoding operations.
 * Provides a method to encode raw passwords using the BCrypt hashing algorithm.
 */
public class PasswordUtility {

    /**
     * Encodes a raw password using the BCrypt hashing algorithm.
     * This method creates a BCrypt hash with a strength of 8.
     *
     * @param rawPassword the raw password to be encoded.
     * @return the encoded (hashed) password.
     */
    public static String encodePassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        return encoder.encode(rawPassword);
    }
}