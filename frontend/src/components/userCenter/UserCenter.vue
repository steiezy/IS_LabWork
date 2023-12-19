<template>
<resetPassword v-if="showModal" @close="closeModal"></resetPassword>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <img class="logo" src="../../assets/logo.png">
        <el-icon><House /></el-icon>
      </el-header>
      
       
      <el-container style="display: flex; height: 100vh;">
        <el-aside width="250px"> 
          <userBar></userBar>
        </el-aside>
        <div style="width: 100%;"><router-view></router-view></div>

   
      </el-container>
    </el-container>
  </div>
</template>

<script>

import userBar from './userBar.vue';
// import userInfo from './userInfo.vue';
export default {
    name: 'UserHome',
    components: {

        userBar,



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

}
</script>

<style>
@import './userCenter.css';
</style>