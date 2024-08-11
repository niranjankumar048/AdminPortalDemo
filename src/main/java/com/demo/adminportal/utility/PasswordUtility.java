package com.demo.adminportal.utility;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtility {

    public static String encodePassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        return encoder.encode(rawPassword);
    }
}