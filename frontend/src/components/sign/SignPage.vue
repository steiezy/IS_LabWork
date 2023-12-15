<template>
    <img class="logo" src="../../assets/logo.png">
    <div class="center-div">
        <button @click="register">register</button>
        <button @click="login">login</button>
    </div>
</template>

<script>
export default {
    methods: {
        register() {
            this.$router.push('/sign-up');
        },
        login() {
            this.$router.push('/sign-in');
        },
    },
    mounted() {
        if (localStorage.getItem('token')) {
            this.$router.push('/home');
        }
    }
};
</script>

<style>
@import './sign.css';
</style>
