<template>
    <img class="logo" src="../../assets/logo.png">
    <h1>Sign Up</h1>
    <form class="register-form">
        <div class="error" v-for="(error, index) in errors.general" :key="index">{{ error }}</div>
        <input type="text" placeholder="Username" v-model="register.username">
        <div class="error" v-for="(error, index) in errors.username" :key="index">{{ error }}</div>

        <input type="email" placeholder="Email" v-model="register.email">
        <div class="error" v-for="(error, index) in errors.email" :key="index">{{ error }}</div>

        <input type="text" placeholder="Phone Number" v-model="register.phoneNum">
        <div class="error" v-for="(error, index) in errors.phoneNum" :key="index">{{ error }}</div>

        <input type="password" placeholder="Password" v-model="register.password">
        <div class="error" v-for="(error, index) in errors.password" :key="index">{{ error }}</div>
        <button @click="singUp">Sign Up</button>
        <router-link to="/sign-in">Login</router-link>
    </form>
</template>
        

<script>
import axios from 'axios';
export default {
    name: 'SignUp',
    data() {
        return {
            register: {
                username: '',
                email: '',
                phoneNum: '',
                password: ''
            },
            errors: {
                general: [],
                username: [],
                email: [],
                phoneNum: [],
                password: []
            }
        }
    },

    methods: {
        async singUp(event) {
            event.preventDefault();
            this.cleaeErrors();
            try {
                let result = await axios.post(
                    `${this.$store.state.server}/${this.$store.state.fakeAuth}/register`, this.register)
                let data = result.data;
                localStorage.setItem('token', data);
                this.$router.push('/user-center');
            } catch (error) {
                if (error.response && error.response.data) {
                    let errors = Array.isArray(error.response.data) ? error.response.data : [error.response.data];
                    errors.forEach(err => {
                        //fking : vs ：
                        let [field, message] = err.split(': ');
                        if (!this.errors[field]) {
                            this.errors['general'].push(message);
                        }else{
                            this.errors[field].push(message);
                        }
                    });
                }
            }
        },
        cleaeErrors() {
            for (let field in this.errors) {
                this.errors[field] = [];
            }
        }
    },
    mounted() {
        if (localStorage.getItem('token')) {
            this.$router.push('/user-center');
        }
    }

}


</script>

<style>
@import './sign.css';
</style>