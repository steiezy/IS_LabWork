<template>
    <div>
        <form class="resetForm">
        <div >
            <label for="password">Password:</label>
            <input id="password" v-model="password" type="password" required>
        </div>
        <div>
            <label for="confirmPassword">Confirm Password:</label>
            <input id="confirmPassword" v-model="confirmPassword" type="password" required>
        </div>
        <div>
            <button type="submit" @click="submit()">Submit</button>
            
        </div>
    </form>
    </div>
</template>

<script>

import axios from 'axios';
import "./userCenter.css"
export default {
    name: 'ResetPassword',
    data() {
        return {
            password: '',
            confirmPassword: '',
            errors: []
        };
    },
    methods: {
        submit() {
            if (this.password !== this.confirmPassword) {
                alert('The two passwords do not match.');
                return;
            }

            // Send a request to update the password.
            let id = localStorage.getItem("userId")
            axios.post(`${this.$store.state.server}/${this.$store.state.user}/${id}/password`,
                {
                    password: this.password
                }, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`
                }
            }).then(() => {
                alert('Password updated successfully.');
                this.$emit('close');
            }).catch(error => {
                if (error.response && error.response.data) {
                    let errors = Array.isArray(error.response.data) ? error.response.data : [error.response.data];
                    errors.forEach(err => {
                        this.errors.push(err);
                    });
                }
                alert('Failed to update password: ' + this.errors);
            });
        },

    },
}
</script>

