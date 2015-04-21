package services;

import java.io.IOException;

import models.exceptions.AuthenticationServiceException;
import models.exceptions.EncodeUtilsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.IEncodeUtils;
import utils.IFileUtils;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AuthenticationImpl implements AuthenticationService {
    
    private static final Logger LOG = LoggerFactory
            .getLogger(AuthenticationService.class);
    
    private final ConfigurationService configurationService;
    private final IEncodeUtils iEncodeUtils;
    private final IFileUtils iFileUtils;
    
    @Inject
    protected AuthenticationImpl(ConfigurationService configurationService,
            IEncodeUtils iEncodeUtils, IFileUtils iFileUtils) {
        
        this.configurationService = configurationService;
        this.iEncodeUtils = iEncodeUtils;
        this.iFileUtils = iFileUtils;
    }
    
    @Override
    public boolean connect(String username, String password) {
        String login = configurationService.get("application.security.login");
        if (!login.equals(username)) {
            return false;
        }
        try {
            String passwordFromFile = getPassword();
            String passwordEncoded = iEncodeUtils.encode(username, password);
            return passwordFromFile.equals(passwordEncoded);
        } catch (IOException | EncodeUtilsException e) {
            LOG.error(e.getMessage());
            return false;
        }
    }
    
    private String getSecurityFilePath() {
        return configurationService.get("application.security.file.pwd");
    }
    
    @VisibleForTesting
    protected String getPassword() throws IOException {
        String filePath = getSecurityFilePath();
        return iFileUtils.getContent(filePath);
    }
    
    @Override
    public boolean firstConnection(String username, String password, String password2)
            throws AuthenticationServiceException {
        
        String login = configurationService.get("application.security.login");
        if ((Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(password2)) 
                || !login.equals(username) 
                || !password.equals(password2)) {
            
            return false;
        }
        
        try {
            if (!Strings.isNullOrEmpty(getPassword())) {
                return false;
            }
            String encodedPassword = iEncodeUtils.encode(username, password);
            iFileUtils.write(getSecurityFilePath(), encodedPassword);
            return true;
        } catch (EncodeUtilsException | IOException e) {
            LOG.error(e.getMessage());
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }
    
}
