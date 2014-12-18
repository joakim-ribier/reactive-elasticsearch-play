package stub;

import services.AuthenticationService;
import services.ConfigurationService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import controllers.AuthenticationController;

@Singleton
public class StubAuthenticationController extends AuthenticationController {

	@Inject
	private StubAuthenticationController(
	        ConfigurationService configurationService,
	        AuthenticationService authenticationService) {

		super(configurationService, authenticationService);
	}

	@Override
	protected void clearSession() {
		// do nothing;
	}
	
	@Override
	protected void flashSuccess(String message) {
		// do nothing;
	}
}
