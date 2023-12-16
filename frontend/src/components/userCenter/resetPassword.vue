<template>
    <div class="modal">
        <div class="modal-content">
            <span class="close" @click="$emit('close')">&times;</span>
            <p>Reset Password</p>
            <form @submit.prevent="submit">
                <input type="password" v-model="password" placeholder="New Password" required>
                <input type="password" v-model="confirmPassword" placeholder="Confirm New Password" required>
                <button type="submit">Submit</button>
            </form>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
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

