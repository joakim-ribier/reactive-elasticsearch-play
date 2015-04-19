package services.i18n;

import java.util.Locale;
import java.util.Map;

public interface I18nService {

    Map<String, String> get(Locale locale) throws I18nException;
}
