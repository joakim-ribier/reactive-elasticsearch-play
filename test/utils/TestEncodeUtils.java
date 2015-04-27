package utils;

import static org.fest.assertions.Assertions.assertThat;
import guice.GuiceTestRunner;
import models.exceptions.EncodeUtilsException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import services.StubConfigurationImplTest;
import services.configuration.ConfigurationService;

import com.google.inject.Inject;

@RunWith(GuiceTestRunner.class)
public class TestEncodeUtils {
    
    // Banzai !!!
    private final static String passwordToBase64 = "BiGlrUzC6WlXq/UoicJIIjvfGww=";
    
    @Inject
    private IEncodeUtils iEncodeUtils;
    
    @Inject
    private ConfigurationService configurationService;
    
    @Before
    public void before() {
        ((StubConfigurationImplTest)configurationService).setAlgorithm("SHA-1");
    }
    
    @Test
    public void testEncodingBanzaiStr() throws EncodeUtilsException {
        String salt = "admin";
        String password = "Banzai !!!";
        
        String encoded = iEncodeUtils.encode(salt, password);
        
        assertThat(encoded).isNotNull().isNotEmpty();
        assertThat(passwordToBase64).isEqualTo(encoded);
    }
    
    @Test
    public void testWithNullPassword() throws EncodeUtilsException {
        String salt = "admin";
        
        String encoded = iEncodeUtils.encode(salt, null);
        
        assertThat(encoded).isNotNull().isNotEmpty();
        assertThat(passwordToBase64).isNotEqualTo(encoded);
    }
    
    @Test(expected = EncodeUtilsException.class)
    public void testWithBadAlgorithm() throws EncodeUtilsException {
        ((StubConfigurationImplTest)configurationService).setAlgorithm("BAD");
        iEncodeUtils.encode("admin", null);
    }
    
}
