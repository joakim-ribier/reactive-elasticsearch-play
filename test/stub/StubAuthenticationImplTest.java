package stub;

import services.AuthenticationService;

public class StubAuthenticationImplTest implements AuthenticationService {

    @Override
    public boolean connect(String username, String password) {
        return username.equals("admin");
    }
}
