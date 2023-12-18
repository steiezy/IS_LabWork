import UserCenter from './components/userCenter/UserCenter.vue';
import SignUp from './components/sign/SignUp.vue';
import SignIn from './components/sign/SignIn.vue';
import SignPage from './components/sign/SignPage.vue';
import ListPage from './components/Homepage/homePage.vue';
import userInfo from './components/userCenter/userInfo.vue';
import addHouse from './components/userCenter/addHouse.vue';
import resetPassword from './components/userCenter/resetPassword.vue';
import {
    createRouter,
    createWebHistory
} from 'vue-router';


const routes = [
    {
        name: 'UserCenter',
        component: UserCenter,
        path: '/user-center',
        // redirect:'/user-info',
        children:[
            {
                path:'/user-info',
                name:'user-info',
                meta:{
                    title:'用户信息'
                },
                component: userInfo
            },
        {
                path:'/add-house',
                name:'add-house',
                meta:{
                    title:'新增房屋'
                },
                component: addHouse
            },
            {
                path:'/reset-password',
                name:'reset-password',
                meta:{
                    title:'重设密码'
                },
                component: resetPassword
            },

        ]
    },
    {
        name: 'ListPage',
        component: ListPage,
        path: '/listpage'
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