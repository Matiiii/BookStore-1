package pl.jstk.generator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordGenerator {
    /**
     * This method helps with generate secure password.
     */
    public void generate() {
        String password = "123";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        System.out.println(hashedPassword);

    }
}
