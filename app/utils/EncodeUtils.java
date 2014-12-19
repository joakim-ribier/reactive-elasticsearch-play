package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import models.exceptions.EncodeUtilsException;

import com.google.inject.Singleton;

@Singleton
public class EncodeUtils implements IEncodeUtils {

    public EncodeUtils() {
    }

    @Override
    public String encode(String salt, String value) throws EncodeUtilsException {
        try {
            String str = salt + value;
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] digest = messageDigest.digest(str.getBytes());
            return Base64.encodeBase64String(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new EncodeUtilsException(e.getMessage(), e);
        }
    }
}
