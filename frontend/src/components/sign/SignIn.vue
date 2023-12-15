<template>
    <img class="logo" src="../../assets/logo.png">
    <h1>Login</h1>
    <form class="sign-form">
        <div class="error" v-for="(error, index) in errors.general" :key="index">{{ error }}</div>
        <input type="text" placeholder="Username" v-model="login.username">
        <div class="error" v-for="(error, index) in errors.username" :key="index">{{ error }}</div>
        <input type="password" placeholder="Password" v-model="login.password">
        <div class="error" v-for="(error, index) in errors.password" :key="index">{{ error }}</div>
        <button @click="Login">login</button>
    </form>
</template>
        

<script>
import axios from 'axios';
export default {
    name: 'SignIn',
    data() {
        return {
            login: {
                username: '',
                password: ''
            },
            errors: {
                general: [],
                username: [],
                password: []
            }
        }
    },

    methods: {
        async Login(event) {
            event.preventDefault();
            this.cleaeErrors();
            try {
                let result = await axios.post(
                    `${this.$store.state.server}/${this.$store.state.auth}/login`, this.login)
                    console.log(result);
                let data = result.data;
                localStorage.setItem('token', data.jwt);
                this.$router.push('/home');
            } catch (error) {
                console.log(error);
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
            this.$router.push('/home');
        }
    }

}


</script>

<style>
@import './sign.css';
</style>