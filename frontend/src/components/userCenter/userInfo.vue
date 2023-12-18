<template lang="">
    <div>
        <el-main class="user-info"><h1>User Center</h1>
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
        </el-main>
    </div>
</template>
<script>
import axios from 'axios';
import "./userCenter.css";
export default {
    data(){
        return {
            userInfo: {
                    username: '',
                    email: '',
            }
        }

    },

    methods: {

    },
    async mounted() {
        if (localStorage.getItem('token')) {
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
<style lang="">
    
</style>