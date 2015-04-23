package services;

import java.nio.file.Path;

import models.exceptions.AuthenticationServiceException;
import models.exceptions.EncodeUtilsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import services.configuration.ConfigurationService;
import services.configuration.ConfigurationServiceException;
import utils.IEncodeUtils;
import utils.file.FileUtilsException;
import utils.file.IFileUtils;

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
        try {
            String login = configurationService.getAuthLogin();
            if (!login.equals(username)) {
                return false;
            }
            
            String passwordFromFile = getPassword();
            String passwordEncoded = iEncodeUtils.encode(username, password);
            return passwordFromFile.equals(passwordEncoded);
        } catch (EncodeUtilsException | ConfigurationServiceException | FileUtilsException e) {
            LOG.error(e.getMessage());
            return false;
        }
    }
    
    @VisibleForTesting
    protected String getPassword() throws ConfigurationServiceException, FileUtilsException {
        Path path = configurationService.getAuthPasswordPath();
        return iFileUtils.getContent(path);
    }
    
    @Override
    public boolean firstConnection(String username, String password, String password2)
            throws AuthenticationServiceException {
        
        try {
            String login = configurationService.getAuthLogin();
            if ((Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(password2)) 
                    || !login.equals(username) 
                    || !password.equals(password2)) {
                
                return false;
            }
            
            if (!Strings.isNullOrEmpty(getPassword())) {
                return false;
            }
            String encodedPassword = iEncodeUtils.encode(username, password);
            iFileUtils.write(configurationService.getAuthPasswordPath(), encodedPassword);
            return true;
        } catch (EncodeUtilsException | FileUtilsException | ConfigurationServiceException e) {
            LOG.error(e.getMessage());
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }
    
}
