<template>
    <img class="logo" src="../assets/logo.png">
    <h1>Sign Up</h1>
    <form class="register-form">
        <input type="text" placeholder="Username" v-model="register.username">
        <div class="error" v-for="(error, index) in errors.username" :key="index">{{ error }}</div>

        <input type="email" placeholder="Email" v-model="register.email">
        <div class="error" v-for="(error, index) in errors.email" :key="index">{{ error }}</div>

        <input type="text" placeholder="Phone Number" v-model="register.phoneNum">
        <div class="error" v-for="(error, index) in errors.phoneNum" :key="index">{{ error }}</div>

        <input type="password" placeholder="Password" v-model="register.password">
        <div class="error" v-for="(error, index) in errors.password" :key="index">{{ error }}</div>
        <button @click="singUp">Sign Up</button>
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
            try {
                let result = await axios.post(
                    `${this.$store.state.server}/${this.$store.state.auth}/register`, this.register)
                console.log(result);
            } catch (error) {
                console.log(error.response.data);
                if (error.response && error.response.data) {
                    error.response.data.forEach(err => {
                        let [field, message] = err.split('： ');
                        this.errors[field].push(message);
                    });
                }
            }
        }
    }

}


</script>

<style>
.logo {
    width: 100px;
    height: 100px;
}

form input {
    width: 300px;
    height: 40px;
    display: block;
    margin: 20px auto;
    border: 1px solid skyblue;
    border-radius: 10px;
    text-align: center;
}

input::placeholder {
    text-align: center;
}

form button {
    width: 200px;
    height: 40px;
    display: block;
    margin: 30px auto;
    border: 1px solid skyblue;
    border-radius: 10px;
    background-color: skyblue;
    color: white;
    font-size: 20px;
    font-weight: bold;
}

.register-form {
    margin: 40px auto;
}
</style>