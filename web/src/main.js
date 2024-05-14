import Vue from 'vue';

import Pui9Login from 'pui9-login';
import Pui9LoginCheckerMixinFactory from 'pui9-login/src/mixins/PuiLoginCheckerMixin';
import Pui9Menu from 'pui9-menu';
import Pui9Notify from 'pui9-notifications';
import Pui9HttpRequests from 'pui9-requests';
import Pui9Datatables from 'pui9-datatables';
import Pui9Components from 'pui9-components';
import Pui9Base from 'pui9-base';
import Pui9Admin from 'pui9-admin';
import Pui9Dashboard from 'pui9-dashboard';

import store from './store/store';
import router from './router/router';
import i18n from './i18n/i18n';
import vuetify from './plugins/vuetify';
import puiEventsBus from './bus';
import beforeRun from './beforeRun';

import App from './App.vue';

import '@fortawesome/fontawesome-pro/css/all.css';

import 'pui9-styles/pui9.css';
import 'pui9-components/dist/pui9-components.css';
import 'pui9-login/dist/pui9-login.css';
import 'pui9-menu/dist/pui9-menu.css';
import 'pui9-datatables/dist/pui9-datatables.css';
import 'pui9-base/dist/pui9-base.css';

import './styles/app.css';

Vue.config.productionTip = false;

function defineGlobalPlugins() {
	Vue.use(Pui9Login);
	Vue.use(Pui9Menu);
	Vue.use(Pui9Notify);
	Vue.use(Pui9Datatables);
	Vue.use(Pui9Components);
	Vue.use(Pui9Base);
	Vue.use(Pui9Admin);
	Vue.use(Pui9Dashboard);

	Object.defineProperty(Vue.prototype, '$puiRequests', { value: Pui9HttpRequests });
	Object.defineProperty(Vue.prototype, '$puiEvents', { value: puiEventsBus });
	Object.defineProperty(Vue.prototype, '$puiI18n', { value: i18n });
}

function defineGlobalMixins() {
	Vue.mixin(Pui9LoginCheckerMixinFactory(store, Pui9HttpRequests));
}

function storeTimezoneHeader() {
	const timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
	store.commit('setHttpRequestHeaders', { timezone });
}

function setLanguage() {
	i18n.locale = window.localStorage.getItem('pui9_lang') || store.state.global.defaultAppLang;
}

(async () => {
	puiEventsBus.$on('onPuiChangedLang', (lang) => {
		i18n.locale = lang;
	});

	Pui9HttpRequests.setStore(store);

	defineGlobalPlugins();

	storeTimezoneHeader();
	setLanguage();

	await beforeRun();

	const vm = new Vue({
		vuetify,
		store,
		router,
		i18n,
		render: (h) => h(App)
	});

	defineGlobalMixins();

	store.$app = vm;

	vm.$mount('#app');
})();
