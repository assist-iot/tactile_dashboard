import Vue from 'vue';
import Vuex from 'vuex';
import i18n from '@/i18n/i18n';
import * as deepmerge from 'deepmerge';
import Pui9Store from 'pui9-store';

import state from './state';
import getters from './getters';
import mutations from './mutations';
import actions from './actions';

Vue.use(Vuex);

const localStore = { state, getters, mutations, actions };
const store = deepmerge(Pui9Store, localStore);
store.state = { ...store.state, i18n };

export default new Vuex.Store(store);
