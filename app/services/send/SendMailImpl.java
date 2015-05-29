package services.send;

import models.MailModel;
import models.exceptions.ESDocumentNotFound;
import models.search.PathModel;
import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;
import services.configuration.ConfigurationService;
import services.configuration.ConfigurationServiceException;
import services.search.ESSearchService;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SendMailImpl implements SendMailService {
    
    private final ConfigurationService configurationService;
    private final ESSearchService esSearchService;
    
    @Inject
    private SendMailImpl(ConfigurationService configurationService, ESSearchService esSearchService) {
        this.configurationService = configurationService;
        this.esSearchService = esSearchService;
    }
    
    @Override
    public void send(MailModel mailModel) 
            throws ConfigurationServiceException, ESDocumentNotFound {
        
        PathModel pathModel = esSearchService.searchFileById(mailModel.getId());
        
        Email email = new Email();
        email.setSubject(mailModel.getSubject());
        email.setFrom(configurationService.get("smtp.from"));
        email.addTo(mailModel.getTo());
        if (mailModel.isBccEnable() && !Strings.isNullOrEmpty(mailModel.getBcc())) {
            email.addBcc(mailModel.getBcc());
        }
        email.addAttachment(
                pathModel.getFilename().get(),
                pathModel.getPath().get().toFile());
        
        email.setBodyText(mailModel.getBody());
        MailerPlugin.send(email);
    }
    
}
