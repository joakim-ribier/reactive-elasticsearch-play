import services.AuthenticationImpl;
import services.AuthenticationService;
import services.ESConstantImpl;
import services.ESConstantService;
import services.ZipImpl;
import services.ZipService;
import services.configuration.ConfigurationImpl;
import services.configuration.ConfigurationService;
import services.i18n.I18nImpl;
import services.i18n.I18nService;
import services.indexation.IndexationImpl;
import services.indexation.IndexationService;
import services.search.ESSearchImpl;
import services.search.ESSearchService;
import utils.EncodeUtils;
import utils.IEncodeUtils;
import utils.eslasticsearch.ESServerEmbedded;
import utils.eslasticsearch.IESServerEmbedded;
import utils.file.FileUtils;
import utils.file.IFileUtils;
import utils.xcontent.IXContentBuilderHelper;
import utils.xcontent.XContentBuilderHelper;

import com.google.inject.AbstractModule;

import configuration.GlobalConfiguration;

public class Global extends GlobalConfiguration {

    @Override
    protected AbstractModule buildAbstractModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(IESServerEmbedded.class).to(ESServerEmbedded.class);
                bind(IEncodeUtils.class).to(EncodeUtils.class);
                bind(IFileUtils.class).to(FileUtils.class);
                bind(IXContentBuilderHelper.class).to(XContentBuilderHelper.class);
                
                bind(ESSearchService.class).to(ESSearchImpl.class);
                bind(ESConstantService.class).to(ESConstantImpl.class);
                bind(ConfigurationService.class).to(ConfigurationImpl.class);
                bind(AuthenticationService.class).to(AuthenticationImpl.class);
                bind(ZipService.class).to(ZipImpl.class);
                bind(I18nService.class).to(I18nImpl.class);
                bind(IndexationService.class).to(IndexationImpl.class);
            }
        };
    }
}
