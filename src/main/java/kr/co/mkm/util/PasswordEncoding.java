package kr.co.mkm.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoding implements PasswordEncoder {
    private PasswordEncoder passwordEncoder;

    public PasswordEncoding() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public PasswordEncoding(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(CharSequence rawPassword) {
        return this.passwordEncoder.encode(rawPassword);
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
