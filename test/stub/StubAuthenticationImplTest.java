package stub;

import java.io.IOException;

import com.google.inject.Inject;

import services.AuthenticationImpl;
import services.ConfigurationService;
import utils.IEncodeUtils;
import utils.IFileUtils;

public class StubAuthenticationImplTest extends AuthenticationImpl {
    
    @Inject
    private StubAuthenticationImplTest(
            ConfigurationService configurationService,
            IEncodeUtils iEncodeUtils, IFileUtils iFileUtils) {
        
        super(configurationService, iEncodeUtils, iFileUtils);
    }
    
    @Override
    protected String getPassword() throws IOException {
        return "BiGlrUzC6WlXq/UoicJIIjvfGww=";
    }
    
}
