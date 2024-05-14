import Pui9HttpRequests from 'pui9-requests';
import store from './store/store';
import puiEventsBus from './bus';
import i18n from './i18n/i18n';

export default async function beforeRun() {
	var session = store.getters.getSession;
	var userLogged = JSON.parse(window.localStorage.getItem(session.keepSessionAliveInfoKey));
	// Si no queremos login de pui, y además no hay un usuario en sesión...
	if (!store.getters.withLogin && !userLogged) {
		// si además no está el usuario anonimo, hacemos el login
		if (!window.localStorage.getItem('anonymous_session')) {
			const loginResponse = await doAnonymousLogin();
			try {
				const loginInfo = loginResponse.data;
				if (loginInfo) {
					setLoginInfo(loginInfo);
					puiEventsBus.$emit('onPuiLoginSignin', loginInfo);
					// save token
					window.localStorage.setItem('anonymous_session', JSON.stringify(loginInfo));
				}
			} catch (error) {
				console.log(error);
			}
		} else {
			const userAnonymous = JSON.parse(window.localStorage.getItem('anonymous_session'));
			// Si está el usuario anonimo, lo metemos en el state
			this.setLoginInfo(userAnonymous);
			puiEventsBus.$emit('onPuiLoginSignin', userAnonymous);
			store.commit('setAnonymousToken', userAnonymous.jwt);
		}
	} else if (userLogged) {
		const pui9Lang = window.localStorage.getItem('pui9_lang');
		if (pui9Lang && pui9Lang !== userLogged.language) {
			userLogged.language = pui9Lang;
			window.localStorage.setItem(session.keepSessionAliveInfoKey, JSON.stringify(userLogged));
		}
		setLoginInfo(userLogged);
		puiEventsBus.$emit('onPuiLoginSignin', userLogged);
		store.commit('setAnonymousToken', userLogged.jwt);
	}
}

async function doAnonymousLogin(succes, error) {
	const loginConfig = store.getters.getLoginConfig;
	const params = {
		usr: loginConfig.anonymousUser,
		password: loginConfig.anonymousPass,
		client: store.getters.clientName,
		persistent: true
	};
	const response = await Pui9HttpRequests.postRequest('/login/signin', params, succes, error);
	return response;
}

function setLoginInfo(loginInfo) {
	//first commit the login info, the language will be set applying some logic
	store.commit('puiLoginSetInfo', loginInfo);
	//then we use the language saved, not the recieved
	const lang = store.getters.getUserLanguage;

	i18n.locale = lang;
	window.localStorage.setItem('pui9_lang', lang);
	store.commit('setUserLanguage', lang);
	store.commit('setHttpRequestHeaders', { authorization: loginInfo.jwt, acceptLanguage: lang });
}
