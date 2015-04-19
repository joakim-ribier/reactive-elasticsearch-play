package services;

import static org.fest.assertions.Assertions.assertThat;
import guice.GuiceTestRunner;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;

import services.i18n.I18nException;
import services.i18n.I18nService;

import com.google.inject.Inject;

@RunWith(GuiceTestRunner.class)
public class TestI18nServiceImpl {

    @Inject
    private I18nService i18nService;
    
    @Test
    public void testGetPropertiesDependsOnTheLocale() throws I18nException {
        assertThat(
                i18nService.get(new Locale("fr"))).isNotEmpty();
    }
    
    @Test(expected = I18nException.class)
    public void testGetWithLocaleNotImplemented() throws I18nException {
        i18nService.get(new Locale("ES"));
    }
}
