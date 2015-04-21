package services;

import models.exceptions.AuthenticationServiceException;

public interface AuthenticationService {
    
    boolean connect(String username, String password);

    boolean firstConnection(String username, String password, String password2)
            throws AuthenticationServiceException;
    
}
