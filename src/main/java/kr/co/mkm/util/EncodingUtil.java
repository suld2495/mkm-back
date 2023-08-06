package kr.co.mkm.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodingUtil {
    public EncodingUtil() {
    }

    public static String encode(String string) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
        return passwordEncoding.encode(string);
    }
}