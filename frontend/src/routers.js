import UserCenter from './components/userCenter/UserCenter.vue';
import SignUp from './components/sign/SignUp.vue';
import SignIn from './components/sign/SignIn.vue';
import SignPage from './components/sign/SignPage.vue';
import {
    createRouter,
    createWebHistory
} from 'vue-router';


const routes = [
    {
        name: 'UserCenter',
        component: UserCenter,
        path: '/user-center'
    },
    {
        name: 'SignPage',
        component: SignPage,
        path: '/sign'
    },
    {
        name: 'SignUp',
        component: SignUp,
        path: '/sign-up'
    },
    {
        name: 'SignIn',
        component: SignIn,
        path: '/sign-in'
    },
    {
        path: '/',
        redirect: '/home'
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;