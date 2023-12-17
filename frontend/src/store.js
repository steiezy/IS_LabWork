import { createStore } from 'vuex'

// Create a new store instance.
const store = createStore({
    state () {
        return {
            server: 'http://192.168.31.158:8090',
            auth: 'api/v1/auth',
            fakeAuth:'open/v1/auth',
            lease: 'api/v1/lease',
        }
    }
})

export default store;
