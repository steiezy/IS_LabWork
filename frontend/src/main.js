import { createApp } from 'vue'
import App from './App.vue'
import store from './store'
import router from './routers'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

createApp(App).use(ElementPlus).use(store).use(router).mount('#app')
