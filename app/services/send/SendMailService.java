package services.send;

import models.MailModel;
import models.exceptions.ESDocumentNotFound;
import services.configuration.ConfigurationServiceException;


public interface SendMailService {

    void send(MailModel mailModel) throws ConfigurationServiceException, ESDocumentNotFound;
    
}
