package services.i18n;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;

@Singleton
public class I18nImpl implements I18nService {

    @Override
    public Map<String, String> get(Locale locale) throws I18nException {
        try {
            String fileName = "conf/messages." + locale.getLanguage();
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ResourceBundle resourceBundle = new PropertyResourceBundle(fileInputStream);
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
    
}
