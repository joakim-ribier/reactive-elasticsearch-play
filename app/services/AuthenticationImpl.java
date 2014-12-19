package services;

import java.io.IOException;

import models.exceptions.EncodeUtilsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.IEncodeUtils;
import utils.IFileUtils;

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
        String filePath = configurationService
                .get("application.security.file.pwd");

        if (!login.contentEquals(username)) {
            return false;
        }

        try {
            String passwordFromFile = getPassword(filePath);
            String passwordEncoded = iEncodeUtils.encode(username, password);
            return passwordFromFile.contentEquals(passwordEncoded);
        } catch (IOException | EncodeUtilsException e) {
            LOG.error(e.getMessage());
            return false;
        }
    }

    protected String getPassword(String filePath) throws IOException {
        return iFileUtils.getContent(filePath);
    }
}
