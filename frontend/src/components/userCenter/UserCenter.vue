<template>
    <resetPassword v-if="showModal" @close="closeModal"></resetPassword>
    <div class="container">
        <div class="sidebar">
            <!-- Add your sidebar content here -->
            <h2>Sidebar</h2>
            <p @click="addHouse">add your first house</p>
            <p @click="resetPassword">reset password</p>
            <p @click="logout">logout</p>
        </div>


        <div class="user-info">
            <h1>User Center</h1>
            <table class="info-table">
                <tr>
                    <th>Username</th>
                    <td>{{ userInfo.username }}</td>
                </tr>
                <tr>
                    <th>Phone Number</th>
                    <td>{{ userInfo.phoneNum }}</td>
                </tr>
                <tr>
                    <th>Email</th>
                    <td>{{ userInfo.email }}</td>
                </tr>
                <tr>
                    <th>Role</th>
                    <td>{{ userInfo.role }}</td>
                </tr>
            </table>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import resetPassword from './resetPassword.vue';
export default {
    name: 'UserHome',
    components: {
        resetPassword
    },
    data() {
        return {
            showModal: false,
            userInfo: {
                username: '',
                email: '',
            }
        }
    },

    methods: {
        async addHouse() {

        },
        resetPassword() {
            this.showModal = true;
        },
        closeModal() {
            this.showModal = false;
        },
        logout() {
            localStorage.removeItem('token');
            localStorage.removeItem('userId');
            this.$router.push('/sign');
            //TODO: need to notify the server
        }
    },

    async mounted() {
        if (!localStorage.getItem('token')) {
            this.$router.push('/sign');
        } else {
            let result = await axios.get(`${this.$store.state.server}/${this.$store.state.auth}/me`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`
                }
            })
            localStorage.setItem('userId', result.data.userId);
            switch (result.data.role) {
                case '0':
                    result.data.role = 'Admin';
                    break;
                case '1':
                    result.data.role = 'Landlord';
                    break;
                case '2':
                    result.data.role = 'User';
                    break;
                default:
                    break;
            }
            this.userInfo = result.data;
            delete this.userInfo.userId;
        }
    }
}
</script>

<style>
@import './userCenter.css';
</style>