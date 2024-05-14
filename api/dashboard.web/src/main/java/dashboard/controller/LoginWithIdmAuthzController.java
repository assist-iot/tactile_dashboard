package dashboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import dashboard.service.AuthzIdmLogin;
import es.prodevelop.pui9.annotations.PuiNoSessionRequired;
import es.prodevelop.pui9.common.exceptions.PuiCommonAnonymousNotAllowedException;
import es.prodevelop.pui9.common.exceptions.PuiCommonIncorrectLoginException;
import es.prodevelop.pui9.common.exceptions.PuiCommonIncorrectUserPasswordException;
import es.prodevelop.pui9.common.exceptions.PuiCommonLoginMaxAttemptsException;
import es.prodevelop.pui9.common.exceptions.PuiCommonUserCredentialsExpiredException;
import es.prodevelop.pui9.common.exceptions.PuiCommonUserDisabledException;
import es.prodevelop.pui9.controller.AbstractPuiController;
import es.prodevelop.pui9.enums.Pui9KnownClients;
import es.prodevelop.pui9.keycloak.dto.KeycloakLoginData;
import es.prodevelop.pui9.login.LoginData;
import es.prodevelop.pui9.login.PuiUserInfo;
import es.prodevelop.pui9.utils.PuiConstants;
import es.prodevelop.pui9.utils.PuiRequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

@Controller
@PuiNoSessionRequired
@RequestMapping("/loginAutzIdm")
public class LoginWithIdmAuthzController extends AbstractPuiController { 

	@Autowired
	AuthzIdmLogin authzIdmLogin;

	@PuiNoSessionRequired
	@Operation(summary = "Login into the application", description = "Login into the application using the given credentials")
	@PostMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
	public PuiUserInfo signin(HttpServletRequest request, @Parameter(required = true) @RequestBody LoginData loginData,
			@Parameter(in = ParameterIn.HEADER, hidden = true) @RequestHeader(value = HttpHeaders.USER_AGENT) String userAgent,
			@Parameter(in = ParameterIn.HEADER, hidden = true) @RequestHeader(value = PuiConstants.HEADER_TIMEZONE) String timezone,
			@Parameter(hidden = true) @RequestHeader HttpHeaders headers)
			throws PuiCommonIncorrectLoginException, PuiCommonIncorrectUserPasswordException,
			PuiCommonUserDisabledException, PuiCommonAnonymousNotAllowedException, PuiCommonLoginMaxAttemptsException,
			PuiCommonUserCredentialsExpiredException {

			return authzIdmLogin.loginUser((KeycloakLoginData) KeycloakLoginData.builder()
				.withJwt(authzIdmLogin.getTokenFromIdm(loginData))
				.withClient(Pui9KnownClients.KEYCLOCK_CLIENT.name())
				.withIp(PuiRequestUtils.extractIp(request))
				.withTimezone(timezone)
				.withUserAgent(userAgent)
				.withHeaders(headers));
	} 
}
