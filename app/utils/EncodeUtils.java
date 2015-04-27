package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import models.exceptions.EncodeUtilsException;

import org.apache.commons.codec.binary.Base64;

import services.configuration.ConfigurationService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class EncodeUtils implements IEncodeUtils {
    
    
    private ConfigurationService configurationService;
    
    @Inject
    public EncodeUtils(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }
    
    @Override
    public String encode(String salt, String value) throws EncodeUtilsException {
        try {
            String str = salt + value;
            MessageDigest messageDigest = MessageDigest.getInstance(
                    configurationService.getAlgorithm());
            
            byte[] digest = messageDigest.digest(str.getBytes());
            return Base64.encodeBase64String(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new EncodeUtilsException(e.getMessage(), e);
        }
    }
    
}
