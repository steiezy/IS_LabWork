<template lang="">
    <div>
        <el-main class="user-info">
           
            <el-descriptions title="User Infomation" :column="1" class="info-table" border>
                <el-descriptions-item
                label="Username"
                label-align="left"
                align="center"
                label-class-name="my-label"
                class-name="my-content"
                width="250px"
                >{{userInfo.username}}</el-descriptions-item
                >
                <el-descriptions-item
                label="Phone Number"
                label-align="left"
                align="center"
                label-class-name="my-label"
                class-name="my-content"
                width="250px"
                >{{userInfo.phoneNum}}</el-descriptions-item
                >
                <el-descriptions-item
                label="Email"
                label-align="left"
                align="center"
                label-class-name="my-label"
                class-name="my-content"
                width="250px"
                >{{userInfo.email}}</el-descriptions-item
                >
                <el-descriptions-item
                label="Role"
                label-align="left"
                align="center"
                label-class-name="my-label"
                class-name="my-content"
                width="250px"
                >{{userInfo.role}}</el-descriptions-item
                >

            </el-descriptions>  

           

        </el-main>
    </div>
</template>
<script>
import axios from 'axios';
import "./userCenter.css";
export default {
    data(){
        return {
            // userInfo: {
            //         username: '',
            //         email: '',
            // },
            userInfo:[]
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