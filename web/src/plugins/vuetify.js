import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import colors from 'vuetify/es5/util/colors';
import es from 'vuetify/es5/locale/es';
import ca from 'vuetify/es5/locale/ca';
import en from 'vuetify/es5/locale/en';
import fr from 'vuetify/es5/locale/fr';

Vue.use(Vuetify);


const opts = {
	theme: {
		options: { customProperties: true },
		light: true,
		themes: {
			light: {
				primary: '#863B59',
				secondary: '#25245d',
				accent: colors.orange.darken2,
				error: colors.red.darken1,
			}
		}
	},
	icons: {
		iconfont: 'fa'
	},
	lang: {
		locales: { en, es, ca, fr },
		current: 'es'
	}
};

export default new Vuetify(opts);
