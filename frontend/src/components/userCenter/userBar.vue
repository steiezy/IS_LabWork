<template lang="">
    <div>
     
        <el-menu
                style="width:100%; height:100vh"
                active-text-color="hwb(133 41% 38%)"
                default-active="user-info"
                background-color="hwb(135 56% 26%)"
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
                <span>add a house ad</span>
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
    </div>
   
</template>

<script>
import "./userCenter.css";
export default {
    
}
</script>
<style lang="">
    
</style>