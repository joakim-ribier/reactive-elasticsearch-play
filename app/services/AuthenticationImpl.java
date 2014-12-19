package services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import models.exceptions.EncodeUtilsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.IEncodeUtils;

import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AuthenticationImpl implements AuthenticationService {

    private static final Logger LOG = LoggerFactory
            .getLogger(AuthenticationService.class);

    private final ConfigurationService configurationService;
    private final IEncodeUtils iEncodeUtils;

    @Inject
    private AuthenticationImpl(ConfigurationService configurationService,
            IEncodeUtils iEncodeUtils) {
        
        this.configurationService = configurationService;
        this.iEncodeUtils = iEncodeUtils;
    }

    @Override
    public boolean connect(String username, String password) {
        String login = configurationService.get("application.security.login");
        String pathFileToFindPassword = configurationService
                .get("application.security.file.pwd");

        if (!login.contentEquals(username)) {
            return false;
        }

        File file = new File(pathFileToFindPassword);
        try {
            String passwordFromFile = Files.toString(file,
                    Charset.defaultCharset());
            
            String passwordEncoded = iEncodeUtils.encode(username, password);
            return passwordFromFile.contentEquals(passwordEncoded);
        } catch (IOException | EncodeUtilsException e) {
            LOG.error(e.getMessage());
            return false;
        }
    }
}
