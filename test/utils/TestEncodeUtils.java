package utils;

import guice.GuiceTestRunner;
import models.exceptions.EncodeUtilsException;

import static org.fest.assertions.Assertions.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(GuiceTestRunner.class)
public class TestEncodeUtils {

    // Banzai !!!
    private final static String passwordToBase64 = "BiGlrUzC6WlXq/UoicJIIjvfGww=";
    
    @Inject
    private IEncodeUtils iEncodeUtils;
    
    @Test
    public void test_encode_banzai_str() throws EncodeUtilsException {
        String salt = "admin";
        String password = "Banzai !!!";
                
        String encoded = iEncodeUtils.encode(salt, password);
        
        assertThat(encoded).isNotNull().isNotEmpty();
        assertThat(passwordToBase64).isEqualTo(encoded);
    }
    
    @Test
    public void test_encode_with_null_password() throws EncodeUtilsException {
        String salt = "admin";
                
        String encoded = iEncodeUtils.encode(salt, null);
        
        assertThat(encoded).isNotNull().isNotEmpty();
        assertThat(passwordToBase64).isNotEqualTo(encoded);
    }
}
