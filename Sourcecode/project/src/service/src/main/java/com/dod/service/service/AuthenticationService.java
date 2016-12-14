package com.dod.service.service;

import com.dod.db.repositories.IPlayerRepository;
import com.dod.models.Player;
import com.dod.service.model.LoginModel;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

/**
 * <pre>
 *     Handles authenticating a user against their user/pass combo
 *     Uses a salt, generated using a secure RNG
 *     Uses PlayerRepository to fetch Player database details
 * </pre>
 */
public class AuthenticationService implements IAuthenticationService {

    IPlayerRepository repository;

    public AuthenticationService(IPlayerRepository repository) {
        this.repository = repository;
    }

    /**
     * Registers a new user
     * @param model LoginModel containing the user/pass to be registered
     * @return boolean true if successful otherwise false
     */
    @Override
    public boolean Register(LoginModel model) {
        boolean result = false;
        Player player = player = model.asPlayer();
        Player repositoryPlayer = null;

        try {
            repositoryPlayer = repository.get(player);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        if(repositoryPlayer == null) {
            try {
                generateSalt(player);
                player.setHashedPassword(hashAndSalt(model.getPassword(), player.getSalt()));
                repository.insert(player);
                result = true;
            } catch (Exception e) {
                result = false;
            }
        }

        return result;
    }

    /**
     * Registers a new user
     * @param model LoginModel containing the user/pass to be authorised
     * @return boolean true if the user is authorised, otherwise false
     */
    @Override
    public boolean Login(LoginModel model) {
        boolean result = false;

        try {
            Player player = repository.get(model.asPlayer());
            if (hashAndSalt(model.getPassword(), player.getSalt()).equals(player.getHashedPassword())) {
                result = true;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Generates a random secure salt
     * @param player Player to set the salt for- gets inserted into the database later
     * @throws NoSuchAlgorithmException could be thrown due to a dependency problem
     */
    private void generateSalt(Player player) throws NoSuchAlgorithmException {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(32);
        player.setSalt(salt);
    }

    /**
     * Hashes and salts a password
     * @param password the password to be hashed/salted
     * @param salt the salt to salt the password with
     * @return String the hashed/salted password
     * @throws NoSuchAlgorithmException could be thrown due to a dependency problem
     * @throws InvalidKeySpecException could be thrown due to a dependency problem
     */
    private String hashAndSalt(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String hashedPassword = hash(password, salt);
        return Base64.encodeBase64String(salt) + hashedPassword;
    }

    /**
     * Hashes a password
     * @param password the password to be hashed
     * @param salt the salt to secure the password with
     * @return String the hashed password
     * @throws NoSuchAlgorithmException could be thrown due to a dependency problem
     * @throws InvalidKeySpecException could be thrown due to a dependency problem
     */
    private String hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, 20*1000, 256)
        );
        return Base64.encodeBase64String(key.getEncoded());
    }
}
