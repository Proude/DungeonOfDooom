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
 * An implementation of IAuthenticationService.
 */
public class AuthenticationService implements IAuthenticationService {

    IPlayerRepository repository;

    public AuthenticationService(IPlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean Register(LoginModel model) throws SQLException {
        boolean result = false;
        Player player = player = model.asPlayer();
        Player repositoryPlayer = repository.get(player);

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

    @Override
    public boolean Login(LoginModel model) {
        try {
            Player player = repository.get(model.asPlayer());
            if (hashAndSalt(model.getPassword(), player.getSalt()).equals(player.getHashedPassword())) {
                return true;
            } else {
                return false;
            }
        }
        catch(Exception e) {
            return false;
        }
    }

    private void generateSalt(Player player) throws NoSuchAlgorithmException {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(32);
        player.setSalt(salt);
    }

    private String hashAndSalt(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String hashedPassword = hash(password, salt);
        return Base64.encodeBase64String(salt) + hashedPassword;
    }

    private String hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, 20*1000, 256)
        );
        return Base64.encodeBase64String(key.getEncoded());
    }
}
