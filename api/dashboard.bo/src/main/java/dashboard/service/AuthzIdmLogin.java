package dashboard.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import es.prodevelop.pui9.common.exceptions.PuiCommonAnonymousNotAllowedException;
import es.prodevelop.pui9.common.exceptions.PuiCommonIncorrectLoginException;
import es.prodevelop.pui9.common.exceptions.PuiCommonIncorrectUserPasswordException;
import es.prodevelop.pui9.common.exceptions.PuiCommonLoginMaxAttemptsException;
import es.prodevelop.pui9.common.exceptions.PuiCommonUserCredentialsExpiredException;
import es.prodevelop.pui9.common.exceptions.PuiCommonUserDisabledException;
import es.prodevelop.pui9.keycloak.dto.KeycloakLoginData;
import es.prodevelop.pui9.keycloak.login.PuiKeycloakLogin;
import es.prodevelop.pui9.login.LoginData;
import es.prodevelop.pui9.login.PuiUserInfo;
import es.prodevelop.pui9.utils.PuiExternalRequest;
import es.prodevelop.pui9.utils.PuiExternalRequestConfig;

@Service
public class AuthzIdmLogin extends PuiKeycloakLogin {

	@Override
	public PuiUserInfo loginUser(KeycloakLoginData loginData) throws PuiCommonIncorrectUserPasswordException,
		PuiCommonIncorrectLoginException, PuiCommonAnonymousNotAllowedException, PuiCommonUserDisabledException,
		PuiCommonLoginMaxAttemptsException, PuiCommonUserCredentialsExpiredException {

		PuiUserInfo user = super.loginUser(loginData);
		getPermit(user);
		return user;
	}

	public String getTokenFromIdm(LoginData loginData) throws PuiCommonIncorrectLoginException {
		String response;
		String token = "";

		String url = variableService.getVariable("AUTHZ_IDM_URL_GET_TOKEN");
		String clientId = variableService.getVariable("AUTHZ_IDM_CLIENT_ID");
		String clientSecret = variableService.getVariable("KEYCLOAK_TOKEN");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", clientId);
		params.add("username", loginData.getUsr());
		params.add("password", loginData.getPassword());
		params.add("grant_type", "password");
		params.add("client_secret", clientSecret);

		PuiExternalRequestConfig config = PuiExternalRequestConfig.builder()
			.withUrl(url)
			.withHeaders(headers)
			.withBody(params)
			.withReturnClass(String.class);

		try {
			response =  PuiExternalRequest.getSingleton().executePost(config);

			JsonElement jsonElement = JsonParser.parseString(response);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			token = jsonObject.get("access_token").getAsString();

		} catch (Exception e) {
			throw new PuiCommonIncorrectLoginException(new Exception("Idm error to validate user or to get token", e.getCause()));
		}

		return token;
	}

	private boolean getPermit(PuiUserInfo user) throws PuiCommonIncorrectLoginException {
		String response;
		String valuation;

		String url = variableService.getVariable("AUTHZ_SERVER_URL_VALIDATE");
		String clientId = variableService.getVariable("AUTHZ_IDM_CLIENT_ID");
		String securityDomain = variableService.getVariable("AUTHZ_SERVER_SECURITY_DOMAIN");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("resource", securityDomain + "@" + clientId);
		params.add("code", user.getUsr());
		params.add("action", "login");

		PuiExternalRequestConfig config = PuiExternalRequestConfig.builder()
			.withUrl(url)
			.withHeaders(headers)
			.withParameters(params)
			.withReturnClass(String.class);
		try {
			response =  PuiExternalRequest.getSingleton().executeGet(config);

			JsonElement jsonElement = JsonParser.parseString(response);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			valuation = jsonObject.get("response").getAsString();

			if (valuation.equals("Permit"))  {
				return true;
			}

		} catch (Exception e) {
			throw new PuiCommonIncorrectLoginException(new Exception("AutzServer Error to validate permit", e.getCause()));
		}

		throw new PuiCommonIncorrectLoginException(new Exception("AutzServer deny"));
	}
}
