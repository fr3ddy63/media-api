package de.home.media.api.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Objects;

public final class PasswordInfo {

    private byte[] password;
    private byte[] salt;
    private Integer iterations;
    private Integer length;

    public PasswordInfo(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        this(password, 1000, 128);
    }

    public PasswordInfo(String password, Integer iterations, Integer length)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String clearPassword = Objects.requireNonNull(password, "password must not be null");
        this.iterations = Objects.requireNonNull(iterations, "iterations must not be null");
        this.length = Objects.requireNonNull(length, "length must not be null");

        this.salt = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(this.salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, this.iterations, this.length);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        this.password = factory.generateSecret(spec).getEncoded();
    }

    public byte[] getPassword() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public Integer getIterations() {
        return iterations;
    }

    public Integer getLength() {
        return length;
    }

    public String createDatabasePassword() {
        return String.format("%s:%s:%d",
                Arrays.toString(this.salt), Arrays.toString(this.password), this.iterations, this.length);
    }
}
