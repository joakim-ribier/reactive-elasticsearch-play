package services.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import play.api.Play;
import scala.Option;
import services.configuration.ConfigurationService;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class I18nImpl implements I18nService {
    
    
    private final ConfigurationService configurationService;
    
    @Inject
    public I18nImpl(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }
    
    @Override
    public Map<String, String> get(Locale locale) throws I18nException {
        try {
            Option<InputStream> resourceAsStream = getInputStream(locale);
            if (resourceAsStream.isEmpty()) {
                return Maps.newHashMap();
            }
            ResourceBundle resourceBundle = new PropertyResourceBundle(resourceAsStream.get());
            Enumeration<String> enumeration = resourceBundle.getKeys();
            Map<String, String> map = Maps.newHashMap();
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = resourceBundle.getString(key);
                map.put(key, value);
            }
            return map;
        } catch (IOException e) {
            throw new I18nException(e.getMessage(), e);
        }
    }
    
    private Option<InputStream> getInputStream(Locale locale) {
        String resource = configurationService.getI18nDirectory();
        String filename = resource + "/Messages_" + locale.getLanguage() + ".properties";
        Option<InputStream> resourceAsStream = Play.current().resourceAsStream(filename);
        return resourceAsStream;
    }
    
}
