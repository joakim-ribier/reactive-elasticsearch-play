package stub;

import services.AuthenticationImpl;
import services.configuration.ConfigurationService;
import utils.IEncodeUtils;
import utils.file.IFileUtils;

import com.google.inject.Inject;

public class StubAuthenticationImplTest extends AuthenticationImpl {
    
    @Inject
    private StubAuthenticationImplTest(
            ConfigurationService configurationService,
            IEncodeUtils iEncodeUtils, IFileUtils iFileUtils) {
        
        super(configurationService, iEncodeUtils, iFileUtils);
    }
    
    @Override
    protected String getPassword() {
        return "BiGlrUzC6WlXq/UoicJIIjvfGww=";
    }
    
}
