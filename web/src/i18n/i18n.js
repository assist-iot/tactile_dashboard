import Vue from 'vue';
import VueI18n from 'vue-i18n';

import enPui from 'pui9-translations/translations/en.json';
import esPui from 'pui9-translations/translations/es.json';

import enApp from './en.json';
import esApp from './es.json';

const en = { ...enPui, ...enApp };
const es = { ...esPui, ...esApp };

Vue.use(VueI18n);

export default new VueI18n({
	locale: 'es',
	fallbackLocale: 'es',
	silentFallbackWarn: true,
	messages: { en: en, es: es }
});
