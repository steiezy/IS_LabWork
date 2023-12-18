<template lang="">
    <div>
        <el-aside width="200px">

            <el-menu
                style="width:100%; height:100vh"
                active-text-color="#ffd04b"
                default-active="user-info"
                background-color="#545c64"
                class="el-menu-vertical-demo"
            
                text-color="#fff"
                @open="handleOpen"
                @close="handleClose"
                router
            >
                <el-menu-item index="user-info">
                <el-icon><icon-menu /></el-icon>
                <span>Personal Information</span>
                </el-menu-item>
                <el-menu-item index="add-house">
                <el-icon><icon-menu /></el-icon>
                <span>add your first house</span>
                </el-menu-item>
                <el-menu-item index="reset-password">
                <el-icon><icon-menu /></el-icon>
                <span>reset password</span>
                </el-menu-item>
                <el-menu-item index="logout">
                <el-icon><icon-menu /></el-icon>
                <span>logout</span>
                </el-menu-item>
            
            </el-menu>
       
        </el-aside>
    </div>
   
</template>
<script>
export default {
    
}
</script>
<style lang="">
    
</style>