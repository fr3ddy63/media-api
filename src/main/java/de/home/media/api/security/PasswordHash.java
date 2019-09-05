package de.home.media.api.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Objects;

public final class PasswordHash {

    private byte[] password;
    private byte[] salt;
    private Integer iterations;
    private Integer length;

    public PasswordHash(String hashed) throws NullPointerException, IllegalArgumentException {
        String[] parts = Objects.requireNonNull(hashed, "hash must not be null").split(":");
        if (parts.length != 4) throw new IllegalArgumentException("hash has wrong format: part number");

        this.salt = Base64.getDecoder().decode(parts[0]);
        this.password = Base64.getDecoder().decode(parts[1]);
        this.iterations = Integer.valueOf(parts[2]);
        this.length = Integer.valueOf(parts[3]);
    }

    public PasswordHash(String password, Integer iterations, Integer length)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String clearPassword = Objects.requireNonNull(password, "password must not be null");
        this.iterations = iterations == null ? 1024 : iterations;
        this.length = length == null ? 128 : length;

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
        return String.format("%s:%s:%d:%d",
                Base64.getEncoder().encodeToString(this.salt),
                Base64.getEncoder().encodeToString(this.password),
                this.iterations,
                this.length);
    }
}
