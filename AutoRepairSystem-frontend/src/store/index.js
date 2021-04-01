import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        activeUserName: ''
    },
    mutations: {
        setActiveUserName(state, payload) {
            state.activeUser = payload
        }
    },
    actions: {
        setActiveUserName({ commit }, payload) {
            commit('setActiveUserName', payload)
        }
    },
    modules: {},
    getters: {
        getActiveUserName(state) {
            return state.activeUserName
        }
    }
})

