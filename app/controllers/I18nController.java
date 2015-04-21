package controllers;

import java.util.Locale;
import java.util.Map;

import play.mvc.Result;
import services.ConfigurationService;
import services.i18n.I18nException;
import services.i18n.I18nService;
import utils.json.JsonHelper;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class I18nController extends AbstractController {
    
    private final I18nService i18nService;
    
    @Inject
    protected I18nController(ConfigurationService configurationService, I18nService i18nService) {
        super(configurationService);
        this.i18nService = i18nService;
    }
    
    public Result index(String lang) throws I18nException {
        Map<String, String> map = i18nService.get(new Locale(lang));
        return ok(JsonHelper.toObjectNode(map));
    }
    
}
